# 数据库启动与兼容说明

## 当前推荐路径

开发环境的稳定启动路径是：

1. 启动 MySQL
2. 启动后端
3. 让 JPA 在 `dev` 环境自动创建/更新表结构

也就是说，新机器或新空库场景下，数据库初始化主路径不是手工执行 SQL，而是：

```bash
docker-compose up -d
mvn spring-boot:run
```

## 开发环境默认行为

默认 profile 为 `dev`，见 `src/main/resources/application.yml`。

`src/main/resources/application-dev.yml` 当前行为：
- 使用 MySQL 数据源
- `spring.jpa.hibernate.ddl-auto=update`
- JDBC URL 启用 `createDatabaseIfNotExist=true`
- 数据库连接可通过环境变量覆盖：`DB_HOST`、`DB_PORT`、`DB_NAME`、`DB_USERNAME`、`DB_PASSWORD`

默认值与 `docker-compose.yml` 保持一致：
- host: `localhost`
- port: `3306`
- db: `E_Bench`
- user: `eprint`
- password: `eprint123`

## 新空库初始化

适用场景：
- 新电脑首次拉代码
- 本地 Docker 卷已清空
- 本地数据库里还没有业务表

步骤：

```bash
docker-compose up -d
mvn spring-boot:run
```

说明：
- Spring Boot 会连接 MySQL
- 如数据库不存在，会因 `createDatabaseIfNotExist=true` 自动创建 `E_Bench`
- JPA 会根据当前实体创建/更新表结构
- 核心表包括：`orders`、`engineering_orders`、`engineering_order_material_lines`、`audit_logs`

## 历史旧库兼容修复

适用场景：
- 本地数据库沿用过旧版本表结构
- 启动时报 `Unknown column`
- 某些列曾手工建成过窄字段，和当前实体不一致
- 文档脚本和真实实体曾经不同步，导致历史库结构漂移

此时再使用兼容 SQL，而不是把它当成首次启动步骤。

### 兼容脚本

- `init-mysql.sql`：旧库兼容入口脚本
- `orders_schema_migration.sql`：只针对 `orders` 表的补列/字段修正脚本

执行示例：

```bash
mysql -u root -p < init-mysql.sql
```

或：

```bash
mysql -u root -p E_Bench < orders_schema_migration.sql
```

说明：
- 这些脚本现在只服务于历史库修复
- 如果目标表不存在，脚本会跳过补丁并提示直接启动应用
- 对于纯本地开发库，若无保留数据需求，优先考虑直接删库重建

## 关于 MongoDB 的说明

当前项目的业务数据和审计日志都落在 MySQL/JPA 上。

例如：
- `orders`
- `engineering_orders`
- `engineering_order_material_lines`
- `audit_logs`

因此，新同事启动当前后端时，不需要额外准备 MongoDB。

## 健康检查

应用启动后可通过以下接口确认服务正常：

```bash
curl http://localhost:3000/api/health
```

## 常见排查

### 1. 应用启动失败，提示连不上 MySQL

先确认：

```bash
docker-compose ps
docker-compose logs -f mysql
```

### 2. 应用启动失败，提示列不存在

说明你连接到的是历史旧库。优先选择：
1. 本地无保留数据需求时，重建本地库
2. 需要保留旧数据时，执行 `init-mysql.sql` 或 `orders_schema_migration.sql`

### 3. 文档与实际行为不一致怎么办

以以下文件为准：
- `README.md`：新机器启动主入口
- `src/main/resources/application.yml`
- `src/main/resources/application-dev.yml`
- 当前 JPA 实体定义
