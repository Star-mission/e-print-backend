-- Orders schema compatibility patch for historical local databases
--
-- Run this only when an existing orders table is missing columns required by the current Order entity.
-- For a brand-new empty database, start the backend directly and let JPA create/update the schema.

USE E_Bench;

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
    'SELECT ''Skip orders_schema_migration.sql: table orders does not exist yet. For a new empty DB, start the backend directly.'' AS message'
);

PREPARE stmt FROM @orders_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
