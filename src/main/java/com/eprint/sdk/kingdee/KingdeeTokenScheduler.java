package com.eprint.sdk.kingdee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KingdeeTokenScheduler {

    private final KingdeeTokenService kingdeeTokenService;

    @Scheduled(
            fixedDelayString = "${external.token.kingdee.refresh-interval-ms:43200000}",
            initialDelayString = "${external.token.kingdee.schedule-initial-delay-ms:43200000}"
    )
    public void refreshTokenOnSchedule() {
        log.info("Triggering scheduled Kingdee token refresh");
        kingdeeTokenService.refreshAndStoreTokenSafely();
    }
}
