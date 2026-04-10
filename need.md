## 进度更新接口

### 2. POST /api/workOrders/updateProgressPur
- 位置：`src/main/java/com/eprint/controller/WorkOrderController.java:250`
- 服务层：`src/main/java/com/eprint/service/WorkOrderService.java:409`
- 作用：更新采购进度
- 请求字段：
  - `work_unique`
  - `intermediaID`
  - `yiGouJianShu`
- 更新逻辑：
  1. 根据 `work_unique` 查询工程单，查不到直接抛异常。
  2. 取出工程单下的 `materialLines` 列表。
  3. 请求里传的是 DTO 字段 `intermediaID`（见 `src/main/java/com/eprint/dto/IntermediaMaterialDTO.java:10`）；后端实体也按 `intermediaID` 语义保存并映射该字段（代码层字段见 `src/main/java/com/eprint/entity/MaterialLine.java:21`，数据库列继续兼容旧列名 `intermedia_index`）。
  4. `updateProgressPur` 会在当前工程单的 `materialLines` 中按 `line.intermediaID == request.intermediaID` 精确匹配目标项；如果 `intermediaID` 为空或找不到匹配项，会直接抛出明确异常，避免误更新到列表中错误位置。
  5. 将目标工序的 `yiGouJianShu` 更新为请求传入值。
  6. 保存整个工程单到数据库。
  7. 写入一条审计日志：
     - `action = UPDATE_PROGRESS_PUR`
     - `description = 更新采购进度`
     - `newValue = yiGouJianShu.toString()`
  8. 返回最新的 `WorkOrderDTO`，并附带该工程单的审计日志。

### 3. POST /api/workOrders/updateProgressOut
- 位置：`src/main/java/com/eprint/controller/WorkOrderController.java:269`
- 服务层：`src/main/java/com/eprint/service/WorkOrderService.java:441`
- 作用：更新外发进度
- 请求字段：
  - `work_unique`
  - `intermediaID`
  - `kaiShiRiQi`
  - `yuQiJieShu`
  - `dangQianJinDu`
- 更新逻辑：
  1. 根据 `work_unique` 查询工程单，查不到直接抛异常。
  2. 取出工程单下的 `materialLines` 列表。
  3. 请求里传的是 DTO 字段 `intermediaID`（见 `src/main/java/com/eprint/dto/IntermediaMaterialDTO.java:10`）；后端实体也按 `intermediaID` 语义保存并映射该字段（代码层字段见 `src/main/java/com/eprint/entity/MaterialLine.java:21`，数据库列继续兼容旧列名 `intermedia_index`）。
  4. `updateProgressOut` 会在当前工程单的 `materialLines` 中按 `line.intermediaID == request.intermediaID` 精确匹配目标项；如果 `intermediaID` 为空或找不到匹配项，会直接抛出明确异常，避免误更新到列表中错误位置。
  5. 如果 `kaiShiRiQi` 不为 `null`，则用 `LocalDateTime.parse(kaiShiRiQi)` 解析后写入该工序的开始时间 `kaiShiShiJian`。
  6. 如果 `yuQiJieShu` 不为 `null`，则用 `LocalDateTime.parse(yuQiJieShu)` 解析后写入该工序的结束时间 `jieShuShiJian`。
  7. 将该工序的 `dangQianJinDu` 更新为请求传入值。
  8. 保存整个工程单到数据库。
  9. 写入一条审计日志：
     - `action = UPDATE_PROGRESS_OUT`
     - `description = 更新外发进度`
     - `newValue = dangQianJinDu`
  10. 返回最新的 `WorkOrderDTO`，并附带该工程单的审计日志。

### 4. POST /api/workOrders/updateProgressMnf
- 位置：`src/main/java/com/eprint/controller/WorkOrderController.java:306`
- 服务层：`src/main/java/com/eprint/service/WorkOrderService.java:480`
- 作用：更新生产装订进度
- 请求字段：
  - `work_unique`
  - `zhuangDingJianShu`
- 更新逻辑：
  1. 根据 `work_unique` 查询工程单，查不到直接抛异常。
  2. 直接更新工程单主表字段 `zhuangDingJianShu`。
  3. 保存工程单到数据库。
  4. 写入一条审计日志：
     - `action = UPDATE_PROGRESS_MNF`
     - `description = 更新生产装订进度`
     - `newValue = zhuangDingJianShu.toString()`
  5. 返回最新的 `WorkOrderDTO`，并附带该工程单的审计日志。
