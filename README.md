# E-Print Backend - Spring Boot 版本

这是 E-Print 订单管理系统的 Java Spring Boot 重构版本，从原 Node.js/TypeScript 项目迁移而来。

## 快速启动 🚀

### 方式一：使用 Docker（推荐）

**前置要求：**
- JDK 17+
- Maven 3.6+
- Docker Desktop

**启动步骤：**

1. 启动数据库（MySQL + MongoDB）：
```bash
docker-compose up -d
```

2. 等待数据库启动完成（约 10-20 秒）：
```bash
docker-compose ps
```

3. 运行应用：
```bash
mvn spring-boot:run
```

应用将在 `http://localhost:3000` 启动

**停止服务：**
```bash
# 停止后端
Ctrl + C

# 停止数据库
docker-compose down

# 停止数据库并删除数据
docker-compose down -v
```

### 方式二：使用本地数据库

**前置要求：**
- JDK 17+
- Maven 3.6+
- MySQL 8.0（本地安装）
- MongoDB 6.0（本地安装）

**启动步骤：**

1. 确保 MySQL 和 MongoDB 服务已启动

2. 创建数据库：
```sql
CREATE DATABASE E_Bench CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. 修改配置文件 `src/main/resources/application-dev.yml`：
```yaml
spring:
  datasource:
    username: root        # 修改为你的 MySQL 用户名
    password: your_password  # 修改为你的 MySQL 密码
```

4. 运行应用：
```bash
mvn spring-boot:run
```

应用将在 `http://localhost:3000` 启动

### 黑湖（Blacklake）Token 说明

系统启动时会自动调用黑湖登录接口获取 token，并将结果写入 MySQL 的 `external_tokens` 表，供后续黑湖相关接口复用。

默认配置位置：
- 服务实现：`src/main/java/com/eprint/service/ExternalTokenService.java`
- 环境变量示例：`.env.example`

默认对接参数：
- Base URL：`https://liteweb.blacklake.cn`
- Login Path：`/api/user/v1/users/_login`
- 环境变量：`BLACKLAKE_PHONE`、`BLACKLAKE_PASSWORD`

刷新时机：
- 应用启动后立即刷新一次
- 之后按 `external.token.refresh-interval-ms` 定时刷新

如果未配置 `BLACKLAKE_PHONE` 或 `BLACKLAKE_PASSWORD`，本次 token 刷新会失败，但不会阻止后端服务启动。

---

## 技术栈

- **Java**: 17
- **Spring Boot**: 3.2.3
- **数据库**: MySQL 8.0 + MongoDB 7.0
- **ORM**: Spring Data JPA (MySQL) + Spring Data MongoDB
- **构建工具**: Maven
- **其他**: Lombok, MapStruct

## 项目结构

```
E_print_backend_java/
├── docker-compose.yml                    # Docker 数据库配置
├── .env.example                          # 环境变量示例
├── pom.xml                               # Maven 依赖配置
├── src/main/
│   ├── java/com/eprint/
│   │   ├── EPrintBackendApplication.java # 应用入口
│   │   ├── entity/                       # JPA 实体类
│   │   │   ├── Order.java                # 订单实体
│   │   │   ├── OrderItem.java            # 订单明细
│   │   │   ├── EngineeringOrder.java     # 工单实体
│   │   │   ├── MaterialLine.java         # 工单物料行
│   │   │   ├── Document.java             # 文档附件
│   │   │   └── mongo/
│   │   │       └── AuditLog.java         # 审计日志 (MongoDB)
│   │   ├── dto/                          # 数据传输对象
│   │   │   ├── OrderDTO.java
│   │   │   ├── WorkOrderDTO.java
│   │   │   ├── ProductDTO.java
│   │   │   ├── IntermediaMaterialDTO.java
│   │   │   ├── AttachmentDTO.java
│   │   │   └── AuditLogDTO.java
│   │   ├── repository/                   # 数据访问层
│   │   │   ├── mysql/
│   │   │   │   ├── OrderRepository.java
│   │   │   │   └── EngineeringOrderRepository.java
│   │   │   └── mongo/
│   │   │       └── AuditLogRepository.java
│   │   ├── service/                      # 业务逻辑层
│   │   │   ├── OrderService.java
│   │   │   ├── WorkOrderService.java
│   │   │   └── FileStorageService.java
│   │   ├── controller/                   # REST API 控制器
│   │   │   ├── OrderController.java
│   │   │   └── WorkOrderController.java
│   │   ├── mapper/                       # DTO 映射器
│   │   │   ├── OrderMapper.java
│   │   │   └── WorkOrderMapper.java
│   │   └── config/                       # 配置类
│   │       ├── WebConfig.java            # CORS 配置
│   │       └── JacksonConfig.java        # JSON 序列化配置
│   └── resources/
│       ├── application.yml               # 主配置文件
│       ├── application-dev.yml           # 开发环境配置
│       └── application-prod.yml          # 生产环境配置
└── uploads/                              # 文件上传目录
```

---

## 核心功能

### 订单管理 (Order Management)
- 创建/更新订单（支持草稿和提交模式）
- 按销售员、审核员、状态查询订单
- 订单状态更新（草稿 → 待审核 → 通过 → 生产中 → 完成）
- 订单通过后自动创建工单
- 文件附件上传

### 工单管理 (Engineering Order Management)
- 创建/更新工单
- 按文员、审核员、状态查询工单
- 工单状态更新
- 生产进度跟踪
- 物料行管理

### 审计日志 (Audit Logging)
- 所有操作自动记录到 MongoDB
- 支持按订单号、用户、实体类型查询

---

## 数据库设计

### MySQL 数据库（E_Bench）- 业务数据

#### orders（订单表）
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（自增） |
| orderNumber | VARCHAR | 订单号（业务主键，格式：AUTO-时间戳-随机数） |
| orderVer | INT | 订单版本号 |
| orderUnique | VARCHAR | 唯一标识（orderNumber_版本号） |
| status | ENUM | 订单状态（草稿、待审核、通过、驳回、生产中、完成、取消） |
| sales | VARCHAR | 业务员姓名 |
| audit | VARCHAR | 审核员姓名 |
| customer | VARCHAR | 客户名称 |
| productName | VARCHAR | 产品名称 |
| customerPO | VARCHAR | 客户采购订单号 |
| isbn | VARCHAR | ISBN 编号 |
| dingDanShuLiang | INT | 订单数量 |
| chuYangShuLiang | INT | 出样数量 |
| chaoBiLiShuLiang | INT | 超比例数量 |
| guigeGaoMm | DOUBLE | 规格高度（毫米） |
| guigeKuanMm | DOUBLE | 规格宽度（毫米） |
| guigeHouMm | DOUBLE | 规格厚度（毫米） |
| fuLiaoShuoMing | TEXT | 辅料说明 |
| wuLiaoShuoMing | TEXT | 物料说明 |
| zhiLiangYaoQiu | TEXT | 质量要求 |
| beiZhu | TEXT | 备注信息 |
| keLaiXinXi | TEXT | 客来信息 |
| xiaZiliaodaiRiqi1/2 | DATETIME | 下资料袋日期 |
| yinzhangRiqi1/2 | DATETIME | 印章日期 |
| jiaoHuoRiQi1/2 | DATETIME | 交货日期 |
| createdAt | DATETIME | 创建时间 |
| updatedAt | DATETIME | 更新时间 |

**索引：** idx_order_unique（唯一索引）

#### order_items（订单明细表）
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（自增） |
| order_id | BIGINT | 关联订单 ID（外键） |
| productName | VARCHAR | 产品名称 |
| quantity | INT | 数量 |
| specification | VARCHAR | 规格 |
| unit | VARCHAR | 单位 |
| unitPrice | DOUBLE | 单价 |
| totalPrice | DOUBLE | 总价 |
| notes | TEXT | 备注 |

#### engineering_orders（工单表）
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（自增） |
| engineeringOrderId | VARCHAR | 工单号（业务主键） |
| workId | VARCHAR | 工作单号 |
| workVer | INT | 版本号 |
| workUnique | VARCHAR | 唯一标识（workId_版本号） |
| reviewStatus | ENUM | 审核状态 |
| workClerk | VARCHAR | 制单员 |
| workAudit | VARCHAR | 审核员 |
| keHu | VARCHAR | 客户 |
| po | VARCHAR | PO 号 |
| chengPinMingCheng | VARCHAR | 成品名称 |
| chuYangShu | INT | 出样数 |
| chaoBiLi | INT | 超比例 |
| beiZhu | TEXT | 备注 |
| createdAt | DATETIME | 创建时间 |
| updatedAt | DATETIME | 更新时间 |

**索引：** idx_work_unique（唯一索引）

#### engineering_order_material_lines（工单物料明细表）
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（自增） |
| engineering_order_id | BIGINT | 关联工单 ID（外键） |
| materialName | VARCHAR | 物料名称 |
| specification | VARCHAR | 规格 |
| quantity | INT | 数量 |
| unit | VARCHAR | 单位 |
| kaiShiShiJian | DATETIME | 开始时间 |
| jieShuShiJian | DATETIME | 结束时间 |
| dangQianJinDu | INT | 当前进度（0-100） |
| notes | TEXT | 备注 |

#### documents（附件表）
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（自增） |
| order_id | BIGINT | 关联订单 ID（外键，可选） |
| engineering_order_id | BIGINT | 关联工单 ID（外键，可选） |
| fileName | VARCHAR | 文件名 |
| filePath | VARCHAR | 文件路径 |
| fileType | VARCHAR | 文件类型 |
| fileSize | BIGINT | 文件大小（字节） |
| category | ENUM | 分类（OrderAttachment/WorkOrderAttachment） |
| uploadedAt | DATETIME | 上传时间 |

### MongoDB 数据库（E_Bench_Logs）- 审计日志

#### audit_logs（审计日志集合）
```json
{
  "_id": "ObjectId",
  "entityType": "Order/EngineeringOrder",
  "entityId": "订单或工单的唯一标识",
  "action": "CREATE/UPDATE/DELETE/STATUS_CHANGE",
  "actionDescription": "操作描述",
  "userId": "操作用户",
  "oldValue": {},  // 修改前的值
  "newValue": {},  // 修改后的值
  "timestamp": "ISODate",
  "ipAddress": "IP地址"
}
```

### 数据库关系图

```
orders (1) ----< (N) order_items
orders (1) ----< (N) documents

engineering_orders (1) ----< (N) engineering_order_material_lines
engineering_orders (1) ----< (N) documents
```

---

---

## API 端点

### 订单 API (`/api/orders`)

| 方法 | 端点 | 说明 |
|------|------|------|
| GET | `/api/health` | 健康检查 |
| POST | `/api/orders/create` | 创建/更新订单 |
| GET | `/api/orders/findById?order_id=...` | 按 ID 查询订单 |
| GET | `/api/orders/findBySales?sales=...` | 按销售员查询 |
| GET | `/api/orders/findByAudit?audit=...` | 按审核员查询 |
| GET | `/api/orders/status?orderstatus=...` | 按状态查询 |
| GET | `/api/orders/all` | 查询所有订单 |
| POST | `/api/orders/updateStatus` | 更新订单状态 |
| DELETE | `/api/orders/{id}` | 删除订单 |

### 工单 API (`/api/workOrders`)

| 方法 | 端点 | 说明 |
|------|------|------|
| POST | `/api/workOrders/create` | 创建/更新工单 |
| GET | `/api/workOrders/findById?work_unique=...` | 按 ID 查询工单 |
| GET | `/api/workOrders/findByClerk?work_clerk=...` | 按文员查询 |
| GET | `/api/workOrders/findByAudit?work_audit=...` | 按审核员查询 |
| GET | `/api/workOrders/findWithStatus?workorderstatus=...` | 按状态查询 |
| POST | `/api/workOrders/updateStatus` | 更新工单状态 |
| POST | `/api/workOrders/updateProcess` | 更新生产进度 |

---

## 配置说明

### Docker 数据库配置（开发环境）

`docker-compose.yml` 包含：

- **MySQL 8.0**
  - 端口：3306
  - 数据库：E_Bench
  - 用户名：eprint
  - 密码：eprint123
  - Root 密码：root123
  - 数据持久化：mysql-data 卷

- **MongoDB 7.0**
  - 端口：27017
  - 数据库：E_Bench_Logs
  - 数据持久化：mongodb-data 卷

**常用命令：**
```bash
# 查看容器状态
docker-compose ps

# 查看日志
docker-compose logs -f

# 重启数据库
docker-compose restart

# 进入 MySQL 容器
docker exec -it eprint-mysql mysql -u eprint -peprint123 E_Bench

# 进入 MongoDB 容器
docker exec -it eprint-mongodb mongosh E_Bench_Logs
```

### 应用配置文件

项目支持多环境配置：

- **application.yml** - 主配置文件，默认激活 dev 环境
- **application-dev.yml** - 开发环境配置
  - 使用 Docker 数据库（eprint/eprint123）
  - 显示 SQL 日志
  - 自动创建/更新表结构（ddl-auto: update）
  - 端口：3000

- **application-prod.yml** - 生产环境配置
  - 使用环境变量配置数据库
  - 关闭 SQL 日志
  - 只验证表结构（ddl-auto: validate）
  - 端口：8080（可通过环境变量修改）

**切换环境：**
```bash
# 开发环境（默认）
mvn spring-boot:run

# 生产环境
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### 文件上传配置

```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB      # 单个文件最大 10MB
      max-request-size: 10MB   # 请求最大 10MB

file:
  upload-dir: ./uploads          # 上传目录
```


## 环境配置

项目支持多环境配置：

- **开发环境**（默认）：`application-dev.yml`
  - 使用 Docker 数据库
  - 显示 SQL 日志
  - 自动创建/更新表结构

- **生产环境**：`application-prod.yml`
  - 使用环境变量配置
  - 关闭 SQL 日志
  - 只验证表结构，不自动修改

**切换环境：**
```bash
# 开发环境（默认）
mvn spring-boot:run

# 生产环境
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## 状态枚举

**订单/工单状态**:
- 草稿
- 待审核
- 通过
- 驳回
- 生产中
- 完成
- 取消

## 开发说明

- 使用 Lombok 减少样板代码
- 使用 MapStruct 进行 DTO 转换（当前使用手动映射）
- 事务管理：使用 `@Transactional` 注解
- 日志：使用 SLF4J + Logback
- 异常处理：基础异常处理已实现，可扩展全局异常处理器

## 待完善功能

1. 用户认证与授权（当前审计日志使用 "SYSTEM" 用户）
2. 全局异常处理器
3. 请求参数验证（Bean Validation）
4. API 文档（Swagger/OpenAPI）
5. 单元测试和集成测试
6. Docker 容器化部署
