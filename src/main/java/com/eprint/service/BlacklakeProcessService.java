package com.eprint.service;

import com.eprint.dto.BlacklakeProcessQueryDTO;
import com.eprint.entity.BlacklakeProcess;
import com.eprint.repository.mysql.BlacklakeProcessRepository;
import com.eprint.repository.mysql.ExternalTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlacklakeProcessService {

    private final ExternalTokenRepository externalTokenRepository;
    private final BlacklakeProcessRepository blacklakeProcessRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${external.token.base-url:https://liteweb.blacklake.cn}")
    private String baseUrl;

    public Map<?, ?> queryProcessList(BlacklakeProcessQueryDTO query) {
        String token = externalTokenRepository.findByProvider("blacklake")
                .orElseThrow(() -> new IllegalStateException("Blacklake token not available"))
                .getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-AUTH", token);

        String url = baseUrl.replaceAll("/$", "") + "/api/dytin/external/process/queryList";
        log.info("Querying Blacklake process list: processCode={}, processName={}", query.getProcessCode(), query.getProcessName());

        ResponseEntity<Map> response = restTemplate.exchange(
                url, HttpMethod.POST, new HttpEntity<>(query, headers), Map.class);

        return response.getBody();
    }

    public void syncAllProcesses() {
        String token = externalTokenRepository.findByProvider("blacklake")
                .orElseThrow(() -> new IllegalStateException("Blacklake token not available"))
                .getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-AUTH", token);

        String url = baseUrl.replaceAll("/$", "") + "/api/dytin/external/process/queryList";

        int pageNum = 1;
        int pageSize = 200;
        int total = Integer.MAX_VALUE;
        int synced = 0;

        while ((pageNum - 1) * pageSize < total) {
            Map<String, Object> body = Map.of(
                    "pageNum", pageNum,
                    "pageSize", pageSize
            );
            log.info("Syncing Blacklake processes: page={}", pageNum);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url, HttpMethod.POST, new HttpEntity<>(body, headers), Map.class);

            Map<?, ?> responseBody = response.getBody();
            if (responseBody == null) {
                log.warn("Blacklake process sync: empty response on page {}", pageNum);
                break;
            }

            Object dataObj = responseBody.get("data");
            if (!(dataObj instanceof Map)) {
                log.warn("Blacklake process sync: unexpected data format on page {}", pageNum);
                break;
            }
            Map<?, ?> data = (Map<?, ?>) dataObj;

            Object totalObj = data.get("total");
            if (totalObj instanceof Number) {
                total = ((Number) totalObj).intValue();
            }

            Object recordsObj = data.get("records");
            if (!(recordsObj instanceof List)) {
                log.warn("Blacklake process sync: no records on page {}", pageNum);
                break;
            }

            List<?> records = (List<?>) recordsObj;
            for (Object rec : records) {
                if (!(rec instanceof Map)) continue;
                Map<?, ?> item = (Map<?, ?>) rec;
                try {
                    upsertProcess(item);
                    synced++;
                } catch (Exception e) {
                    log.error("Blacklake process sync: failed to upsert record id={}: {}", item.get("id"), e.getMessage());
                }
            }

            pageNum++;
        }

        log.info("Blacklake process sync complete: synced={}, total={}", synced, total);
    }

    private void upsertProcess(Map<?, ?> item) throws Exception {
        Object idObj = item.get("id");
        if (idObj == null) return;
        Long blacklakeId = ((Number) idObj).longValue();

        BlacklakeProcess process = blacklakeProcessRepository.findByBlacklakeId(blacklakeId)
                .orElse(new BlacklakeProcess());

        process.setBlacklakeId(blacklakeId);
        process.setCode(getString(item, "code"));
        process.setName(getString(item, "name"));

        Object rate = item.get("outputRate");
        if (rate instanceof Number) process.setOutputRate(((Number) rate).doubleValue());

        Object blAll = item.get("outputBlAll");
        if (blAll instanceof Boolean) process.setOutputBlAll((Boolean) blAll);

        process.setCreatorName(getString(item, "creatorName"));
        process.setUpdatorName(getString(item, "updatorName"));
        process.setBlacklakeCreatedAt(getString(item, "createdAt"));
        process.setBlacklakeUpdatedAt(getString(item, "updatedAt"));

        Object cfv = item.get("customFieldValues");
        if (cfv != null) {
            process.setCustomFieldValues(objectMapper.writeValueAsString(cfv));
        }

        process.setSyncedAt(LocalDateTime.now());
        blacklakeProcessRepository.save(process);
    }

    private String getString(Map<?, ?> map, String key) {
        Object val = map.get(key);
        return val != null ? val.toString() : null;
    }

    public void syncAllProcessesSafely() {
        try {
            syncAllProcesses();
        } catch (Exception e) {
            log.error("Blacklake process sync failed: {}", e.getMessage(), e);
        }
    }
}
