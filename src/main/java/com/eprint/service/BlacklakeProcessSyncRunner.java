package com.eprint.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlacklakeProcessSyncRunner implements ApplicationRunner {

    private final BlacklakeProcessService blacklakeProcessService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Triggering initial Blacklake process sync on application startup");
        blacklakeProcessService.syncAllProcessesSafely();
    }
}
