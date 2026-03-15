-- 创建数据库
CREATE DATABASE IF NOT EXISTS E_Bench
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE E_Bench;

-- 注意：Spring Boot JPA 会自动创建表结构
-- 此脚本仅用于手动初始化或参考

SELECT 'Database E_Bench created successfully!' AS message;
