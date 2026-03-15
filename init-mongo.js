// 连接到 MongoDB
use E_Bench_Logs

// 创建审计日志集合的索引
db.audit_logs.createIndex({ "orderNumber": 1 })
db.audit_logs.createIndex({ "userId": 1 })
db.audit_logs.createIndex({ "entityType": 1, "entityId": 1 })
db.audit_logs.createIndex({ "time": -1 })
db.audit_logs.createIndex({ "action": 1 })

print("MongoDB indexes created successfully!")

// 验证索引
db.audit_logs.getIndexes()
