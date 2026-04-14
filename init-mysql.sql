-- 开发库兼容修复脚本
--
-- 用途：
-- 1. 为历史本地库补齐当前实体需要的已知字段
-- 2. 不作为“新机器 / 新空库”首次启动的主路径
--
-- 推荐流程：
-- - 新空库：启动 MySQL 后直接执行 `mvn spring-boot:run`，由 JPA 自动建表/补表
-- - 历史旧库：如启动时报 Unknown column、字段类型不兼容等问题，再执行本脚本

CREATE DATABASE IF NOT EXISTS E_Bench
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE E_Bench;

-- 仅当 orders 表已经存在时，才对历史结构做兼容修复。
-- 如果是全新空库，请直接启动应用，让 JPA 按实体创建表结构。
SET @orders_table_exists = (
    SELECT COUNT(*)
    FROM information_schema.tables
    WHERE table_schema = DATABASE()
      AND table_name = 'orders'
);

SET @orders_sql = IF(
    @orders_table_exists > 0,
    'ALTER TABLE orders
        MODIFY COLUMN bei_zhu TEXT NULL,
        MODIFY COLUMN fu_liao_shuo_ming TEXT NULL,
        MODIFY COLUMN ke_lai_xin_xi TEXT NULL,
        MODIFY COLUMN wu_liao_shuo_ming TEXT NULL,
        MODIFY COLUMN zhi_liang_yao_qiu TEXT NULL,
        MODIFY COLUMN ding_zhi_bei_zhu TEXT NULL,
        MODIFY COLUMN gen_se_zhi_shi TEXT NULL,
        MODIFY COLUMN yong_tu TEXT NULL,
        MODIFY COLUMN audit_date DATETIME(6) NULL,
        MODIFY COLUMN sales_date DATETIME(6) NULL,
        MODIFY COLUMN chuyang_riqi1 DATETIME(6) NULL,
        MODIFY COLUMN chuyang_riqi2 DATETIME(6) NULL,
        MODIFY COLUMN zhepai_riqi1 DATETIME(6) NULL,
        MODIFY COLUMN zhepai_riqi2 DATETIME(6) NULL,
        ADD COLUMN IF NOT EXISTS audit_date DATETIME(6) NULL,
        ADD COLUMN IF NOT EXISTS sales_date DATETIME(6) NULL,
        ADD COLUMN IF NOT EXISTS bao_jia_dan_hao VARCHAR(255) NULL,
        ADD COLUMN IF NOT EXISTS bao_liu_qian_se VARCHAR(255) NULL,
        ADD COLUMN IF NOT EXISTS bei_pin_shu_liang INT NULL,
        ADD COLUMN IF NOT EXISTS chan_pin_da_lei VARCHAR(255) NULL,
        ADD COLUMN IF NOT EXISTS chan_pin_ming_xi_te_bie_shuo_ming TEXT NULL,
        ADD COLUMN IF NOT EXISTS chu_huo_shu_liang INT NULL,
        ADD COLUMN IF NOT EXISTS chu_yang_shuo_ming INT NULL,
        ADD COLUMN IF NOT EXISTS chuyang_riqi1 DATETIME(6) NULL,
        ADD COLUMN IF NOT EXISTS chuyang_riqi2 DATETIME(6) NULL,
        ADD COLUMN IF NOT EXISTS cpc_que_ren BIT(1) NULL,
        ADD COLUMN IF NOT EXISTS cpsia_yaoqiu BIT(1) NULL,
        ADD COLUMN IF NOT EXISTS fen_ban_shuo_ming TEXT NULL,
        ADD COLUMN IF NOT EXISTS fsc_type VARCHAR(255) NULL,
        ADD COLUMN IF NOT EXISTS te_shu_liu_shu_yang INT NULL,
        ADD COLUMN IF NOT EXISTS te_shu_liu_yang_zhang INT NULL,
        ADD COLUMN IF NOT EXISTS waixiao_flag BIT(1) NULL,
        ADD COLUMN IF NOT EXISTS xi_lie_dan_ming VARCHAR(255) NULL,
        ADD COLUMN IF NOT EXISTS zhepai_riqi1 DATETIME(6) NULL,
        ADD COLUMN IF NOT EXISTS zhepai_riqi2 DATETIME(6) NULL,
        ADD COLUMN IF NOT EXISTS zhuang_ding_fang_shi VARCHAR(255) NULL,
        ADD COLUMN IF NOT EXISTS zong_shu_liang INT NULL,
        ADD COLUMN IF NOT EXISTS zi_lei_xing TEXT NULL',
    'SELECT ''Skip orders compatibility patch: table orders does not exist yet. Start the backend once on a new empty database so JPA can create tables.'' AS message'
);

PREPARE stmt FROM @orders_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SELECT 'init-mysql.sql finished. New empty DBs should use JPA auto schema creation; this script is only for historical schema compatibility.' AS message;
