# 数据库设计文档

## MySQL 数据库（E_Bench）

---

### 表：orders（订单表）

| 字段名 | 数据类型 | 允许空 | 键 | 默认值 | 额外信息 | 注释 |
|--------|----------|--------|-----|--------|----------|------|
| id | BIGINT | NO | PRI | NULL | AUTO_INCREMENT | 主键 |
| order_number | VARCHAR(255) | NO | UNI | NULL | | 订单号（业务主键），格式：AUTO-时间戳-随机数 |
| order_ver | INT | YES | | NULL | | 订单版本号，初始为1 |
| order_unique | VARCHAR(255) | YES | UNI | NULL | | 唯一标识，格式：orderNumber_orderVer |
| status | VARCHAR(50) | YES | | NULL | | 订单状态：草稿/待审核/通过/驳回/生产中/完成/取消 |
| sales | VARCHAR(255) | YES | | NULL | | 业务员姓名 |
| audit | VARCHAR(255) | YES | | NULL | | 审核员姓名 |
| customer | VARCHAR(255) | YES | | NULL | | 客户名称 |
| product_name | VARCHAR(255) | YES | | NULL | | 产品名称 |
| customer_po | VARCHAR(255) | YES | | NULL | | 客户采购订单号 |
| isbn | VARCHAR(255) | YES | | NULL | | ISBN编号（图书类产品）|
| ding_dan_shu_liang | INT | YES | | NULL | | 订单数量 |
| chu_yang_shu_liang | INT | YES | | NULL | | 出样数量 |
| chao_bi_li_shu_liang | INT | YES | | NULL | | 超比例数量 |
| guige_gao_mm | DOUBLE | YES | | NULL | | 规格高度（毫米）|
| guige_kuan_mm | DOUBLE | YES | | NULL | | 规格宽度（毫米）|
| guige_hou_mm | DOUBLE | YES | | NULL | | 规格厚度（毫米）|
| fu_liao_shuo_ming | VARCHAR(2000) | YES | | NULL | | 辅料说明 |
| wu_liao_shuo_ming | VARCHAR(2000) | YES | | NULL | | 物料说明 |
| zhi_liang_yao_qiu | VARCHAR(2000) | YES | | NULL | | 质量要求 |
| bei_zhu | VARCHAR(2000) | YES | | NULL | | 备注信息 |
| ke_lai_xin_xi | VARCHAR(2000) | YES | | NULL | | 客来信息 |
| xia_ziliaodai_riqi1 | DATETIME | YES | | NULL | | 下资料袋日期1 |
| xia_ziliaodai_riqi2 | DATETIME | YES | | NULL | | 下资料袋日期2 |
| yinzhang_riqi1 | DATETIME | YES | | NULL | | 印章日期1 |
| yinzhang_riqi2 | DATETIME | YES | | NULL | | 印章日期2 |
| jiao_huo_ri_qi1 | DATETIME | YES | | NULL | | 交货日期1 |
| jiao_huo_ri_qi2 | DATETIME | YES | | NULL | | 交货日期2 |
| ye_wu_dai_biao_fen_ji | VARCHAR(255) | YES | | NULL | | 业务代表分级 |
| shen_he_ren | VARCHAR(255) | YES | | NULL | | 审核人 |
| da_yin_ren | VARCHAR(255) | YES | | NULL | | 打印人 |
| created_at | DATETIME | NO | | NULL | 自动设置 | 创建时间 |
| updated_at | DATETIME | NO | | NULL | 自动更新 | 更新时间 |

---

### 表：order_items（订单明细表）

| 字段名 | 数据类型 | 允许空 | 键 | 默认值 | 额外信息 | 注释 |
|--------|----------|--------|-----|--------|----------|------|
| id | BIGINT | NO | PRI | NULL | AUTO_INCREMENT | 主键 |
| order_id | BIGINT | NO | FK | NULL | | 外键，关联 orders.id |
| product_name | VARCHAR(255) | YES | | NULL | | 产品名称 |
| quantity | INT | YES | | NULL | | 数量 |
| specification | VARCHAR(255) | YES | | NULL | | 规格 |
| unit | VARCHAR(255) | YES | | NULL | | 单位 |
| unit_price | DOUBLE | YES | | NULL | | 单价 |
| total_price | DOUBLE | YES | | NULL | | 总价 |
| notes | VARCHAR(1000) | YES | | NULL | | 备注 |

---

### 表：engineering_orders（工程单表）

| 字段名 | 数据类型 | 允许空 | 键 | 默认值 | 额外信息 | 注释 |
|--------|----------|--------|-----|--------|----------|------|
| id | BIGINT | NO | PRI | NULL | AUTO_INCREMENT | 主键 |
| engineering_order_id | VARCHAR(255) | NO | UNI | NULL | | 工程单业务主键 |
| work_id | VARCHAR(255) | YES | | NULL | | 工程单号，格式：order_id+_W |
| work_ver | INT | YES | | NULL | | 版本号，与订单版本相同 |
| work_unique | VARCHAR(255) | YES | UNI | NULL | idx_work_unique | 唯一索引，格式：work_id_work_ver |
| review_status | VARCHAR(50) | YES | | NULL | | 状态：草稿/待审核/通过/驳回/生产中/完成/取消 |
| work_clerk | VARCHAR(255) | YES | | NULL | | 制单员名称或工号 |
| work_audit | VARCHAR(255) | YES | | NULL | | 审核员名称或工号 |
| ke_hu | VARCHAR(255) | YES | | NULL | | 客户名称 |
| po | VARCHAR(255) | YES | | NULL | | 客户PO |
| cheng_pin_ming_cheng | VARCHAR(255) | YES | | NULL | | 产品名称 |
| chu_yang_shu | INT | YES | | NULL | | 出样数量 |
| chao_bi_li | INT | YES | | NULL | | 超比例数量 |
| zhuang_ding_jian_shu | INT | YES | | NULL | | 已装订件数（用于计算装订进度）|
| head_mnf | VARCHAR(255) | YES | | NULL | | 生产装订负责人 |
| bei_zhu | VARCHAR(2000) | YES | | NULL | | 备注 |
| created_at | DATETIME | NO | | NULL | 自动设置 | 创建时间 |
| updated_at | DATETIME | NO | | NULL | 自动更新 | 更新时间 |

---

### 表：engineering_order_material_lines（工序物料表）

| 字段名 | 数据类型 | 允许空 | 键 | 默认值 | 额外信息 | 注释 |
|--------|----------|--------|-----|--------|----------|------|
| id | BIGINT | NO | PRI | NULL | AUTO_INCREMENT | 主键 |
| engineering_order_id | BIGINT | NO | FK | NULL | | 外键，关联 engineering_orders.id |
| intermedia_index | INT | YES | | NULL | | 在 intermedia 数组中的索引位置 |
| material_name | VARCHAR(255) | YES | | NULL | | 物料名称 |
| specification | VARCHAR(255) | YES | | NULL | | 规格 |
| quantity | INT | YES | | NULL | | 数量 |
| unit | VARCHAR(255) | YES | | NULL | | 单位 |
| kai_shi_shi_jian | DATETIME | YES | | NULL | | 工序开始时间 |
| jie_shu_shi_jian | DATETIME | YES | | NULL | | 工序结束时间 |
| dang_qian_jin_du | VARCHAR(255) | YES | | NULL | | 工序当前进度（外发进度）|
| yi_gou_jian_shu | INT | YES | | NULL | | 已采购件数 |
| head_pur | VARCHAR(255) | YES | | NULL | | 采购负责人 |
| head_out | VARCHAR(255) | YES | | NULL | | 外发负责人 |
| notes | VARCHAR(1000) | YES | | NULL | | 备注 |

---

### 表：documents（附件文档表）

| 字段名 | 数据类型 | 允许空 | 键 | 默认值 | 额外信息 | 注释 |
|--------|----------|--------|-----|--------|----------|------|
| id | BIGINT | NO | PRI | NULL | AUTO_INCREMENT | 主键 |
| file_name | VARCHAR(255) | YES | | NULL | | 文件名 |
| file_path | VARCHAR(255) | YES | | NULL | | 文件存储路径 |
| file_type | VARCHAR(255) | YES | | NULL | | 文件类型（MIME类型）|
| file_size | BIGINT | YES | | NULL | | 文件大小（字节）|
| category | VARCHAR(50) | YES | | NULL | | 分类：OrderAttachment / WorkOrderAttachment |
| order_id | BIGINT | YES | FK | NULL | | 外键，关联 orders.id |
| engineering_order_id | BIGINT | YES | FK | NULL | | 外键，关联 engineering_orders.id |
| uploaded_at | DATETIME | NO | | NULL | 自动设置 | 上传时间 |

---

### 表：users（用户表）

| 字段名 | 数据类型 | 允许空 | 键 | 默认值 | 额外信息 | 注释 |
|--------|----------|--------|-----|--------|----------|------|
| id | BIGINT | NO | PRI | NULL | AUTO_INCREMENT | 主键 |
| user_id | VARCHAR(255) | NO | UNI | NULL | | 用户唯一标识符（UUID或工号）|
| username | VARCHAR(255) | NO | UNI | NULL | idx_username | 登录账号名 |
| email | VARCHAR(255) | NO | UNI | NULL | idx_email | 电子邮箱 |
| password_hash | VARCHAR(255) | NO | | NULL | | 加密后的密码哈希值 |
| full_name | VARCHAR(255) | NO | | NULL | | 用户真实姓名 |
| is_active | TINYINT(1) | NO | | 1 | | 账号是否启用 |
| order_submit | TINYINT(1) | NO | | 0 | | 订单提交权限（业务员）|
| order_audit | TINYINT(1) | NO | | 0 | | 订单审核权限（审单员/主管）|
| work_submit | TINYINT(1) | NO | | 0 | | 工程单提交权限（制单员）|
| work_audit | TINYINT(1) | NO | | 0 | | 工程单审核权限（工程主管）|
| order_check | TINYINT(1) | NO | | 0 | | 订单查看权限 |
| work_check | TINYINT(1) | NO | | 0 | | 工程单查看权限 |
| pmc_check | TINYINT(1) | NO | | 0 | | PMC生产排期查看权限 |
| pmc_edit | TINYINT(1) | NO | | 0 | | PMC生产排期修改权限 |
| delieve_check | TINYINT(1) | NO | | 0 | | 发货/出库记录查看权限 |
| delieve_edit | TINYINT(1) | NO | | 0 | | 发货/出库单据编辑权限 |
| is_sal | TINYINT(1) | NO | | 0 | | 是否能查看销售部页面 |
| is_pur | TINYINT(1) | NO | | 0 | | 是否能查看采购部页面 |
| is_out | TINYINT(1) | NO | | 0 | | 是否能查看外发部页面 |
| is_mnf | TINYINT(1) | NO | | 0 | | 是否能查看生产部页面 |
| is_adm | TINYINT(1) | NO | | 0 | | 是否能查看办公室页面 |
| last_login | DATETIME | YES | | NULL | | 最后登录时间 |
| created_at | DATETIME | NO | | NULL | 自动设置 | 创建时间 |
| updated_at | DATETIME | NO | | NULL | 自动更新 | 更新时间 |

---

## MongoDB 数据库（E_Bench_Logs）

---

### 集合：audit_logs（审计日志集合）

| 字段名 | 数据类型 | 索引 | 默认值 | 注释 |
|--------|----------|------|--------|------|
| _id | ObjectId | PRI | 自动生成 | MongoDB主键 |
| action | String | IDX | NULL | 操作类型（如：CREATE_ORDER、UPDATE_STATUS）|
| action_description | String | | NULL | 操作描述 |
| user_id | String | IDX | NULL | 操作用户ID |
| entity_type | String | IDX | NULL | 实体类型（Order / EngineeringOrder）|
| entity_id | String | IDX | NULL | 实体业务主键 |
| order_number | String | IDX | NULL | 订单号 |
| old_value | String | | NULL | 变更前的值 |
| new_value | String | | NULL | 变更后的值 |
| ip_address | String | | NULL | 操作来源IP地址 |
| time | DateTime | IDX | 创建时自动设置 | 操作时间 |

---

## 接口与数据库数据流关系

---

### 订单接口（/api/orders）

| 接口 | 方法 | 请求参数 | 读写表 | 数据流说明 |
|------|------|----------|--------|------------|
| /api/orders/create | POST | `orderData`(JSON), `isDraft`(bool), `files`(multipart) | orders(W), order_items(W), documents(W), audit_logs(W) | 将 orderData 反序列化为 OrderDTO，写入 orders 及关联的 order_items、documents，同时在 MongoDB 写入创建日志 |
| /api/orders/findById | GET | `order_id`(string) | orders(R), order_items(R), documents(R), audit_logs(R) | 按 order_unique 查询订单，返回含明细、附件、审计日志的完整 OrderDTO |
| /api/orders/findBySales | GET | `sales`(string) | orders(R) | 按业务员姓名查询订单列表 |
| /api/orders/findByAudit | GET | `audit`(string) | orders(R) | 按审核员姓名查询订单列表 |
| /api/orders/status | GET | `orderstatus`(string) | orders(R) | 按订单状态查询订单列表 |
| /api/orders/all | GET | 无 | orders(R) | 查询所有订单列表 |
| /api/orders/updateStatus | POST | `order_unique`, `orderstatus`, `auditor` | orders(W), audit_logs(W) | 更新订单状态字段，同时在 MongoDB 写入状态变更日志 |
| /api/orders/{id} | DELETE | `id`(path) | orders(W), order_items(W), documents(W), audit_logs(W) | 级联删除订单及其明细、附件，写入删除日志 |

---

### 工程单接口（/api/workOrders）

| 接口 | 方法 | 请求参数 | 读写表 | 数据流说明 |
|------|------|----------|--------|------------|
| /api/workOrders/create | POST | `workOrderJson`(JSON), `files`(multipart) | engineering_orders(W), engineering_order_material_lines(W), documents(W), audit_logs(W) | 将 workOrderJson 反序列化为 WorkOrderDTO，写入 engineering_orders 及关联的物料行、附件，写入创建日志 |
| /api/workOrders/findById | GET | `work_unique`(string) | engineering_orders(R), engineering_order_material_lines(R), documents(R), audit_logs(R) | 按 work_unique 查询工程单，返回含物料行、附件、审计日志的完整 WorkOrderDTO |
| /api/workOrders/findByClerk | GET | `work_clerk`(string) | engineering_orders(R) | 按制单员名称查询工程单列表 |
| /api/workOrders/findByAudit | GET | `work_audit`(string) | engineering_orders(R) | 按审核员名称查询工程单列表 |
| /api/workOrders/findWithStatus | GET | `workorderstatus`(string) | engineering_orders(R) | 按状态查询工程单列表 |
| /api/workOrders/updateStatus | POST | `work_unique`, `workorderstatus` | engineering_orders(W), audit_logs(W) | 更新工程单状态，写入状态变更日志 |
| /api/workOrders/updateProcess | POST | `work_id`, `process`, `dangQianJinDu` | engineering_order_material_lines(W), audit_logs(W) | 更新第一条物料行的当前进度，写入进度日志 |
| /api/workOrders/addHeadPur | POST | `work_unique`, `intermediaID`, `head_PUR` | engineering_order_material_lines(W), audit_logs(W) | 按 intermediaID 索引到指定物料行，将 head_PUR 字段赋值为指定负责人，写入日志 |
| /api/workOrders/addHeadOut | POST | `work_unique`, `intermediaID`, `head_OUT` | engineering_order_material_lines(W), audit_logs(W) | 按 intermediaID 索引到指定物料行，将 head_OUT 字段赋值为指定负责人，写入日志 |
| /api/workOrders/addHeadMnf | POST | `work_unique`, `head_MNF` | engineering_orders(W), audit_logs(W) | 将工程单的 head_mnf 字段赋值为指定生产负责人，写入日志 |
| /api/workOrders/updateProgressPur | POST | `work_unique`, `intermediaID`, `yiGouJianShu` | engineering_order_material_lines(W), audit_logs(W) | 按 intermediaID 索引到指定物料行，更新 yi_gou_jian_shu（已采购件数），写入日志 |
| /api/workOrders/updateProgressOut | POST | `work_unique`, `intermediaID`, `kaiShiRiQi`, `yuQiJieShu`, `dangQianJinDu` | engineering_order_material_lines(W), audit_logs(W) | 按 intermediaID 索引到指定物料行，更新工序开始时间、结束时间、当前进度，写入日志 |
| /api/workOrders/updateProgressMnf | POST | `work_unique`, `zhuangDingJianShu` | engineering_orders(W), audit_logs(W) | 更新工程单的 zhuang_ding_jian_shu（已装订件数），写入日志 |

---

### 用户接口（/api/users）

| 接口 | 方法 | 请求参数 | 读写表 | 数据流说明 |
|------|------|----------|--------|------------|
| /api/users/create | POST | 用户信息JSON + `passwordHash` | users(W) | 创建用户，校验用户名/邮箱唯一性后写入 users 表 |
| /api/users/findByUsername | GET | `username`(string) | users(R) | 按用户名查询用户信息（不含密码哈希）|
| /api/users/findByUserId | GET | `userId`(string) | users(R) | 按用户ID查询用户信息（不含密码哈希）|
| /api/users/all | GET | 无 | users(R) | 查询所有用户列表 |
| /api/users/update | POST | `userId` + 更新字段JSON | users(W) | 按 userId 查找用户，更新基础信息和权限字段 |
| /api/users/{userId} | DELETE | `userId`(path) | users(W) | 按 userId 删除用户 |
| /api/users/updateLastLogin | POST | `userId` | users(W) | 更新用户最后登录时间 |

---

### 数据流图（文字描述）

```
前端
  │
  ├─ POST /orders/create ──────────────────► orders + order_items + documents
  │                                                        │
  │                                                        └─► audit_logs (MongoDB)
  │
  ├─ POST /orders/updateStatus ────────────► orders.status
  │                                                        │
  │                                                        └─► audit_logs (MongoDB)
  │
  ├─ POST /workOrders/create ─────────────► engineering_orders
  │                                       ► engineering_order_material_lines
  │                                       ► documents
  │                                                        │
  │                                                        └─► audit_logs (MongoDB)
  │
  ├─ POST /workOrders/addHeadPur/Out ─────► engineering_order_material_lines.head_pur/head_out
  ├─ POST /workOrders/addHeadMnf ─────────► engineering_orders.head_mnf
  ├─ POST /workOrders/updateProgressPur ──► engineering_order_material_lines.yi_gou_jian_shu
  ├─ POST /workOrders/updateProgressOut ──► engineering_order_material_lines.dang_qian_jin_du
  ├─ POST /workOrders/updateProgressMnf ──► engineering_orders.zhuang_ding_jian_shu
  │
  └─ POST /users/create ──────────────────► users
```
