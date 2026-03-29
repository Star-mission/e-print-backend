package com.eprint.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlacklakeProcessSyncScheduler {

    private final BlacklakeProcessService blacklakeProcessService;

    @Scheduled(
            fixedDelayString = "${blacklake.process.sync.interval-ms:86400000}",
            initialDelayString = "${blacklake.process.sync.initial-delay-ms:86400000}"
    )
    public void syncProcessesOnSchedule() {
        log.info("Triggering scheduled Blacklake process sync");
        blacklakeProcessService.syncAllProcessesSafely();
    }
}
