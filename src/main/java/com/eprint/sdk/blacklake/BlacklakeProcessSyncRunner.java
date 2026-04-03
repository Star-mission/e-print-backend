package com.eprint.sdk.blacklake;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
//@Component  // 暂时禁用启动时同步，避免外部 API 连接失败导致启动中断
@RequiredArgsConstructor
public class BlacklakeProcessSyncRunner implements ApplicationRunner {

    private final BlacklakeProcessService blacklakeProcessService;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Triggering initial Blacklake process sync on application startup");
        blacklakeProcessService.syncAllProcessesSafely();
    }
}
