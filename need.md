# 前后端接口核对报告

> 核对时间：2026-03-22
> 前端来源：E_Print/src/stores/request.ts + types/Order.ts + types/WorkOrder.ts
> 后端来源：E_print_backend_java controllers + DTOs

---

## 一、接口路径与参数核对

### 订单接口（/api/orders）

| 接口 | 前端参数 | 后端参数 | 状态 |
|------|----------|----------|------|
| POST /orders/create | FormData: `orderData`(JSON), `files` | @RequestParam `orderData`, `files` | ✅ 一致 |
| GET /orders/findBySales | params: `sales` | @RequestParam `sales` | ✅ 一致 |
| GET /orders/findByAudit | params: `audit` | @RequestParam `audit` | ✅ 一致 |
| GET /orders/findById | params: `order_id` | @RequestParam `order_id` | ✅ 一致 |
| GET /orders/status | params: `orderstatus` | @RequestParam `orderstatus` | ✅ 一致 |
| POST /orders/updateStatus | body: `order_unique`, `orderstatus` | request.get `order_unique`, `orderstatus` | ✅ 一致 |

### 工程单接口（/api/workOrders）

| 接口 | 前端参数 | 后端参数 | 状态 |
|------|----------|----------|------|
| POST /workOrders/create | FormData: `workOrderData`(JSON), `files` | @RequestParam `workOrderJson`, `files` | ❌ **不一致** |
| GET /workOrders/findById | params: `work_unique` | @RequestParam `work_unique` | ✅ 一致 |
| GET /workOrders/findByClerk | params: `work_clerk` | @RequestParam `work_clerk` | ✅ 一致 |
| GET /workOrders/findByAudit | params: `work_audit` | @RequestParam `work_audit` | ✅ 一致 |
| GET /workOrders/findWithStatus | params: `orderstatus` | @RequestParam `workorderstatus` | ❌ **不一致** |
| POST /workOrders/updateStatus | body: `work_unique`, `workorderstatus` | request.get `work_unique`, `workorderstatus` | ✅ 一致 |
| POST /workOrders/updateProcess | body: `work_id`, `process`, `dangQianJinDu` | request.get `work_id`, `process`, `dangQianJinDu` | ✅ 一致 |

---

## 二、DTO 字段核对

### OrderDTO 与 IOrder 字段对比

| 前端字段(IOrder) | 类型 | 后端字段(OrderDTO) | 类型 | 状态 |
|-----------------|------|-------------------|------|------|
| order_id | string | order_id | String | ✅ |
| order_ver | string | order_ver | Integer | ❌ **类型不一致** |
| order_unique | string | order_unique | String | ✅ |
| orderstatus | string | orderstatus | String | ✅ |
| customer | string | customer | String | ✅ |
| sales | string | sales | String | ✅ |
| audit | string | audit | String | ✅ |
| productName | string | productName | String | ✅ |
| customerPO | string | customerPO | String | ✅ |
| isbn | string | isbn | String | ✅ |
| dingDanShuLiang | number | dingDanShuLiang | Integer | ✅ |
| chuYangShuLiang | number | chuYangShuLiang | Integer | ✅ |
| chaoBiLiShuLiang | number | chaoBiLiShuLiang | Integer | ✅ |
| guigeGaoMm | number | guigeGaoMm | Double | ✅ |
| guigeKuanMm | number | guigeKuanMm | Double | ✅ |
| guigeHouMm | number | guigeHouMm | Double | ✅ |
| keLaiXinxi | string | keLaiXinXi | String | ⚠️ **大小写不一致**（前端xi，后端Xi）|
| fuLiaoShuoMing | string | fuLiaoShuoMing | String | ✅ |
| xiaZiliaodaiRiqiRequired | string | xiaZiliaodaiRiqi1 | LocalDateTime | ❌ **字段名不一致** |
| xiaZiliaodaiRiqiPromise | string | xiaZiliaodaiRiqi2 | LocalDateTime | ❌ **字段名不一致** |
| yinzhangRiqiRequired | string | yinzhangRiqi1 | LocalDateTime | ❌ **字段名不一致** |
| yinzhangRiqiPromise | string | yinzhangRiqi2 | LocalDateTime | ❌ **字段名不一致** |
| chuHuoRiqiRequired | string | jiaoHuoRiQi1 | LocalDateTime | ❌ **字段名不一致** |
| chuHuoRiqiPromise | string | jiaoHuoRiQi2 | LocalDateTime | ❌ **字段名不一致** |
| chanPinMingXi | IProduct[] | chanPinMingXi | List<ProductDTO> | ✅ |
| attachments | IAttachment[] | attachments | List<AttachmentDTO> | ✅ |
| auditLogs | IAuditLog[] | auditLogs | List<AuditLogDTO> | ⚠️ 字段结构见下方 |
| cpcQueRen | boolean | - | - | ❌ **后端缺失** |
| waixiaoFlag | boolean | - | - | ❌ **后端缺失** |
| cpsiaYaoqiu | boolean | - | - | ❌ **后端缺失** |
| dingZhiBeiZhu | string | - | - | ❌ **后端缺失** |
| baoJiaDanHao | string | - | - | ❌ **后端缺失** |
| xiLieDanMing | string | - | - | ❌ **后端缺失** |
| chanPinDaLei | string | - | - | ❌ **后端缺失** |
| ziLeiXing | string | - | - | ❌ **后端缺失** |
| fscType | string | - | - | ❌ **后端缺失** |
| fenBanShuoMing | string | - | - | ❌ **后端缺失** |
| baoLiuQianSe | string | - | - | ❌ **后端缺失** |
| zhuangDingFangShi | string | - | - | ❌ **后端缺失** |
| genSeZhiShi | string | - | - | ❌ **后端缺失** |
| salesDate | string | - | - | ❌ **后端缺失** |
| auditDate | string | - | - | ❌ **后端缺失** |
| teShuLiuYangZhang | number | - | - | ❌ **后端缺失** |
| beiPinShuLiang | number | - | - | ❌ **后端缺失** |
| teShuLiuShuYang | number | - | - | ❌ **后端缺失** |
| zongShuLiang | number | - | - | ❌ **后端缺失** |
| chuYangShuoMing | number | - | - | ❌ **后端缺失** |
| zhuangDingFangShi | string | - | - | ❌ **后端缺失** |
| genSeZhiShi | string | - | - | ❌ **后端缺失** |
| zhepaiRiqiRequired | string | - | - | ❌ **后端缺失** |
| zhepaiRiqiPromise | string | - | - | ❌ **后端缺失** |
| chuyangRiqiRequired | string | - | - | ❌ **后端缺失** |
| chuyangRiqiPromise | string | - | - | ❌ **后端缺失** |
| chuHuoShuLiang | number | - | - | ❌ **后端缺失** |
| yongTu | string | - | - | ❌ **后端缺失** |
| chanPinMingXiTeBieShuoMing | string | - | - | ❌ **后端缺失** |

### WorkOrderDTO 与 IWorkOrder 字段对比

| 前端字段(IWorkOrder) | 类型 | 后端字段(WorkOrderDTO) | 类型 | 状态 |
|--------------------|------|----------------------|------|------|
| work_id | string | work_id | String | ✅ |
| work_ver | string | work_ver | Integer | ❌ **类型不一致** |
| work_unique | string | work_unique | String | ✅ |
| workorderstatus | string | workorderstatus | String | ✅ |
| work_clerk | string | work_clerk | String | ✅ |
| work_audit | string | work_audit | String | ✅ |
| customer | string | customer | String | ✅ |
| customerPO | string | customerPO | String | ✅ |
| productName | string | productName | String | ✅ |
| chuYangShuLiang | number | chuYangShuLiang | Integer | ✅ |
| chaoBiLiShuLiang | number | chaoBiLiShuLiang | Integer | ✅ |
| intermedia | IIM[] | intermedia | List<IntermediaMaterialDTO> | ⚠️ 字段结构见下方 |
| attachments | IAttachment[] | attachments | List<AttachmentDTO> | ✅ |
| auditLogs | IAuditLog[] | auditLogs | List<AuditLogDTO> | ⚠️ 字段结构见下方 |
| gongDanLeiXing | string | - | - | ❌ **后端缺失** |
| caiLiao | string | - | - | ❌ **后端缺失** |
| chanPinLeiXing | string | - | - | ❌ **后端缺失** |
| chanPinGuiGe | string | - | - | ❌ **后端缺失** |
| dingDanShuLiang | number | - | - | ❌ **后端缺失** |
| benChangFangSun | number | - | - | ❌ **后端缺失** |
| chuYangRiqiRequired | string | - | - | ❌ **后端缺失** |
| chuHuoRiqiRequired | string | - | - | ❌ **后端缺失** |
| clerkDate | string | - | - | ❌ **后端缺失** |
| auditDate | string | - | - | ❌ **后端缺失** |
| zhiDanShiJian | string | - | - | ❌ **后端缺失** |

### IIM 与 IntermediaMaterialDTO 字段对比

| 前端字段(IIM) | 类型 | 后端字段(IntermediaMaterialDTO) | 类型 | 状态 |
|--------------|------|-------------------------------|------|------|
| kaiShiRiQi | string | kaiShiRiQi | LocalDateTime | ⚠️ **类型不一致**（前端string，后端LocalDateTime）|
| yuQiJieShu | string | yuQiJieShu | LocalDateTime | ⚠️ **类型不一致**（前端string，后端LocalDateTime）|
| dangQianJinDu | number | dangQianJinDu | String | ❌ **类型不一致**（前端number，后端String）|
| yiGouJianShu | - | yiGouJianShu | Integer | ⚠️ **前端无此字段（仅后端）** |
| head_PUR | - | head_PUR | String | ⚠️ **前端无此字段（仅后端）** |
| head_OUT | - | head_OUT | String | ⚠️ **前端无此字段（仅后端）** |
| buJianMingCheng | string | - | - | ❌ **后端缺失** |
| yinShuaYanSe | string | - | - | ❌ **后端缺失** |
| wuLiaoMingCheng | string | - | - | ❌ **后端缺失** |
| pinPai | string | - | - | ❌ **后端缺失** |
| caiLiaoGuiGe | string | - | - | ❌ **后端缺失** |
| FSC | string | - | - | ❌ **后端缺失** |
| kaiShu | number | - | - | ❌ **后端缺失** |
| shangJiChiCun | string | - | - | ❌ **后端缺失** |
| paiBanMuShu | number | - | - | ❌ **后端缺失** |
| yinChuShu | number | - | - | ❌ **后端缺失** |
| yinSun | number | - | - | ❌ **后端缺失** |
| lingLiaoShu | number | - | - | ❌ **后端缺失** |
| biaoMianChuLi | string | - | - | ❌ **后端缺失** |
| yinShuaBanShu | number | - | - | ❌ **后端缺失** |
| shengChanLuJing | string | - | - | ❌ **后端缺失** |
| paiBanFangShi | string | - | - | ❌ **后端缺失** |

### IAuditLog 与 AuditLogDTO 字段对比

| 前端字段(IAuditLog) | 类型 | 后端字段(AuditLogDTO) | 类型 | 状态 |
|-------------------|------|---------------------|------|------|
| time | string | time | LocalDateTime | ⚠️ 类型不一致 |
| operator | string | userId | String | ❌ **字段名不一致** |
| action | string | action | String | ✅ |
| comment | string | actionDescription | String | ❌ **字段名不一致** |

---

## 三、需要修改后端的问题汇总（按优先级）

### 高优先级（导致请求失败）

1. **POST /workOrders/create 参数名错误**
   - 前端发送：`workOrderData`
   - 后端接收：`workOrderJson`
   - 修复：后端改为 `workOrderData`

2. **GET /workOrders/findWithStatus 参数名错误**
   - 前端发送：`orderstatus`
   - 后端接收：`workorderstatus`
   - 修复：后端改为 `orderstatus`

3. **order_ver / work_ver 类型不一致**
   - 前端：string（如 "V1"）
   - 后端：Integer
   - 修复：后端 OrderDTO.order_ver 和 WorkOrderDTO.work_ver 改为 String

4. **dangQianJinDu 类型不一致**
   - 前端 IIM：number
   - 后端 IntermediaMaterialDTO：String
   - 修复：后端改为 Integer

### 中优先级（数据丢失）

5. **日期字段名不一致（OrderDTO）**
   - 前端使用 Required/Promise 命名，后端使用 1/2 命名
   - 修复：后端 OrderDTO 增加对应字段名

6. **keLaiXinxi 大小写不一致**
   - 前端：keLaiXinxi（小写i）
   - 后端：keLaiXinXi（大写X）
   - 修复：后端改为小写 keLaiXinxi

7. **AuditLogDTO 字段名不一致**
   - 前端 operator → 后端 userId
   - 前端 comment → 后端 actionDescription

### 低优先级（功能缺失，不影响现有流程）

8. **OrderDTO 缺少大量前端字段**（cpcQueRen、waixiaoFlag 等）
   - Jackson 反序列化会忽略未知字段，不影响创建，但这些字段不会被保存

9. **WorkOrderDTO 缺少大量前端字段**（gongDanLeiXing、caiLiao 等）
   - 同上，不影响创建，但数据会丢失

10. **IntermediaMaterialDTO