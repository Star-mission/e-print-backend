# E-Print 后端系统 - 接口与数据库设计文档

## 目录
1. [系统概述](#系统概述)
2. [数据库设计](#数据库设计)
3. [REST API 接口](#rest-api-接口)
4. [接口与数据库关系映射](#接口与数据库关系映射)

---

## 系统概述

**项目名称**: E-Print Backend
**技术栈**: Spring Boot 3.2.3 + JPA + MySQL
**端口**: 3000
**基础路径**: `/api`

**核心业务流程**:
```
订单创建(草稿/提交) → 订单审核(通过/驳回) → 工程单创建 → 生产进度跟踪 → 订单完成
```

---

## 数据库设计

### 1. 订单表 (orders)

**表名**: `orders`
**说明**: 管理印刷订单的完整生命周期

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 主键ID | PRIMARY KEY, AUTO_INCREMENT |
| order_number | VARCHAR(255) | 订单号 (AUTO-时间戳-随机数) | UNIQUE, NOT NULL |
| order_ver | INT | 订单版本号 | - |
| order_unique | VARCHAR(255) | 订单唯一标识 (order_number_ver) | UNIQUE |
| status | VARCHAR(50) | 订单状态 | ENUM |
| sales | VARCHAR(100) | 业务员姓名 | - |
| audit | VARCHAR(100) | 审核员姓名 | - |
| customer | VARCHAR(255) | 客户名称 | - |
| product_name | VARCHAR(255) | 产品名称 | - |
| customer_po | VARCHAR(255) | 客户采购订单号 | - |
| isbn | VARCHAR(50) | ISBN编号 | - |
| ding_dan_shu_liang | INT | 订单数量 | - |
| chu_yang_shu_liang | INT | 出样数量 | - |
| chao_bi_li_shu_liang | INT | 超比例数量 | - |
| guige_gao_mm | DOUBLE | 规格高度(毫米) | - |
| guige_kuan_mm | DOUBLE | 规格宽度(毫米) | - |
| guige_hou_mm | DOUBLE | 规格厚度(毫米) | - |
| is_deleted | VARCHAR(10) | 是否删除 (是/否) | NOT NULL, DEFAULT '否' |
| created_at | DATETIME | 创建时间 | NOT NULL |
| updated_at | DATETIME | 更新时间 | NOT NULL |

**订单状态枚举**: 草稿、待审核、通过、驳回、生产中、完成、取消

**索引**:
- `idx_order_unique` (order_unique) - UNIQUE

**关联关系**:
- `order_items` (一对多) - 订单明细
- `documents` (一对多) - 附件文档

---

### 2. 订单明细表 (order_items)

**表名**: `order_items`
**说明**: 订单的产品明细行

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 主键ID | PRIMARY KEY, AUTO_INCREMENT |
| order_id | BIGINT | 订单ID (外键) | NOT NULL |
| product_name | VARCHAR(255) | 产品名称 | - |
| quantity | INT | 数量 | - |
| specification | VARCHAR(255) | 规格 | - |
| unit | VARCHAR(50) | 单位 | - |
| unit_price | DOUBLE | 单价 | - |
| total_price | DOUBLE | 总价 | - |
| notes | VARCHAR(1000) | 备注 | - |

**外键**:
- `order_id` → `orders.id` (CASCADE DELETE)

---

### 3. 工程单表 (engineering_orders)

**表名**: `engineering_orders`
**说明**: 订单审核通过后创建的工程单

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 主键ID | PRIMARY KEY, AUTO_INCREMENT |
| engineering_order_id | VARCHAR(255) | 工程单号 (WORK-UUID) | UNIQUE, NOT NULL |
| work_id | VARCHAR(255) | 工单ID | - |
| work_ver | INT | 工单版本号 | - |
| work_unique | VARCHAR(255) | 工单唯一标识 (work_id_ver) | UNIQUE |
| review_status | VARCHAR(50) | 审核状态 | ENUM |
| work_clerk | VARCHAR(100) | 制单员 | - |
| work_audit | VARCHAR(100) | 审核员 | - |
| ke_hu | VARCHAR(255) | 客户 | - |
| po | VARCHAR(255) | PO号 | - |
| cheng_pin_ming_cheng | VARCHAR(255) | 成品名称 | - |
| chu_yang_shu | INT | 出样数 | - |
| chao_bi_li | INT | 超比例 | - |
| zhuang_ding_jian_shu | INT | 已装订件数 | - |
| head_mnf | VARCHAR(100) | 生产负责人 | - |
| bei_zhu | TEXT | 备注 | - |
| is_deleted | VARCHAR(10) | 是否删除 (是/否) | NOT NULL, DEFAULT '否' |
| created_at | DATETIME | 创建时间 | NOT NULL |
| updated_at | DATETIME | 更新时间 | NOT NULL |

**索引**:
- `idx_work_unique` (work_unique) - UNIQUE

**关联关系**:
- `material_lines` (一对多) - 物料工序行
- `documents` (一对多) - 附件文档

---

### 4. 物料工序表 (engineering_order_material_lines)

**表名**: `engineering_order_material_lines`
**说明**: 工程单的物料和工序明细

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 主键ID | PRIMARY KEY, AUTO_INCREMENT |
| engineering_order_id | BIGINT | 工程单ID (外键) | NOT NULL |
| intermedia_index | INT | 工序索引 | - |
| material_name | VARCHAR(255) | 物料名称 | - |
| specification | VARCHAR(255) | 规格 | - |
| quantity | INT | 数量 | - |
| unit | VARCHAR(50) | 单位 | - |
| kai_shi_shi_jian | DATETIME | 开始时间 | - |
| jie_shu_shi_jian | DATETIME | 结束时间 | - |
| dang_qian_jin_du | VARCHAR(255) | 当前进度 | - |
| yi_gou_jian_shu | INT | 已采购件数 | - |
| head_pur | VARCHAR(100) | 采购负责人 | - |
| head_out | VARCHAR(100) | 外发负责人 | - |
| notes | VARCHAR(1000) | 备注 | - |

**外键**:
- `engineering_order_id` → `engineering_orders.id` (CASCADE DELETE)

---

### 5. 文档附件表 (documents)

**表名**: `documents`
**说明**: 订单和工程单的附件文档

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 主键ID | PRIMARY KEY, AUTO_INCREMENT |
| file_name | VARCHAR(255) | 文件名 | - |
| file_path | VARCHAR(500) | 文件路径 | - |
| file_type | VARCHAR(100) | 文件类型 | - |
| file_size | BIGINT | 文件大小(字节) | - |
| category | VARCHAR(50) | 文档分类 | ENUM |
| order_id | BIGINT | 订单ID (外键) | - |
| engineering_order_id | BIGINT | 工程单ID (外键) | - |
| uploaded_at | DATETIME | 上传时间 | NOT NULL |

**文档分类枚举**: OrderAttachment, WorkOrderAttachment

**外键**:
- `order_id` → `orders.id` (CASCADE DELETE)
- `engineering_order_id` → `engineering_orders.id` (CASCADE DELETE)

---

### 6. 用户表 (users)

**表名**: `users`
**说明**: 系统用户和权限管理

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 主键ID | PRIMARY KEY, AUTO_INCREMENT |
| user_id | VARCHAR(255) | 用户唯一标识 | UNIQUE, NOT NULL |
| username | VARCHAR(255) | 登录账号名 | UNIQUE, NOT NULL |
| email | VARCHAR(255) | 电子邮箱 | UNIQUE, NOT NULL |
| password_hash | VARCHAR(255) | 密码哈希值 | NOT NULL |
| full_name | VARCHAR(255) | 真实姓名 | NOT NULL |
| is_active | BOOLEAN | 账号是否启用 | NOT NULL, DEFAULT TRUE |
| order_submit | BOOLEAN | 订单提交权限 | NOT NULL, DEFAULT FALSE |
| order_audit | BOOLEAN | 订单审核权限 | NOT NULL, DEFAULT FALSE |
| work_submit | BOOLEAN | 工程单提交权限 | NOT NULL, DEFAULT FALSE |
| work_audit | BOOLEAN | 工程单审核权限 | NOT NULL, DEFAULT FALSE |
| order_check | BOOLEAN | 订单查看权限 | NOT NULL, DEFAULT FALSE |
| work_check | BOOLEAN | 工程单查看权限 | NOT NULL, DEFAULT FALSE |
| pmc_check | BOOLEAN | PMC查看权限 | NOT NULL, DEFAULT FALSE |
| pmc_edit | BOOLEAN | PMC修改权限 | NOT NULL, DEFAULT FALSE |
| delieve_check | BOOLEAN | 发货查看权限 | NOT NULL, DEFAULT FALSE |
| delieve_edit | BOOLEAN | 发货修改权限 | NOT NULL, DEFAULT FALSE |
| is_sal | BOOLEAN | 销售部页面访问权限 | NOT NULL, DEFAULT FALSE |
| is_pur | BOOLEAN | 采购部页面访问权限 | NOT NULL, DEFAULT FALSE |
| is_out | BOOLEAN | 外发部页面访问权限 | NOT NULL, DEFAULT FALSE |
| is_mnf | BOOLEAN | 生产部页面访问权限 | NOT NULL, DEFAULT FALSE |
| is_adm | BOOLEAN | 办公室页面访问权限 | NOT NULL, DEFAULT FALSE |
| last_login | DATETIME | 最后登录时间 | - |
| created_at | DATETIME | 创建时间 | NOT NULL |
| updated_at | DATETIME | 更新时间 | NOT NULL |

**索引**:
- `idx_username` (username) - UNIQUE
- `idx_email` (email) - UNIQUE

---

### 7. 审计日志表 (audit_logs)

**表名**: `audit_logs`
**说明**: 记录所有关键操作的审计日志

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 主键ID | PRIMARY KEY, AUTO_INCREMENT |
| action | VARCHAR(100) | 操作类型 | - |
| action_description | VARCHAR(500) | 操作描述 | - |
| user_id | VARCHAR(255) | 操作用户ID | - |
| entity_type | VARCHAR(100) | 实体类型 | - |
| entity_id | VARCHAR(255) | 实体业务主键 | - |
| order_number | VARCHAR(255) | 订单号 | - |
| old_value | TEXT | 变更前的值 | - |
| new_value | TEXT | 变更后的值 | - |
| ip_address | VARCHAR(50) | 操作来源IP | - |
| time | DATETIME | 操作时间 | - |

**索引**:
- `idx_audit_action` (action)
- `idx_audit_user_id` (user_id)
- `idx_audit_entity_type` (entity_type)
- `idx_audit_entity_id` (entity_id)
- `idx_audit_order_number` (order_number)
- `idx_audit_time` (time)

---

## REST API 接口

### 1. 订单管理接口 (OrderController)

**基础路径**: `/api/orders`

#### 1.1 健康检查
```
GET /api/orders/health
```
**响应**:
```json
{
  "status": "OK",
  "service": "E-Print Backend"
}
```

#### 1.2 创建/更新订单
```
POST /api/orders/create
Content-Type: multipart/form-data
```
**请求参数**:
- `orderData` (String, required): 订单JSON数据
- `isDraft` (Boolean, optional): 是否为草稿
- `salesman` (String, optional): 业务员
- `files` (MultipartFile[], optional): 附件文件列表

**响应**: OrderDTO

**数据库操作**:
1. 插入/更新 `orders` 表
2. 级联插入/更新 `order_items` 表
3. 保存文件到 `documents` 表
4. 记录审计日志到 `audit_logs` 表

#### 1.3 根据订单唯一标识查询
```
GET /api/orders/findById?order_id={order_unique}
```
**响应**: OrderDTO (包含订单明细、附件、审计日志)

**数据库操作**:
1. 查询 `orders` 表 (WHERE order_unique = ?)
2. 关联查询 `order_items` 表
3. 关联查询 `documents` 表
4. 关联查询 `audit_logs` 表

#### 1.4 根据业务员查询订单列表
```
GET /api/orders/findBySales?sales={业务员姓名}
```
**响应**: List<OrderDTO>

**数据库操作**:
- 查询 `orders` 表 (WHERE sales = ? AND is_deleted = '否')

#### 1.5 根据审核员查询订单列表
```
GET /api/orders/findByAudit?audit={审核员姓名}
```
**响应**: List<OrderDTO>

**数据库操作**:
- 查询 `orders` 表 (WHERE audit = ? AND is_deleted = '否')

#### 1.6 根据状态查询订单列表
```
GET /api/orders/status?orderstatus={状态}
```
**状态值**: 草稿、待审核、通过、驳回、生产中、完成、取消

**响应**: List<OrderDTO>

**数据库操作**:
- 查询 `orders` 表 (WHERE status = ? AND is_deleted = '否')

#### 1.7 查询所有订单
```
GET /api/orders/all
```
**响应**: List<OrderDTO>

**数据库操作**:
- 查询 `orders` 表 (WHERE is_deleted = '否')

#### 1.8 更新订单状态
```
POST /api/orders/updateStatus
Content-Type: application/json
```
**请求体**:
```json
{
  "order_unique": "AUTO-123_1",
  "orderstatus": "通过",
  "auditor": "李四"
}
```
**响应**: OrderDTO

**数据库操作**:
1. 更新 `orders` 表的 status 字段
2. 记录审计日志到 `audit_logs` 表
3. **注意**: 如果状态变更为"通过"，工程单由前端通过 `/api/workOrders/create` 接口创建

#### 1.9 删除订单
```
DELETE /api/orders/{id}
```
**响应**: 204 No Content

**数据库操作**:
1. 更新 `orders` 表 (SET is_deleted = '是')
2. 级联软删除 `order_items` 表
3. 级联软删除 `documents` 表
4. 记录审计日志到 `audit_logs` 表

---

### 2. 工程单管理接口 (WorkOrderController)

**基础路径**: `/api/workOrders`

#### 2.1 创建工程单
```
POST /api/workOrders/create
Content-Type: multipart/form-data
```
**请求参数**:
- `workOrderData` (String, required): 工程单JSON数据
- `files` (MultipartFile[], optional): 附件文件列表

**响应**: WorkOrderDTO

**数据库操作**:
1. 插入 `engineering_orders` 表
2. 级联插入 `engineering_order_material_lines` 表
3. 保存文件到 `documents` 表
4. 记录审计日志到 `audit_logs` 表

#### 2.2 根据工程单唯一标识查询
```
GET /api/workOrders/findById?work_unique={work_unique}
```
**响应**: WorkOrderDTO

**数据库操作**:
1. 查询 `engineering_orders` 表 (WHERE work_unique = ?)
2. 关联查询 `engineering_order_material_lines` 表
3. 关联查询 `documents` 表
4. 关联查询 `audit_logs` 表

#### 2.3 根据制单员查询工程单列表
```
GET /api/workOrders/findByClerk?work_clerk={制单员姓名}
```
**响应**: List<WorkOrderDTO>

**数据库操作**:
- 查询 `engineering_orders` 表 (WHERE work_clerk = ? AND is_deleted = '否')

#### 2.4 根据审核员查询工程单列表
```
GET /api/workOrders/findByAudit?work_audit={审核员姓名}
```
**响应**: List<WorkOrderDTO>

**数据库操作**:
- 查询 `engineering_orders` 表 (WHERE work_audit = ? AND is_deleted = '否')

#### 2.5 根据状态查询工程单列表
```
GET /api/workOrders/findWithStatus?orderstatus={状态}
```
**响应**: List<WorkOrderDTO>

**数据库操作**:
- 查询 `engineering_orders` 表 (WHERE review_status = ? AND is_deleted = '否')

#### 2.6 更新工程单状态
```
POST /api/workOrders/updateStatus
Content-Type: application/json
```
**请求体**:
```json
{
  "work_unique": "3_W_1",
  "workorderstatus": "通过"
}
```
**响应**: WorkOrderDTO

**数据库操作**:
1. 更新 `engineering_orders` 表的 review_status 字段
2. 记录审计日志到 `audit_logs` 表

#### 2.7 更新工程单进度
```
POST /api/workOrders/updateProcess
Content-Type: application/json
```
**请求体**:
```json
{
  "work_id": "3_W",
  "process": 50,
  "dangQianJinDu": "进行中"
}
```
**响应**: WorkOrderDTO

**数据库操作**:
1. 更新 `engineering_orders` 表
2. 记录审计日志到 `audit_logs` 表

#### 2.8 为工序分配采购负责人
```
POST /api/workOrders/addHeadPur
Content-Type: application/json
```
**请求体**:
```json
{
  "work_unique": "3_W_1",
  "intermediaID": 0,
  "head_PUR": "张三"
}
```
**响应**: WorkOrderDTO

**数据库操作**:
1. 更新 `engineering_order_material_lines` 表的 head_pur 字段
2. 记录审计日志到 `audit_logs` 表

#### 2.9 为工序分配外发负责人
```
POST /api/workOrders/addHeadOut
Content-Type: application/json
```
**请求体**:
```json
{
  "work_unique": "3_W_1",
  "intermediaID": 0,
  "head_OUT": "李四"
}
```
**响应**: WorkOrderDTO

**数据库操作**:
1. 更新 `engineering_order_material_lines` 表的 head_out 字段
2. 记录审计日志到 `audit_logs` 表

#### 2.10 为工单分配生产装订负责人
```
POST /api/workOrders/addHeadMnf
Content-Type: application/json
```
**请求体**:
```json
{
  "work_unique": "3_W_1",
  "head_MNF": "王五"
}
```
**响应**: WorkOrderDTO

**数据库操作**:
1. 更新 `engineering_orders` 表的 head_mnf 字段
2. 记录审计日志到 `audit_logs` 表

#### 2.11 更新采购进度
```
POST /api/workOrders/updateProgressPur
Content-Type: application/json
```
**请求体**:
```json
{
  "work_unique": "3_W_1",
  "intermediaID": 0,
  "yiGouJianShu": 100
}
```
**响应**: WorkOrderDTO

**数据库操作**:
1. 更新 `engineering_order_material_lines` 表的 yi_gou_jian_shu 字段
2. 记录审计日志到 `audit_logs` 表

#### 2.12 更新外发进度
```
POST /api/workOrders/updateProgressOut
Content-Type: application/json
```
**请求体**:
```json
{
  "work_unique": "3_W_1",
  "intermediaID": 0,
  "kaiShiRiQi": "2024-01-01",
  "yuQiJieShu": "2024-01-10",
  "dangQianJinDu": "进行中"
}
```
**响应**: WorkOrderDTO

**数据库操作**:
1. 更新 `engineering_order_material_lines` 表的时间和进度字段
2. 记录审计日志到 `audit_logs` 表

#### 2.13 更新生产装订进度
```
POST /api/workOrders/updateProgressMnf
Content-Type: application/json
```
**请求体**:
```json
{
  "work_unique": "3_W_1",
  "zhuangDingJianShu": 500
}
```
**响应**: WorkOrderDTO

**数据库操作**:
1. 更新 `engineering_orders` 表的 zhuang_ding_jian_shu 字段
2. 记录审计日志到 `audit_logs` 表

#### 2.14 删除工程单
```
DELETE /api/workOrders/{workUnique}
```
**响应**: 204 No Content

**数据库操作**:
1. 更新 `engineering_orders` 表 (SET is_deleted = '是')
2. 级联软删除 `engineering_order_material_lines` 表
3. 级联软删除 `documents` 表
4. 记录审计日志到 `audit_logs` 表

---

### 3. 用户管理接口 (UserController)

**基础路径**: `/api/users`

#### 3.1 创建用户
```
POST /api/users/create
Content-Type: application/json
```
**请求体**:
```json
{
  "userId": "U001",
  "username": "zhangsan",
  "email": "zhangsan@example.com",
  "fullName": "张三",
  "passwordHash": "hashed_password",
  "isActive": true,
  "order_submit": true,
  "order_audit": false,
  "work_submit": false,
  "work_audit": false,
  "order_check": true,
  "work_check": true,
  "pmc_check": false,
  "pmc_edit": false,
  "delieve_check": false,
  "delieve_edit": false,
  "isSAL": true,
  "isPUR": false,
  "isOUT": false,
  "isMNF": false,
  "isADM": false
}
```
**响应**: UserDTO

**数据库操作**:
1. 插入 `users` 表
2. 记录审计日志到 `audit_logs` 表

#### 3.2 根据用户名查询用户
```
GET /api/users/findByUsername?username={username}
```
**响应**: UserDTO

**数据库操作**:
- 查询 `users` 表 (WHERE username = ?)

#### 3.3 根据用户ID查询用户
```
GET /api/users/findByUserId?userId={userId}
```
**响应**: UserDTO

**数据库操作**:
- 查询 `users` 表 (WHERE user_id = ?)

#### 3.4 获取所有用户
```
GET /api/users/all
```
**响应**: List<UserDTO>

**数据库操作**:
- 查询 `users` 表

#### 3.5 更新用户信息
```
POST /api/users/update
Content-Type: application/json
```
**请求体**:
```json
{
  "userId": "U001",
  "fullName": "张三",
  "email": "zhangsan@example.com",
  "isActive": true,
  "order_submit": true,
  ...
}
```
**响应**: UserDTO

**数据库操作**:
1. 更新 `users` 表
2. 记录审计日志到 `audit_logs` 表

#### 3.6 删除用户
```
DELETE /api/users/{userId}
```
**响应**: 204 No Content

**数据库操作**:
1. 删除 `users` 表中的记录
2. 记录审计日志到 `audit_logs` 表

#### 3.7 更新最后登录时间
```
POST /api/users/updateLastLogin
Content-Type: application/json
```
**请求体**:
```json
{
  "userId": "U001"
}
```
**响应**: 204 No Content

**数据库操作**:
- 更新 `users` 表的 last_login 字段

---

### 4. 黑湖工序查询接口 (BlacklakeProcessController)

**基础路径**: `/api/blacklake`

#### 4.1 查询工序列表
```
POST /api/blacklake/processes/query
Content-Type: application/json
```
**请求体**: BlacklakeProcessQueryDTO

**响应**: Map<?, ?>

**说明**: 此接口用于与黑湖系统集成，查询工序信息

---

## 接口与数据库关系映射

### 订单业务流程

#### 1. 创建订单 (POST /api/orders/create)
```
前端提交 → OrderController → OrderService → 数据库操作
```

**涉及的表**:
1. `orders` - 插入订单主记录
2. `order_items` - 插入订单明细（级联）
3. `documents` - 保存附件（如果有）
4. `audit_logs` - 记录 CREATE_ORDER 操作

**字段映射**:
- 前端 `order_id` → 数据库 `order_number`
- 前端 `order_unique` → 数据库 `order_unique`
- 前端 `orderstatus` → 数据库 `status`
- 前端 `customer` → 数据库 `customer`
- 前端 `productName` → 数据库 `product_name`

#### 2. 订单审核通过 (POST /api/orders/updateStatus)
```
前端提交审核 → OrderController → OrderService.updateOrderStatus() → 更新订单状态
```

**涉及的表**:
1. `orders` - 更新 status 字段为"通过"
2. `audit_logs` - 记录 UPDATE_STATUS 操作

**重要**: 订单审核通过后，工程单由前端通过 `/api/workOrders/create` 接口创建

#### 3. 创建工程单 (POST /api/workOrders/create)
```
前端提交 → WorkOrderController → WorkOrderService → 数据库操作
```

**涉及的表**:
1. `engineering_orders` - 插入工程单主记录
2. `engineering_order_material_lines` - 插入物料工序行（级联）
3. `documents` - 保存附件（如果有）
4. `audit_logs` - 记录 CREATE_WORK_ORDER 操作

**字段映射**:
- 前端 `work_id` → 数据库 `work_id` (格式: order_id + '_W')
- 前端 `work_unique` → 数据库 `work_unique` (格式: work_id + '_' + work_ver)
- 前端 `ke_hu` → 数据库 `ke_hu`
- 前端 `intermedia` 数组 → 数据库 `engineering_order_material_lines` 表

---

### 数据流转图

```
┌─────────────┐
│  前端创建订单  │
└──────┬──────┘
       │
       ↓
┌─────────────────────┐
│  POST /api/orders/create  │
│  - 保存到 orders 表        │
│  - 保存到 order_items 表   │
│  - 保存到 documents 表     │
└──────┬──────────────┘
       │
       ↓
┌─────────────────────┐
│  订单状态: 待审核      │
└──────┬──────────────┘
       │
       ↓
┌─────────────────────────┐
│  审核员审核订单            │
│  POST /api/orders/updateStatus │
│  - 更新 orders.status = '通过' │
└──────┬──────────────────┘
       │
       ↓
┌──────────────────────────┐
│  前端创建工程单             │
│  POST /api/workOrders/create │
│  - 保存到 engineering_orders 表 │
│  - 保存到 material_lines 表     │
└──────┬───────────────────┘
       │
       ↓
┌─────────────────────┐
│  生产进度跟踪          │
│  - 分配负责人          │
│  - 更新采购进度        │
│  - 更新外发进度        │
│  - 更新装订进度        │
└─────────────────────┘
```

---

### 关键字段对照表

#### 订单相关

| 前端字段 | 后端DTO字段 | 数据库字段 | 说明 |
|---------|-----------|----------|------|
| order_id | order_id | order_number | 订单号 |
| order_unique | order_unique | order_unique | 订单唯一标识 |
| orderstatus | orderstatus | status | 订单状态 |
| customer | customer | customer | 客户名称 |
| productName | productName | product_name | 产品名称 |
| sales | sales | sales | 业务员 |
| audit | audit | audit | 审核员 |

#### 工程单相关

| 前端字段 | 后端DTO字段 | 数据库字段 | 说明 |
|---------|-----------|----------|------|
| work_id | work_id | work_id | 工单ID |
| work_unique | work_unique | work_unique | 工单唯一标识 |
| ke_hu | ke_hu | ke_hu | 客户 |
| workorderstatus | workorderstatus | review_status | 工程单状态 |
| work_clerk | work_clerk | work_clerk | 制单员 |
| work_audit | work_audit | work_audit | 审核员 |
| intermedia | intermedia | material_lines | 物料工序数组 |

---

## 重要说明

### 1. 订单与工程单的关系

- **一对一关系**: 一个订单审核通过后，创建一个工程单
- **创建方式**: 前端在审核通过时调用 `/api/workOrders/create` 接口创建
- **唯一标识**:
  - 订单: `order_unique = order_number + '_' + order_ver`
  - 工程单: `work_unique = work_id + '_' + work_ver`
  - 其中 `work_id = order_number + '_W'`

### 2. 软删除机制

所有实体都使用软删除：
- `is_deleted` 字段标记为 "是" 或 "否"
- 查询时默认过滤 `is_deleted = '否'` 的记录
- 级联删除时，子记录也会被标记为软删除

### 3. 审计日志

所有关键操作都会记录到 `audit_logs` 表：
- CREATE_ORDER - 创建订单
- UPDATE_STATUS - 更新订单状态
- CREATE_WORK_ORDER - 创建工程单
- UPDATE_WORK_ORDER - 更新工程单
- DELETE_ORDER - 删除订单
- DELETE_WORK_ORDER - 删除工程单

### 4. 文件上传

- 订单和工程单都支持附件上传
- 文件保存在服务器文件系统
- 文件元数据保存在 `documents` 表
- 通过 `category` 字段区分订单附件和工程单附件

---

## 数据库ER图

```
┌─────────────┐         ┌──────────────────┐
│   orders    │1      * │   order_items    │
│             ├─────────┤                  │
│ order_number│         │   order_id (FK)  │
│ order_unique│         └──────────────────┘
│   status    │
└──────┬──────┘
       │1
       │
       │*
┌──────┴──────────┐
│   documents     │
│                 │
│  order_id (FK)  │
│  category       │
└─────────────────┘

┌──────────────────────┐         ┌────────────────────────────┐
│ engineering_orders   │1      * │ engineering_order_material │
│                      ├─────────┤         _lines             │
│ engineering_order_id │         │ engineering_order_id (FK)  │
│    work_unique       │         │    intermedia_index        │
│   review_status      │         │    head_pur                │
└──────┬───────────────┘         │    head_out                │
       │1                        └────────────────────────────┘
       │
       │*
┌──────┴──────────────────┐
│      documents          │
│                         │
│ engineering_order_id(FK)│
│       category          │
└─────────────────────────┘

┌─────────────┐
│    users    │
│             │
│   user_id   │
│  username   │
│ permissions │
└─────────────┘

┌─────────────┐
│ audit_logs  │
│             │
│   action    │
│ entity_type │
│  entity_id  │
└─────────────┘
```

---

## 总结

本文档详细描述了 E-Print 后端系统的：
1. 7个核心数据库表的结构和关系
2. 4个主要Controller的REST API接口
3. 接口与数据库之间的映射关系
4. 完整的业务流程和数据流转

**核心业务流程**: 订单创建 → 订单审核 → 工程单创建 → 生产进度跟踪

**技术特点**:
- 使用JPA进行ORM映射
- 软删除机制保护数据
- 完整的审计日志追踪
- 支持文件上传和管理
- RESTful API设计
