package com.eprint.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KingdeeExternalTokenScheduler {

    private final KingdeeExternalTokenService kingdeeExternalTokenService;

    @Scheduled(
            fixedDelayString = "${external.token.kingdee.refresh-interval-ms:43200000}",
            initialDelayString = "${external.token.kingdee.schedule-initial-delay-ms:43200000}"
    )
    public void refreshTokenOnSchedule() {
        log.info("Triggering scheduled Kingdee token refresh");
        kingdeeExternalTokenService.refreshAndStoreTokenSafely();
    }
}
