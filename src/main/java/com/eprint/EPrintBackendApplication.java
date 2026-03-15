package com.eprint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * E-Print 后端服务 - Spring Boot 应用主入口
 *
 * 功能概述：
 * - 订单管理系统（Order Management）
 * - 工单管理系统（Engineering Order Management）
 * - 双数据库支持（MySQL + MongoDB）
 * - 文件上传与管理
 * - 审计日志记录
 *
 * 技术栈：
 * - Spring Boot 3.2.3
 * - Spring Data JPA (MySQL)
 * - Spring Data MongoDB
 * - Lombok (简化代码)
 * - MapStruct (DTO 映射)
 *
 * @author E-Print Team
 * @version 1.0.0
 */
@SpringBootApplication  // Spring Boot 主注解，启用自动配置
@EnableJpaRepositories(basePackages = "com.eprint.repository.mysql")  // 启用 JPA 仓库，指定 MySQL 仓库包路径
@EnableMongoRepositories(basePackages = "com.eprint.repository.mongo")  // 启用 MongoDB 仓库，指定 MongoDB 仓库包路径
public class EPrintBackendApplication {

    /**
     * 应用程序入口方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(EPrintBackendApplication.class, args);
    }
}
