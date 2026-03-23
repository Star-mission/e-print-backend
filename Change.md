# 后端功能清单

> 标注说明：✅ 已完成 | ⚠️ 部分完成 | ❌ 未实现

---

## 一、订单管理（/api/orders）

| 状态 | 功能 | 接口 | 说明 |
|------|------|------|------|
| ✅ | 创建/更新订单 | POST /api/orders/create | 支持草稿(isDraft=true)和提交，支持文件上传，orderData 为 JSON 字符串 |
| ✅ | 查询所有订单 | GET /api/orders/all | 返回所有订单列表 |
| ✅ | 按业务员查询 | GET /api/orders/findBySales?sales= | 返回该业务员的所有订单 |
| ✅ | 按审核员查询 | GET /api/orders/findByAudit?audit= | 返回该审核员相关的所有订单 |
| ✅ | 按唯一索引查询 | GET /api/orders/findById?order_id= | 返回完整订单（含明细、附件、审计日志）|
| ✅ | 按状态查询 | GET /api/orders/status?orderstatus= | 支持：草稿/待审核/通过/驳回/生产中/完成/取消 |
| ✅ | 更新订单状态 | POST /api/orders/updateStatus | 参数：order_unique, orderstatus, auditor |
| ✅ | 删除订单 | DELETE /api/orders/{id} | 级联删除明细和附件 |
| ✅ | 健康检查 | GET /api/orders/health | 返回服务状态 |
| ❌ | 查询待审核订单（专用） | GET /api/orders/pending | need.md 标注为未实现，目前可用 /status?orderstatus=待审核 代替 |

---

## 二、工程单管理（/api/workOrders）

| 状态 | 功能 | 接口 | 说明 |
|------|------|------|------|
| ✅ | 创建/更新工程单 | POST /api/workOrders/create | workOrderJson 为 JSON 字符串，支持文件上传 |
| ✅ | 按唯一索引查询 | GET /api/workOrders/findById?work_unique= | 返回完整工程单（含物料行、附件、审计日志）|
| ✅ | 按制单员查询 | GET /api/workOrders/findByClerk?work_clerk= | 返回该制单员的所有工程单 |
| ✅ | 按审核员查询 | GET /api/workOrders/findByAudit?work_audit= | 返回该审核员相关的所有工程单 |
| ✅ | 按状态查询 | GET /api/workOrders/findWithStatus?workorderstatus= | 支持：草稿/待审核/通过/驳回/生产中/完成/取消 |
| ✅ | 更新工程单状态 | POST /api/workOrders/updateStatus | 参数：work_unique, workorderstatus |
| ✅ | 更新工序进度（旧） | POST /api/workOrders/updateProcess | 参数：work_id, process, note（更新第一条物料行）|

### 2.1 负责人分配

| 状态 | 功能 | 接口 | 说明 |
|------|------|------|------|
| ✅ | 分配采购负责人 | POST /api/workOrders/addHeadPur | 参数：work_unique, intermediaID, head_PUR |
| ✅ | 分配外发负责人 | POST /api/workOrders/addHeadOut | 参数：work_unique, intermediaID, head_OUT |
| ✅ | 分配生产负责人 | POST /api/workOrders/addHeadMnf | 参数：work_unique, head_MNF |

### 2.2 进度更新

| 状态 | 功能 | 接口 | 说明 |
|------|------|------|------|
| ✅ | 更新采购进度 | POST /api/workOrders/updateProgressPur | 参数：work_unique, intermediaID, yiGouJianShu（已采购件数）|
| ✅ | 更新外发进度 | POST /api/workOrders/updateProgressOut | 参数：work_unique, intermediaID, kaiShiRiQi, yuQiJieShu, dangQianJinDu |
| ✅ | 更新生产装订进度 | POST /api/workOrders/updateProgressMnf | 参数：work_unique, zhuangDingJianShu（已装订件数）|

---

## 三、用户管理（/api/users）

| 状态 | 功能 | 接口 | 说明 |
|------|------|------|------|
| ✅ | 创建用户 | POST /api/users/create | 包含完整权限字段，需传 passwordHash |
| ✅ | 按用户名查询 | GET /api/users/findByUsername?username= | 返回用户信息（不含密码哈希）|
| ✅ | 按用户ID查询 | GET /api/users/findByUserId?userId= | 返回用户信息（不含密码哈希）|
| ✅ | 查询所有用户 | GET /api/users/all | 返回所有用户列表 |
| ✅ | 更新用户信息 | POST /api/users/update | 可更新基础信息和所有权限字段 |
| ✅ | 删除用户 | DELETE /api/users/{userId} | 按 userId 删除 |
| ✅ | 更新最后登录时间 | POST /api/users/updateLastLogin | 参数：userId |
| ❌ | 用户登录认证 | POST /api/users/login | 未实现，暂无 JWT/Session 认证机制 |
| ❌ | 修改密码 | POST /api/users/changePassword | 未实现 |

---

## 四、数据库结构

| 状态 | 表名 | 说明 |
|------|------|------|
| ✅ | orders | 订单主表 |
| ✅ | order_items | 订单明细表 |
| ✅ | engineering_orders | 工程单主表（含 zhuangDingJianShu, headMNF）|
| ✅ | engineering_order_material_lines | 工序物料表（含 yiGouJianShu, headPUR, headOUT, intermediaIndex）|
| ✅ | documents | 附件文档表 |
| ✅ | users | 用户权限表 |
| ✅ | audit_logs | 审计日志表（已从 MongoDB 迁移至 MySQL）|

---

## 五、待办 / 已知问题

| 优先级 | 问题 | 说明 |
|--------|------|------|
| 高 | 用户登录认证未实现 | 当前所有接口无鉴权，需实现 JWT 或 Session 认证 |
| 高 | need.md 中三个进度更新函数有 JS 语法 bug | UpdateProgress_Pur/Out/Mnf 使用了逗号运算符而非函数第二参数，导致请求体为空，需在前端修复 |
| 中 | /api/orders/pending 接口未实现 | 可用 /status?orderstatus=待审核 代替，或单独实现 |
| 中 | Order 实体字段与前端 IOrder 存在差异 | 前端定义了大量字段（如 cpcQueRen, waixiaoFlag 等），后端 Order 实体未完全对应 |
| 低 | 旧的 mongo 包仍存在 | src/main/java/com/eprint/repository/mongo/ 目录已空，可清理 |
