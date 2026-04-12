# E-Print Backend - Spring Boot 版本

这是 E-Print 订单管理系统的 Java Spring Boot 重构版本。

## 快速启动

README 是新机器启动后端的唯一主入口。

### 前置要求

- JDK 17+
- Maven 3.6+
- Docker Desktop（推荐用来启动 MySQL）

### 推荐流程：新机器 / 新空库

默认 profile 是 `dev`，直接执行以下两步即可：

1. 启动 MySQL
```bash
docker-compose up -d
docker-compose ps
```

2. 启动后端
```bash
mvn spring-boot:run
```

应用默认启动在 `http://localhost:3000`。

在 `dev` 环境下：
- 默认使用 MySQL
- 默认连接 `localhost:3306/E_Bench`
- 开启 `spring.jpa.hibernate.ddl-auto=update`
- 会在新空库场景下自动创建/更新表结构
- JDBC URL 保留 `createDatabaseIfNotExist=true`

### 开发环境数据库配置

`src/main/resources/application-dev.yml` 支持用环境变量覆盖默认连接参数：

```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=E_Bench
export DB_USERNAME=eprint
export DB_PASSWORD=eprint123
```

如果不设置，默认值会直接匹配 `docker-compose.yml` 中的 MySQL 配置。

### 新空库 vs 历史旧库

#### 新空库

不要先执行 `init-mysql.sql`。

推荐做法：
1. 启动 MySQL
2. 直接执行 `mvn spring-boot:run`
3. 让 JPA 根据当前实体自动创建/更新表结构

#### 历史旧库

如果你复用的是旧的本地数据库，并在启动时遇到这类问题：
- `Unknown column ...`
- 字段类型与当前实体不兼容
- 之前手工建过表，结构与当前代码漂移

再执行兼容脚本：

```bash
mysql -u root -p < init-mysql.sql
```

或只针对订单表执行：

```bash
mysql -u root -p E_Bench < orders_schema_migration.sql
```

如果旧库只是本地开发数据，通常更简单的做法是直接重建本地库或 `docker-compose down -v` 后重新启动。

### Docker MySQL 默认配置

`docker-compose.yml` 默认启动：
- MySQL 8.0
- 端口：`3306`
- 数据库：`E_Bench`
- 用户：`eprint`
- 密码：`eprint123`
- Root 密码：`root123`

常用命令：

```bash
docker-compose up -d
docker-compose ps
docker-compose logs -f mysql
docker-compose down
docker-compose down -v
```

进入数据库：

```bash
docker exec -it eprint-mysql mysql -u eprint -peprint123 E_Bench
```

### 验证启动是否成功

启动后可检查：

1. 健康接口：
```bash
curl http://localhost:3000/api/health
```

2. 抽查核心表是否已由 JPA 创建：
```sql
SHOW TABLES LIKE 'orders';
SHOW TABLES LIKE 'engineering_orders';
SHOW TABLES LIKE 'engineering_order_material_lines';
```

### 日志

日志文件：`./logs/eprint.log`

```bash
tail -f ./logs/eprint.log
```

### 生产环境

生产环境不要依赖 `ddl-auto=update` 自动改表。

如需使用生产配置：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 技术栈

- Java 17
- Spring Boot 3.2.3
- MySQL 8.0
- Spring Data JPA
- Maven
- Lombok
- MapStruct
