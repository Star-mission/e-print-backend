package com.eprint.service;

import com.eprint.entity.ExternalToken;
import com.eprint.repository.mysql.ExternalTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalTokenService {

    /**
     * 外部系统 token 刷新与持久化。
     *
     * 当前默认对接「黑湖（Blacklake）」：
     * - baseUrl 默认 https://liteweb.blacklake.cn
     * - 登录接口默认 /api/user/v1/users/_login
     *
     * 启动时会自动刷新一次（见 {@link ExternalTokenRefreshRunner}），随后按配置定时刷新（见 {@link ExternalTokenScheduler}）。
     * 成功获取到的 token 会写入 MySQL 表 external_tokens（provider 维度保存），供后续调用黑湖相关接口复用。
     */
    private final ExternalTokenRepository externalTokenRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${external.token.provider:blacklake}")
    private String provider;

    @Value("${external.token.base-url:https://liteweb.blacklake.cn}")
    private String baseUrl;

    @Value("${external.token.login-path:/api/user/v1/users/_login}")
    private String loginPath;

    @Value("${external.token.client-header-name:X-CLIENT}")
    private String clientHeaderName;

    @Value("${external.token.client-header-value:lite-web}")
    private String clientHeaderValue;

    /**
     * 黑湖登录手机号/密码：
     * - 推荐通过环境变量 BLACKLAKE_PHONE / BLACKLAKE_PASSWORD 注入（见 application-*.yml）
     * - 这两个值缺失时会导致本次 token 刷新失败（但不会阻止服务启动）
     */
    @Value("${external.token.phone:}")
    private String phone;

    @Value("${external.token.password:}")
    private String password;

    @Transactional
    public void refreshAndStoreToken() {
        log.info("Starting token refresh for provider={}", provider);

        String token = fetchRemoteToken();
        LocalDateTime fetchedAt = LocalDateTime.now();

        ExternalToken externalToken = externalTokenRepository.findByProvider(provider)
                .orElseGet(ExternalToken::new);

        externalToken.setProvider(provider);
        externalToken.setToken(token);
        externalToken.setFetchedAt(fetchedAt);

        externalTokenRepository.save(externalToken);
        log.info("Token refresh succeeded for provider={}, fetchedAt={}", provider, fetchedAt);
    }

    public void refreshAndStoreTokenSafely() {
        try {
            refreshAndStoreToken();
        } catch (Exception ex) {
            log.error("Token refresh failed for provider={}, keeping existing token. reason={}", provider, ex.getMessage(), ex);
        }
    }

    private String fetchRemoteToken() {
        if (phone == null || phone.isBlank()) {
            throw new IllegalStateException("external.token.phone is not configured");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalStateException("external.token.password is not configured");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(clientHeaderName, clientHeaderValue);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type", 0);
        requestBody.put("phone", phone);
        requestBody.put("password", hashPassword(password));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        String url = normalizeUrl(baseUrl, loginPath);

        ResponseEntity<BlacklakeLoginResponse> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, BlacklakeLoginResponse.class);
        } catch (RestClientException ex) {
            throw new IllegalStateException("Failed to call token endpoint", ex);
        }

        BlacklakeLoginResponse responseBody = response.getBody();
        if (responseBody == null) {
            throw new IllegalStateException("Token endpoint returned empty body");
        }
        if (responseBody.getStatusCode() != 200) {
            throw new IllegalStateException("Token endpoint returned statusCode=" + responseBody.getStatusCode()
                    + ", message=" + responseBody.getMessage());
        }
        if (responseBody.getData() == null || responseBody.getData().isBlank()) {
            throw new IllegalStateException("Token endpoint returned empty token");
        }

        return responseBody.getData();
    }

    private String normalizeUrl(String baseUrl, String path) {
        String trimmedBaseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String trimmedPath = path.startsWith("/") ? path : "/" + path;
        return trimmedBaseUrl + trimmedPath;
    }

    private String hashPassword(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-224");
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : hash) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA3-224 is not supported", ex);
        }
    }

    @lombok.Data
    public static class BlacklakeLoginResponse {
        private Integer statusCode;
        private String message;
        private String data;
    }
}

@Slf4j
@Component
@RequiredArgsConstructor
class ExternalTokenRefreshRunner implements ApplicationRunner {

    private final ExternalTokenService externalTokenService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Triggering initial token refresh on application startup");
        externalTokenService.refreshAndStoreTokenSafely();
    }
}

@Slf4j
@Component
@RequiredArgsConstructor
class ExternalTokenScheduler {

    private final ExternalTokenService externalTokenService;

    @Scheduled(fixedDelayString = "${external.token.refresh-interval-ms:86400000}", initialDelayString = "${external.token.schedule-initial-delay-ms:86400000}")
    public void refreshTokenOnSchedule() {
        log.info("Triggering scheduled token refresh");
        externalTokenService.refreshAndStoreTokenSafely();
    }
}
