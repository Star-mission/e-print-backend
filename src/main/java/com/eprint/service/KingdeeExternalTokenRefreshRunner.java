package com.eprint.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KingdeeExternalTokenRefreshRunner implements ApplicationRunner {

    private final KingdeeExternalTokenService kingdeeExternalTokenService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Triggering initial Kingdee token refresh on application startup");
        kingdeeExternalTokenService.refreshAndStoreTokenSafely();
    }
}
