#!/bin/bash

echo "======================================"
echo "E-Print Backend - 启动脚本"
echo "======================================"

# 检查 Java 版本
echo "检查 Java 版本..."
java -version

# 检查 Maven
echo ""
echo "检查 Maven..."
mvn -version

# 检查 MySQL
echo ""
echo "检查 MySQL 连接..."
mysql -u root -e "SELECT 1;" 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✓ MySQL 连接成功"
else
    echo "✗ MySQL 连接失败，请确保 MySQL 服务已启动"
fi

# 检查 MongoDB
echo ""
echo "检查 MongoDB 连接..."
mongosh --eval "db.version()" 2>/dev/null || mongo --eval "db.version()" 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✓ MongoDB 连接成功"
else
    echo "✗ MongoDB 连接失败，请确保 MongoDB 服务已启动"
fi

# 创建上传目录
echo ""
echo "创建上传目录..."
mkdir -p uploads
echo "✓ 上传目录已创建"

# 编译项目
echo ""
echo "编译项目..."
mvn clean install -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "======================================"
    echo "✓ 编译成功！"
    echo "======================================"
    echo ""
    echo "启动应用..."
    mvn spring-boot:run
else
    echo ""
    echo "======================================"
    echo "✗ 编译失败，请检查错误信息"
    echo "======================================"
    exit 1
fi
