# E-Print Backend - Spring Boot 版本

这是 E-Print 订单管理系统的 Java Spring Boot 重构版本，从原 Node.js/TypeScript 项目迁移而来。

## 技术栈

- **Java**: 17
- **Spring Boot**: 3.2.3
- **数据库**: MySQL 8.0 + MongoDB 6.0
- **ORM**: Spring Data JPA (MySQL) + Spring Data MongoDB
- **构建工具**: Maven
- **其他**: Lombok, MapStruct

## 项目结构

```
E_print_backend_java/
├── src/main/java/com/eprint/
│   ├── EPrintBackendApplication.java    # 应用入口
│   ├── entity/                          # JPA 实体类
│   │   ├── Order.java                   # 订单实体
│   │   ├── OrderItem.java               # 订单明细
│   │   ├── EngineeringOrder.java        # 工单实体
│   │   ├── MaterialLine.java            # 工单物料行
│   │   ├── Document.java                # 文档附件
│   │   └── mongo/
│   │       └── AuditLog.java            # 审计日志 (MongoDB)
│   ├── dto/                             # 数据传输对象
│   │   ├── OrderDTO.java
│   │   ├── WorkOrderDTO.java
│   │   ├── ProductDTO.java
│   │   ├── IntermediaMaterialDTO.java
│   │   ├── AttachmentDTO.java
│   │   └── AuditLogDTO.java
│   ├── repository/                      # 数据访问层
│   │   ├── mysql/
│   │   │   ├── OrderRepository.java
│   │   │   └── EngineeringOrderRepository.java
│   │   └── mongo/
│   │       └── AuditLogRepository.java
│   ├── service/                         # 业务逻辑层
│   │   ├── OrderService.java
│   │   ├── WorkOrderService.java
│   │   └── FileStorageService.java
│   ├── controller/                      # REST API 控制器
│   │   ├── OrderController.java
│   │   └── WorkOrderController.java
│   ├── mapper/                          # DTO 映射器
│   │   ├── OrderMapper.java
│   │   └── WorkOrderMapper.java
│   └── config/                          # 配置类
│       ├── WebConfig.java               # CORS 配置
│       └── JacksonConfig.java           # JSON 序列化配置
├── src/main/resources/
│   └── application.yml                  # 应用配置
└── pom.xml                              # Maven 依赖配置
```

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

## 配置说明

### 数据库配置 (`application.yml`)

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/E_Bench
    username: root
    password:

  data:
    mongodb:
      uri: mongodb://localhost:27017/E_Bench_Logs
```

### 文件上传配置

```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB

file:
  upload-dir: ./uploads
```

## 运行项目

### 前置要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0
- MongoDB 6.0

### 启动步骤

1. 确保 MySQL 和 MongoDB 服务已启动

2. 创建数据库：
```sql
CREATE DATABASE E_Bench CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. 编译项目：
```bash
mvn clean install
```

4. 运行应用：
```bash
mvn spring-boot:run
```

应用将在 `http://localhost:3000` 启动

## 与原 Node.js 版本的对应关系

| Node.js/TypeScript | Java Spring Boot |
|-------------------|------------------|
| Express.js | Spring MVC |
| Prisma ORM | Spring Data JPA + MongoDB |
| Zod 验证 | Bean Validation (@Valid) |
| Multer | MultipartFile |
| orderService.ts | OrderService.java |
| workOrderService.ts | WorkOrderService.java |
| orderDTO.ts | OrderDTO.java + OrderMapper.java |
| debugLogger.ts | SLF4J + Logback |

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
