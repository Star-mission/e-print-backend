# 黑湖智造 OpenAPI 查询接口开发文档

## 通用说明

- **认证**：所有接口 Header 中传 `X-AUTH: <token>`（来自登录接口），`Content-Type: application/json`
- **协议**：HTTPS，请求方式 POST（查询附件接口为 GET）
- **统一响应**：`code`（成功 `01000000`）、`data`、`msg`；部分接口含 `reference.allCustomFieldMetadata`（自定义字段 Metadata）
- **分页参数**：统一格式 `"page": {"pageNum": 1, "pageSize": 10}`，最大每页 100 条
- **时间格式**：统一 `yyyy-MM-dd HH:mm:ss`

---

## 目录

### 基础数据
1. [单位列表查询](#1-单位列表查询)
2. [产品列表查询](#2-产品列表查询)
3. [工序列表查询](#3-工序列表查询)
4. [工艺路线查询](#4-工艺路线查询)
5. [物料清单BOM查询](#5-物料清单bom查询)
6. [用户列表查询](#6-用户列表查询)
7. [查询客户](#7-查询客户)
8. [查询供应商](#8-查询供应商)
9. [查询设备](#9-查询设备)

### 工单与任务
10. [查询工单](#10-查询工单)
11. [查询工单当前工序](#11-查询工单当前工序)
12. [任务列表查询](#12-任务列表查询)
13. [查询自定义表单](#13-查询自定义表单)
14. [查询用料清单](#14-查询用料清单)
15. [查询跨厂工单](#15-查询跨厂工单)

### 报工与库存
16. [查询报工](#16-查询报工)
17. [查询出入库单](#17-查询出入库单)
18. [查询库存余额明细](#18-查询库存余额明细)
19. [查询调整单](#19-查询调整单)

### 订单
20. [查询销售订单](#20-查询销售订单)
21. [查询采购订单](#21-查询采购订单)
22. [查询供应商价目表](#22-查询供应商价目表)
23. [查询客户价目表](#23-查询客户价目表)

### 财务
24. [查询应收单详情](#24-查询应收单详情)
25. [查询应收单列表](#25-查询应收单列表)
26. [查询收款单列表](#26-查询收款单列表)
27. [客户账本查询](#27-客户账本查询)
28. [查询应付单详情](#28-查询应付单详情)
29. [查询应付单列表](#29-查询应付单列表)
30. [查询付款单列表](#30-查询付款单列表)
31. [供应商账本查询](#31-供应商账本查询)
32. [待开票列表查询](#32-待开票列表查询)

### 质检
33. [查询检验项](#33-查询检验项)
34. [查询检验规范](#34-查询检验规范)
35. [查询检验单](#35-查询检验单)

### 扩展查询
36. [查询自定义字段Metadata](#36-查询自定义字段metadata)
37. [查询关联对象业务实体](#37-查询关联对象业务实体)
38. [查询附件](#38-查询附件)
39. [查询操作日志列表](#39-查询操作日志列表)
40. [查询操作日志详情](#40-查询操作日志详情)

---

## 1. 单位列表查询

**路径：** `POST /api/dytin/external/unit/queryList2`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| name | String | 否 | 单位名称 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| pageNum | Number | 当前页 |
| pageSize | Number | 每页条数 |
| total | Number | 总数 |
| data[].id | Number | 单位ID |
| data[].name | String | 单位名称 |
| data[].remark | String | 备注 |


---

## 2. 产品列表查询

**路径：** `POST /api/dytin/external/product/queryList2`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| productCode | String | 否 | 产品编号 |
| productName | String | 否 | 产品名称 |
| createdAtStart | String | 否 | 创建开始时间 |
| createdAtEnd | String | 否 | 创建结束时间 |
| updatedAtStart | String | 否 | 更新开始时间 |
| updatedAtEnd | String | 否 | 更新结束时间 |
| originType | Number[] | 否 | 产品属性：0-自制 / 1-外购 / 2-委外 |
| vendorCode | String | 否 | 默认供应商编号（精确） |
| criticalFlag | Number[] | 否 | 关键部件：0-否 / 1-是 |
| productionType | Number[] | 否 | 制造模式：1-MTO / 2-MTS |
| page | Object | 否 | 分页，默认10条，最大100条 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 产品ID |
| productCode | String | 产品编号 |
| productName | String | 产品名称 |
| productSpecification | String | 产品规格 |
| unit | String | 单位 |
| originType | Number | 产品属性 |
| stockQty | Number | 库存数量 |
| costPrice | Number | 成本单价 |
| salesPrice | Number | 销售单价 |
| safetyQty | Number | 安全库存 |
| prcessRoutingCode | String | 工艺路线编号 |
| vendorCode | String | 供应商编号 |
| customFieldValues | Object[] | 自定义字段值 |

> `reference.allCustomFieldMetadata` 包含自定义字段定义

---

## 3. 工序列表查询

**路径：** `POST /api/dytin/external/process/queryList`

> ⚠️ 查询参数不能全部为空；调用账户须有管理员权限

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| processCode | String | 否 | 工序编号（模糊） |
| processName | String | 否 | 工序名称（模糊） |
| createdAtGte | String | 否 | 创建时间 >= |
| createdAtLte | String | 否 | 创建时间 <= |
| updatedAtGte | String | 否 | 更新时间 >= |
| updatedAtLte | String | 否 | 更新时间 <= |
| page | Object | 否 | 分页，默认10条 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 工序ID |
| code | String | 工序编号 |
| name | String | 工序名称 |
| outputRate | Number | 报工数配比 |
| outputBlAll | Boolean | 是否所有人可报工 |
| outputUsers | List | 报工权限用户列表（含id/username/name） |
| outputGroups | List | 报工权限部门列表（含id/name/code） |
| fields | List | 工序采集数据（类型：1文本/2数字/3时间/4照片） |
| customFieldValues | Object[] | 自定义字段值 |


---

## 4. 工艺路线查询

**路径：** `POST /api/dytin/external/processRouting/queryList2`

> `processRoutingCode` 与 `processRoutingCodeList` 互斥，不可同时传

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| processRoutingCode | String | 否 | 工艺路线编号（模糊） |
| processRoutingCodeList | String[] | 否 | 工艺路线编号集合（精确） |
| processRoutingName | String | 否 | 工艺路线名称（模糊） |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围 |
| updatedAtGte / updatedAtLte | String | 否 | 更新时间范围 |
| page | Object | 否 | 分页，最大100条 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| processRoutingCode | String | 工艺路线编号 |
| processRoutingName | String | 工艺路线名称 |
| processRoutingSteps[].seq | Number | 工序顺序号 |
| processRoutingSteps[].type | Number | 类型：1-工序 / 2-子工艺路线 |
| processRoutingSteps[].code | String | 工序编号 |
| processRoutingSteps[].name | String | 工序名称 |
| processRoutingSteps[].outputRate | Number | 报工数配比 |
| processRoutingSteps[].customFieldValues | Object[] | 工序自定义字段（type=1时有值） |

---

## 5. 物料清单BOM查询

**路径：** `POST /api/dytin/external/materials/queryList`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| lastProductCode | String | 否 | 父项产品编号 |
| nextProductCode | String | 否 | 子项产品编号 |
| feedProcessCode | String | 否 | 投料工序编号 |
| remark | String | 否 | 备注 |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围 |
| updatedAtGte / updatedAtLte | String | 否 | 更新时间范围 |
| page | Object | 否 | 分页 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| lastProductCode | String | 父项产品编号 |
| nextProductCode | String | 子项产品编号 |
| unitQty | Number | 单位用量 |
| feedProcessCode | String | 投料工序编号 |
| remark | String | 备注 |

---

## 6. 用户列表查询

**路径：** `POST /api/dytin/external/user/queryList2`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| accounts | String[] | 否 | 账号列表（与其他参数互斥，传此参数则其他参数无效） |
| name | String | 否 | 用户姓名（模糊） |
| enabled | Boolean | 否 | true-启用 / false-禁用 / 不传-全部 |
| page | Object | 否 | 分页，默认50条 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 用户ID |
| username | String | 账号 |
| name | String | 姓名 |
| phone | String | 手机号 |
| enabled | Boolean | 是否启用 |
| groups | Object[] | 所属部门 |


---

## 7. 查询客户

**路径：** `POST /api/dytin/external/customer/queryList`

> ⚠️ 请求参数不支持全部为空

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| customerCode | String | 否 | 客户编号（模糊） |
| customerName | String | 否 | 客户名称（模糊） |
| customerFullName | String | 否 | 客户全称（模糊） |
| enabled | Boolean | 否 | 是否启用 |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围 |
| updatedAtGte / updatedAtLte | String | 否 | 更新时间范围 |
| page | Object | 否 | 分页 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 客户ID |
| customerCode | String | 客户编号 |
| customerName | String | 客户名称 |
| customerFullName | String | 客户全称 |
| contactName | String | 联系人 |
| contactNumber | String | 联系电话 |
| contactAddress | String | 联系地址 |
| enabled | Boolean | 是否启用 |

---

## 8. 查询供应商

**路径：** `POST /api/dytin/external/vendor/queryList`

> ⚠️ 请求参数不支持全部为空

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| vendorCode | String | 否 | 供应商编号（模糊） |
| vendorName | String | 否 | 供应商名称（模糊） |
| vendorFullName | String | 否 | 供应商全称（模糊） |
| enabled | Boolean | 否 | 是否启用 |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围 |
| page | Object | 否 | 分页 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 供应商ID |
| vendorCode | String | 供应商编号 |
| vendorName | String | 供应商名称 |
| contactName | String | 联系人 |
| contactNumber | String | 联系电话 |
| enabled | Boolean | 是否启用 |

---

## 9. 查询设备

**路径：** `POST /api/dytin/external/equipment/queryList`

> ⚠️ 请求参数不支持全部为空

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| equipmentCode | String | 否 | 设备编号（模糊） |
| equipmentName | String | 否 | 设备名称（模糊） |
| enabled | Boolean | 否 | 是否启用 |
| page | Object | 否 | 分页 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 设备ID |
| equipmentCode | String | 设备编号 |
| equipmentName | String | 设备名称 |
| enabled | Boolean | 是否启用 |


---

## 10. 查询工单

**路径：** `POST /api/dytin/external/project/queryList3`

> ⚠️ 查询参数不能全部为空；调用账户须有工单查询权限
> `projectCodes` 与 `projectCode` 互斥，不可同时传
> `actualStartTime` 与 `actualEndTime` 必须同时传，最大跨度30天

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| projectCodes | String[] | 否 | 工单编号集合（精确，与projectCode互斥） |
| projectCode | String | 否 | 工单编号（模糊，与projectCodes互斥） |
| projectId | Integer | 否 | 工单ID（与编号同时传时优先取编号） |
| status | String | 否 | 状态：0-新建 / 10-执行中 / 20-已结束 / 30-已取消 |
| formCode | String | 否 | 关联单据编号 |
| planStartTime | String | 否 | 计划开始时间（最大跨度30天） |
| planEndTime | String | 否 | 计划结束时间（最大跨度30天） |
| actualStartTime | String | 否 | 实际开始时间（须与actualEndTime同时传） |
| actualEndTime | String | 否 | 实际结束时间（须与actualStartTime同时传） |
| planStartTimeGte / planStartTimeLte | String | 否 | 计划开始时间范围 |
| planEndTimeGte / planEndTimeLte | String | 否 | 计划结束时间范围 |
| actualStartTimeGte / actualStartTimeLte | String | 否 | 实际开始时间范围 |
| actualEndTimeGte / actualEndTimeLte | String | 否 | 实际结束时间范围 |
| page | Object | 否 | 分页 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 工单ID |
| projectCode | String | 工单编号 |
| status | Number | 工单状态 |
| productCode | String | 产品编号 |
| productName | String | 产品名称 |
| planAmount | Number | 计划数量 |
| finishedAmount | Number | 完工数量 |
| planStartTime | String | 计划开始时间 |
| planEndTime | String | 计划结束时间 |
| actualStartTime | String | 实际开始时间 |
| actualEndTime | String | 实际结束时间 |
| customFieldValues | Object[] | 自定义字段值 |

---

## 11. 查询工单当前工序

**路径：** `POST /api/dytin/external/project/queryList/expand`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| projectIds | Number[] | 是 | 工单ID集合 |

**示例：** `{"projectIds":[5366532,5368104]}`

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| projectId | Number | 工单ID |
| currentProcessId | Number | 当前工序ID |
| currentProcessName | String | 当前工序名称 |


---

## 12. 任务列表查询

**路径：** `POST /api/dytin/external/task/queryList2`

> `projectCode` 与 `projectIds` 二选一，不能同时为空

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| projectCode | String | 二选一 | 工单编号 |
| projectIds | Number[] | 二选一 | 工单ID集合 |
| processCode | String | 否 | 工序编号 |
| status | Number[] | 否 | 任务状态：0-未开始 / 10-执行中 / 20-已完成 |
| page | Object | 否 | 分页 |
| customFieldSearchConditions | Object | 否 | 自定义字段查询条件 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 任务ID |
| projectCode | String | 工单编号 |
| processCode | String | 工序编号 |
| processName | String | 工序名称 |
| status | Number | 任务状态 |
| planAmount | Number | 计划数量 |
| finishedAmount | Number | 完成数量 |
| customFieldValues | Object[] | 自定义字段值 |

---

## 13. 查询自定义表单

**路径：** `POST /api/dytin/external/custom_form/item/queryList2`

> `formType` 为必填

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| formCode | String | 否 | 表单编号 |
| formType | Number | 是 | 表单类型：101-销售订单 / 102-生产计划 / 103-装配工单 |
| status | Number | 否 | 0-未开始 / 10-执行中 / 20-已完成 |
| createdAtStart / createdAtEnd | String | 否 | 创建时间范围 |
| updatedAtStart / updatedAtEnd | String | 否 | 更新时间范围 |
| page | Object | 否 | 分页，默认10条 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| customFormFieldId | Number | 表单ID |
| serialCode | String | 表单编号 |
| formType | Number | 表单类型 |
| status | Number | 表单状态 |
| productStatistics.productCount | Number | 明细行数（工单数） |
| productStatistics.totalPlanAmount | Number | 合计计划数 |
| productStatistics.finishedAmount | Number | 合计完工数 |
| productStatistics.totalFineAmount | Number | 合计良品数 |

---

## 14. 查询用料清单

**路径：** `POST /api/dytin/external/projectMaterials/queryList2`

> `projectId` 与 `projectCode` 至少传一个

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| projectId | Number | 二选一 | 工单ID |
| projectCode | String | 二选一 | 工单编号 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 明细行ID |
| seq | Number | 序号 |
| productCode | String | 产品编号 |
| unitQty | Number | 单位用量 |
| feedProcessCode | String | 投料工序编号 |
| remark | String | 备注 |
| customFieldValueList | Object[] | 自定义字段值 |

> 无分页参数，返回工单下全量用料清单


---

## 15. 查询跨厂工单

**路径：** `POST /api/dytin/external/owner/project/queryList`

> ⚠️ `fieldQueryValues` 为必填，且不能全部为空
> 自定义字段需先通过接口36获取 fieldName

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| fieldQueryValues | Object[] | 是 | 筛选条件列表，每项包含 fieldName / queryOperator / value |
| page | Object | 否 | 分页，默认10条 |
| orders | Object | 否 | 排序，默认按创建时间降序 |

**fieldQueryValues 支持的系统字段：** `suppCode`、`suppNickName`、`projectCode`、`productCode`、`productName`、`productSpec`、`status`（0未开始/10执行中/20已结束/30已取消）、`priority`（1加急/0默认/-1暂停）、`startTimePlanned`、`endTimePlanned`、`startTimeReal`、`endTimeReal`、`createdAt`、`updatedAt`

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| projectCode | String | 工单编号 |
| suppCode / suppNickName | String | 供应商编号/名称 |
| productCode / productName / productSpec | String | 产品信息 |
| status / statusStr | Number/String | 状态（数字/中文） |
| priority | Number | 优先级 |
| planAmount / realAmount | Number | 计划数/实际数 |
| planStartTime / planEndTime | String | 计划时间 |

---

## 16. 查询报工

**路径：** `POST /api/dytin/external/output/queryList3`

> ⚠️ `processCode` 实际会校验：不传返回错误码1015，工序不存在返回1016
> `approvedAtGte/Lte` 不能传空字符串

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| projectCode | String | 否 | 工单编号（模糊） |
| processCode | String | 否 | 工序编号 |
| productCode | String | 否 | 产品编号（模糊） |
| approveStatus | Number | 否 | 审批状态：0-未审批 / 10-已审批 |
| starttimeofReportGte / starttimeofReportLte | String | 否 | 报工开始时间范围 |
| endtimeofReportGte / endtimeofReportLte | String | 否 | 报工结束时间范围 |
| approvedAtGte / approvedAtLte | String | 否 | 审批时间范围（不能传空字符串） |
| page | Object | 否 | 分页，默认10条，最大100条 |
| customFieldSearchConditions | Object | 否 | 自定义字段查询条件 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| reportingId | String | 报工ID |
| projectCode | String | 工单编号 |
| processCode | String | 工序编号 |
| productCode / productName | String | 产品信息 |
| workreportQuantity | String | 报工数量 |
| numberofgoodProducts | String | 良品数 |
| numberofDefectives | String | 不良品数 |
| approvalStatus | String | 审批状态 |
| starttimeofReport / endtimeofReport | String | 报工时间 |
| taskId | String | 任务ID |
| estimatedSalary | String | 预计工资 |


---

## 17. 查询出入库单

**路径：** `POST /api/dytin/external/stock/order/queryList3`

> `orderType` 为必填；入库单传10，出库单传20，通过 `bizTypes` 细分

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderType | Number | 是 | 10-入库单 / 20-出库单 |
| orderNo | String | 否 | 单据编号（模糊） |
| bizTypes | List\<Number\> | 否 | 入库：11采购/13成品/14调拨/15销售退货/16其他；出库：21普通/22销售/23生产/24调拨/25采购退货/26其他 |
| orderStatusList | List\<Number\> | 否 | 状态：0-未审批 / 1-已审批 |
| productCode | String | 否 | 产品编号（模糊） |
| warehouseCode | String | 否 | 仓库编号（精确） |
| customerCode | String | 否 | 客户编号（精确） |
| vendorCode | String | 否 | 供应商编号（精确） |
| bizAtStart / bizAtEnd | String | 否 | 出入库时间范围 |
| createdAtStart / createdAtEnd | String | 否 | 创建时间范围 |
| appliedAtStart / appliedAtEnd | String | 否 | 申请时间范围 |
| approvedAtStart / approvedAtEnd | String | 否 | 审批时间范围 |
| page | Object | 否 | 分页 |

---

## 18. 查询库存余额明细

**路径：** `POST /api/dytin/external/stock/queryStockInfoDetail`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| stockInfoQryOpenApiCOList | Object[] | 是 | 查询条件列表，每项包含 warehouseId 或 warehouseCode（二选一）+ productId 或 productCode（二选一） |

**示例：** `{"stockInfoQryOpenApiCOList":[{"warehouseId":11,"productId":1}]}`

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| productId | Number | 产品ID |
| productCode | String | 产品编号 |
| warehouseId | Number | 仓库ID |
| qty | Number | 库存余额 |


---

## 19. 查询调整单

**路径：** `POST /api/dytin/external/stock/order/queryList3`

> `orderType` 固定传 30；`createdAtStart/End` 为必填

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderType | Number | 是 | 固定传 30 |
| bizTypes | List\<Number\> | 否 | 27-期初调整 / 28-期末调整 / 29-其他调整 |
| orderStatusList | List\<Number\> | 否 | 0-未审批 / 1-已审批 |
| productCode | String | 否 | 产品编号（模糊） |
| warehouseCode | String | 否 | 仓库编号（精确） |
| createdAtStart / createdAtEnd | String | 是 | 创建时间范围 |
| bizAtStart / bizAtEnd | String | 否 | 调整时间范围 |
| page | Object | 否 | 分页 |

---

## 20. 查询销售订单

**路径：** `POST /api/dytin/external/saleOrder/queryList2`

> `customerCodes` 与 `customerIds` 二选一，不可同时传

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderNo | String | 否 | 订单编号 |
| status | Number[] | 否 | 0-未审批 / 10-执行中 / 20-已结束 / 30-已取消 |
| contractNo | String | 否 | 合同号（模糊） |
| customerCodes | String[] | 否 | 客户编号（与customerIds二选一） |
| customerIds | Number[] | 否 | 客户ID（与customerCodes二选一） |
| orderTimeGte / orderTimeLte | String | 否 | 下单日期范围 |
| arrivalPlanTimeGte / arrivalPlanTimeLte | String | 否 | 计划交货日期范围 |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围 |
| updatedAtGte / updatedAtLte | String | 否 | 更新时间范围 |
| page | Object | 否 | 分页 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 订单ID |
| orderNo | String | 订单编号 |
| status | Number | 订单状态 |
| customerCode | String | 客户编号 |
| orderTime | String | 下单日期 |
| totalAmount | Number | 订单金额 |
| totalQty | Number | 订单数量 |

---

## 21. 查询采购订单

**路径：** `POST /api/dytin/external/purchaseOrder/queryList2`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderNo | String | 否 | 订单编号 |
| status | Number[] | 否 | 0-未审批 / 10-执行中 / 20-已结束 / 30-已取消 |
| vendorCodes | String[] | 否 | 供应商编号 |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围 |
| updatedAtGte / updatedAtLte | String | 否 | 更新时间范围 |
| page | Object | 否 | 分页 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 订单ID |
| orderNo | String | 订单编号 |
| status | Number | 订单状态 |
| vendorCode | String | 供应商编号 |
| totalAmount | Number | 订单金额 |


---

## 22. 查询供应商价目表

**路径：** `POST /api/dytin/external/purchase/vendor/product/price/queryList`

> ⚠️ 请求参数不支持全部为空；`productCode` 为必填

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| productCode | String | 是 | 产品编号 |
| vendorCode | String | 否 | 供应商编号 |
| createdAtStart / createdAtEnd | String | 否 | 创建时间范围 |
| updatedAtStart / updatedAtEnd | String | 否 | 更新时间范围 |
| page | Object | 否 | 分页 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 价目表ID |
| vendorCode | String | 供应商编号 |
| productCode | String | 产品编号 |
| price | Number | 价格 |

---

## 23. 查询客户价目表

**路径：** `POST /api/dytin/external/sale/customer/product/price/queryList`

> ⚠️ 请求参数不支持全部为空；`productCode` 为必填

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| productCode | String | 是 | 产品编号 |
| customerCode | String | 否 | 客户编号 |
| createdAtStart / createdAtEnd | String | 否 | 创建时间范围 |
| updatedAtStart / updatedAtEnd | String | 否 | 更新时间范围 |
| page | Object | 否 | 分页 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 价目表ID |
| customerCode | String | 客户编号 |
| productCode | String | 产品编号 |
| price | Number | 价格 |

---

## 24. 查询应收单详情

**路径：** `POST /api/dytin/external/receivable/order/detail`

> `id` 与 `orderNo` 二选一，同时传以 `id` 为准

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Number | 二选一 | 应收单ID |
| orderNo | String | 二选一 | 应收单号 |

### 返回字段

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 应收单ID |
| orderNo | String | 应收单号 |
| receiptDate | String | 开票日期 |
| customerName / customerCode | String | 客户信息 |
| bizUserName | String | 业务员 |
| receiptAmount | Number | 开票金额 |
| realReceivableAmount | Number | 实际应收金额 |
| receiveWay | String | 收款方式 |
| receiveEndDate | String | 收款到期日 |
| discount | Number | 整单折扣% |
| advance | Number | 使用预收 |
| details | Object[] | 应收明细列表 |


---

## 25. 查询应收单列表

**路径：** `POST /api/dytin/external/receivable/queryList`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderNo | String | 否 | 应收单号（模糊） |
| remark | String | 否 | 应收备注（模糊） |
| customerName | String | 否 | 客户名称（模糊） |
| status | Number[] | 否 | 状态 |
| receiptDateGte / receiptDateLte | String | 否 | 开票日期范围 |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围 |
| page | Object | 否 | 分页 |

---

## 26. 查询收款单列表

**路径：** `POST /api/dytin/external/receipt/queryList`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderNo | String | 否 | 收款单号（模糊） |
| remark | String | 否 | 收款备注（模糊） |
| customerName | String | 否 | 客户名称（模糊） |
| settleWay | String | 否 | 结算方式（模糊） |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围 |
| page | Object | 否 | 分页 |

---

## 27. 客户账本查询

**路径：** `POST /api/dytin/external/querySaleWorkbenchCustomerLedger`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| customerCode | String | 否 | 客户编号（模糊） |
| customerName | String | 否 | 客户名称（模糊） |
| customerFullName | String | 否 | 客户全称（模糊） |
| contactName | String | 否 | 联系人（模糊） |
| contactNumber | String | 否 | 联系电话（模糊） |
| contactAddress | String | 否 | 联系地址（模糊） |
| page | Object | 否 | 分页 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| customerCode / customerName | String | 客户信息 |
| totalReceivable | Number | 应收总额 |
| totalReceipt | Number | 已收总额 |
| balance | Number | 余额 |

---

## 28. 查询应付单详情

**路径：** `POST /api/dytin/external/payable/order/detail`

> `id` 与 `orderNo` 二选一

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Number | 二选一 | 应付单ID |
| orderNo | String | 二选一 | 应付单号 |

### 返回字段

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 应付单ID |
| orderNo | String | 应付单号 |
| vendorName / vendorCode | String | 供应商信息 |
| payableAmount | Number | 应付金额 |
| realPayableAmount | Number | 实际应付金额 |
| details | Object[] | 应付明细列表 |


---

## 29. 查询应付单列表

**路径：** `POST /api/dytin/external/payable/queryList`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderNo | String | 否 | 应付单号（模糊） |
| remark | String | 否 | 应付备注（模糊） |
| vendorName | String | 否 | 供应商名称（模糊） |
| status | Number[] | 否 | 状态 |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围 |
| page | Object | 否 | 分页 |

---

## 30. 查询付款单列表

**路径：** `POST /api/dytin/external/payment/queryList`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderNo | String | 否 | 付款单号（模糊） |
| remark | String | 否 | 付款备注（模糊） |
| vendorName | String | 否 | 供应商名称（模糊） |
| settleWay | String | 否 | 结算方式（模糊） |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围 |
| page | Object | 否 | 分页 |

---

## 31. 供应商账本查询

**路径：** `POST /api/dytin/external/queryPurchaseWorkbenchVendorLedger`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| vendorCode | String | 否 | 供应商编号（模糊） |
| vendorName | String | 否 | 供应商名称（模糊） |
| vendorFullName | String | 否 | 供应商全称（模糊） |
| contactName | String | 否 | 联系人（模糊） |
| contactNumber | String | 否 | 联系电话（模糊） |
| page | Object | 否 | 分页 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| vendorCode / vendorName | String | 供应商信息 |
| totalPayable | Number | 应付总额 |
| totalPayment | Number | 已付总额 |
| balance | Number | 余额 |

---

## 32. 待开票列表查询

**路径：** `POST /api/dytin/external/queryInvoicingList`

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderNo | String | 否 | 订单号（模糊） |
| remark | String | 否 | 备注（模糊） |
| productCode | String | 否 | 产品编码（模糊） |
| productName | String | 否 | 产品名称（模糊） |
| productSpec | String | 否 | 产品规格（模糊） |
| bizType | Number | 否 | 业务类型：1-销售 / 2-采购 |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围 |
| page | Object | 否 | 分页 |


---

## 33. 查询检验项

**路径：** `POST /api/dytin/external/inspectionItem/queryList`

> ⚠️ 查询时间范围不能超过2年

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| itemCode | String | 否 | 检验项编号（模糊） |
| itemName | String | 否 | 检验项名称（模糊） |
| enabled | Boolean | 否 | 是否启用 |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围（不超过2年） |
| updatedAtGte / updatedAtLte | String | 否 | 更新时间范围 |
| page | Object | 否 | 分页 |
| customFieldSearchConditions | Object | 否 | 自定义字段查询条件 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 检验项ID |
| itemCode | String | 检验项编号 |
| itemName | String | 检验项名称 |
| enabled | Boolean | 是否启用 |
| customFieldValues | Object[] | 自定义字段值 |

---

## 34. 查询检验规范

**路径：** `POST /api/dytin/external/inspectionSpecifications/queryList`

> ⚠️ 查询时间范围不能超过2年

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| specCode | String | 否 | 规范编号（模糊） |
| specName | String | 否 | 规范名称（模糊） |
| enabled | Boolean | 否 | 是否启用 |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围（不超过2年） |
| page | Object | 否 | 分页 |
| customFieldSearchConditions | Object | 否 | 自定义字段查询条件 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 规范ID |
| specCode | String | 规范编号 |
| specName | String | 规范名称 |
| enabled | Boolean | 是否启用 |
| inspectionItems | Object[] | 检验项列表 |

---

## 35. 查询检验单

**路径：** `POST /api/dytin/external/inspection/queryList`

> ⚠️ 查询时间范围不能超过2年

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| orderNo | String | 否 | 检验单编号（模糊） |
| projectCode | String | 否 | 工单编号 |
| productCode | String | 否 | 产品编号 |
| status | Number[] | 否 | 状态：0-待检 / 10-检验中 / 20-已完成 |
| inspectionType | Number | 否 | 检验类型 |
| createdAtGte / createdAtLte | String | 否 | 创建时间范围（不超过2年） |
| page | Object | 否 | 分页 |
| customFieldSearchConditions | Object | 否 | 自定义字段查询条件 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 检验单ID |
| orderNo | String | 检验单编号 |
| projectCode | String | 工单编号 |
| productCode / productName | String | 产品信息 |
| status | Number | 检验状态 |
| inspectionType | Number | 检验类型 |
| customFieldValues | Object[] | 自定义字段值 |


---

## 36. 查询自定义字段Metadata

**路径：** `POST /api/dytin/external/customField/queryCustomFieldMetadata`

> 两个参数均未填则查询组织下所有自定义字段

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| customFieldIds | Number[] | 否 | 自定义字段ID数组，按ID精确查询 |
| targetType | String | 否 | 所属表单类型，按类型查询（如 process、product 等） |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 字段ID |
| name | String | 字段名称 |
| fieldName | String | 字段标识（用于跨厂工单等查询的 fieldName 参数） |
| type | Number | 字段类型：1-文本/2-数字/3-时间/4-照片/5-关联对象 |
| targetType | String | 所属表单类型 |
| businessObjectType | String | 关联对象类型（type=5时有值，用于接口37） |

---

## 37. 查询关联对象业务实体

**路径：** `POST /api/dytin/external/customField/associatedBusinessObject/entity/queryList`

> ids 来源：从查询接口返回的关联对象类型自定义字段的 value 中取得
> 最多传 80 个 id

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| ids | List\\<Number\\> | 是 | 实体主键ID列表，最多80个 |
| type | String | 是 | 业务对象类型，对应 customFieldMetadata 的 businessObjectType |

### 返回字段

返回对应业务实体的字段集合，结构随 type 动态变化。

---

## 38. 查询附件

**路径：** `GET /api/filebase/v1/files/_openapi/_list`

> ⚠️ 成功码为 `statusCode: 200`，与其他接口的 `01000000` 不同
> fileId 来源：先调用工单/产品等查询接口，从 `customFieldValues` 的 `value` 中取得附件类型字段值

### 请求参数（Query String）

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| fileIds | String | 是 | 多个文件ID用逗号分隔，如 `?fileIds=1,2,3` |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | Number | 文件ID |
| originalFileName | String | 文件名称 |
| originalExtension | String | 扩展名 |
| uri | String | 文件下载地址 |
| uploaderId | String | 上传人ID |

---

## 39. 查询操作日志列表

**路径：** `POST /api/mmtin/external/operationLog/list`

> 支持模块：工单、产品、出入库单、工序、销售订单、采购订单、客户、供应商等
> 默认查询近三个月数据

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| objectName | String | 是 | 模块中文名，如「工单」、「产品」、「客户」 |
| logFrom | Long | 否 | 操作开始时间（毫秒时间戳），默认近三个月 |
| logTo | Long | 否 | 操作结束时间（毫秒时间戳） |
| instanceIds | List\<Integer\> | 否 | 业务实例ID集合 |
| instanceCode | String | 否 | 单据编号（模糊） |
| page | Object | 否 | 分页，最大100条 |

### 返回字段（data[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| instanceId | Long | 业务实例ID |
| instanceCode | String | 业务实例编号 |
| objectName | String | 模块名称 |
| operateType | String | 操作类型（新增/删除/编辑） |
| operateTimeString | String | 操作时间 |
| tsMs | Long | 操作时间毫秒时间戳（用于接口40） |
| operator | Object | 操作人信息 |

---

## 40. 查询操作日志详情

**路径：** `POST /api/mmtin/external/operationLog/detail`

> 三个参数均为必填
> 错误码：`02000003`-时间戳不能为空；`02000004`-业务ID不能为空

### 请求参数

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| objectName | String | 是 | 模块中文名，同接口39 |
| tsMs | Long | 是 | 日志毫秒时间戳（从接口39返回的 tsMs 取得） |
| bizId | Long | 是 | 业务实例ID |

### 返回字段（data.list[]）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| fieldName | String | 字段名 |
| fieldCode | String | 字段code |
| beforeValue | String | 变更前的值 |
| afterValue | String | 变更后的值 |
| isModified | Boolean | 是否发生变更 |
