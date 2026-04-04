# 修复订单审核通过后工程单重复创建问题 - 实施总结

## 实施日期
2026-04-04

## 问题描述
订单审核通过时，工程单被创建了两次：
- 前端创建：`work_unique = "3_W_1"` (work_id = "3_W")
- 后端自动创建：`work_unique = "3_1"` (workId = "3")

## 根本原因
前端和后端同时创建工程单，导致重复：
1. 前端在 OrderReviewer.vue 中调用 `POST /api/workOrders/create`
2. 后端在 OrderService.updateOrderStatus() 中自动创建工程单

## 实施的修改

### 1. OrderService.java (行 350-360)
**修改前**：
```java
// 如果订单状态变更为"通过"，自动创建工单
if (newStatus == Order.OrderStatus.通过) {
    log.info("订单已通过，开始自动创建工单");
    try {
        workOrderService.createWorkOrderFromOrder(savedOrder);
        log.info("工单创建成功");
    } catch (Exception e) {
        log.error("工单创建失败: {}", e.getMessage(), e);
        throw e;
    }
}
```

**修改后**：
```java
// 订单通过后，工程单由前端通过 /workOrders/create 接口创建
if (newStatus == Order.OrderStatus.通过) {
    log.info("订单已通过，工程单将由前端创建");
    // 注意：工程单创建逻辑已移至前端，通过 POST /api/workOrders/create 接口完成
    // 这样可以让审核员在审核时同时填写工程单的详细信息
}
```

### 2. WorkOrderService.java (行 86-87)
添加 @Deprecated 注解和文档说明：
```java
/**
 * 从订单自动创建工单（已废弃）
 *
 * @deprecated 此方法已不再使用。工程单创建已改为由前端在审核通过时手动创建。
 *             前端通过 POST /api/workOrders/create 接口提交工程单数据。
 *             保留此方法仅用于向后兼容或特殊场景。
 */
@Deprecated
@Transactional
public void createWorkOrderFromOrder(Order order) {
```

## 编译验证
✅ 代码编译成功（mvn clean compile）

## 预期效果
1. 订单审核通过时，后端只更新订单状态，不再自动创建工程单
2. 前端调用 `/api/workOrders/create` 接口创建工程单
3. 每个订单只会创建一个工程单
4. 日志显示：`订单已通过，工程单将由前端创建`

## 测试步骤
1. 启动应用：`mvn spring-boot:run`
2. 创建测试订单并提交审核
3. 审核员点击"通过"按钮
4. 检查日志：应该只看到一次工程单创建
5. 检查数据库：每个订单只有一个工程单记录

## 数据库清理（如需要）
```sql
-- 查找重复的工程单
SELECT work_id, COUNT(*) as count
FROM engineering_orders
WHERE is_deleted = '否'
GROUP BY work_id
HAVING COUNT(*) > 1;

-- 删除后端自动创建的重复记录（保留前端创建的）
UPDATE engineering_orders
SET is_deleted = '是'
WHERE work_unique NOT LIKE '%_W_%';
```

## 风险评估
- 风险等级：低
- 影响范围：仅影响订单审核通过后的工程单创建流程
- 回滚方案：恢复 OrderService.java 中的自动创建逻辑

## 后续建议
1. 添加单元测试验证订单审核流程
2. 监控生产环境日志，确认不再出现重复创建
3. 考虑在数据库层面添加更严格的唯一约束
