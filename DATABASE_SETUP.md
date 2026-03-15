# 数据库初始化脚本

## MySQL 数据库初始化

```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS E_Bench
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE E_Bench;

-- Spring Boot 会自动创建表结构（通过 JPA），但如果需要手动创建，可以使用以下脚本：

-- 订单表
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(255) NOT NULL UNIQUE,
    order_ver INT,
    order_unique VARCHAR(255) UNIQUE,
    status VARCHAR(50),
    sales VARCHAR(255),
    audit VARCHAR(255),
    customer VARCHAR(255),
    product_name VARCHAR(255),
    customer_po VARCHAR(255),
    isbn VARCHAR(255),
    ding_dan_shu_liang INT,
    chu_yang_shu_liang INT,
    chao_bi_li_shu_liang INT,
    guige_gao_mm DOUBLE,
    guige_kuan_mm DOUBLE,
    guige_hou_mm DOUBLE,
    fu_liao_shuo_ming TEXT,
    wu_liao_shuo_ming TEXT,
    zhi_liang_yao_qiu TEXT,
    bei_zhu TEXT,
    ke_lai_xin_xi TEXT,
    xia_ziliaodai_riqi1 DATETIME,
    xia_ziliaodai_riqi2 DATETIME,
    yinzhang_riqi1 DATETIME,
    yinzhang_riqi2 DATETIME,
    jiao_huo_ri_qi1 DATETIME,
    jiao_huo_ri_qi2 DATETIME,
    ye_wu_dai_biao_fen_ji VARCHAR(255),
    shen_he_ren VARCHAR(255),
    da_yin_ren VARCHAR(255),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    INDEX idx_order_unique (order_unique)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 订单明细表
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_name VARCHAR(255),
    quantity INT,
    specification VARCHAR(255),
    unit VARCHAR(50),
    unit_price DOUBLE,
    total_price DOUBLE,
    notes TEXT,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 工单表
CREATE TABLE IF NOT EXISTS engineering_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    engineering_order_id VARCHAR(255) NOT NULL UNIQUE,
    work_id VARCHAR(255),
    work_ver INT,
    work_unique VARCHAR(255) UNIQUE,
    review_status VARCHAR(50),
    work_clerk VARCHAR(255),
    work_audit VARCHAR(255),
    ke_hu VARCHAR(255),
    po VARCHAR(255),
    cheng_pin_ming_cheng VARCHAR(255),
    chu_yang_shu INT,
    chao_bi_li INT,
    bei_zhu TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    INDEX idx_work_unique (work_unique)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 物料行表
CREATE TABLE IF NOT EXISTS engineering_order_material_lines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    engineering_order_id BIGINT NOT NULL,
    material_name VARCHAR(255),
    specification VARCHAR(255),
    quantity INT,
    unit VARCHAR(50),
    kai_shi_shi_jian DATETIME,
    jie_shu_shi_jian DATETIME,
    dang_qian_jin_du INT,
    notes TEXT,
    FOREIGN KEY (engineering_order_id) REFERENCES engineering_orders(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 文档附件表
CREATE TABLE IF NOT EXISTS documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255),
    file_path VARCHAR(500),
    file_type VARCHAR(100),
    file_size BIGINT,
    category VARCHAR(50),
    order_id BIGINT,
    engineering_order_id BIGINT,
    uploaded_at DATETIME NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (engineering_order_id) REFERENCES engineering_orders(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

## MongoDB 初始化

MongoDB 不需要预先创建集合，Spring Data MongoDB 会自动创建。但如果需要创建索引，可以使用以下脚本：

```javascript
// 连接到 MongoDB
use E_Bench_Logs

// 创建审计日志集合的索引
db.audit_logs.createIndex({ "orderNumber": 1 })
db.audit_logs.createIndex({ "userId": 1 })
db.audit_logs.createIndex({ "entityType": 1, "entityId": 1 })
db.audit_logs.createIndex({ "time": -1 })
db.audit_logs.createIndex({ "action": 1 })

// 验证索引
db.audit_logs.getIndexes()
```

## 快速初始化命令

### MySQL
```bash
mysql -u root -p < init-mysql.sql
```

### MongoDB
```bash
mongosh < init-mongo.js
```

## 注意事项

1. Spring Boot 的 `spring.jpa.hibernate.ddl-auto=update` 配置会自动创建和更新表结构
2. 首次运行时，JPA 会根据实体类自动创建所有表
3. MongoDB 的索引会在应用启动时自动创建（通过 `@Indexed` 注解）
4. 确保 MySQL 和 MongoDB 服务都已启动
5. 确保数据库用户有足够的权限
