package com.eprint.service;

import com.eprint.entity.ExternalToken;
import com.eprint.repository.mysql.ExternalTokenRepository;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KingdeeExternalTokenService {

    private final ExternalTokenRepository externalTokenRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${external.token.kingdee.provider:kingdee}")
    private String provider;

    @Value("${external.token.kingdee.base-url:https://api.kingdee.com}")
    private String baseUrl;

    @Value("${external.token.kingdee.token-path:/jdyconnector/app_management/kingdee_auth_token}")
    private String tokenPath;

    @Value("${external.token.kingdee.client-id:}")
    private String clientId;

    @Value("${external.token.kingdee.client-secret:}")
    private String clientSecret;

    @Value("${external.token.kingdee.app-key:}")
    private String appKey;

    @Value("${external.token.kingdee.app-secret:}")
    private String appSecret;

    @Transactional
    public void refreshAndStoreToken() {
        log.info("Starting Kingdee token refresh for provider={}", provider);

        String token = fetchRemoteToken();
        LocalDateTime fetchedAt = LocalDateTime.now();

        ExternalToken externalToken = externalTokenRepository.findByProvider(provider)
                .orElseGet(ExternalToken::new);

        externalToken.setProvider(provider);
        externalToken.setToken(token);
        externalToken.setFetchedAt(fetchedAt);

        externalTokenRepository.save(externalToken);
        log.info("Kingdee token refresh succeeded for provider={}, fetchedAt={}", provider, fetchedAt);
    }

    public void refreshAndStoreTokenSafely() {
        try {
            refreshAndStoreToken();
        } catch (Exception ex) {
            log.error("Kingdee token refresh failed for provider={}, keeping existing token. reason={}", provider, ex.getMessage(), ex);
        }
    }

    private String fetchRemoteToken() {
        requireConfigured("external.token.kingdee.client-id", clientId);
        requireConfigured("external.token.kingdee.client-secret", clientSecret);
        requireConfigured("external.token.kingdee.app-key", appKey);
        requireConfigured("external.token.kingdee.app-secret", appSecret);

        String ts = String.valueOf(System.currentTimeMillis());
        String nonce = String.valueOf(1000000000L + (long) (Math.random() * 9000000000L));

        String appSignature = computeAppSignature(appKey, appSecret);
        String apiSignature = computeApiSignature(tokenPath, appKey, appSignature, nonce, ts, clientSecret);

        String url = normalizeUrl(baseUrl, tokenPath) + "?app_key=" + encodeURIComponentLikeJs(appKey)
                + "&app_signature=" + encodeURIComponentLikeJs(appSignature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-ClientID", clientId);
        headers.set("X-Api-Auth-Version", "2.0");
        headers.set("X-Api-TimeStamp", ts);
        headers.set("X-Api-Nonce", nonce);
        headers.set("X-Api-SignHeaders", "X-Api-Nonce,X-Api-TimeStamp");
        headers.set("X-Api-Signature", apiSignature);

        ResponseEntity<KingdeeTokenResponse> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), KingdeeTokenResponse.class);
        } catch (RestClientException ex) {
            throw new IllegalStateException("Failed to call Kingdee token endpoint", ex);
        }

        KingdeeTokenResponse body = response.getBody();
        if (body == null) {
            throw new IllegalStateException("Kingdee token endpoint returned empty body");
        }
        if (body.getErrcode() == null) {
            throw new IllegalStateException("Kingdee token endpoint returned missing errcode");
        }
        if (body.getErrcode() != 0) {
            throw new IllegalStateException("Kingdee error errcode=" + body.getErrcode() + ", description=" + body.getDescription());
        }
        if (body.getData() == null || body.getData().getAppToken() == null || body.getData().getAppToken().isBlank()) {
            throw new IllegalStateException("Kingdee response missing app-token");
        }

        return body.getData().getAppToken();
    }

    /**
     * app_signature = Base64(hex(HmacSHA256(appKey, appSecret)))
     * 注意：Base64 的输入是 hex 字符串（ASCII bytes），不是原始 digest bytes。
     */
    static String computeAppSignature(String appKey, String appSecret) {
        String hex = hmacSha256Hex(appSecret, appKey);
        return Base64.getEncoder().encodeToString(hex.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * X-Api-Signature = Base64(hex(HmacSHA256(signingString, clientSecret)))
     */
    static String computeApiSignature(String path, String appKey, String appSignature, String nonce, String ts, String clientSecret) {
        // params string: key ASCII 排序，值二次 encodeURIComponent
        Map<String, String> params = new LinkedHashMap<>();
        params.put("app_key", appKey);
        params.put("app_signature", appSignature);

        StringBuilder paramsString = new StringBuilder();
        params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    if (paramsString.length() > 0) paramsString.append("&");
                    String k = entry.getKey();
                    String v = entry.getValue();
                    String vv = encodeURIComponentLikeJs(encodeURIComponentLikeJs(v));
                    paramsString.append(k).append("=").append(vv);
                });

        String encodedPath = encodeURIComponentLikeJs(path);
        String headersString = "x-api-nonce:" + nonce + "\n" + "x-api-timestamp:" + ts;
        String signingString = "GET\n" + encodedPath + "\n" + paramsString + "\n" + headersString + "\n";

        String sigHex = hmacSha256Hex(clientSecret, signingString);
        return Base64.getEncoder().encodeToString(sigHex.getBytes(StandardCharsets.UTF_8));
    }

    private static String hmacSha256Hex(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] digest = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to compute HmacSHA256", ex);
        }
    }

    /**
     * JS encodeURIComponent 风格（UTF-8），空格编码为 %20。
     * 这里实现最关键的兼容点：不要把空格编码为 '+'。
     */
    static String encodeURIComponentLikeJs(String value) {
        if (value == null) return "";
        StringBuilder out = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (isUnreserved(ch)) {
                out.append(ch);
                continue;
            }

            byte[] bytes = String.valueOf(ch).getBytes(StandardCharsets.UTF_8);
            for (byte b : bytes) {
                out.append('%');
                out.append(Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16)));
                out.append(Character.toUpperCase(Character.forDigit(b & 0xF, 16)));
            }
        }
        return out.toString();
    }

    private static boolean isUnreserved(char ch) {
        return (ch >= 'A' && ch <= 'Z')
                || (ch >= 'a' && ch <= 'z')
                || (ch >= '0' && ch <= '9')
                || ch == '-' || ch == '_' || ch == '.' || ch == '!' || ch == '~' || ch == '*' || ch == '\'' || ch == '(' || ch == ')';
    }

    private String normalizeUrl(String baseUrl, String path) {
        String trimmedBaseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String trimmedPath = path.startsWith("/") ? path : "/" + path;
        return trimmedBaseUrl + trimmedPath;
    }

    private void requireConfigured(String fieldName, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(fieldName + " is not configured");
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KingdeeTokenResponse {
        private Integer errcode;
        private String description;
        private KingdeeTokenData data;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KingdeeTokenData {
        @JsonAlias("app-token")
        private String appToken;
    }
}
