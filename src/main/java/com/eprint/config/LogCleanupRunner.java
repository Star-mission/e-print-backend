package com.eprint.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 应用启动时清理旧日志文件
 */
@Slf4j
@Component
public class LogCleanupRunner implements ApplicationRunner {

    @Value("${logging.file.name:./logs/eprint.log}")
    private String logFilePath;

    @Override
    public void run(ApplicationArguments args) {
        try {
            Path logPath = Paths.get(logFilePath);
            File logFile = logPath.toFile();

            if (logFile.exists()) {
                boolean deleted = logFile.delete();
                if (deleted) {
                    log.info("旧日志文件已删除: {}", logFilePath);
                } else {
                    log.warn("无法删除旧日志文件: {}", logFilePath);
                }
            } else {
                log.info("日志文件不存在，无需删除: {}", logFilePath);
            }

            // 确保日志目录存在
            File logDir = logFile.getParentFile();
            if (logDir != null && !logDir.exists()) {
                boolean created = logDir.mkdirs();
                if (created) {
                    log.info("日志目录已创建: {}", logDir.getAbsolutePath());
                }
            }

        } catch (Exception e) {
            log.error("清理日志文件时出错", e);
        }
    }
}
