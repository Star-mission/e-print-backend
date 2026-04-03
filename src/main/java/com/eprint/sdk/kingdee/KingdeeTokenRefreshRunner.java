package com.eprint.sdk.kingdee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
//@Component  // 暂时禁用金蝶 token 刷新功能
@RequiredArgsConstructor
public class KingdeeTokenRefreshRunner implements ApplicationRunner {

    private final KingdeeTokenService kingdeeTokenService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Triggering initial Kingdee token refresh on application startup");
        kingdeeTokenService.refreshAndStoreTokenSafely();
    }
}
