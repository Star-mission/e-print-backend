小工单OpenAPI接口（该文档动态更新）

接入说明💡
为保证对接过程的顺利，请先阅读OpenAPI接入说明  


更新日志
见更新日志。

动态公告
小工单已于 2025/03/08 00:00 上线OpenAPI的白名单功能，上线后未开通白名单的客户将无法使用开放接口，因此请注意：
2025/03/08 00:00 后首次对接系统的客户，请先申请开通OpenAPI白名单2025/03/08 00:00 前完成首次对接的客户，无需申请，系统将自动开通白名单白名单申请是免费的，无需另外付费申请方式
渠道：请联系小工单对接人直销：请在客户的售后服务群中直接申请

调用限制和说明
- 全部接口
  - 每小时调用量限制：1w
  - QPS：20
- 创建、更新、修改等写接口
  - 每小时调用量限制：5k
  - QPS：10
- 特定接口（后续会根据使用情况更新）
  - 工单查询接口/任务列表查询接口 10s内相同请求参数只能调用一次
  - 登录接口每小时调用量限制：75
每个接口调用需要判断下接口返回状态码，尤其创建和更新接口，否则可能由于返回失败和错误信息而导致您的程序一直重试。如，token失效、产品已存在等。



🎯  单位
创建单位
1.功能描述
外部系统通过此接口创建基础数据单位
2.接口定义
2.1请求地址：/api/dytin/external/unit/create
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
name
String
是
单位名称
remark
String

否

备注

请求参数示例：
{
    "name": "pcs"
}

2.3 返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Number
单位id
msg
String
返回消息

2.4错误代码
0100x0002
单位名称重复

单位列表查询
1.功能描述
外部系统通过此接口查询小工单的单位数据
2.接口定义
2.1请求地址：/api/dytin/external/unit/queryList2
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
name
String
否
单位

请求参数示例：
{
    "name": "pcs"
}

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
String
返回消息

data
参数名
字段类型
描述
pageNum
Number
当前页数
pageSize
Number
每页数量
total
Number
总数
data
List<Object>


id
Number
单位id

name
String
单位

remark
String
备注

🎯  产品定义
创建产品
1.功能描述
通过该接口可在小工单创建产品基础信息。
2.接口定义
2.1请求地址：/api/dytin/external/product/create
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账号须有产品创建权限，建议使用有管理员权限的账号）
- Content-Type:application/json
2.2请求参数
注意:非必参不想传的话不传即可，Object[] 类型字段不要传null。
参数名
字段类型
是否必填
字段解释
productCode
String

否

产品编号，校验所传产品编号在系统是否存在，若已存在，则接口返回“1020”。若不传，由系统自动编号生成。
productName
String
是
产品名称，校验是否传输该字段，若没有，则接口返回“1021”
productSpecification
String

否
产品规格，校验所传产品编号在系统是否存在，若不存在，则接口返回“1022”
model

String
否
工艺路线型号（可以理解为code），校验所传工艺路线编号在系统是否存在，若不存在，则接口返回“1023”
unit
String
是
单位名称，校验是否传输该字段，若没有，则接口返回“1024”，校验所传单位在系统是否存在，若不存在，则接口返回“1025”
originType
Number
 否
产品属性，默认0，自制
0: 自制
1: 外购
2: 委外
costPrice
Number
否
成本单价
salesPrice
Number
否
销售单价
safetyQty
Number
否
安全库存
maxQty
Number
否
最大库存
minQty
 Number
否
最小库存
vendorCode
String
否
默认供应商编码
criticalFlag

Number
否
关键部件
0: 否
1: 是
productionType

Number
否
制造模式
1: MTO
2: MTS
customFieldValues
Object[]

否

产品自定义字段值，传值方式见「自定义字段-传值说明」
inspectionSpecificationsTypes
Integer[]
否
检验类型 1, 来料检验 2, 成品入库检验  3, 销售发货检验 4, 其他检验

materialIncomingInspectionSpecificationsCode
String
否
来料检验规范编号

finishedProductStorageInspectionSpecificationsCode
String
否
成品入库检验规范编号
salesDeliveryInspectionSpecificationsCode
String
否
销售发货检验规范编号
warehouseCode
string
否
产品默认仓库

customFieldValues  Object[]
参数名
类型
是否必填
描述
name
String
是
自定义字段元数据名称
value
String
否
自定义字段属性值


2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据。当失败时该字段返回值为null
msg
String
返回消息

data对象
参数名
类型
描述
productCode
String
产品编号

2.4错误代码
1020
产品编号在系统内已存在
1021
产品名称不能为空
1022
产品规格在系统内不存在
1023
工艺路线编号在系统内不存在
1024
单位不能为空
1025
单位在系统内不存在
1061
产品编号长度超过限制，支持最大120字符
1062
产品名称长度超过限制，支持最大120字符
1063
产品规格长度超过限制，支持最大240字符
1100
自定义字段【xx】不存在
1101
自定义字段【xx】值有问题：【问题描述】

请求参数示例：
{
    "productCode": "201210116",
    "productName": "3240板B级环氧板",
    "productSpecification": "0.5*204*582",
    "unit": "pcs"
}


更新产品
1.功能描述
通过该接口可在小工单更新产品基础信息。
2.接口定义
2.1请求地址：/api/dytin/external/product/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账号须有产品编辑权限，建议使用有管理员权限的账号）
- Content-Type:application/json
2.2请求参数
注意:非必填字段不想传的话不传即可，Object[] 类型字段不要传null。
参数名
字段类型
是否必填
字段解释
productCode
String

是

产品编号，校验是否传输该字段，若没有，则接口返回“1003”，校验所传产品编号在系统是否存在，若不存在，则接口返回“1010”，若存在，更新该产品所传字段信息。
productName
String
否
产品名称
productSpecification
String
否
产品规格
model
String
否
工艺路线型号（可以理解为code），校验所传工艺路线编号在系统是否存在，若不存在，则接口返回“1023”
unit
String
否
单位名称，校验所传单位在系统是否存在，若不存在，则接口返回“1025”
originType
Number
 否
产品属性，默认0，自制
0: 自制
1: 外购
2: 委外
costPrice
Number
否
成本单价
salesPrice
Number
否
销售单价
safetyQty
Number
否
安全库存
maxQty
Number
否
最大库存
minQty
Number
否
最小库存
vendorCode
String
否
默认供应商编码
criticalFlag
Number
否
关键部件
0: 否
1: 是
productionType
Number
否
制造模式
1: MTO
2: MTS
customFieldValues
Object[]
否
产品自定义字段值，传值方式见「自定义字段-传值说明」
inspectionSpecificationsTypes
Integer[]
否
检验类型 1, 来料检验 2, 成品入库检验  3, 销售发货检验 4, 其他检验

materialIncomingInspectionSpecificationsCode
String
否
来料检验规范编号

finishedProductStorageInspectionSpecificationsCode
String
否
成品入库检验规范编号
salesDeliveryInspectionSpecificationsCode
String
否
销售发货检验规范编号
warehouseCode
string
否

产品默认仓库

customFieldValues  Object[]
参数名
类型
是否必填
描述
name
String
是
自定义字段元数据名称
value
String
否
自定义字段属性值


2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据。当失败时该字段返回值为null
msg
String
返回消息

data对象
参数名
类型
描述
productCode
String
产品编号

2.4错误代码
1020
产品编号在系统内已存在
1021
产品名称不能为空
1022
产品规格在系统内不存在
1023
工艺路线编号在系统内不存在
1024
单位不能为空
1025
单位在系统内不存在
1061
产品编号长度超过限制，支持最大120字符
1062
产品名称长度超过限制，支持最大120字符
1063
产品规格长度超过限制，支持最大240字符
1100
自定义字段【xx】不存在
1101
自定义字段【xx】值有问题：【问题描述】

请求参数示例：
{
    "productCode": "006.04964",
    "productName": "F8ER-3.81-08P-3-L35",
    "productSpecification": "F8ER-3.81-08P-3-L35",
    "unit": "PCS"
}


产品列表查询
1.功能描述
查询产品信息。
20230620 ：更新列表返回产品自定义字段
2.接口定义
2.1请求地址：/api/dytin/external/product/queryList2
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
字段类型
是否必填
字段解释
productCode
String
否
产品编号
productName
String
否
产品名称
createdAtStart
String
否
产品创建开始时间   格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
createdAtEnd
String
否
产品创建结束时间   格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
updatedAtStart
String
否
产品更新开始时间   格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
updatedAtEnd
String
否
产品更新结束时间   格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
originType

Number[]
否
产品属性，支持多选
0: 自制
1: 外购
2: 委外
vendorCode
String
否
默认供应商编号，精确匹配
criticalFlag
Number[]
否
关键部件，支持多选
0: 否
1: 是
productionType
Number[]
否

制造模式，支持多选
1: MTO
2: MTS
page

Object
否
不传 默认每页 10条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条
最大支持每页100条

2.3返回参数

参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回信息
msg
String
返回消息
reference
Object
自定义字段metadata  "reference": {
        "allCustomFieldMetadata": {
            "product17177495029340": {
                "id": null,
                "orgId": null,
                "targetType": null,
                "targetTypeDisplayName": null,
                "name": "产品-数字",
                "fieldName": "product17177495029340",
                "type": 2,
                "businessObjectType": null,
                "displayAttributeName": null,
                "displayAttribute": null,
                "displayAttributeDetail": null,
                "associatedBusinessObjectFilter": null,
                "associatedViewId": null,
                "associatedViewName": null,
                "dataFillRule": null,
                "dataFillRuleDetail": null,
                "associatedBusinessObjectName": null,
                "associatedBusinessObjectField": null,
                "associatedBusinessObjectFieldDetail": null,
                "options": null,
                "optionColors": null,
                "expression": null,
                "blScanCodeInput": null,
                "blModifyScanResult": null,
                "blRepeatInput": null,
                "tips": null,
                "description": null,
                "blReadonly": null,
                "blThousandSeparator": null,
                "lineNumber": null,
                "decimalLength": null,
                "hidden": null,
                "defaultValue": null,
                "blRequired": null,
                "allowAddOption": null,
                "deletedAt": null,
                "creatorId": null,
                "creator": null,
                "createdAt": null,
                "updatorId": null,
                "updator": null,
                "updatedAt": null,
                "sort": 0,
                "blTextArea": null,
                "blSearch": false,
                "dateTimeDisplayType": null,
                "showType": null
            }}}

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数


data[]
参数名
字段类型
描述
productCode
String
产品编号
productName
String
产品名称
productSpecification
String
产品规格
unit
String
单位
originType

Number

产品属性，
0: 自制
1: 外购
2: 委外
customFieldValues
Object[]
产品自定义字段
stockQty
Number
库存数量
costPrice
Number
成本单价
salesPrice
Number
销售单价
safetyQty
Number
安全库存
maxQty
Number
最大库存
minQty
Number
最小库存
prcessRoutingCode
String
工艺路线编号
vendorCode
String
供应商编号
criticalFlag
Number
关键部件
0: 否
1: 是
productionType

Number
制造模式
1: MTO
2: MTS
inspectionSpecificationsTypes
Integer[]
检验类型
inspectionSpecificationsTypesName
String
检验类型名称
materialIncomingInspectionSpecificationsId
Number
来料检验规范

materialIncomingInspectionSpecificationsName
String
来料检验规范名称
finishedProductStorageInspectionSpecificationsId
Number
成品入库检验规范
finishedProductStorageInspectionSpecificationsName
String
成品入库检验规范名称
salesDeliveryInspectionSpecificationsId
Number
销售发货检验规范
salesDeliveryInspectionSpecificationsName
String
销售发货检验规范名称
warehouseCode
String
仓库编号


🎯  工序
创建工序
1.功能描述
通过该接口可在小工单新增工序
2.接口定义
2.1请求地址：/api/dytin/external/process/create
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
类型
是否必填
描述
code
String
否
工序编码。若不传系统自动生成；校验字符长度120，超出创建失败
name
String
是
工序名称。若不传，创建失败；校验字符长度120，超出创建失败
outputRate
Number
是

报工数配比。数据校验：小数点之前最多10位，小数点之后最多6位
outputUsers
Object[]
否
报工权限-用户。拥有报工权限的用户信息
outputGroups
Object[]
否
报工权限-部门。拥有报工权限的部门信息
outputBlAll
Boolean
否
是否允许所有人报工。默认值：false，当该字段为false时，outputUsers和outputGroups不可同时为空
defects
String[]
否
不良品项编号列表。
fields
Object[]
否
工序采集数据。支持类型：1 文本  2 数字  3 时间  4 照片
customFieldValues
Object[]
否
工序自定义字段，传值方式见「自定义字段-传值说明」

outputUsers
参数名
类型
是否必填
描述
id
Number
否
用户id
name
String
否
用户姓名

outputGroups
参数名
类型
是否必填
描述
id
Number
否
部门id
name
String
否
部门名称

fields
参数名
类型
是否必填
描述
type
Number
是
类型。支持类型：1 文本  2 数字  3 时间  4 照片
field
String
否
名称

customFieldValues  []
参数名
类型
是否必填
描述
name
String
是
自定义字段元数据名称
value
String
否
自定义字段属性值

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Number   
返回具体信息
msg
String
返回消息

data
参数名
类型
描述
processCode
String
工序编号

2.4错误信息
编码
描述
1047
工序最多创建{条数限制}条
1048
工序名称已存在
1049
工序编号已存在
1050
报工数配比需为大于0，且小数点之前最多10位，小数点之后最多6位的数字
1051
报工权限不能为空
1052
报工权限所传用户「用户id/name」不存在
1053
报工权限所传用户「用户id/name」存在重名用户
1054
报工权限所传部门「部门id/name」不存在
1055
报工权限所传部门「部门id/name」存在重名部门
1056
不良品项不能为空
1057
存在系统中不存在的不良品项
1058
工序采集字段不能重复
1059
工序采集数据不能大于{条数限制}
1060
工序采集数据暂不支持该字段类型：{type}

请求参数示例：
{
    "code": "242",
    "name": "外观检查-242",
    "outputRate": 1,
    "outputBlAll": true,
    "defects": [
        "qita"
    ]
}


工序列表查询
1.功能描述
外部系统可通过此接口能查询到小工单内的工序信息
2.接口定义
2.1请求地址：/api/dytin/external/process/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账户须有管理员权限）
- Content-Type:application/json
2.2请求参数
⚠️ 查询参数不能全部同时为空
参数名
字段类型
是否必填
字段解释
processCode
String
否
工序编号，支持模糊查询
processName
String
否
工序名称，支持模糊查询
createdAtGte
Boolean
否
创建时间大于等于，格式为：yyyy-MM-dd HH:mm:ss。如"2025-09-30 23:59:59"，返回创建时间大于等于"2025-09-30 23:59:59"的工序。
createdAtLte
String
否
创建时间小于等于，格式为：yyyy-MM-dd HH:mm:ss。如"2025-09-30 23:59:59"，返回创建时间小于等于"2025-09-30 23:59:59"的工序。
updatedAtGte
String

否
更新时间大于等于，格式为：yyyy-MM-dd HH:mm:ss。如"2025-09-30 23:59:59"，返回更新时间大于等于"2025-09-30 23:59:59"的工序。
updatedAtLte
String
否
更新时间小于等于，格式为：yyyy-MM-dd HH:mm:ss。如"2025-09-30 23:59:59"，返回更新时间小于等于"2025-09-30 23:59:59"的工序。
page

Object
否

分页参数对象。不传则默认每页 10条数据。具体样式例如： {"pageNum":1,"pageSize":50} 
表示每页 50条，查询第1页结果

2.3返回参数
参数名
类型
描述
code
string
成功与否 成功：01000000 ，其他为失败， 
data
[]
返回具体信息
msg
string
返回消息
reference
Object
返回自定义字段metadata信息 "allCustomFieldMetadata": {}

data
参数名
字段类型
描述
pageNum
Number
当前页数
pageSize
Number
每页数量
total
Number
总数
data
List<Object>


id
Number
工序id

code
String
工序编号

name
String
工序名称

outputRate
Number
报工数配比

outputUsers

List<Object>

报工权限-用户
[{"id":32996,"username":"test","name":"测试"},{"id":24662,"username":"admin","name":"系统管理员"}]

outputGroups
List<String>
报工权限-部门
[[{"id":5589,"name":"注塑部","code":"zhusu"},{"id":266,"name":"技术部","code":"JiShuBu1"}]]

outputBlAll
Boolean
是否允许所有人报工

fields
List<Object>
工序采集数据。支持类型：1 文本 2 数字 3 时间 4 照片;
[{"field":"组装人员","type":1},{"field":"组装时间","type":3}]

defects
List<String>
不良品项code列表
["BL202205100001","BL202205100002"]

creatorName
String
创建人

updatorName
String
更新人

updatedAt
String
更新时间

createdAt
String
创建时间

customFieldValues
List<Object>
用户自定义字段

🎯 工单
创建工单
1.功能描述
用于在小工单中创建工单。

生产任务
将根据请求字段自动生成：
1. 优先根据工艺路线编号生成
2. 工艺路线未传值时，根据工序列表生成
3. 工序列表未传值时，根据产品绑定工艺路线生成
4. 产品绑定工艺路线不存在时，生产任务部分为空，需要创建工单后再通过创建任务接口进行补充

用料清单
根据产品绑定物料清单自动生成：
5. 产品绑定物料清单不存在时，用料清单部分为空
6. 如需指定用料清单，可在工单创建成功后通过「工单-用料清单」接口进行变更

2.接口定义
2.1请求地址：/api/dytin/external/project/create
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账号须有工单创建权限，建议使用有管理员权限的账号）
- Content-Type:application/json

2.2请求参数
参数名
类型
是否必填
描述
projectCode

String

否
工单编号，没传根据编号规则生成
productCode
String

是

产品编号，校验是否传输该字段，若没有，则接口返回“1003”，校验所传产品编号在系统是否存在，若不存在，则接口返回“1010”
plannedNum

String

是
计划数，校验是否传输该字段，若没有，则接口返回“1004”

routing

String
否
工艺路线编号，若所传工艺路线编号在系统内不存在，则接口返回错误 1023。
如果传了工艺路线编号，将会忽略工序列表内容，按工艺路线包含的工序创建工单。
processlist
String[]

否
工序编号列表，若所传工序编号在系统内不存在，接口返回错误 1016。
如果传了工艺路线编号，该字段内容不生效。
如果未传工艺路线编号，且该字段也未传，则会尝试使用产品所关联的工艺路线创建工单。
如果产品也无绑定的工艺路线，则创建的工单中生产任务部分为空。
planStartTime

String
是
开始时间和结束时间必须同时存在，开始时间不能大于结束时间。
格式为：yyyy-MM-dd HH:mm:ss，校验是否传输该字段，若没有，则接口返回“1005”，若超出时间范围，接口返回“1006”，,若开始时间大于结束时间返回1007
例如："2025-01-01 12:00:00"
planEndTime 

String

是
开始时间和结束时间必须同时存在，开始时间不能大于结束时间。
格式为：yyyy-MM-dd HH:mm:ss，校验是否传输该字段，若没有，则接口返回“1005”，若超出时间范围，接口返回“1006”,若开始时间大于结束时间返回1007
例如："2025-08-01 12:00:00"
workOrderCustomFieldsValue

Object[]

否
工单自定义字段，传值方式见「自定义字段-传值说明」

remark
String
否
备注
priority
Number
否
工单优先级
1 加急 0 默认 -1 暂停不会同步修改任务优先级
workOrderCustomFieldsValue  Object[]
参数名
类型
是否必填
描述
name
String
是
自定义字段元数据名称
value
String
否
自定义字段属性值

请求参数示例：
{
    "projectCode":"xel0010",
    "productCode":"121",
    "plannedNum":1000,
    "planStartTime":"2021-09-08 00:00:00",
    "planEndTime":"2021-09-08 23:59:59",
    "workOrderCustomFieldsValue":[
        {
            "name":"自定义字段名称1",
            "value":"自定义字段值1"
        },
        {
            "name":"自定义字段名称2",
            "value":"自定义字段值2"
        }
    ]
}


2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
data[]
返回具体信息
msg
String
返回消息

data[]
参数名
类型
描述
projectCode
String
工单编号
projectId
Number
工单Id

postman请求示例：
配置header：

请求例子：


2.4错误代码
1001
工单编号不能为空
1002
工单编号已存在
1003
产品编号不能为空
1004
没有计划数
1005
没有计划开始时间或计划结束时间
1006
计划开始时间和计划结束时间超出范围
1007
计划开始时间大于计划结束时间
1008
没有工序编号
1009
没有工序名称
1010
产品编号不存在
1011
计划数不能小于0
1012
工单编号长度不能超过50
1013
计划数小数点不能超过6位

查询工单
1.功能描述
通过工单编号、工单状态，计划开始开始和计划结束时间，获取工单列表。
2.接口定义
2.1请求地址：/api/dytin/external/project/queryList3
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账户须有工单查询权限）
- Content-Type:application/json
示例：
X-AUTH:eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEzMzE0LCJvcmdJZCI6NTc0LCJzc29Ub2tlbiI6InYxdkxjV0tUdVhMVlpkencifQ.lH5cpUfbDW9lI-_J-rz0xPzPRbbK0JgT3Ukpbxd33Y_44abguPZiccCjlqy53pR4cKHP2sJxaxgR1iNWsEXpZA

2.2请求参数
⚠️ 查询参数不能全部同时为空
参数名
字段类型
是否必参
传参示例
字段解释
projectCodes
string[]
否
"projectCodes":["code1","code2"]
工单编号集合（不可与工单编号同时传）精确查询
projectCode
string
否
"projectCode":"code1"
工单编号（不可与工单编号集合同时传）模糊查询
projectId
integer
否
"projectId ":10001
与工单编号同时存在时，优先取工单编号
status
string
否
"status":"10"
工单状态(0-新建，10-执行中，20-已结束，30-已取消)，单选
formCode
string
否
"formCode":"code"
关联单据编号
planStartTime
string

否

"planStartTime":"2021-09-30 23:59:59"
计划开始时间，格式为：yyyy-MM-dd HH:mm:ss，例如"2021-09-30 23:59:59"。最大时间不可超过30天,开始时间不能大于结束时间。
planEndTime
string
否
"planEndTime":"2021-09-30 23:59:59"
计划结束时间，格式为：yyyy-MM-dd HH:mm:ss，最大时间不可超过30天,开始时间不能大于结束时间。
actualStartTime

String
否
"actualStartTime":"2021-09-30 23:59:59"
实际开始时间，格式为：yyyy-MM-dd HH:mm:ss，例如"2021-09-30 23:59:59"。最大时间不可超过30天,开始时间不能大于结束时间。实际开始时间和实际结束时间必须同时存在，否则查询失败。
特殊说明：工单编号和计划开始\结束时间和实际开始\结束时间不能同时为空，否则调用失败。
actualEndTime

String
否
"actualEndTime":"2021-09-30 23:59:59"
实际结束时间，格式为：yyyy-MM-dd HH:mm:ss，最大时间不可超过30天,开始时间不能大于结束时间。
planStartTimeGte

string
否
"planStartTimeGte":"2021-09-30 23:59:59"
大于等于计划开始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回计划开始时间大于等于"2021-09-30 23:59:59"的工单。
planStartTimeLte

string
否
"planStartTimeLte":"2021-09-30 23:59:59"
小于等于计划开始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回计划开始时间小于等于"2021-09-30 23:59:59"的工单。
planEndTimeGte

string
否
"planEndTimeGte":"2021-09-30 23:59:59"
大于等于计划结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回计划结束时间大于等于"2021-09-30 23:59:59"的工单。
planEndTimeLte
string
否
"planEndTimeLte":"2021-09-30 23:59:59"
小于等于计划结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回计划结束时间小于等于"2021-09-30 23:59:59"的工单。
actualStartTimeGte

string
否
"actualStartTimeGte":"2021-09-30 23:59:59"
大于等于实际开始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回计划结束时间小于等于"2021-09-30 23:59:59"的工单。
actualStartTimeLte
string
否
"actualStartTimeLte":"2021-09-30 23:59:59"
小于等于实际开始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回实际开始时间小于等于"2021-09-30 23:59:59"的工单。
actualEndTimeGte

string
否
"actualEndTimeGte":"2021-09-30 23:59:59"
大于等于实际结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回实际开始时间大于等于"2021-09-30 23:59:59"的工单。
actualEndTimeLte
string
否
"actualEndTimeLte":"2021-09-30 23:59:59"
小于等于实际结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回实际开始时间小于等于"2021-09-30 23:59:59"的工单。
prioritys
List<integer>
否
"prioritys":[1]
优先级，0 无，1 加急 -1 暂停
createdAtGte
string
否
"createdAtGte":"2021-09-30 23:59:59"
创建时间大于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
 与createdAtLte时间相差不能超过180天
createdAtLte
string
否
"createdAtLte":"2021-09-30 23:59:59"
创建时间小于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
与createdAtGte时间相差不能超过180天
updatedAtGte
string
否
"updatedAtGte":"2021-09-30 23:59:59"
更新时间大于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29"  与updatedAtLte时间相差不能超过180天 
updatedAtLte
string
否
"updatedAtLte": "2021-09-30 23:59:59"
更新时间小于 格式为：yyyy-MM-dd HH:mm:ss  例如"2025-08-26 11:12:29" 与updatedAtGte时间相差不能超过180天
page

Object
否
"page":{"pageNum":1,"pageSize":10}
不传 默认每页 10条数据,最大限度每页100条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条
   customFieldSearchConditions

Object

否
"customFieldSearchConditions":
[
        {
            "name": "字段名",
            "queryOperator": "equal",
            "fieldValue": [
                "测试"
            ]
        }
    ],
自定义字段查询  具体传参说明请看文档描述 「自定义字段传参说明」
 例子：
"customFieldSearchConditions":
[
        {
            "name": "字段名",
            "queryOperator": "equal",
            "fieldValue": [
                "测试"
            ]
        }
    ],

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
[]
返回具体信息
msg
string
返回消息
page
Object
返回分页信息     "page": {"pageNum": 1, "pageSize": 10, "total": 477,  }
reference
Object
返回自定义字段metadata信息 "allCustomFieldMetadata": {}

data[]
参数名
字段类型
描述

id
number
工单id

projectCode

string

工单编号

status
string
工单状态

productCode
string
产品编号

productName
string
产品名称

productSpecification
string
产品规格

planAmount
string
计划数

projectUnit
string
单位

planStartTime
LocalDateTime
计划开始时间
例如"2025-09-01 12:00:00"
planEndTime
LocalDateTime
计划结束时间
例如"2025-09-01 12:00:00"
actualStartTime
LocalDateTime
实际开始时间
例如"2025-09-01 12:00:00"
actualEndTime
LocalDateTime
实际结束时间
 例如"2025-09-01 12:00:00"
numberofGoodProducts
string
良品数

numberofDefectives
string

不良品数

createTime
LocalDateTime
创建时间
 例如"2025-09-01 12:00:00"
updateTime
LocalDateTime
更新时间
 例如"2025-09-01 12:00:00"
actualAmount
string
实际数

creator
string
创建人

updator
string
修改人

remark
string
备注

customFieldValues
object[]
工单自定义字段

qrCode
string
工单二维码，例如："projectId:70330"

formCode
string
关联单据编号，不存在时返回""

externalId
long
关联外部id

priority 
integer
优先级
优先级，0 无，1 加急 -1 暂停
shipmentAmount
Number
发货数量

returnAmount
Number
退货数量

unclearAmount
Number
未清数量


2.4错误代码
1007
计划开始时间不能大于计划结束时间
1006
计划开始时间和计划结束时间超出范围
1005
没有计划开始时间或计划结束时间
1014
工单编号不存在/工单ID不存在
1026
工单状态不存在
1040
查询字段工单编号、工单ID、计划开始时间、计划结束时间、实际开始时间、实际结束时间不能同时为空
1041
查询字段实际开始、结束时间必须同时存在
1042
实际开始时间不能大于实际结束时间
1043
实际开始时间和时间结束时间超出最大限制


查询工单当前工序
1.功能描述
通过该接口可查询工单列表补充信息，工单的当前工序信息
2.接口定义
2.1请求地址：/api/dytin/external/project/queryList/expand
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账户须有工单查询权限）
- Content-Type:application/json
示例：
X-AUTH:eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEzMzE0LCJvcmdJZCI6NTc0LCJzc29Ub2tlbiI6InYxdkxjV0tUdVhMVlpkencifQ.lH5cpUfbDW9lI-_J-rz0xPzPRbbK0JgT3Ukpbxd33Y_44abguPZiccCjlqy53pR4cKHP2sJxaxgR1iNWsEXpZA

2.2请求参数
参数名
类型
是否必参
描述
projectIds

Number[]

是

工单id集合


示例：
{"projectIds":[5366532,5368104]}


2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
data[]
返回具体信息
msg
string
返回消息

data[]
参数名
类型
描述
projectId
Number
工单Id
currentProcessId
Number
当前工序id
currentProcessName
String
当前工序名称

2.4错误代码
暂无
批量删除工单
1.功能描述
通过该接口外部系统可批量删除小工单系统内的工单，一次删除数量不能超过100数量工单
2.接口定义
2.1请求地址：/api/dytin/external/project/batch/delete
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
字段类型
是否必填
字段解释
projectCodes
String[]
否
工单编号列表；与工单id列表，二选一不能同时为空
projectIds
Number[]
否
工单id列表；与工单编号列表，二选一不能同时为空

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Number
返回 删除数量
msg
String
返回消息

请求参数示例：
{
    "projectCodes": [
        "SCRW000421",
        "SCRW000453",
        "SCRW000454",
        "SCRW000455",
        "SCRW000456",
        "SCRW000457",
        "SCRW000458",
        "SCRW000463",
        "SCRW000465",
        "SCRW000466",
        "SCRW000467",
        "SCRW000468",
        "SCRW000469",
        "SCRW000470",
        "SCRW000472",
        "SCRW000475",
        "SCRW000476",
        "SCRW000485",
        "SCRW000486"
    ]
}


更新工单 
1.功能描述
通过该接口外部系统可更新在小工单的工单信息。
2.接口定义
2.1请求地址：/api/dytin/external/project/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账户须有工单编辑权限）
- Content-Type:application/json
2.2请求参数
注意:非必参不想传的话不传即可，Object[] 类型字段不要传null。
参数名
类型
是否必填
描述
projectCode

String

是
工单编号，校验是否传输该字段，若没有，则接口返回“1001”，若不存在则返回1014
productCode
String

否
产品编号，校验所传产品编号在系统是否存在，若不存在，则接口返回“1010”；
如果传了产品编号，则计划数，开始时间，结束时间都是必参；如果没传产品编号，则只有工单号是必参
plannedNum

String

否
计划数；
注意：此接口仅更新工单计划数，任务上的计划数需要通过调用更新任务接口实现
planStartTime

String
否
开始时间和结束时间必须同时存在，开始时间不能大于结束时间。
格式为：yyyy-MM-dd HH:mm:ss，例如"2025-09-01 12:00:00", 若超出时间范围，接口返回“1006”，,若开始时间大于结束时间返回1007
planEndTime 
String

否
开始时间和结束时间必须同时存在，开始时间不能大于结束时间。
格式为：yyyy-MM-dd HH:mm:ss， 例如"2025-09-01 12:00:00", 若超出时间范围，接口返回“1006”,若开始时间大于结束时间返回1007
workOrderCustomFieldsValue
Object[]

否
工单自定义字段 ，传值方式见「自定义字段-传值说明」


remark
String
否
备注
priority
Number
否
工单优先级
1 加急 0 默认 -1 暂停不会同步修改任务优先级
workOrderCustomFieldsValue   Object[]
参数名
类型
是否必填
描述
name
String
是
自定义字段元数据名称
value
String
否
自定义字段属性值

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回具体信息
msg
String
返回消息

data    object
参数名
类型
描述
projectCode
String
工单编号
projectId
Number
固定值 null

2.4错误代码
1001
工单编号不能为空
1014
工单编号不存在
1005
没有计划开始时间或计划结束时间
1006
计划开始时间和计划结束时间超出范围
1007
计划开始时间大于计划结束时间
1010
产品编号不存在
1011
计划数不能小于0
1012
工单编号长度不能超过50
1013
计划数小数点不能超过6位

请求参数示例：
{
    "projectCode": "SEORD001698-1",
    "productCode": "11.0679-02-003",
    "plannedNum": 6000,
    "planStartTime": "2025-05-12 00:00:00",
    "planEndTime": "2025-05-17 00:00:00",
    "workOrderCustomFieldsValue": [
        {
            "name": "色粉号",
            "value": "ABS火花纹"
        },
        {
            "name": "客户编码",
            "value": "270216040"
        },
        {
            "name": "关联",
            "value": "11.0679-02-003"
        },
        {
            "name": "镶件规格",
            "value": ""
        }
    ]
}


更新工单状态
1.功能描述
通过工单编号或工单id更新工单状态。
2.接口定义
2.1请求地址：/api/dytin/external/project/updateStatus
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
字段类型
是否必填
字段解释
projectCode
String
否
工单编号
projectId
Number
否
工单id （与工单编号同时存在时优先取工单id）
status
Number
是
工单状态(0-新建，10-执行中，20-已结束，30-已取消)
updateTasks
boolean
否
是否同步任务 false 否 true 是  默认不传值是false

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
[]
返回具体信息
msg
String
返回消息

请求参数示例：
{
    "projectCode": "j250003560-0015",
    "status": 20
}

🎯  工艺路线
创建工艺路线
1.功能描述
通过该接口可在小工单新增工艺路线。
2.接口定义
2.1请求地址：/api/dytin/external/processRouting/2.0/add
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
类型
是否必填
描述
processRoutingCode
String
否
工艺路线编号。若不传，系统自动生成。
合法性校验同web端processRoutingName
String
是
工艺路线名称。若不传，则创建失败，提示工艺路线名称不能为空
合法性校验同web端processRoutingSteps
Object[]
是
工序列表，包含字段如下：
seq

Number
是
序号
限制 > 0的整数type

Number
是

类型
1 - 工序2 - 工艺路线非以上值，则创建失败，提示：不支持的工序类型
processOrSubRoutingCode

String
是

工序或工艺路线编号
限制同web端，工艺路线层级不支持超过2层需要校验工序/工艺路线是否已存在不存在时，创建失败，提示工序/工艺路线不存在outputRate

Number
取决于字段 type 的传值
报工数配比
类型为1（工序），必填类型为2（工艺路线），非必填，传值自动忽略customFieldValues

Object[]
取决于字段 type 的传值

自定义字段
类型为1（工序），根据实际需求传值或不传值，传值方式见「自定义字段-传值说明」类型为2（工艺路线），传值自动忽略
请求参数示例：
{
    "processRoutingCode": "api生成250319001",
    "processRoutingName": "api生成250319",
    "processRoutingSteps": [
        {
            "seq": 1,
            "type": 1, 
            "processOrSubRoutingCode": "code486992",
            "outputRate": 1.2,
            "customFieldValues": [
                {
                    "name": "工序-文本",
                    "value": "请求的文本"
                },
                {
                    "name": "工序-数字",
                    "value": 3.33
                }
            ]                                // 工序类型示例
        },
        {
            "seq": 2,
            "type": 2,
            "processOrSubRoutingCode": "工艺路线编号778024"
        }                                    // 工艺路线示例
    ]
}



2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
返回一个包含创建出来的工艺线路线编号字段的对象，如{"processRoutingCode":"gylx001"}
msg
String
返回消息


2.4错误信息

01003017
（注：错误代码返回01003017，错误提示信息各不相同）

工艺路线名称不能为空
工艺路线名称不符合规则
工艺路线编号不符合规则
01003019
工艺路线名称不能重复
01003024
工艺路线编号不能重复
01003027
工序列表不能为空
01003025
工艺路线最多创建10000条

工艺路线编辑接口
1.功能描述
通过该接口可在小工单编辑工艺路线。
❗️此接口为覆盖更新逻辑，未传值的自定义字段内容将会被置空

2.接口定义
2.1请求地址：/api/dytin/external/processRouting/2.0/update
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
类型
是否必填
描述
processRoutingCode
String
是
工艺路线编号。若不传，则编辑失败，提示工艺路线编号不能为空
合法性校验同web端processRoutingName
String
是
工艺路线名称。若不传，则编辑失败，提示工艺路线名称不能为空
合法性校验同web端processRoutingSteps
Object[]
是
工序列表，包含字段如下：
seq
Number
是
序号，是明细行的更新依据。若不传，则编辑失败，提示序号不能为空
type

Number
是
支持以下参数：
1 - 工序2 - 工艺路线非以上值，则更新失败，提示：不支持的工序类型
processOrSubRoutingCode

String
是

工序或工艺路线编号
限制同web端，工艺路线层级不支持超过2层需要校验工序/工艺路线是否已存在不存在时，创建失败，提示工序/工艺路线<替换请求内容>不存在outputRate

Number
取决于字段 类型 的传值
类型为1（工序），必填类型为2（工艺路线），非必填，传值自动忽略customFieldValues

Object[]
取决于字段「类型」的传值
类型为1（工序），支持同步自定义字段，传值方式见「自定义字段-传值说明」类型为2（工艺路线），传值自动忽略

2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
返回一个包含创建出来的工艺线路线编号字段的对象，如{"processRoutingCode":"gylx001"}
msg
String
返回消息


2.4错误信息

01003017
（注：错误代码返回01003017，错误提示信息各不相同）

工艺路线名称不能为空
工艺路线名称不符合规则
工艺路线编号不符合规则
01003019
工艺路线名称不能重复
01003024
工艺路线编号不能重复
01003027
工序列表不能为空
01003025
工艺路线最多创建10000条


工艺路线删除接口
1.功能描述
通过该接口可在小工单删除工艺路线。

2.接口定义
2.1请求地址：/api/dytin/external/processRouting/delete
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
类型
是否必填
描述
processRoutingCode
String
是
工艺路线编号。若不传，则删除失败，提示工艺路线编号不能为空
删除限制同web端，被工单、产品引用时，无法删除
2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
msg
String
返回消息

2.4错误信息
01003017
（注：错误代码返回01003017，错误提示信息各不相同）
工艺路线编号不符合规则

产品正在使用，无法删除


工艺路线查询接口
1.功能描述
通过该接口可在小工单新增工艺路线
2.接口定义
2.1请求地址：/api/dytin/external/processRouting/queryList2
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
类型
是否必填
描述
processRoutingCode
String
否
工艺路线编号 不支持和工艺路线编号集合同时传（模糊查询）
processRoutingCodeList
String[]
否
工艺路线编号集合 不支持和工艺路线编号同时传（精确查询）
processRoutingName
String
否
工艺路线名称（模糊查询）
createdAtGte
String
否
创建开始时间大于等于 格式为：yyyy-MM-dd HH:mm:ss 
例如："2025-02-10 12:00:00"
createdAtLte
String
否
创建结束时间小于等于 格式为：yyyy-MM-dd HH:mm:ss
例如："2025-02-10 12:00:00"
updatedAtGte
String
否
更新开始时间大于等于 格式为：yyyy-MM-dd HH:mm:ss
例如："2025-02-10 12:00:00"
updatedAtLte
String
否
更新结束时间小于等于 格式为：yyyy-MM-dd HH:mm:ss
例如："2025-02-10 12:00:00"
page

Object
否
不传 默认每页 10条数据 "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50} 每页 50条
最大支持每页100条

2.3返回参数

参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回信息
msg
String
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "process17235164190354": { }}}

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
参数名
字段类型
描述
processRoutingCode
String
工艺路线编号
processRoutingName
String
工艺路线名称
createdAt
String
创建时间 例如"2025-09-01 12:00:00"
updatedAt
String
更新时间 例如"2025-09-01 12:00:00"
processRoutingSteps
Object[]

工序列表
参数名                    类型               描述
id                            Number             行id 
seq                         Number         序号
type                        Number         类型 1工序 2 工艺路线
name                      String           工序/子工艺路线名称
code                       String         工序/子工艺路线编号
outputRate             Number    报工数配比
processRoutingSteps    Object[]           工序列表，type=2时有值
customFieldValues         Object[]         自定义字段，type=1时有值


2.4错误信息
🎯  物料清单
创建/更新物料清单
1.功能描述
外部系统通过此接口可将在外部系统的物料清单同步至小工单系统内。
  - 一个接口处理创建和更新
  - 不存在的物料清单明细：创建
  - 存在的物料清单明细：更新单位用量、备注、自定义字段、投料工序
2.接口定义
2.1请求地址：/api/dytin/external/materials/upsert
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
lastProductCode
String
是
父项产品编号
nextProducts
Object[]
否
子项产品集合（见下表）

nextProducts传参详情
参数名
字段类型
是否必填
字段解释
nextProductCode
String
是
子项产品编号
feedProcessCode
String
否
投料工序编号
unitQty
String
否
单位用量 默认：1
remark
String
否
备注
customFieldValues

Object[]
否
物料清单自定义字段，传值方式见「自定义字段-传值说明」

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回数据
msg
String
返回消息

2.3错误信息
1064
父项物料不存在
1065
子项物料不存在
1071
相同子项产品的投料工序不能重复
1070
投料工序不存在


01000002

单位用量整数位支持最多10位,小数位最多6位
备注超出字段长度限制,支持最多120

删除物料清单
1.功能描述
外部系统通过此接口删除物料清单
2.接口定义
2.1请求地址：/api/dytin/external/materials/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
lastProductCode
String
是
父项产品编号
nextProductCode
String
是
子项产品编号
feedProcessCode
strign
否
投料工序


2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回数据
msg
String
返回消息

2.3错误信息
1064
父项物料不存在
1065
子项物料不存在
1068
物料清单不存在

查询物料清单
1.功能描述
外部系统通过此接口查询小工单系统内的物料清单。
- 列表支持分页，分页逻辑同web端
2.接口定义
2.1请求地址：/api/dytin/external/materials/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释 （以下参数至少传一个）
lastProductCode
String
否
父项产品编号
nextProductCode
String
否
子项产品编号
feedProcessCode
String
否
投料工序编号
remark
String
否
备注
createdAtGte
String
否
创建时间大于等于 yyyy-MM-dd HH:mm:ss 例如 "2025-09-01 12:00:00"
createdAtLte
String
否
创建时间小于等于 yyyy-MM-dd HH:mm:ss  例如 "2025-09-01 12:00:00"
updatedAtGte
String
否
更新时间大于等于 yyyy-MM-dd HH:mm:ss  例如 "2025-09-01 12:00:00"
updatedAtLte
String
否
更新时间小于等于 yyyy-MM-dd HH:mm:ss 例如 "2025-09-01 12:00:00"
page

Object
否
不传 默认每页 10条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条


2.3返回参数

参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回信息
msg
String
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "process17235164190354": { }}}

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]
参数名
字段类型
描述
lastProductCode
String
父项产品编号
nextProductCode
String
子产品编号
feedProcessCode
Stirng
投料工序编号
unitQty
Number
单位用量
remark
String
备注
createdAt
String
创建时间 例如"2025-09-01 12:00:00"
updatedAt
String
更新时间 例如"2025-09-01 12:00:00"
customFieldValues
List<Object>
自定义字段


2.4错误信息
1069
请求参数至少存在一个




🎯 自定义表单（装配工单/销售订单/生产计划）
自定义表单（生产计划/装配工单/生产管理-销售订单）创建接口
1.功能描述
外部系统通过此接口创建自定义表单，明细行里工单需要预先创建完成，通过此接口创建表单与工单关联一起。
2.接口定义
2.1请求地址：/api/dytin/external/custom_form/create
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
formCode
String
否
表单编号，未填时根据编号规则自动生成
formType

Number

是

自定义表单类型：
101 销售订单102 生产计划103 装配工单customFieldValueList
List<Object>
否
自定义表单表头自定义字段，传值方式见「自定义字段-传值说明」
projectCOList

List<Object>

是
明细行，传工单编号或id
[
        {
            "projectCode": "GD230001-001",
            "projectId": 123456
        },
        {
            "projectCode": "GD230001-002",
            "projectId": 123457
        }
    ]

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
String
自定义表单编号
msg
String
返回消息

2.4 错误信息
01010001
单位用量不能为空
层级编号不能为空
层级编号不能重复
父级编号不能为空
自定义表单字段不能为空
01010002
自定义表单产品不能为空
01010003
存在非法产品
01010004
表单编号不能为空
01010005
表单编号已存在
01010006
表单值不存在
01010007
自定义表单字段值已删除
01010008
工单编号重复
01010009
产品明细数量不能大于1000
01010010
自定义表单字段值不处于删除状态，无法恢复

自定义表单更新接口
1.功能描述
外部系统通过此接口更新自定义表单。
2.接口定义
2.1请求地址：/api/dytin/external/custom_form/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
customFormFieldId
Number
否

customFormFieldId(自定义表单主键id)或fromCode任意一个必填，用于定位待更新自定义表单
formCode
String
否
customFormFieldId(自定义表单主键id)或fromCode任意一个必填，用于定位待更新自定义表单
customFieldValueList
List<Object>
是
自定义表单表头自定义字段，传值方式见「自定义字段-传值说明」

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
String
自定义表单编号
msg
String
返回消息

自定义表单更新明细行
1.功能描述
外部系统通过此接口更新自定义表单明细行，原先的明细工单不会删除，此接口仅是追加明细行工单；
 若需要删除明细行工单，调用删除明细行工单接口
2.接口定义
2.1请求地址：/api/dytin/external/custom_form/item/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
customFormFieldId
Number
否
customFormFieldId(自定义表单主键id)或fromCode任意一个必填，用于定位待更新自定义表单
formCode
String
否
customFormFieldId(自定义表单主键id)或fromCode任意一个必填，用于定位待更新自定义表单
projectCOList

List<Object>

是
明细行，明细行及相关上限同web端一致。
[
        {
            "projectCode": "GD230001-001",
            "projectId": 123456
        },
        {
            "projectCode": "GD230001-002",
            "projectId": 123457
        }
    ]

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
String
自定义表单编号
msg
String
返回消息

删除自定义表单明细行
1.功能描述
外部系统通过此接口删除自定义表单明细行。
2.接口定义
2.1请求地址：/api/dytin/external/custom_form/item/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
customFormFieldId
Number
否
customFormFieldId(自定义表单主键id)或fromCode任意一个必填，用于定位待更新自定义表单
formCode
String

否

customFormFieldId(自定义表单主键id)或fromCode任意一个必填，用于定位待更新自定义表单
deleteMode

Number
否
删除模式
10 默认，仅支持删除「未开始」状态的工单20 强制删除，忽略工单状态，删除工单及关联所有任务和报工projectCOList

Object[]

是
[
        {
            "projectCode": "GD230001-001",
            "projectId": 123456
        },
        {
            "projectCode": "GD230001-002",
            "projectId": 123457
        }
    ]

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
String
自定义表单编号
msg
String
返回消息

查询自定义表单
1.功能描述
外部系统通过此接口查询自定义表单
2.接口定义
2.1请求地址：/api/dytin/external/custom_form/item/queryList2
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
formCode
String
否
表单编号
formType

Number

是

自定义表单类型：
101 销售订单102 生产计划103 装配工单status

Number
否
表单状态
0：未开始10：执行中20：已完成createdAtStart
String
否
创建日期大于等于  yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
createdAtEnd
String
否
创建日期小于等于  yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
updatedAtStart
String
否
更新日期大于等于  yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
updatedAtEnd
String
否
更新日期小于等于  yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
page

Object[]
否
分页 不传 默认每页 10条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条

.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回信息
msg
String
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "so17235164190354": { }}}

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
参数名
类型
描述
customFormFieldId
Number
自定义表单id
serialCode
String
表单编号
formType
Number

自定义表单类型：
101 销售订单102 生产计划103 装配工单productStatistics
Object
表单进度，字段见下方
productCount
Number
明细行数量 代表工单数
totalPlanAmount
Number
明细行合计计划数（同web端取值逻辑）
finishedAmount
Number
明细行合计完工数（同web端取值逻辑）
totalFineAmount
Number
明细行合计良品数（同web端取值逻辑）

status

Number
表单状态
0：未开始10：执行中20：已完成customFieldsValue
Object[]
自定义表单的表头自定义字段
createdAt
String
创建日期大于等于
updatedAt
String
更新日期小于等于
page
Object[]
分页




🎯  用料清单
创建用料清单接口
1.功能描述
本接口用于在已有工单中创建用料清单，每一次创建将本次同步的用料添加至最后。
2.接口定义
2.1请求地址：/api/dytin/external/projectMaterials/add
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
projectCode
String
否，工单编号或id至少传1个
工单编号
projectId

否，工单编号或id至少传1个
工单id
productCode

String

是

产品编号

unitQty
Number
是
单位用量
feedProcessCode
String
否
投料工序-编号
feedProcessId
Number
否
投料工序-id
remark
String
否
备注
customFieldValueList

Object[]
否

用料清单自定义字段值，传值方式见「自定义字段-传值说明」

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，               
data
Object
返回数据
msg
String
返回消息

data
参数名
类型
描述
id
Number
用料清单id
seq
Number
序号


更新用料清单接口
1.功能描述
本接口用于更新已有的用料清单。可通过以下2种方式定位到待更新的用料清单明细行，具体如下：
1. id，更新指定明细行
2. 工单（id或编号）+ 用料清单明细行序号
以上方式任选一即可。若传入的请求参数（组合）不属于以上2种情况之一，则无法保证请求处理结果的正确性。
注意：接口将覆盖更新用料清单上的所有字段，不填将清空原字段值。
2.接口定义
2.1请求地址：/api/dytin/external/projectMaterials/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
用料清单明细行id
seq
intger
否
用料清单明细行序号
projectId
Number
否
工单id
projectCode
String
否
工单编号
productCode

String

是

产品编号
unitQty
Number
是
单位用量
feedProcessCode
String
否
投料工序-编号
feedProcessId
Number
否
投料工序-id
remark
String
否
备注
customFieldValueList
list
否

用料清单自定义字段值，传值方式见「自定义字段-传值说明」


2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，               
data
Object
返回数据
msg
String
返回消息

查询用料清单
1.功能描述
本接口用于查询指定工单下的所有用料清单。
2.接口定义
2.1请求地址：/api/dytin/external/projectMaterials/queryList2
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
projectId
Number
否，id或编号至少传1个
工单id
projectCode
String
否，id或编号至少传一个
工单编号


2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，               
data
Object[]
返回数据
msg
String
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "materials17235164190354": { }}}

data
参数名称
类型
是否必填
参数解释
id
Number
否
用料清单明细行id
seq
Number
否
用料清单明细行序号
productCode
String

是

产品编号

unitQty
Number
是
单位用量
feedProcessCode
String
否
投料工序-编号
feedProcessId
Number
否
投料工序-id
remark
String
否
备注
customFieldValueList
Object[]
否
用料清单自定义字段值

删除用料清单
1.功能描述
本接口用于删除指定工单下的用料清单。可通过以下3种方式定位到待删除的用料清单明细行，具体如下：
1. 工单（id或编号），删除工单下所有用料清单
2. id，删除指定id的用料清单明细行
3. 工单（id或编号）+ 用料清单明细行序号，删除指定用料清单明细行
以上方式任选一即可。若传入的请求参数（组合）不属于以上3种情况之一，则无法保证请求处理结果的正确性。
2.接口定义
2.1请求地址：/api/dytin/external/projectMaterials/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
用料清单明细行id
seq
Number
否
用料清单明细行号
projectId
Number
否
工单id
projectCode
String
否
工单编号


2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，               
data
Object[]
返回数据
msg
String
返回消息



 🎯 任务
创建任务
1.功能描述
通过工单编号和工序编号新增工单下任务。
- 计划数：根据公式计划数默认值，自动计算
- 计划开始、结束时间：取工单计划开始、结束时间
- 报工数配比：取工序报工数配比
- 自定义字段：暂不支持同步（待更新，可以通过任务更新接口实现）
2.接口定义
2.1请求地址：/api/dytin/external/task/add
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账户须有任务编辑权限）
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
projectCode
String
是
工单编号
processCode
String
是
工序编号
planAmount
String
否
计划量
seq
Number
否
插入工序在工单任务里的序号，不填默认在最后一位
outputRate
String
否
报工数配比
priority

Number

否
任务优先级
1 加急 0 默认 -1 暂停processStartTime
String
否
计划开始时间 例如"2025-09-01 12:00:00"
processEndTime
String
否
计划结束时间 例如"2025-09-01 12:00:00"
taskCustomFieldsValue
object[]
否
自定义字段  暂不支持同步，默认填充为所选工序的自定义字段值（可以通过任务更新接口改值）

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回数据
msg
String
返回消息

data
参数名
字段类型
描述
projectProcessId
Number
任务id

删除任务
1.功能描述
通过此接口删除工单编号下的任务
2.接口定义
2.1请求地址：/api/dytin/external/task/del
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账户须有任务编辑权限）
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
projectCode
String
是
工单编号
projectProcessId
Number
是
任务id
isOutPut
boolean
否
默认否，true：代表删除任务报工数据
               false：代表不删除任务报工数据
如果传否，或者不传，任务下有报工数据的话 会返回删除失败

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
String
返回消息

更新任务
1.功能描述
支持通过以下参数组合，定位到指定任务后进行更新：
1. 工单编号 + 工序编号。存在重复工序时，多个工序同步修改；此组合会忽略任务id、序号两个参数
2. 工单编号 + 序号。可以通过序号唯一定位到待更新任务，前提：工序编号为空
3. 工单编号 + 任务id。可以通过任务id唯一定位到待更新任务，前提：工序编号、序号为空
报工数配比固定取创建时刻的值，不会根据更新时刻的最新工单计划数及任务计划数自动刷新。
2.接口定义
2.1请求地址：/api/dytin/external/task/modify
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账户须有任务编辑权限）
- Content-Type:application/json
2.2请求参数
注意:非必参不想传的话不传即可，Object[] 类型字段不要传null。
参数名
字段类型
是否必填
字段解释
projectCode
String
是
工单编号
processCode
String
否
工序编号
planAmount
String
否
计划数
projectProcessId
Number
否
任务Id
seq

Number
否
序号
（序号不会被更新，但可以传入，想调整seq，可以通过add，会自动重新排序。
譬如123，插入2，则变为 1 2 3(原2) 4(原3)
outputRate
String
否
报工数配比
priority
Number

否
任务优先级
1 加急 0 默认 -1 暂停processStartTime
String
否
计划开始时间 例如"2025-09-01 12:00:00"
processEndTime
String
否
计划结束时间 例如"2025-09-01 12:00:00"
taskCustomFieldsValue

Object[]
否
任务自定义字段，传值方式见「自定义字段-传值说明」


2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
object
null
msg
String
返回消息

2.4错误代码
1001
工单编号不能为空
1027
工序编号不能为空
1014
工单编号不存在
1016
工序编号不存在
1028
工序编号不在工单内
1011
计划数不能小于0
1013
计划数整数位不能超过10位，小数点不能超过6位
1007
计划开始时间不能大于计划结束时间
1006
计划开始时间和计划结束时间超出范围



更新任务状态
1.功能描述
通过工单编号和任务id定位到指定任务，更新状态。
2.接口定义
2.1请求地址：/api/dytin/external/task/updateStatus
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账户须有任务编辑权限）
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
projectCode
String
否
工单编号（与projectId二选一）
projectId
Number
否
与工单编号同时存在时，以工单编号为准
projectProcessId
Number
是
任务id
processStatus
Number
是
任务状态:0:未开始；10：执行中；20：已结束

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
object
null
msg
String
返回消息

2.4错误代码
1001
工单编号不能为空
1034
任务id不能为空
1014
工单编号不存在
1036
任务id不在工单内
1035
任务状态不能为空
1037
任务状态不存在


任务列表查询
1.功能描述
通过工单编号查询工单下的任务列表。
20230608  支持任务列表返回seq
2.接口定义
2.1请求地址：/api/dytin/external/task/queryList2
通信协议：HTTPS
请求方式：POST
2.2请求参数
注意:非必参不想传的话不传即可，Object[] 类型字段不要传null。
参数名
字段类型
是否必填
字段解释
projectCode
String
是
工单编号
processCode
String
否
工序编号
projectIds
Number[]
否
工单id集合 与工单编号二选一，不能同时为空
processStartTimeGte
String
否

计划开始时间大于等于 例如"2025-09-01 12:00:00"

processEndTimeGte
String
否
计划结束时间大于等于 例如"2025-09-01 12:00:00"
processStartRealTimeGte
String
否
实际开始时间大于等于 例如"2025-09-01 12:00:00"
processEndRealTimeGte
String

否
实际结束时间大于等于 例如"2025-09-01 12:00:00"
processStartTimeLte
String
否
计划开始时间小于等于 例如"2025-09-01 12:00:00"
processEndTimeLte
String
否
计划结束时间小于等于 例如"2025-09-01 12:00:00"

processStartRealTimeLte
String
否
实际开始时间小于等于 例如"2025-09-01 12:00:00"
processEndRealTimeLte
String
否
实际结束时间小于等于 例如"2025-09-01 12:00:00"
createdAtGte
String
否
创建时间大于等于 例如"2025-09-01 12:00:00"
createdAtLte
String
否
创建时间小于等于 例如"2025-09-01 12:00:00"
updatedAtGte
String
否
更新时间大于等于 例如"2025-09-01 12:00:00"
updatedAtLte
String
否
更新时间小于等于 例如"2025-09-01 12:00:00"
page

Object
否
不传 默认每页 10条数据,最大限度每页100条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条
   customFieldSearchConditions
Object
否
自定义字段查询  具体传参说明请看文档描述 「自定义字段传参说明」
 例子：
"customFieldSearchConditions":
[
        {
            "name": "字段名",
            "queryOperator": "equal",
            "fieldValue": [
                "测试"
            ]
        }
    ],

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据。当失败时该字段返回值为null
msg
String
返回消息
page
Object
返回分页信息     "page": {"pageNum": 1, "pageSize": 10, "total": 477,  }
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "process17235164190354": { }}}

data[]
参数名
字段类型
描述
id
Number
任务id
projectId
Number
工单id
projectCode
String
工单编号
processCode
String
工序编号
processId
Number
工序模版id
processName
String
工序名称
processStatus
Number
工序状态 枚举备注: 
0 :未开始 10 :执行中 20 :已结束
productCode
String
产品名称
productName
String
产品名称
productSpec
String
产品规格
outputObject
object []
报工权限
taskOperators
object []
分配列表
isDistribute
Boolean
任务分配状态 true 已分配；false 未分配
distributeAmount
Number
任务分配总数
outputRate
Number
报工数配比
planAmount
Number
计划数
fineAmount
Number
良品数
defectsAmount
Number
不良品数
remnantAmount
Number
工序报工剩余数（计划数-良品数）
approvedFineAmount
Number
工序已审批报工良品数
approvedDefectsAmount
Number
工序已审批报工不良品数
unApprovedFineAmount
Number
工序待审批报工良品数
unApprovedDefectsAmount
Number
工序待审批报工不良品数
outputDurationForSecond
Number
工序累计报工时长(秒)
sumOutputSalary
Number
工序累计工资
processStartTime
String
计划开始时间 例如"2025-09-01 12:00:00"
processEndTime
String
计划结束时间 例如"2025-09-01 12:00:00"
processStartRealTime
String
实际开始时间 例如"2025-09-01 12:00:00"
processEndRealTime
String
实际结束时间  例如"2025-09-01 12:00:00"
lastOutputTime
String
最近报工时间 例如"2025-09-01 12:00:00"
customFieldValues
object []
自定义字段
seq
Number
序号
priority
Number
任务优先级；1 加急、0 默认、-1 暂停
remark
string
子工艺路线备注

2.4错误代码
1001
工单编号不能为空


🎯  报工
创建报工
1.功能
通过该接口可在小工单新增报工数据。
2.接口定义
2.1请求地址：/api/dytin/external/output/add
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json

2.2请求参数

参数名
类型
是否必填
描述
projectCode
String

否
工单编号和工单id不能同时为空

projectId
Number
否
工单id和工单编号不能同时为空，与工单编号同时存在时，优先取工单编号
projectProcessId
Number
是
任务id
operatorId
Number
是
报工用户id，小工单内
operatorName
String
是
报工用户姓名
outputAmount
Number
是
报工数
fineAmount
Number
否
良品数和不良品数不能同时为空
defectsAmount
Number
否
良品数和不良品数不能同时为空
defectList

Object[]
否
不良品项
传不良品项编号和数量
例
[{"code":"m1","amount":2},{"code":"m2","amount":2}]
planStartTime
String
否
报工开始时间，不传默认当前时间 例如"2025-09-01 12:00:00"
planEndTime
String
否
报工结束时间，不传默认当前时间 例如"2025-09-01 12:00:00"
customFieldValueList
Object[]
否
报工自定义字段，传值方式见「自定义字段-传值说明」
outputTime
String
否
报工时间，不传默认当前时间 例如"2025-09-01 12:00:00"
pricingType
Number
否
1 计件 2 计时
不传，默认计件approveStatus
Number
否
审批状态 0：未审批，10： 已审批

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object[]
返回具体信息
msg
String
返回消息

data[]
参数名
类型
描述
outputId
Number
报工id

2.4错误代码
1001
（注：错误代码返回1001，错误提示信息各不相同）

工单编号不能为空

工单编号不存在
任务id不能为空
报工用户id不能为空
报工用户姓名不能为空
报工数不能为空
良品数和不良品数不能为空
报工结束时间不能早于报工开始时间
计价方式输入非法


更新报工
1.功能描述
通过该接口可在小工单更新报工数据。
2.接口定义
2.1请求地址：/api/dytin/external/output/modify
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json

2.2请求参数

参数名
类型
是否必填
描述
outputId
Number
是
报工id
operatorId
Number
否
报工用户id
operatorName
String
否
报工用户姓名
outputAmount
Number
否
报工数
fineAmount
Number
否
良品数
stockUnit
String
否
库存单位
defectsAmount
Number
否
不良品数
defectList

Object[]
否
不良品项
传不良品项编号和数量
例
[{"code":"m1","amount":2},{"code":"m2","amount":2}]
planStartTime
String
否
报工开始时间，格式为：yyyy-MM-dd HH:mm:ss，报工结束时间不能早于报工开始时间 例如"2025-09-01 12:00:00"
planEndTime
String
否
报工结束时间，格式为：yyyy-MM-dd HH:mm:ss，报工结束时间不能早于报工开始时间 例如"2025-09-01 12:00:00"
customFieldValueList
Object[]
否
报工自定义字段，传值方式见「自定义字段-传值说明」

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
object
null
msg
String
返回消息


2.4错误信息

1001
（注：错误代码返回1001，错误提示信息各不相同）

报工id不能为空
报工id不存在
不良品项不存在
不良品项数量不能为空
不良品数、不良品项需同时填写
报工结束时间不能早于报工开始时间


批量更新报工审批状态
1.功能描述
外部系统批量更新报工审批状态
2.接口定义
2.1请求地址：/api/dytin/external/output/approveStatus
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
outputApproveList
List<Object>
必填
{"outputApproveList":[{"approveStatus":10,"outputId":213540,"approveId":12209,"approver":"ceshiyongju"},{"approveStatus":10,"outputId":213539}]}

outputId ：                  报工ID                                             必填
approveStatus：        审批状态  0 未审批 10已审批          否（不填默认为0 未审批）
approveId：                审批人id                                           否（不填取登陆用户id）     
approver ：                 审批人名称                                       否（不填取登陆用户名称）


2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，               
data
Object
列表数据
msg
String
返回消息

data{}
参数名
类型
描述
successCount
Number
成功数量
failCount
Number
失败数量

查询报工
1.功能描述
通过该接口外部系统可查询在小工单产生的报工数据。
2.接口定义
2.1请求地址：/api/dytin/external/output/queryList3
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
projectCode
String
否
工单编号 模糊查询
projectIds
Number[]
否
工单id列表
processCode

String

否

工序编号，校验是否传输该字段，若没有，则接口返回1015，校验所传工序名称在系统内是否存在，若不存在，则接口返回1016
processId
Number
否
工序id
projectProcessId
Number
否
任务id
approveStatus

Number
否
报工审批状态，0: 未审批。10: 已审批。不传不作为限制条件，传其他内容接口会返回错误 1030。
createdAtLte

String
否
小于等于报工创建时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间小于等于"2021-09-30 23:59:59"的报工。
createdAtGte

String
否
大于等于报工创建时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间大于等于"2021-09-30 23:59:59"的报工。
updatedAtGte

String
否
大于等于报工更新时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间大于等于"2021-09-30 23:59:59"的报工。
updatedAtLte
String
否
小于等于报工更新时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间小于等于"2021-09-30 23:59:59"的报工。
starttimeofReportLte
String
否
小于等于报工开始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回开始时间小于等于"2021-09-30 23:59:59"的报工。
starttimeofReportGte
String
否
大于等于报工开始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回报工开始时间大于等于"2021-09-30 23:59:59"的报工。
endtimeofReportLte
String
否
小于等于报工结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回报工结束时间小于等于"2021-09-30 23:59:59"的报工。
endtimeofReportGte
String
否
大于等于报工结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回报工结束时间小于等于"2021-09-30 23:59:59"的报工。
approvedAtLte
String
否
审批时间小于等于 格式为：yyyy-MM-dd HH:mm:ss 不能传""
approvedAtGte
String
否
审批时间大于等于 格式为：yyyy-MM-dd HH:mm:ss  不能传""
page

Object

否

不传 默认每页 10条数据,最大限度每页100条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条
   customFieldSearchConditions
Object
否
自定义字段查询  具体传参说明请看文档描述 「自定义字段传参说明」
 例子：
"customFieldSearchConditions":
[
        {
            "name": "字段名",
            "queryOperator": "equal",
            "fieldValue": [
                "测试"
            ]
        }
    ],

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
data[]
返回具体信息
msg
String
返回消息
page
Object
返回分页信息     "page": {"pageNum": 1, "pageSize": 10, "total": 477,  }
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "process17235164190354": { }}}

data[]
参数名
字段类型
描述
reportingId
String
报工id
projectCode

String

工单编号
processCode

String
工序编号
productCode
String
产品编号
productName
String
产品名称
productSpecification
String
产品规格
workreportQuantity
String
报工数量
pricingMethod
String
计价方式
wageunitPrice
String
工资单价
estimatedSalary
String
预计工资
numberofgoodProducts
String
良品数
numberofDefectives
String
不良品数
productionStaff
String
生产人员
approvalStatus
String
审批状态 0.未审批 10.已审批
starttimeofReport
String
报工开始时间 例如"2025-09-01 12:00:00"
endtimeofReport
String
报工结束时间 例如"2025-09-01 12:00:00"
defectlist
String
不良品项列表
taskId
String
任务ID
createdAt
String
创建日期 例如"2025-09-01 12:00:00"
updatedAt
String
更新日期 例如"2025-09-01 12:00:00"
approvedAt
String
审批时间 例如"2025-09-01 12:00:00"
customFieldValues
Object[]
报工自定义字段
approver
String
审批人名称
approverId
Number
审批人id

customFieldValues  Object[]
参数名
类型
描述
customFieldMetadata
Object[]

自定义字段元数据信息

2.4错误代码
1014
工单编号不存在
1016
工序编号不存在
1017
报工开始时间和报工结束时间超出范围
1018
报工开始时间大于报工结束时间
1012
没有报工开始时间或报工结束时间
01000002
请核对文档后，重新请求！
1011
工单编号和报工时间不能同时为空

报工删除接口
1.功能描述
通过该接口可在小工单系统内删除报工数据，一次删除报工数据不能超过100条数据，如果一个工单下边超过一百条报工数据，传工单信息删除就会被限制，需要传递报工id删除不超过100条
2.接口定义
2.1请求地址：/api/dytin/external/output/delete
通信协议：HTTPS
请求方式：POST
2.2请求参数 
参数名
类型
是否必填
描述
projectCode
String
否
工单编号 与其他参数不能同时 为空
projectId
Number
否
工单ID 与其他参数不能同时 为空
projectProcessId
Number
否
任务ID 与其他参数不能同时 为空
outputIds
List<Number>
否
报工ID列表 与其他参数不能同时 为空

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Number
返回删除数量
msg
String
返回消息



🎯 出入库单
创建出入库单
2.1请求地址：/api/dytin/external/stock/order/add
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
orderNo
String
否
如果没有，则按系统内部规则自动生成单据编号
orderType
Number
是
10 入库单，20 出库单
bizAt
String
否
出入库时间   格式为：yyyy-MM-dd HH:mm:ss，如不传，则默认请求时间
bizType

Number

是

11 普通入库 12 采购入库 13 成品入库 14 调拨入库 15 销售退货入库 16 其他入库 30 生产退料入库 31 委外加工入库
21 普通出库 22 销售出库 23 生产出库 24 调拨出库  25 采购退货出库 26 其他出库 32 生产领料出库 33 委外加工出库
orderStatus
Number
否
0 1  (0表示待审批 1表示已审批)
remark
String
否
备注
warehouseCode
String
否
传仓库编号；表头与明细仓库至少需要维护一个，如果明细没有指定仓库，默认取表头默认仓库
customerCode
String
否
客户编号
vendorCode
String
否
供应商编号
stockOrderCustomFieldsValue

Object[]

否
出入库自定义字段，传值方式见「自定义字段-传值说明」
stockOrderDetails
Object[]
否
出入库明细，结构见下方stockOrderDetail

stockOrderDetail
参数名
字段类型
是否必填
字段解释
seq
Number
是
明细行序号
productCode
Number
是
产品编号
qty
Number
是
出入库数量
costAmount
Number
否
成本金额；costAmount与costUnitPrice填一个即可，都填时，以costAmount为准推算costUnitPrice
costUnitPrice
Number
否
成本单价；无costAmount，才会取这里的costUnitPrice，并推算costAmount
detailWarehouseCode

String
否
明细行仓库（表头与明细仓库至少需要维护一个，如果明细没有指定仓库，默认取表头默认仓库）
associatedBizOrderCode 
String
否
关联的业务单据编号  （采购入库/采购退货出库 支持关联采购订单  销售出库/销售退货入库 支持关联销售订单  成品入库/工单退货入库/ 生产出库 /工单发货出库 /生产领料出库 /生产退料入库 支持关联工单）
stockOrderDetailCustomFieldsValue
Object[]
否
出入库明细自定义字段，传值方式见「自定义字段-传值说明」

完整示例：
    "stockOrderDetails": [
        {
            "seq": 1,
            "productCode": "CP123456",
            "qty": 10,
            "stockOrderDetailCustomFieldsValue": [
                {
                    "name": "出入库明细自定义字段名称",
                    "value": 1212
                }
            ]
        }
    ]


2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回数据
msg
String
返回消息

更新出入库单
2.1请求地址：/api/dytin/external/stock/order/batch/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必参
字段解释
id
Number
否
出/入库单id
与出/入库单编号二选一，不能同时为空与出/入库单编号同时存在传值时，以id为准orderNo

string
否
出/入库单编号
与出/入库单id二选一，不能同时为空与出/入库单id同时存在传值时，以id为准orderType
Integer
是
10 入库单，20 出库单
bizAt
string
是
出入库时间  格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
bizType

Integer
是

11 普通入库 12 采购入库 13 成品入库 14 调拨入库 15 销售退货入库 16 其他入库 
21 普通出库 22 销售出库 23 生产出库 24 调拨出库  25 采购退货出库 26 其他出库
orderStatus
Number
否
0 1  (0表示待审批 1表示已审批)
remark
string
否
备注
warehouseCode
string
否
传仓库编号；表头与明细仓库至少需要维护一个，如果明细没有指定仓库，默认取表头默认仓库
customerCode
string
否
客户编号 非销售出库、销售退货入库，自动清空字段值
vendorCode
string
否
供应商编号 非采购入库、采购退货入库，自动清空字段值
stockOrderCustomFieldsValue
object[]
否
出入库自定义字段
例
[{"name":"名称1","value":"文本-LQ产品01"},{"name":"名2","value":174237},{"name":"名称3","value":"1"},{"name":"名称4","value":"文本-LQ产品01"}]
如果自定义字段类型是关联对象，value传值设定根据关联的不同对象类型，分别为:
用户对象 value 传名称
单位对象 value 传名称
产品对象 value传产品编号
工艺路线  value 传工艺路线名称
工序          value 传工序编号
不良品       value 传不良品编号
部门           value 传部门编号
客户           value 传客户编号
供应商       value 传供应商编号


stockOrderDetails
List<Objec>

是
出入库明细 全删全增
[{"seq":1,"productCode":"产品编号1","
qty":10, "stockOrderDetailCustomFieldsValue":[]}]


              seq                                               产品明细序号  必填
productCode                                             产品编号  必填
                qty                                              出入库数量        必填
detailWarehouseCode                             明细行仓库（表头与明细仓库至少需要维护一个，如果明细没有指定仓库，默认取表头默认仓库）
associatedBizOrderCode                          关联的业务单据编号  （采购入库/采购退货出库 支持关联采购订单  销售出库/销售退货入库 支持关联销售订单  成品入库/工单退货入库/ 生产出库 /工单发货出库 /生产领料出库 /生产退料入库 支持关联工单）
stockOrderDetailCustomFieldsValue     出入库明细自定义字段
costAmount 成本金额 非必填 costAmount与costUnitPrice填一个即可，都填时以costAmount为准
costUnitPrice 成本单价 非必填
例
[{"name":"名称1","value":"文本-LQ产品01"},{"name":"名2","value":174237},{"name":"名称3","value":"1"},{"name":"名称4","value":"文本-LQ产品01"}]
如果自定义字段类型是关联对象，value传值设定根据关联的不同对象类型，分别为:
用户对象 value 传名称
单位对象 value 传名称
产品对象 value传产品编号
工艺路线  value 传工艺路线名称
工序          value 传工序编号
不良品       value 传不良品编号
部门           value 传部门编号
客户           value 传客户编号
供应商       value 传供应商编号

完整示例：
  {
   "orderNo":"231111",
   "orderType":30,
   "bizAt":"2025-07-22 00:00:00",
   "bizType":11,
   "remark": null,
   "warehouseCode":"仓库code",
   "customerCode": null,
   "vendorCode": null,
   "orderStatus": 1,
   "stockOrderCustomFieldsValue":[{
                    "name": "自定义字段名称",
                    "value": 1212
                }], 
   "stockOrderDetails": [
        {
            "seq": 1,
            "productCode": "CP123456",
            "qty": 10,
            "stockOrderDetailCustomFieldsValue": [
                {
                    "name": "明细自定义字段名称",
                    "value": 1212
                }
            ]
        }
    ]
    }

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回数据
msg
string
返回消息

2.4错误信息
01000002
单据编号不存在
仓库编号不能为空
客户编号不能为空

查询出入库单
2.1请求地址：/api/dytin/external/stock/order/queryList3
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
orderNo
String
否
单据编号(模糊查询)
orderType
Number
是
10 入库单，20 出库单
bizTypes

List<Number>

否
11 普通入库 12 采购入库 13 成品入库 14 调拨入库 15 销售退货入库 16 其他入库 
21 普通出库 22 销售出库 23 生产出库 24 调拨出库  25 采购退货出库 26 其他出库
orderStatusList

List<Number>

否

单据状态
已审批 1未审批 0productCode
String
否
产品编号(模糊查询)
remark
String
否
备注
warehouseCode
String
否
仓库编号(精确查找)
warehouseName
String
否
仓库名称(精确查找)
customerCode
String
否
客户编号(精确查找)
vendorCode
String
否
供应商编号(精确查找)
bizAtStart
String
否
出入库时间大于等于   格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
bizAtEnd
String
否
出入库时间小于等于    格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"例如"2025-09-01 12:00:00"
createdAtStart
String
否
创建时间大于等于.       格式为：yyyy-MM-dd HH:mm:ss   例如"2025-09-01 12:00:00"
createdAtEnd
String
否
创建时间小于等于.       格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
updatedAtStart
String
否
更新时间大于等于.       格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
updatedAtEnd
String
否
更新时间小于等于.        格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
appliedAtStart
String
否
申请时间开始 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
appliedAtEnd
String
否
申请时间结束 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
approvedAtStart
String
否
审批时间开始 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
approvedAtEnd
String
否
审批时间结束 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
page

Object
否
不传 默认每页 10条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条


2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回信息
msg
String
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "stockDetails17235164190354": { }}}

data
参数名
字段类型
描述
pageNum
Number
当前页数
pageSize
Number
每页数量
total
Number
总数
data[]
List<Object>

id
Number
出/入库单id
orderType
Number
单据类型  10 入库单     20 出库单
bizAt
String
出入库时间
bizType
Number
11 普通入库 12 采购入库 13 成品入库 14 调拨入库 15 销售退货入库 16 其他入库 
21 普通出库 22 销售出库 23 生产出库 24 调拨出库  25 采购退货出库 26 其他出库
bizTypeDisplay
String
出入库类型 描述
orderNo
String
出入库编号
remark
String
备注
creatorName
String
创建人
createdAt
String
创建时间
updatorName
String
更新人
updatedAt
String
更新时间
lineCount
Number
行数
sumProductQty
Number
单据总业务数量：sum 入库(出库)数量
warehouse
object
{
"id": 1000
 "code": "SL0002", 仓库编码
 "name": "仓库2" 仓库名称
"status": 10 10-启用 20-停用
"remark": 备注
"creatorId": 2900
"creatorName": name
"createdAt": "2025-01-01 00:00:00"
"updatorId": 200
"updatedAt": "2025-01-01 00:00:00"
}
customer

object
{
 "code": "xxxxx"    客户编号
}
vendor
object

{
 "vendorCode": "xxxx",    供应商编号
}
customFieldValues
List<Object>
自定义字段
orderStatus
Number
单据状态，1，0
orderStatusDisplay
String
单据状态展示，已审批（1），待审批（0）
orderDetails
List<Object>
出入库明细
参数名              类型               描述
seq                   Number          序号
productId        Number            产品ID
productCode    String            产品编号
productName    String            产品名称
productSpec    String             产品规格
unitName          String              单位
stockQty            Number          库存数量
qty                     Number    出入库数量
changeQty          Number     异动数量
originType           Number     产品属性 0-自制 1-外购 2-委外    
originTypeDesc      String     产品属性 名称
safetyQty          Number    安全库存
maxQty          Number     最大库存
minQty          Number     最小库存
originDetailId          Number     来源明细id
originDetailDisplayInfo          String     来源明细展示名称
customerId          Number     客户id
customerName          String     客户名称
vendorId          Number     供应商id
vendorName          String     供应商名称
associatedBizOrderId          Number     关联业务单据id
associatedBizOrderType          Number     关联业务单据类型 10-工单 30-采购订单 40-销售订单 50-调拨单
associatedBizOrderCode          String     关联业务单据编号
creatorName     String            创建人
createdAt        String             创建时间
updatorName    String             更新人
updatedAt          String             更新时间
detailWarehouse Object        明细行仓库信息
detailWarehouseId Ingeter    明细行仓库id
changedCostAmountSigned Number 库存变更成本金额，出库单时为负数
costUnitPrice Number 库存变更成本单价
customFieldValues     List<Object>   自定义字段
originCodeList
Object[]
来源单据 当类型为报工时，字段值为空
[{
"id":"1002",
"code":"Sale001"
}]
originIdList
Number[]
来源单据idList
originType
Number
0 默认值 10 报工 20 工单 30 采购订单 40 销售订单
stockProductStatVO
object
出入库产品统计信息
{
"amount":10, //出入库数
"count":10, //产品数
"stockProductVOList":[
{"id":1001, //单据明细id
"orderId":10098, //单据id
"productCode":"产品编号",
"productName":"产品名称",
"productSpec":"产品规格",
"qty":10,//出入库数量
"seq": 1 //产品序号
}] //产品信息
}

删除出入库单
2.1请求地址：/api/dytin/external/stock/order/batch/remove
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
ids
List<Number>
是
ids不能为空
orderType
Number
是
10 入库单，20 出库单

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
boolean
返回是否成功
msg
String
返回消息

查询库存余额明细接口
1.功能描述
外部系统通过该接口获取小工单系统的库存余额明细信息。支持批量查询。
2.接口定义
2.1请求地址：/api/dytin/external/stock/queryStockInfoDetail
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账户须有工单查询权限）
- Content-Type:application/json
示例：
X-AUTH:eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEzMzE0LCJvcmdJZCI6NTc0LCJzc29Ub2tlbiI6InYxdkxjV0tUdVhMVlpkencifQ.lH5cpUfbDW9lI-_J-rz0xPzPRbbK0JgT3Ukpbxd33Y_44abguPZiccCjlqy53pR4cKHP2sJxaxgR1iNWsEXpZA

2.2请求参数
参数名
类型
是否必参
描述
stockInfoQryOpenApiCOList

Object[]

是

库存余额明细查询条件
warehouseId  仓库ID  id和编号二选一 同时传以id为准
productId  产品ID   id和编号二选一 同时传以id为准
warehouseCode 仓库编号   id和编号二选一 同时传以id为准
productCode 产品编号   id和编号二选一 同时传以id为准


示例：
{
    "stockInfoQryOpenApiCOList":[
        {
            "warehouseId": 11,
            "productId":1
        }
    ]
}


2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
data[]
返回具体信息
msg
string
返回消息

data[]
参数名
类型
描述
productId
Number
产品id
productName
String
产品名称
productCode
String
产品编号
warehouseId
Number
仓库id
warehouseName
String
仓库名称
warehouseCode
String
仓库编号
qtyInWarehouse
String
库存数量
unitName
String
单位

2.4错误代码
01000002
单次请求产品-仓库组合数不能超过100
01000002
仓库编号和仓库id不能同时为空
01000002
产品编号和产品id不能同时为空

🎯 调整单
创建调整单
2.1请求地址：/api/dytin/external/stock/order/add
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
orderNo
String
否
调整单号，如果没有，则按系统内部规则自动生成单据编号
orderType
Number
是
单据类型 30
bizAt
String
否
调整时间 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00". 如不传，则默认请求时间
bizType

Number

是

调整单类型
27 期初调整
28 期末调整
29 其他调整
36 成本调整
orderStatus
Number
否
0 1  (0表示待审批 1表示已审批)
remark
String
否
备注
warehouseCode
String
是
传仓库编号；表头与明细仓库至少需要维护一个，如果明细没有指定仓库，默认取表头默认仓库
stockOrderCustomFieldsValue

Object[]

否
调整单自定义字段，传值方式见「自定义字段-传值说明」
stockOrderDetails
Object[]
是
调整单明细，结构见下方stockOrderDetail





stockOrderDetail
参数名
字段类型
是否必填
字段解释
seq
Number
是
明细行序号
productCode
String
是
产品编号
qty
Number
否
库存实际数量；调整单类型为期初调整、期末调整、其他调整时必填
realCostAmount

Number
否
实际成本金额；调整单为成本调整单时生效，realCostAmount与realCostUnitPrice填一个即可，都填时，以realCostAmount为准推算realCostUnitPrice
realCostUnitPrice
Number
否
实际成本单价；调整单为成本调整单时生效，无realCostAmount，才会取这里的realCostUnitPrice，并推算realCostAmount
 detailWarehouseCode 
String
否
 明细行仓库（表头与明细仓库至少需要维护一个，如果明细没有指定仓库，默认取表头默认仓库）

完整示例：
    "stockOrderDetails": [
        {
            "seq": 1,
            "productCode": "CP123456",
            "qty": 10,
            "stockOrderDetailCustomFieldsValue": [
                {
                    "name": "出入库明细自定义字段名称",
                    "value": 1212
                }
            ]
        }
    ]


2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
返回数据
msg
String
返回消息

2.4错误信息
0000001
单据类型不能为空
仓库编号不能为空
调整单类型不能为空
单据类型与调整单类型不符
orderNo超出字段长度限制,支持最多120
remark超出字段长度限制,支持最多1500
调整单明细不能为空
调整单明细seq不能为空
产品编号不能为空
调整单数量不能为空
调整单数量必须大于0
调整单明细seq存在重复
存在非法产品


查询调整单
2.1请求地址：/api/dytin/external/stock/order/queryList3
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
orderNo
String
否
调整单编号(模糊查询)
orderType
Number
是
30 调整单
bizTypes

List<Number>

否

27 期初调整
28 期末调整
29 其他调整
orderStatusList

List<Number>

否

单据状态
已审批 1未审批 0productCode
String
否
产品编号(模糊查询)
remark
String
否
备注
warehouseCode
String
否
仓库编号(精确查找)
warehouseName
String
否
仓库名称(精确查找)
bizAtStart
String
否
调整时间大于等于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
bizAtEnd
String
否
调整时间小于等于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
createdAtStart
String
是
创建时间大于等于. 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
createdAtEnd
String
是
创建时间小于等于. 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
updatedAtStart
String
否
更新时间大于等于. 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
updatedAtEnd
String
否
更新时间小于等于. 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
appliedAtStart
String
否
申请时间大于等于. 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
appliedAtEnd
String
否
申请时间小于等于. 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
approvedAtStart
String
否
审批时间大于等于. 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
approvedAtEnd
String
否
审批时间小于等于. 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-09-01 12:00:00"
page

Object
否
不传 默认每页 10条数据 "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50} 每页 50条


2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
返回数据
msg
String
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "stockDetails17235164190354": { }}}

data
参数名
字段类型
描述
pageNum
Number
当前页数
pageSize
Number
每页数量
total
Number
总数
data[]
List<Object>

id
Number
调整单id
orderType
Number
单据类型 30 调整单
bizAt
String
调整时间
bizType
Number
27 期初调整
28 期末调整
29 其他调整
bizTypeDisplay
String
调整单类型 描述
orderNo
String
调整单编号
remark
String
备注
creatorName
String
创建人
createdAt
String
创建时间
updatorName
String
更新人
updatedAt
String
更新时间
warehouse
object
{
"id": 1000
 "code": "SL0002", 仓库编码
 "name": "仓库2" 仓库名称
"status": 10 10-启用 20-停用
"remark": 备注
"creatorId": 2900
"creatorName": name
"createdAt": "2025-01-01 00:00:00"
"updatorId": 200
"updatedAt": "2025-01-01 00:00:00"
}
customFieldValues
List<Object>
自定义字段
orderStatus
Number
单据状态，枚举值：1，0
orderStatusDisplay
String
单据状态展示，已审批（1），待审批（0）
orderDetails
List<Object>
调整单明细
参数名              类型               描述
seq                   Number          序号
productId        Number            产品ID
productCode    String            产品编号
productName    String            产品名称
productSpec    String             产品规格
unitName          String              单位
stockQty            Number          库存数量
qty                     Number    调整单数量
changeQty          Number     异动数量
originType           Number     产品属性 0-自制 1-外购 2-委外    
originTypeDesc      String     产品属性 名称
safetyQty          Number    安全库存
maxQty          Number     最大库存
minQty          Number     最小库存
originDetailId          Number     来源明细id
originDetailDisplayInfo          String     来源明细展示名称
customerId          Number     客户id
customerName          String     客户名称
vendorId          Number     供应商id
vendorName          String     供应商名称
associatedBizOrderId          Number     关联业务单据id
associatedBizOrderType          Number     关联业务单据类型 10-工单 30-采购订单 40-销售订单 50-调拨单
associatedBizOrderCode          String     关联业务单据编号
creatorName     String            创建人
createdAt        String             创建时间
updatorName    String             更新人
updatedAt          String             更新时间
detailWarehouse Object        明细行仓库信息
detailWarehouseId Ingeter    明细行仓库id
realCostAmount Number 实际成本金额
realCostUnitPrice Number 实际成本单价
customFieldValues     List<Object>   自定义字段

2.4错误信息


0000001
创建开始时间不能为空
创建结束时间不能为空
创建时间查询范围不能超过180天
单据类型不能为空


删除调整单
2.1请求地址：/api/dytin/external/stock/order/batch/remove
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
ids
List<Number>
是
ids不能为空
orderType
Number
是
30 调整单

2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
boolean
返回是否成功
msg
String
返回消息

2.4错误信息
0000001
ids不能为空
ids最多1000条
单据类型不能为空

更新调整单
2.1请求地址：/api/dytin/external/stock/order/batch/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必参
字段解释
id
Number
否
调整单id
与调整单编号二选一，不能同时为空与调整单编号同时存在传值时，以id为准orderNo
string

否
调整单编号
与调整单id二选一，不能同时为空与调整单id同时存在传值时，以id为准orderType
Integer
是
单据类型 30
bizAt
string
是
调整时间   格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
bizType

Integer
是

调整单类型
27 期初调整
28 期末调整
orderStatus
Number
否
0 1  (0表示待审批 1表示已审批)
remark
string
否
备注
stockOrderCustomFieldsValue

Object[]

否
调整单自定义字段，传值方式见「自定义字段-传值说明」
warehouseCode
String
是
传仓库编号；表头与明细仓库至少需要维护一个，如果明细没有指定仓库，默认取表头默认仓库
stockOrderDetails
Object[]
是
调整单明细，结构见下方stockOrderDetail


参数名
字段类型
是否必填
字段解释
seq
Number
是
明细行序号
productCode
String
是
产品编号
qty
Number
是
库存实际数量
stockOrderDetailCustomFieldsValue
Object[]
否
调整明细自定义字段，传值方式见「自定义字段-传值说明」
detailWarehouseCode
String
否
明细行仓库（表头与明细仓库至少需要维护一个，如果明细没有指定仓库，默认取表头默认仓库）

完整示例：
  {
   "id": 10,
   "orderNo":"231111",
   "orderType":30,
   "bizAt":"2025-07-22 00:00:00",
   "bizType":27,
   "remark": null,
   "warehouseCode":"仓库code",
   "stockOrderCustomFieldsValue":[{
                    "name": "自定义字段名称",
                    "value": 1212
                }], 
   "stockOrderDetails": [
        {
            "seq": 1,
            "productCode": "CP123456",
            "qty": 10,
            "stockOrderDetailCustomFieldsValue": [
                {
                    "name": "明细自定义字段名称",
                    "value": 1212
                }
            ]
        }
    ]
    }

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回数据
msg
string
返回消息

2.4错误信息
01000002
单据编号不存在
仓库编号不能为空
客户编号不能为空

🎯  销售管理 / 销售订单(旗舰版)
创建销售订单
1.功能描述
可通过此接口在小工单中创建销售订单。
2.接口定义
2.1请求地址：/api/dytin/external/saleOrder/create
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
orderNo
String
否

销售订单编号
未填自动生成customerCode
String
是
客户编号
orderTime
String
是
下单日期  格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
arrivalPlanTime
String
是
计划交付日期 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
operatorName
String
是
业务人员名称
contractNo
String
否
合同号
contactName
String
否
联系人
contactNumbers
String
否
联系电话
contactAddress
String
否
联系地址
remark
String
否

status
Number

否
订单状态：
0 未审批：010 已审批（执行中）未传值时，默认未审批非法传值（非0或10），默认未审批approverName

String
否
审批人
字段「审批状态」存在值，且值为「10」时必填存在同名审批人时，任意取值审批人不存在/已停用/虚拟用户时，返回对应的错误信息saleOrderCustomFieldsValue
Object[]
否

销售订单自定义字段值，传值方式见「自定义字段-传值说明」

saleOrderDetailApiDTOList
Object[]
是
销售订单明细，结构见下方saleOrderDetailApiDTOList






saleOrderDetailApiDTOList
参数名
字段类型
是否必填
字段解释
seq
Number
是
明细行序号
productCode
String
是
产品编号
qty
Number
是
数量
unitPrice
Number
是
单价
arrivalPlanTime
String
是
产品交货日期 例如“2025-01-01 00:00:00”
remark
String
否
备注
saleOrderDetailCustomFieldsValue
Object[]
否
销售明细自定义字段，传值参考：自定义字段同步
amount
Number
否
报价金额
discount
Number
否
折扣%
：该值是去掉 % 后的，例如：90% 传值 0.9 ，校验整数位 2 小数位 4，
taxRate
Number
否
税率%
：该值是去掉 % 后的，例如：90% 传值 0.9 ，校验整数位 2 小数位 4，


2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息

data
参数名
类型
描述
orderNo
String
销售订单编号
saleOrdrId
Number
销售订单id


更新销售订单
1.功能描述
可通过此接口在小工单中编辑销售订单字段（不包含明细）。
2.接口定义
2.1请求地址：/api/dytin/external/saleOrder/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id

Number
否
销售订单id 
与订单编号二选一，不能同时为空与订单编号同时存在传值时，以ID为准orderNo
String
否
订单编号
customerCode
String
是
客户编号
orderTime
String
是
下单日期
arrivalPlanTime
String
是
计划交付日期
operatorName
String
是
业务人员名称
contractNo
String
否
合同号
contactName
String
否
联系人
contactNumbers
String
否
联系电话
contactAddress
String
否
联系地址
remark
String
否
备注
saleOrderCustomFieldsValue
Object[]

否
销售订单自定义字段值，传值参考： 自定义字段同步

2.3 返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息



查询销售订单
1.功能描述
可通过此接口查询小工单中的销售订单。
2.接口定义
2.1请求地址：/api/dytin/external/saleOrder/queryList2
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
page

Object

否

不传 默认每页 10条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条
orderNo
String
否
订单编号
orderTimeGte
String
否
下单日期大于等于
orderTimeLte
String
否
下单日期小于等于
arrivalPlanTimeGte
String
否
计划交货日期大于等于
arrivalPlanTimeLte
String
否
计划交货日期小于等于
approvedTimeGte
String
否
审批日期大于等于
approvedTimeLte
String
否
审批日期小于等于
endTimeGte
String
否
结束日期大于等于
endTimeLte
String
否
结束日期小于等于
createdAtGte
String
否
创建时间大于等于
createdAtLte
String
否
创建时间小于等于
updatedAtGte
String
否
更新时间大于等于
updatedAtLte
String
否
更新时间小于等于
contractNo
String
否
模糊匹配
status

Number[]
否
支持多选值范围为状态代码：未审批：0执行中：10已结束：20已取消：30customerCodes

String[]
否
客户编号
需要根据客户查询时与客户id二选一，不允许同时传值customerIds
Number[]
否
客户id
需要根据客户查询时与客户id二选一，不允许同时传值

2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息
page
Object
返回分页信息     "page": {"pageNum": 1, "pageSize": 10, "total": 477,  }

data
参数名
类型
描述
orderNo
String
销售订单编号
id
Number
销售订单id
status
Number

订单状态
未审批：0执行中：10已结束：20已取消：30customerCode
String
客户编号
orderTime
String
下单日期
arrivalPlanTime
String
计划交货日期
totalItem
Number
订单项数
totalAmount
Number
订单金额
totalQty
Number
订单数量
operatorName
String
业务员名称
approvedTime
String
审批日期
approverName
String
审批人名称
endTime
String
结束日期
contractNo
String
合同号
contactName
String
联系人
contactNumbers
String
联系电话
contactAddress
String
联系地址
remark
String
订单备注
shipmentQty
Number
发货数量
returnQty
Number
退货数量
creatorName
String
创建人名称
createdAt
String
创建时间
updatorName
String
更新人名称
updatedAt
String
更新时间
customFieldValues
Object[]
销售订单自定义字段
SaleProductStatVO
Object
销售明细对象
saleManageOrderDetailRowApiVOList
Object[]
销售明细集合，结构见下方

id
Number
销售明细ID
seq
Number
销售明细序号
productCode
String
销售明细产品编号
productName
String
销售明细产品名称
productSpec
String
销售明细产品规格
productOriginType
Number
销售明细产品属性
productStockQty
Number
销售明细库存数量
productUnitName
String
销售明细产品单位
productShipmentQty
Number
销售明细发货数量
productReturnQty
Number
销售明细退货数量
productUnClearQty
Number
销售明细未清数量
totalPlanAmount
Number
销售明细已排产数量
totalRealAmount
Number
销售明细产出数量
pendingAmount
Number
销售明细待排产数量
qty
Number
销售明细数量
unitPrice
Number
销售明细单价
amount
Number
销售明细金额
arrivalPlanTime
String
销售明细产品交货日期
remark
String
销售明细产品备注
customFieldValues
Object[]
销售明细销售明细自定义字段



创建销售明细
1.功能描述
通过此接口在小工单中针对指定销售订单新增明细行。新增明细行将被添加至当前明细行的最后一行。
2.接口定义
2.1请求地址：/api/dytin/external/saleOrder/create/detail
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
saleOrderNo
String
否
订单编号
saleOrderId

Number
否
销售订单id
与订单编号二选一，不能同时为空与订单编号同时存在传值时，以ID为准与productCode
String
是
产品编号
qty
Number
是
数量
unitPrice
Number
是
单价
arrivalPlanTime
String
是
产品交货日期
remark
String
否
产品备注
saleOrderDetailCustomFieldsValue
Object[]
否
销售明细自定义字段值，销售订单自定义字段值，传值参考：自定义字段同步

2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息

data
参数名
类型
描述
orderNo
String
销售订单编号
saleOrderDetailId
Number
销售订单明细id


更新销售明细
1.功能描述
可通过此接口在小工单中更新指定销售明细。 
2.接口定义
2.1请求地址：/api/dytin/external/saleOrder/update/detail
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
saleOrderNo
String
否
订单编号 当销售明细id为空时 必填
id
Number
否
销售明细id
与订单编号+seq二选一，不能同时为空seq
Number
否
当销售明细id为空时 必填
productCode
String
是
产品编号
qty
Number
是
数量
unitPrice
Number
是
单价
arrivalPlanTime
String
是
产品交货日期
remark
String
否
产品备注
saleOrderDetailCustomFieldsValue
Object[]
否
销售明细自定义字段值，传值参考：自定义字段同步


2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息

data
参数名
类型
描述
orderNo
String
销售订单编号
saleOrderDetailId
Number
销售订单明细id


删除销售明细
1.功能描述
可通过此接口在小工单中删除销售明细。支持通过以下2种方式定位到待删除的明细行，具体如下：
1. 指定「销售明细ID」，删除指定id的销售明细
2. 指定「销售订单（订单id或编号）+ 序号」，删除指定序号的销售明细
2.接口定义
2.1请求地址：/api/dytin/external/saleOrder/delete/detail
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
saleOrderId
Number
否
销售订单ID
saleOrderNo
String
否
销售订单编号
seq
Number
否
序号
id
Number
否
销售明细ID

2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息

参数名
类型
描述
orderNo
String
销售订单编号
saleOrdrId
Number
销售订单id


更新订单状态
1.功能描述
接口用于更新销售订单状态。
2.接口定义
2.1请求地址：/api/dytin/external/saleOrder/update/status
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
saleOrderId
Number
否
销售订单ID与销售订单不能同时为空
saleOrderNo
String
否
销售订单编号与销售订单id不能同时为空
status
Number

是
值范围为状态代码：
0-新建，10-执行中，20-已结束，30-已取消不受状态流转限制（允许任意修改状态）校验：非法传值时，返回错误提示approverName

String
否
审批人名称
字段「状态」的值为「10」时，字段必填存在同名审批人时，允许任意取值校验：审批人不存在/已停用/虚拟用户时，返回对应错误信息校验：未传值时，返回对应错误信息字段「状态」的值为「0、20、30」时，忽略此字段传值
2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息

删除销售订单
1.功能描述
可通过此接口在小工单中删除指定销售订单。删除校验同web端，即存在关联工单、出入库单时，单据无法被删除。
2.接口定义
2.1请求地址：/api/dytin/external/saleOrder/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
saleOrderId
Number
否
销售订单id
与编号二选一，不能同时为空与编号同时存在传值时，以id为准saleOrderNo
String
否
销售订单编号
与编号二选一，不能同时为空与编号同时存在传值时，以id为准
2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息



🎯   采购管理 / 采购订单(旗舰版)
创建采购订单
1. 功能描述
外部系统可通过此接口在小工单中创建采购订单。
2. 接口定义
2.1 请求地址
- URL: /api/dytin/external/purchaseOrder/create
- 通信协议: HTTPS
- 请求方式: POST
- 请求header参数: 
  - X-AUTH: 值为登录接口获取的token
  - Content-Type: application/json
2.2请求参数
参数名
类型
是否必填
描述
orderCode
String
否
未填时根据编号规则自动生成
vendorCode
String
是
供应商编号，供应商编号不存在时，返回错误信息
purchaseTime
String
是
采购日期，格式：yyyy-MM-dd hh:mm:ss
planArrivalTime
String
是
计划到货日期，格式：yyyy-MM-dd hh:mm:ss
purchaserName
String
是
采购员名称
contactName
String
否
联系人
contactNumber
String
否
联系电话
contactAddress
String
否
联系地址
remark
String
否
订单备注
status

Number

否
订单状态：
0 未审批1 已审批（执行中）未传值时，默认未审批非法传值（非0或1），默认未审批approverName

String
否
审批人。
字段「审批状态」存在值，且值为「1」时必填存在同名审批人时，任意取值审批人不存在/已停用/虚拟用户时，返回错误信息orderDiscount

Number
否
整单折扣
未传值时，填充默认值：100。传值时，合法性校验同web端。不需要联动明细行（明细行需要通过接口同步）purchaseOrderCustomFieldsValue

Object[]

否

采购订单自定义字段，传值方式见「自定义字段-传值说明」

purchaseSubOrderApiDTOList
Object[]
是
采购明细，结构见下方
明细行数量上限同web端
purchaseSubOrderApiDTOList 采购明细
参数名
类型
是否必填
描述
seq
Number
是
明细行序号
productCode
String
是
产品编号
产品不存在时，返回错误信息purchaseNum
Number
是
数量，合法性校验同web端
purchasePrice
Number
是
单价，合法性校验同web端
productArrivalTime
String
是
产品到货日期，格式：yyyy-MM-dd hh:mm:ss
remark
String
否
产品备注
discount
Number
否
折扣。
未传值时，填充默认值：采购订单-整单折扣传值时，该值是去掉 % 后的，例如：90% 传值 0.9 ，校验整数位 2 小数位 4，且不需要与订单-整单折扣联动taxRate
Number
否
税率。
未传值时，填充默认值：0该值是去掉 % 后的，例如：90% 传值 0.9 ，校验整数位 2 小数位 4，purchaseSubOrderCustomFieldsValue
Object[]
否
采购订单自定义字段，传值方式见「自定义字段-传值说明」

2.3 返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息

data
参数名
类型
描述
orderCode
String
采购订单编号
purchaseOrderId
Number
采购订单ID




更新采购订单
1.功能描述
可通过此接口在小工单中编辑采购订单字段（不包含明细）。
2.接口定义
2.1请求地址：/api/dytin/external/purchaseOrder/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
订单ID
ID与订单编号二选一，不能同时为空与订单编号同时存在传值时，将以ID为准orderCode
String
否
订单编号
与订单ID二选一，不能同时为空与订单ID同时存在传值时，将以ID为准vendorCode
String
是
供应商编号
供应商编号不存在时，返回对应错误信息purchaseTime
String
是
采购日期，格式：yyyy-MM-dd hh:mm:ss
planArrivalTime
String
是
计划到货日期，格式：yyyy-MM-dd hh:mm:ss
purchaserName
String
否
采购员名称
存在同名业务员时，将任意取值业务员不存在/已停用/虚拟用户时，返回错误信息contactName
String
否
联系人
contactNumber
String
否
联系电话
contactAddress
String
否
联系地址
remark
String
否
订单备注
purchaseOrderCustomFieldsValue
Object[]
否
采购订单自定义字段，传值方式见「自定义字段-传值说明」
orderDiscount
Number
否
整单折扣
未传值时，填充默认值：100传值时，合法性校验同web端；不联动修改明细行，明细行需要通过接口单独更新
2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息


查询采购订单
1.功能描述
可通过此接口查询小工单中的采购订单。
2.接口定义
2.1请求地址：/api/dytin/external/purchaseOrder/queryList2
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
page
Object
否
分页/排序参数。
未传时根据默认分页/排序逻辑返回列表结果orderCode
String
否
订单编号
purchaseTimeGte
String
否
采购日期大于等于，格式：yyyy-MM-dd hh:mm:ss
purchaseTimeLte
String
否
采购日期小于等于，格式：yyyy-MM-dd hh:mm:ss
planArrivalTimeGte
String
否
计划到货日期大于等于，格式：yyyy-MM-dd hh:mm:ss
planArrivalTimeLte
String
否
计划到货日期小于等于，格式：yyyy-MM-dd hh:mm:ss
approveTimeGte
String
否
审批日期大于等于，格式：yyyy-MM-dd hh:mm:ss
approveTimeLte
String
否
审批日期小于等于，格式：yyyy-MM-dd hh:mm:ss
finishTimeGte
String
否
结束日期大于等于，格式：yyyy-MM-dd hh:mm:ss
finishTimeLte
String
否
结束日期小于等于，格式：yyyy-MM-dd hh:mm:ss
createdAtGte
String
否
创建时间大于等于，格式：yyyy-MM-dd hh:mm:ss
createdAtLte
String
否
创建时间小于等于，格式：yyyy-MM-dd hh:mm:ss
updatedAtGte
String
否
更新时间大于等于，格式：yyyy-MM-dd hh:mm:ss
updatedAtLte
String
否
更新时间小于等于，格式：yyyy-MM-dd hh:mm:ss
status
Number[]
否
订单状态
支持多选订单状态：0 未审批1 执行中2 已结束3 已取消vendorCodes
String[]
否
供应商编号列表
vendorIds
Number[]
否
供应商id列表


2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息
page
Object
返回分页信息     "page": {"pageNum": 1, "pageSize": 10, "total": 477,  }
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "purchaseDetails17235164190354": { }}}


data
参数名
类型
描述
orderCode
String
采购订单编号
id
Number
订单ID
orderStatus
Number
订单状态，返回状态代码：未审批：0；执行中：1；已结束：2；已取消：3
orderStatusDesc
String
订单状态名称
vendorCode
String
供应商编号
purchaseTime
String
采购日期
planArrivalTime
String
计划到货日期
subOrderNum
Number
订单项数
subOrderAmountTotal
Number
订单金额
subOrderNumTotal
Number
订单数量
purchaserName
String
采购员名称
approveTime
String
审批日期
approverName
String
审批人名称
finishTime
String
结束日期
contactName
String
联系人
contactNumber
String
联系电话
contactAddress
String
联系地址
remark
String
订单备注
receivedNum
Number
收货数量
returnNum
Number
退货数量
creatorName
String
创建人名称
createdAt
String
创建时间
updaterName
String
更新人名称
updatedAt
String
更新时间
customFields
Object[]
采购订单自定义字段
orderDiscount
Number
整单折扣
subOrderAmountTotal
Number
订单报价金额
totalTaxExclusiveAmount
Number
订单不含税金额
totalTaxInclusiveAmount
Number
订单含税金额
purchaseDetails
Object[]
采购明细集合，结构见下方
id
Number
采购明细ID
seq
Number
序号
productCode
String
产品编号
productName
String
产品名称
specification
String
产品规格
productOriginType
String
产品属性
stockQty
Number
库存数量
receiveNum
Number
收货数量
returnNum
Number
退货数量
unClearNum
Number
未清数量
purchaseNum
Number
采购数量
unitName
String
单位
purchasePrice
Number
单价
amount
Number
金额（报价金额）
purchaseAmount
String
产品到货日期
remark
String
备注
customFields
Object[]
采购明细自定义字段
discount
Number
折扣
：该值是去掉 % 后的，例如：90% 传值 0.9
taxRate
Number
税率
：该值是去掉 % 后的，例如：90% 传值 0.9 
discountAmount
Number
折扣额
taxExclusiveAmount
Number
不含税金额
taxInclusiveUnitPrice
Number
含税单价
taxAmount
Number
税额
taxInclusiveAmount
Number
含税金额





创建采购明细
1.功能描述
通过此接口在小工单中针对指定采购订单新增明细行。
新增明细行将被添加至当前明细行的最后一行。
2.接口定义
2.1请求地址：/api/dytin/external/purchaseOrder/create/detail
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名

类型
是否必填
描述
purchaseOrderNo
String
否
采购订单编号
与purchaseOrderId二选一，不能同时为空与purchaseOrderId同时存在传值时，以purchaseOrderId为准purchaseOrderId
Number

否
采购订单id
与purchaseOrderNo二选一，不能同时为空与purchaseOrderNo同时存在传值时，以purchaseOrderId为准productCode
String
是
产品编号，产品不存在时，需要返回错误信息
purchaseNum
Number
是

采购数量
合法性校验同web端purchasePrice
Number
是
单价
合法性校验同web端productArrivalTime
String
是
产品到货日期，格式：yyyy-MM-dd hh:mm:ss
remark
String
否
产品备注
discount
Number
否
折扣
未传值时，填充默认值：采购订单-整单折扣传值时，该值是去掉 % 后的，例如：90% 传值 0.9 ，校验整数位 2 小数位 4，不与订单-整单折扣联动taxRate
Number
否
税率
未传值时，填充默认值：0传值时，该值是去掉 % 后的，例如：90% 传值 0.9 ，校验整数位 2 小数位 4，purchaseSubOrderCustomFieldsValue
Object[]
否
采购明细自定义字段，传值方式见「自定义字段-传值说明」

2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息

data
参数名
类型
描述
purchaseOrderNo
String
采购订单编号
purchaseDetailId
Number
采购明细id



更新采购明细
1.功能描述
可通过此接口在小工单中更新指定采购明细。支持通过以下2种方式定位到指定采购明细后进行更新：
1. 采购明细ID。可以通过明细ID 唯一定位到待更新采购明细
2. 采购订单编号 + 序号。可以通过序号唯一定位到待更新采购明细
❗️此接口为覆盖更新逻辑，未传值的系统字段、自定义字段内容将会被置空

2.接口定义
2.1请求地址：/api/dytin/external/purchaseOrder/update/detail
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
采购明细ID
存在传值时，以ID为准，忽略其他组合传值指定采购明细id不存在时，返回错误提示purchaseOrderNo
String
否
采购订单编号
「采购明细ID」未传值时必填需要修改采购订单编号时「采购明细ID」必填指定采购订单编号不存在时，返回错误提示seq
Number
否
序号
「采购明细ID」未传值时必填指定采购明细序号不存在时，返回错误提示productCode
String
是
产品编号，产品不存在时，需要返回错误信息
purchaseNum
Number
是
数量，合法性校验同web端
purchasePrice
Number
是
单价，合法性校验同web端
productArrivalTime
String
是
产品到货日期，格式：yyyy-MM-dd hh:mm:ss
remark
String
否
产品备注
purchaseSubOrderCustomFieldsValue
Object[]
否
采购明细自定义字段，传值方式见「自定义字段-传值说明」
discount
Number
否

折扣
未传值时，填充默认值：采购订单-整单折扣；传值时，该值是去掉 % 后的，例如：90% 传值 0.9 ，校验整数位 2 小数位 4，且不需要与订单-整单折扣联动taxRate
Number
否
税率
未传值时，填充默认值：0传值时，该值是去掉 % 后的，例如：90% 传值 0.9 ，校验整数位 2 小数位 4，

2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息

data
参数名
类型
描述
purchaseOrderNo
String
采购订单编号
purchaseDetailId
Number
采购明细ID



删除采购明细
1.功能描述
可通过此接口在小工单中删除销售明细。支持通过以下2种方式定位到待删除的明细行，具体如下： 
1. 采购明细ID，通过明细ID 唯一定位到待删除采购明细
2. 采购订单（编号 or id）+ 序号，通过序号唯一定位到待删除采购明细

2.接口定义
2.1请求地址：/api/dytin/external/purchaseOrder/delete/detail
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
purchaseOrderId
Number
否
采购订单ID
purchaseOrderNo
String
否
采购订单编号
seq
Number
否
序号
id
Number
否
采购明细ID

2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息

参数名
类型
描述
purchaseOrderNo
String
采购订单编号
purchaseDetailId
Number
采购明细ID



更新采购订单状态
1.功能描述
接口用于更新采购订单状态。
2.接口定义
2.1请求地址：/api/dytin/external/purchaseOrder/update/status
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
采购订单ID
采购订单ID和编号二选一必填；同时存在时，以ID为准orderCode
String
否
采购订单编号
采购订单ID和编号二选一必填；同时存在时，以ID为准status
Number
是
订单状态
0-待审批，1-执行中，2-已结束，3-已取消不受状态流转限制（允许任意修改状态）非法传值时，返回错误提示approverName
String
否
审批人名称

2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息


删除采购订单
1.功能描述
可通过此接口在小工单中删除指定采购订单。
删除校验：存在关联工单、出入库单时，单据无法被删除。

2.接口定义
2.1请求地址：/api/dytin/external/purchaseOrder/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
purchaseOrderNo
String
否
采购订单编号，与purchaseOrderId二选一，不能同时为空；与purchaseOrderId同时存在传值时，以purchaseOrderId为准
purchaseOrderId
Number
否
采购订单id，与purchaseOrderNo二选一，不能同时为空；与purchaseOrderNo同时存在传值时，以purchaseOrderId为准

2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
数据
msg
String
返回消息



🎯 用户
创建用户
1.功能描述
外部系统可通过此接口创建用户
2.接口定义
2.1请求地址：/api/dytin/external/user/add
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账户须有任务编辑权限）
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
username
String
是
账号
name
String
是
姓名
phone
String
否
手机号
fake
boolean
是
flase 非虚拟用户  true 虚拟用户
roleIds
List
否

非虚拟用户角色为空时，默认为  轻量-管理员 
虚拟用户角色不支持填写，后台默认处理为空
角色id  多个id逗号分割. [id1,id2]，
生产环境枚举：
11092  轻量-管理员 
11091  轻量-生产人员
12        用户账号管理员 
如果是用的测试环境账号调用接口 请使用以下枚举：
测试环境枚举：
1050  轻量-管理员 
1053  轻量-生产人员

groupNames
List
否
部门名称
customFieldValueList

Object[]
否

用户自定义字段，传值方式见「自定义字段-传值说明」

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败
data
Object
返回数据
msg
String
返回消息

data
参数名
字段类型
描述
id
Number
用户id
password
String
用户密码
username
String
账号
name
String
姓名
phone
String
手机号
roles

List<Object>
[{ "id": 11092, "name": "轻量-管理员"},
{ "id": 11091, "name": "轻量-生产人员"},
{ "id": 12, "name": "用户账号管理员"}]
groupNames
List<String>
["部门1","部门2"]
fake
boolean
true 虚拟用户 false 普通用户
customFieldValues
Object[]
用户自定义字段


用户列表查询
1.功能描述
外部系统可通过此接口能查询到小工单的用户信息
2.接口定义
2.1请求地址：/api/dytin/external/user/queryList2
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。（注：调用登录接口的账户须有管理员权限）
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
usernameList
List<String>
否

账号多个查询  例如 ["账号1","账号2"]，此参数与其他参数互斥，输入账号查询，其他参数无效
likeName
String
否
用户姓名，支持模糊查询
active
Boolean
否
true 启用  false 禁用，不传 则查询全部
roleIds
String
否
角色id，多个id逗号分割
page

Object
否

分页参数对象。不传则默认每页 10条数据。具体样式例如： {"pageNum":1,"pageSize":50} 
表示每页 50条，查询第1页结果

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
String
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "user17235164190354": { }}}

data
参数名
字段类型
描述
pageNum
Number
当前页数
pageSize
Number
每页数量
total
Number
总数
data
List<Object>


id
Number
用户id

username
String
账号

name
String
姓名

phone
String
手机号

roles

List<Object>
[{ "id": 11092, "name": "轻量-管理员"},
{ "id": 11091, "name": "轻量-生产人员"},
{ "id": 12, "name": "用户账号管理员"}
]

groupNames
List<String>
["部门1","部门2"]

fake
boolean
true 虚拟用户 false 普通用户

customFieldValues
List<Object>
用户自定义字段



🎯  自定义字段
查询自定义字段metadata定义
1.功能描述
本接口用于查询指定组织下所有自定义字段
2.接口定义
2.1请求地址：/api/dytin/external/customField/queryCustomFieldMetadata
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
优先使用fieldIds，如空则使用targetType；
如果两个参数均未填，则查询组织下所有自定义字段
参数名
类型
是否必填
描述
fieldIds

Number[]

否

自定义字段id数组
根据指定“自定义字段id数组”，查询组织下的自定义字段meatadata，
最多限制80个。
targetType

Number
否

所属表单类型
根据指定的“所属表单类型”，查询组织下的自定义字段meatadata
PRODUCT(1), 产品PROCESS(2), 工序PROJECT(3), 工单OUTPUT(4),   报工USER(5),STOCK_DETAILS(7), 出入库收发明细STOCK(8), 出入库单MATERIALS(10), 物料清单GROUP(12), 部门CUSTOMER(13), 客户VENDOR(15), 供应商PURCHASE(16), 采购订单PURCHASE_DETAIL(17), 采购订单明细SALES(18), 销售管理-销售订单SALES_DETAIL(19), 销售管理-销售订单明细
WAREHOUSE(20), 仓库OWNER_PROJECT(23), 跨厂工单SO(101),   自定义表单-销售管理POS(102), 自定义表单-生产计划APO(103) 自定义表单-装配计划page

Object

否
不传 默认每页 10条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条

2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
customFieldMetadata 数组
msg
String
返回消息
page
Object
返回分页信息 "page": {"pageNum": 1, "pageSize": 10, "total": 80, }

data（customFieldMetadata数组）
参数名
类型
是否必填
参数解释
fieldId
Number
是
字段id
blSearch
Boolean
是
是否筛选条件
name
String
是
名称
targetType

Number
是

所属表单类型
PRODUCT(1), 产品PROCESS(2), 工序PROJECT(3), 工单OUTPUT(4),   报工USER(5),STOCK_DETAILS(7), 出入库收发明细STOCK(8), 出入库单MATERIALS(10), 物料清单GROUP(12), 部门CUSTOMER(13), 客户VENDOR(15), 供应商PURCHASE(16), 采购订单PURCHASE_DETAIL(17), 采购订单明细SALES(18), 销售管理-销售订单SALES_DETAIL(19), 销售管理-销售订单明细
WAREHOUSE(20), 仓库SO(101),   自定义表单-销售管理POS(102), 自定义表单-生产计划APO(103) 自定义表单-装配计划sort
Number
是
排序
type

Number
是
控件类型

TEXT(1, "文本"),NUMERIC(2, "数字"),TIME(3, "时间"),ATTACHMENT(4, "附件"),SINGLE_BOX(5, "单选框"),CHECK_BOX(6, "复选框"),FORMULA(7, "公式"),ASSOCIATED_OBJECT(8, "关联对象"),ASSOCIATED_ATTRIBUTES(9, "关联属性"),CHILD_TABLE(10, "子表"),PICTURE(11, "图片"),HYPER_LINK(12, "超链接"),FORMULA_TIME(13, "公式(时间)")businessObjectType
String
否
关联业务对象 - 业务对象类型

查询关联对象业务实体信息
1.功能描述
本接口用于查询指定组织下的关联对象业务实体信息
2.接口定义
2.1请求地址：/api/dytin/external/customField/associatedBusinessObject/entity/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
ids
List<Number>

是

限制80个。
实体ids (与type类型对应实体对象的主键ID
比如 type类型是产品，
对应的就是产品的主键id，
对象的主键id从 查询/列表接口中返回的对象类型自定义字段的value值里获取
)
type

String
是

业务对象类型
和 customFieldMetadata的 businessObjectType对应


2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
Object 数组
msg
String
返回消息

data（associatedObjectValueDetail数组）
参数名
类型
是否必填
参数解释
实体字段


会这样的类似结果






🎯  跨厂工单
查询跨厂工单
1.功能描述
可通过此接口查询小工单中的跨厂工单。
基于链主系统中当前全量跨厂工单数据。
2.接口定义
2.1请求地址：/api/dytin/external/owner/project/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
page
object
否
分页参数：
不传 默认每页 10条数据 
"page":{"pageNum":1,"pageSize":10}
orders
object
否
排序参数：
不传 默认以工单创建时间降序
"orders": [ { "orderBy": "createdAt", "sort": "DESC" } ]
fieldQueryValues
Object[]
是
筛选条件：需要包含至少一个筛选条件
示例：
{
    "fieldQueryValues": [
        {
            "fieldName": "productCode",  
            "queryOperator": "equal", 
            "fieldValue": [
                "阀门"
            ]        // 筛选条件-系统字段-产品编号等于"阀门"
        },
        {
            "fieldName": "ownerProject1788888888817",
            "queryOperator": "lessEqual",
            "fieldValue": [
                2222
            ]        // 筛选条件-自定义字段-自定义字段小于等于2222
        }
    ]
}

筛选条件-系统字段
suppCode
String
否
供应商代码，见「3.筛选条件传参示例-文本」
suppNickName
String
否
供应商名称，见「3.筛选条件传参示例-文本」
projectCode
String
否
工单编号，见「3.筛选条件传参示例-文本」
productCode
String
否
产品编号，见「3.筛选条件传参示例-文本」
productName
String
否
产品名称，见「3.筛选条件传参示例-文本」
productSpec
String
否
产品规格，见「3.筛选条件传参示例-文本」
remark
String
否
备注，见「3.筛选条件传参示例-文本」
startTimePlanned
String
否
计划开始时间，见「3.筛选条件传参示例-时间」
startTimeReal
String
否
实际开始时间，见「3.筛选条件传参示例-时间」
endTimePlanned
String
否
计划结束时间，见「3.筛选条件传参示例-时间」
endTimeReal
String
否
实际结束时间，见「3.筛选条件传参示例-时间」
createdAt
String
否
创建时间，见「3.筛选条件传参示例-时间」
updatedAt
String
否
更新时间，见「3.筛选条件传参示例-时间」
priority

Number[]
否
优先级，见「3.筛选条件传参示例-单选」
值范围1 加急0 默认-1 暂停status

Number[]
否
状态，见「3.筛选条件传参示例-单选」
值范围0 未开始10 执行中20 已结束30 已取消筛选条件-自定义字段
自定义字段作为筛选条件时，需要先通过「查询自定义字段Metadata查询接口」获得字段对应的field_name后，将field_name作为请求参数


自定义字段_文本
String

见「3.筛选条件传参示例-文本」
自定义字段_数字
String

见「3.筛选条件传参示例-数字」
自定义字段_时间
String

见「3.筛选条件传参示例-时间」
自定义字段_单选
String

见「3.筛选条件传参示例-时间」
自定义字段_多选
String

见「3.筛选条件传参示例-多选」

2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
Object 数组
msg
String
返回消息

data[]
参数名
字段类型
描述
projectCode
String
工单编号
suppId
Number
供应商id
suppCode
String
供应商代码
suppNickName
String
供应商名称
productCode
String
产品编号
productName
String
产品名称
productSpec
String
产品规格
productUnit
String
单位
statusStr
String
状态-中文
status
Number
状态-数字
priority
Number
优先级
planAmount
Number
计划数
realAmount
Number
实际数
planStartTime
String
计划开始时间
planEndTime
String
计划结束时间
realStartTime
String
实际开始时间
realEndTime
String
实际结束时间
remark
String
备注
expiredDayCount
Number
工期状态
currentProcessName
String
当前工序
taskCount
Number
任务数
endTaskCount
Number
已结束任务数
createdAt
String
创建时间
updatedAt
String
更新时间
syncAt
String
同步时间
creatorName
String
创建人名称
updatorName
String
更新人名称
customFieldValues
Object[]
跨厂工单自定义字段

2.4错误代码
1075
查询条件全部为空
1076
查询条件值非法

3.筛选条件传参示例
所有类型的筛选条件结构相同，由以下3个部分构成：
    {
        "fieldName": "productName",  // 作为筛选条件的字段名称
                                     // - 系统字段：见接口文档中的定义
                                     // - 自定义字段：通过「查询自定义字段Metadata定义接口」获得
        "queryOperator": "like",     // 匹配方式，不同类型字段支持的匹配方式不同
        "fieldValue": ["阀门"]        // 匹配值，不同类型字段支持的匹配方式不同
    }


序号
字段类型
queryOperator
fieldValue
示例
1

文本

like包含：模糊匹配equal等于：精确匹配
fieldValue[0]
匹配值
[
    {
        "fieldName": "productName",
        "queryOperator": "like",     // 包含
        "fieldValue": ["阀门"]
    },
    {
        "fieldName": "productName",
         "queryOperator": "equal",   // 等于
         "fieldValue": ["阀门"]
    }
]

2
日期
lessEqual小于等于greaterEqual大于等于rangeQuery范围fieldValue[0]精度，需要传固定值"second"fieldValue[1]匹配值1fieldValue[2]匹配值2
[
  {
    "fieldName": "createdAt",
    "queryOperator": "lessEqual",    // 小于等于
    "fieldValue": [
      "second",
      "2025-01-15 00:00:07"
    ]
  },
  {
    "fieldName": "startTimePlanned",
    "queryOperator": "rangeQuery",    // 范围
    "fieldValue": [
      "second",                      // 精度
      "2025-01-16 00:00:07",
      "2025-01-31 10:49:49"
    ]
  },
  {
    "fieldName": "updatedAt",
    "queryOperator": "greaterEqual",    // 大于等于
    "fieldValue": [
      "second",                    // 精度
      "2024-01-31 10:50:24"
    ]
  }
]

3
数字

lessEqual小于等于greaterEqual大于等于rangeQuery范围
fieldValue[0], fieldValue[1]
匹配值
[
  {
    "fieldName": "amountPlanned",
    "queryOperator": "lessEqual",     // 小于等于
    "fieldValue": [
      100
    ]
  },
  {
    "fieldName": "amountReal",
    "queryOperator": "greaterEqual",  // 大于等于
    "fieldValue": [
      2
    ]
  },
  {
    "fieldName": "defectAmount",
    "queryOperator": "rangeQuery",    // 范围，此处为[-1，999]
    "fieldValue": [
      -1,
      999
    ]
  }
]

4
单选
in等于任意一个值
fieldValue[0～n]
匹配值
[
  {
    "fieldName": "status",
    "queryOperator": "in",      // 匹配状态等于0或10的记录
    "fieldValue": [
      0,
      10
    ]
  }
]

5
多选

containOne包含任意一个选项值
fieldValue[0～n]
匹配值[
  {
    "fieldName": "project12341234567890",
    "queryOperator": "containOne",    // 匹配选项值中包含1或2的记录
    "fieldValue": [
      "1",
      "2"
    ]
  }
]

6
其他类型


暂不支持

同步跨厂工单
1.功能描述
可通过此接口触发跨厂工单的同步，同步限制同web端。
2.接口定义
2.1请求地址：/api/dytin/external/owner/project/sync
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
无。

2.3返回数据
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
boolean
成功与否 成功：true
msg
String
返回消息

2.4错误代码
1074
一小时内只能同步一次，请稍后再试



🎯  图片、附件
上传附件
1.功能描述
接口用于新增工单、创建产品、更新产品、更新任务、更新工单等接口的附件、图片类型自定义字段值的写入。具体方式如下：
第一步    调用方通过此接口上传附件，上传成功后拿到返回data数据项
第二步    根据需要传入的自定义字段类型传值：
  - 自定义字段-附件：取data中的id，将id作为自定义字段的value传入
  - 自定义字段-图片：取data中的uri，将uri作为自定义字段的value传入


2.接口定义
2.1请求地址：/api/filebase/v1/files/_openapi/_upload
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token）
- Content-Type:multipart/form-data
2.2请求参数
参数名
类型
是否必填
描述
file
File

是
文件大小限制为20M，后缀名仅限
       .jpg
       .png
       .jpeg
      .pdf
      .dwg
      .dwt
      .dxf
      .xlsx
      .xls
      .docx
      .doc
      .ppt
      .pptx
      .zip
      .rar

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败                   
data
data[]
返回具体信息
msg
String
返回消息

data[]
参数名
类型
描述
id
Number
文件id ，作为自定义字段附件value的值
orgId
Number
工厂id
originalFileName
String
文件名称
uniqueFileName
String
文件唯一名称
originalExtension
String 
扩展名
uploaderId
String 
上传id
uri
String
文件地址，通过此地址可以获取下载文件


2.4错误代码
102x
文件大小不能超过20M 

查询附件
1.功能描述
接口用于查询附件文件的详细信息。附件类型自定义字段的值是文件id，通过此接口可以拿到附件的下载链接，文件名等。具体使用方式如下：
第一步 调用方通过查询工单、产品等查询接口，获取到附件类型的自定义字段值（取data中附件字段 customFieldValues的value），该值就是接口查询需要用到的fileId。

第二步 将前面取到的值传入请求链接中：

2.接口定义
2.1请求地址：/api/filebase/v1/files/_openapi/_list?fileIds=
通信协议：HTTPS
请求方式：GET
请求header参数：
- X-AUTH，值就是登录接口拿到的token
- Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
fileIds
String
是
是多个文件id的拼接字符串， 多个id间用“,”分隔。

2.3返回参数
参数名
类型
描述
statusCode
Number
成功与否 成功：200 ，其他为失败 
data
data[]
返回具体信息
msg
String
返回消息
total
Number
返回具体信息数量

data[]
参数名
类型
描述
id
Number
文件id ，作为自定义字段附件value的值
orgId
Number
工厂id
originalFileName
String
文件名称
originalExtension
String 
扩展名
uploaderId
String 
上传id
uri
String
文件地址，通过此地址可以获取下载文件

2.4错误代码



🎯 标签模板
创建标签接口
1.功能描述
通过该接口可在小工单新增标签模版
2.接口定义
2.1请求地址：/api/dytin/external/label/output/create
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
类型
是否必填
描述
qrCode
String
是
标签二维码
projectCode
String
否
工单编号
processName
String
否
工序名称
outputAmount
Number
否
报工数
fineAmount
Number
否
良品数
customFieldValues

CustomFieldValue[]

否
报工自定义字段
例
[{"name":"名称1","value":"文本-报工01"},{"name":"名2","value":174237},{"name":"名称3","value":"1"},{"name":"名称4","value":"文本-01"}]
如果自定义字段类型是关联对象，value传值设定根据关联的不同对象类型，分别为:
用户对象   value 传名称
单位对象   value 传名称
产品对象    value传产品编号
工艺路线    value 传工艺路线名称
工序            value 传工序编号
不良品         value 传不良品编号
仓库           value传仓库编号（仅支持启用中）

CustomFieldValue 类型字段介绍
字段名
类型
描述
name
String
自定义字段名称
value
String
自定义字段值

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
ExternalLink
返回具体信息
msg
String
返回消息

ExternalLink 类型字段介绍
字段名
类型
描述
externalLink
String
外链, 客户可通过该地址快速进入报工页面

2.4错误信息
1030
业务操作类型非法
1031
标签已存在

更新标签接口
1.功能描述
通过该接口可在小工单更新标签模版
2.接口定义
2.1请求地址：/api/dytin/external/label/output/update
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
类型
是否必填
描述
qrCode
String

标签二维码
projectCode
String
否
工单编号
processName
String
否
工序名称
outputAmount
Number
否
报工数
fineAmount
Number
否
良品数
customFieldValues

Object[]

否
报工自定义字段
例
[{"name":"名称1","value":"文本-报工01"},{"name":"名2","value":174237},{"name":"名称3","value":"1"},{"name":"名称4","value":"文本-01"}]
如果自定义字段类型是关联对象，value传值设定根据关联的不同对象类型，分别为:
用户对象   value 传名称
单位对象   value 传名称
产品对象    value传产品编号
工艺路线    value 传工艺路线名称
工序            value 传工序编号
不良品         value 传不良品编号
仓库           value传仓库编号（仅支持启用中）

CustomFieldValue 类型字段介绍
字段名
类型
描述
name
String
自定义字段名称
value
String
自定义字段值

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
msg
String
返回消息


2.4错误信息
1032
标签不存在


删除标签接口
1.功能描述
通过该接口可在小工单删除标签模版
2.接口定义
2.1请求地址：/api/dytin/external/label/delete
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
类型
是否必填
描述
qrCode
String
是
标签二维码

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
msg
String
返回消息

2.4错误信息
1032
标签不存在


🎯 部门
创建部门接口
1.功能描述
通过该接口可在小工单批量新增部门
2.接口定义
2.1请求地址：/api/dytin/external/group/createBatch
通信协议：HTTPS
请求方式：POST
2.2请求参数
参数名
类型
是否必填
描述
code
String
是
部门编号
name
String
是
部门名称
userIds
Number[]
否
用户id集合

请求参数示例：
[{
    "code": "String",
    "name": "String"
}]

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
msg
String
返回消息
data
Object
true/false

🎯 操作日志
查询操作日志列表
1. 功能描述
通过该接口可以查询操作日志列表数据
2. 接口定义
2.1 请求地址：/api/mmtin/external/operationLog/list
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
示例：
X-AUTH:eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEzMzE0LCJvcmdJZCI6NTc0LCJzc29Ub2tlbiI6InYxdkxjV0tUdVhMVlpkencifQ.lH5cpUfbDW9lI-_J-rz0xPzPRbbK0JgT3Ukpbxd33Y_44abguPZiccCjlqy53pR4cKHP2sJxaxgR1iNWsEXpZA


2.2 请求参数
参数名
类型
是否必参
描述
objectName

String

是

模块中文名，以导航栏的模块中文名为准，例如“工单”、“产品”等
自定义事件规则配置自定义字段表单提交校验规则出入库单工序采购明细产品自定义字段值物料清单履约助手销售明细自定义事件供应商库存收发明细工艺路线自定义表单工单报工自定义单据用户配置-打印系统模版用户配置-透视表模板用户配置-透视表系统模板任务采购订单视图销售订单绩效工资配置客户logFrom
Long
否
毫秒级时间戳，操作时间-起始，为空时默认为近三个月
logTo
Long
否
毫秒级时间戳，操作时间-截止，为空时默认为近三个月
instanceIds
List<Integer>
否
业务实例id集合，当有值时，根据所筛选的业务实例id查询对应的操作日志记录
instanceCode
String
否
业务编号（单据编号），支持根据业务编号查询，如业务对象不存在编号定义，支持根据上级关联对象的编号查询，对应的关系如下
库存收发明细 - 出入库单编号自定义时间规则配置 - 自定义事件名称物料清单 - 产品编号销售明细 - 销售订单编号履约助手 - 销售订单编号采购明细 - 采购订单编号任务 - 工单编号报工 - 工单编号自定义单据 - 工单编号
page

Object
否

不传 默认每页 10条数据,最大限度每页100条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条

示例：
{
    "page": {
        "pageNum": 1,
        "pageSize": 10
    },
    "objectName": "履约助手",
    "logFrom": 1760162110000,
    "logTo": 1760162120000,
    "instanceIds": [678020, 678019]
}


2.3 返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
data[]
返回具体信息
msg
string
返回消息
page
Object
返回分页信息     "page": {"pageNum": 1, "pageSize": 10, "total": 477}


data[]
参数名
类型
描述
instanceId
Long
业务实例id，例如：工单id，任务id，报工id等
instanceCode
String
业务实例编号，如果实例不存在编号则为空
objectCode
String
对象模块编号
objectName
String
对象模块中文名
operateType
String
操作类型，例如：新增、删除、编辑
operateTimeString
String
操作时间，格式：yyyy-mm-dd hh:mm:ss，例如：2025-10-11 13:55:12
tsMs
Long
操作时间毫秒级时间戳
operator
Object
操作人
fieldType
String
操作日志字段类型，分为系统字段、自定义字段

2.4 错误代码
02000001
操作日志对象名称不能为空
02000002
操作日志对象不存在
02000003
操作日志时间戳不能为空
02000004
操作日志业务ID不能为空


查询操作日志详情
1. 功能描述
通过该接口可以查询操作日志详情
2. 接口定义
2.1 请求地址：/api/mmtin/external/operationLog/detail
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
示例：
X-AUTH:eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEzMzE0LCJvcmdJZCI6NTc0LCJzc29Ub2tlbiI6InYxdkxjV0tUdVhMVlpkencifQ.lH5cpUfbDW9lI-_J-rz0xPzPRbbK0JgT3Ukpbxd33Y_44abguPZiccCjlqy53pR4cKHP2sJxaxgR1iNWsEXpZA


2.2 请求参数
参数名
类型
是否必参
描述
objectName

String

是

模块中文名，以导航栏的模块中文名为准，例如“工单”、“产品”等
自定义事件规则配置自定义字段表单提交校验规则出入库单工序采购明细产品自定义字段值物料清单履约助手销售明细自定义事件供应商库存收发明细工艺路线自定义表单工单报工自定义单据用户配置-打印系统模版用户配置-透视表模板用户配置-透视表系统模板任务采购订单视图销售订单绩效工资配置客户tsMs
Long
是
毫秒级日志时间戳，可以从列表接口的返参中获取或者手动拼凑
bizId
Long
是
业务实例id

示例：
{
    "bizId": 678020,
    "objectName": "履约助手",
    "tsMs": 1758782619555
}


2.3 返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
data
返回具体信息
msg
string
返回消息

data
参数名
类型
描述
list
object[]
字段变更信息列表

list
参数名
类型
描述
fieldName
String
字段名
fieldCode
String
字段code
beforeValue
String
变更前的值
afterValue
String
变更后的值
isModified
Boolean
是否发生变更：true为是，false为否


2.4 错误代码
02000001
操作日志对象名称不能为空
02000002
操作日志对象不存在
02000003
操作日志时间戳不能为空
02000004
操作日志业务ID不能为空


🎯 客户
创建客户接口
1.功能描述
通过该接口可在小工单系统创建客户信息，负责人 （用户账号）  与 负责人（ 部门编号）都不填写时默认为所有人
2.接口定义
2.1请求地址：/api/dytin/external/customer/add
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释

code
string
否
客户编号
name
string
是
客户名称
fullName
string
否
客户全称
contact
string
否
联系人
phone
string
否
联系电话
address
string
否
联系地址
receivableDays

Integer

否
收款期限（天） 收款期限需大等于于0，最多4位整数
responsibleUsers
string[]
否
负责人 （用户账号） 
responsibleGroups
string[]
否
负责人（ 部门编号）
customFieldValues
Object[]
否
客户自定义字段，传值方式见「自定义字段-传值说明」

示例：
{
    "code": 678020,
    "name": "履约助手",
    "fullName": "",
    "contact": "",
    "phone": 13120338931,
    "address": "漕宝路",
    "receivableDays":10,
    "responsibleUser": ["用户01","用户02"],
    "customFieldValues": 
}

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
客户id
code
String
客户编号

更新客户接口
1.功能描述
通过该接口可在小工单系统更新客户信息
2.接口定义
2.1请求地址：/api/dytin/external/customer/modify
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
id
number
否
客户id/客户编号二选一，不能同时为空；与code同时存在传值时，以id为准
code
string
否
客户编号/客户id二选一，不能同时为空；与id同时存在传值时，以id为准
name
string
否
客户名称
fullName
string
否
客户全称
contact
string
否
联系人
phone
string
否
联系电话
address
string
否
联系地址
receivableDays

Integer

否
收款期限

responsibleUsers
string[]
否
负责人 （用户账号）  与 负责人（ 部门编号）二选一，不可同时为空
responsibleGroups
string[]
否
负责人（ 部门编号）与 负责人 （用户账号）二选一，不可同时为空
customFieldValues
Object[]
否
客户自定义字段，传值方式见「自定义字段-传值说明」

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
客户id
code
String
客户编号

删除客户接口
1.功能描述
通过该接口可在小工单系统删除客户信息
2.接口定义
2.1请求地址：/api/dytin/external/customer/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
id
number
否
客户id/客户编号二选一，不能同时为空；与code同时存在传值时，以id为准
code
string
否
客户编号/客户id二选一，不能同时为空；与id同时存在传值时，以id为准

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
客户id
code
String
客户编号

查询客户接口
1.功能描述
通过该接口可在小工单系统查询客户信息，请求参数不支持全部为空
2.接口定义
2.1请求地址：/api/dytin/external/customer/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
code
string
否
客户编号
name
string
否
客户名称
fullName
string
否
客户全称
createdAtStart

String
否
客户创建时间起始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间大于等于"2021-09-30 23:59:59"的供应商。
createdAtEnd

String
否
客户创建时间结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间小于等于"2021-09-30 23:59:59"的供应商。
updatedAtStart

String
否
客户更新时间起始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间大于等于"2021-09-30 23:59:59"的供应商。
updatedAtEnd
String
否
客户更新时间结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间小于等于"2021-09-30 23:59:59"的供应商。
page

Object

否

不传 默认每页 10条数据,最大限度每页100条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条

2.3返回参数

参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回信息
msg
String
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "process17235164190354": { }}}

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
参数名
字段类型
描述
id
number
客户id
code
string
客户编号
name
string
客户名称
fullName
string
客户全称
contact
string
联系人
phone
string
联系电话
address
string
联系地址
receivableDays

Integer

收款期限
responsibleUsers
Object[]
负责人 （用户） 
responsibleGroups
Object[]
负责人（ 部门）
customFieldValues
Object[]
自定义字段

customFieldValues  Object[]
参数名
类型
描述
customFieldMetadata
Object[]

自定义字段元数据信息


🎯 供应商
创建供应商接口
1.功能描述
通过该接口可在小工单系统创建供应商信息
2.接口定义
2.1请求地址：/api/dytin/external/vendor/add
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
code
string
否
供应商编号
shortName
string
是
供应商名称
name 
string
否
供应商全称
contact
string
否
联系人
phone
string
否
联系电话
address
string
否
联系地址
payableDays

Integer

否

付款期限

assOrg
Integer
否
可委外（传0或1） 1-是，0-否  不传默认为 0
chOrgName
String
否
供应商对应的上下游组织名称 可委外时必填
customFieldValues
Object[]
否
供应商自定义字段，传值方式见「自定义字段-传值说明」

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
供应商id
code
String
供应商编号

更新供应商接口
1.功能描述
通过该接口可在小工单系统更新供应商信息
2.接口定义
2.1请求地址：/api/dytin/external/vendor/modify
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
id
number
否
供应商id/供应商编号二选一，不能同时为空；与code同时存在传值时，以id为准
code
string
否
供应商编号/供应商id二选一，不能同时为空；与id同时存在传值时，以id为准
shortName
string
否
供应商名称
name 
string
否
供应商全称
contact
string
否
联系人
phone
string
否
联系电话
address
string
否
联系地址
payableDays

Integer

否

付款期限

assOrg
Integer
否
可委外（传0或1） 1-是，0-否  不传默认为 0
customFieldValues
Object[]
否
供应商自定义字段，传值方式见「自定义字段-传值说明」

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
供应商id
code
String
供应商编号

删除供应商接口
1.功能描述
通过该接口可在小工单系统删除供应商信息
2.接口定义
2.1请求地址：/api/dytin/external/vendor/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
id
number
否
供应商id/供应商编号二选一，不能同时为空；与code同时存在传值时，以id为准
code
string
否
供应商编号/供应商id二选一，不能同时为空；与id同时存在传值时，以id为准

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
供应商id
code
String
供应商编号

查询供应商接口
1.功能描述
通过该接口可在小工单系统查询供应商信息，请求参数不支持全部为空
2.接口定义
2.1请求地址：/api/dytin/external/vendor/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
code
string
否
供应商编号
shortName
string
否
供应商名称
name 
string
否
供应商全称
assOrg
Integer
否
可委外
createdAtStart

String
否
供应商创建时间起始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间大于等于"2021-09-30 23:59:59"的供应商。
createdAtEnd

String
否
供应商创建时间结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间小于等于"2021-09-30 23:59:59"的供应商。
updatedAtStart

String
否
供应商更新时间起始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间大于等于"2021-09-30 23:59:59"的供应商。
updatedAtEnd
String
否
供应商更新时间结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间小于等于"2021-09-30 23:59:59"的供应商。
page

Object

否

不传 默认每页 10条数据,最大限度每页100条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回信息
msg
String
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "process17235164190354": { }}}

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
参数名
字段类型
描述
id
number
供应商id
code
string
供应商编号
shortName
string
供应商名称
name
string
供应商全称
contact
string
联系人
phone
string
联系电话
address
string
联系地址
assOrg
Integer
可委外
responsibleUser
string[]
负责人
customFieldValues
Object[]
自定义字段

customFieldValues  Object[]
参数名
类型
描述
customFieldMetadata
Object[]

自定义字段元数据信息

🎯 设备
创建设备接口
1.功能描述
通过该接口可在小工单系统创建设备信息
2.接口定义
2.1请求地址：/api/dytin/external/equipment/add
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
code
string
否
设备编号
name 
string
是
设备名称
status
Number
否
默认是10   10-启用 20-废弃
customFieldValues
Object[]
否
设备自定义字段，传值方式见「自定义字段-传值说明」

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
设备id
code
String
设备编号

更新设备接口
1.功能描述
通过该接口可在小工单系统更新设备信息
2.接口定义
2.1请求地址：/api/dytin/external/equipment/modify
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
id
number
否
设备id/设备编号二选一，不能同时为空；与code同时存在传值时，以id为准
code
string
否
设备编号
name 
string
是
设备名称
status
Number
否
默认是10   10-启用 20-废弃
customFieldValues
Object[]
否
设备自定义字段，传值方式见「自定义字段-传值说明」

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
设备id
code
String
设备编号

删除设备接口
1.功能描述
通过该接口可在小工单系统删除设备信息
2.接口定义
2.1请求地址：/api/dytin/external/equipment/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
id
number
否
设备id/设备编号二选一，不能同时为空；与code同时存在传值时，以id为准
code
string
否
设备编号/设备id二选一，不能同时为空；与id同时存在传值时，以id为准

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
设备id
code
String
设备编号

查询设备接口
1.功能描述
通过该接口可在小工单系统查询设备信息，请求参数不支持全部为空
2.接口定义
2.1请求地址：/api/dytin/external/equipment/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
code
string
否
设备编号
name 
string
是
设备名称
status
Number
否
默认是10   10-启用 20-废弃
createdAtStart

String
否
设备创建时间起始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间大于等于"2021-09-30 23:59:59"的设备。
createdAtEnd

String
否
设备创建时间结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间小于等于"2021-09-30 23:59:59"的设备。
updatedAtStart

String
否
设备更新时间起始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间大于等于"2021-09-30 23:59:59"的设备。
updatedAtEnd
String
否
设备更新时间结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间小于等于"2021-09-30 23:59:59"的设备。
page

Object

否

不传 默认每页 10条数据,最大限度每页100条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条

2.3返回参数
参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回信息
msg
String
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "equipment17235164190354": { }}}

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
参数名
字段类型
描述
id
Number
设备id
code
string
设备编号
name
string
设备名称
status
Number
状态 10-启用 20-废弃
customFieldValues
Object[]
自定义字段

customFieldValues  Object[]
参数名
类型
描述
customFieldMetadata
Object[]

自定义字段元数据信息

🎯 供应商价目表
创建供应商价目表接口
1.功能描述
通过该接口可在小工单系统创建供应商价目表信息
2.接口定义
2.1请求地址：/api/dytin/external/purchase/vendor/product/price/add
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
vendorCode
string
是
供应商编号
productCode 
string
是
产品编号
price
Number
是
价格
vendorProductCode
string
否
供应商产品编号
vendorProductName
string
否
供应商产品名称
vendorProductSpec
string
否
供应商产品规格
notes
string
否
注意事项
remark
string
否
备注

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
供应商价目表id

更新供应商价目表接口
1.功能描述
通过该接口可在小工单系统更新供应商价目表信息 
通过供应商编号和产品编号来确认唯一的价目表信息
2.接口定义
2.1请求地址：/api/dytin/external/purchase/vendor/product/price/modify
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
vendorCode
string
是
供应商编号
productCode 
string
是
产品编号
price
Number
是
价格
vendorProductCode
string
否
供应商产品编号
vendorProductName
string
否
供应商产品名称
vendorProductSpec
string
否
供应商产品规格
notes
string
否
注意事项
remark
string
否
备注

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
供应商价目表id

删除供应商价目表接口
1.功能描述
通过该接口可在小工单系统删除供应商价目表信息
2.接口定义
2.1请求地址：/api/dytin/external/purchase/vendor/product/price/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
ids
number[]
否
供应商价目表id集合

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述

查询供应商价目表接口
1.功能描述
通过该接口可在小工单系统查询供应商价目表信息，请求参数不支持全部为空
2.接口定义
2.1请求地址：/api/dytin/external/purchase/vendor/product/price/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
vendorCode
string
否
供应商编号
productCode 
string
是
产品编号
vendorProductCode
Number
否
供应商产品编号
createdAtStart

String
否
供应商价目表创建时间起始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间大于等于"2021-09-30 23:59:59"的供应商价目表。
createdAtEnd

String
否
供应商价目表创建时间结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间小于等于"2021-09-30 23:59:59"的供应商价目表。
updatedAtStart

String
否
供应商价目表更新时间起始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间大于等于"2021-09-30 23:59:59"的供应商价目表。
updatedAtEnd
String
否
供应商价目表更新时间结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间小于等于"2021-09-30 23:59:59"的供应商价目表。
page

Object

否

不传 默认每页 10条数据,最大限度每页100条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条

2.3返回参数

参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回信息
msg
String
返回消息

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
参数名
字段类型
描述
id
Number
供应商价目表id
vendorShortName
string
供应商名称
productCode
string
产品编号
productName
string
产品名称
productSpec
Object[]
产品规格
price
Number
单价
vendorProductCode
string
供应商产品编号
vendorProductName
string
供应商产品名称
vendorProductSpec
string
供应商产品规格
notes
string
注意事项
remark
string
要求备注
createdAt
localdatatime
创建时间
updatedAt
localdatatime
更新时间

🎯 客户价目表
创建客户价目表接口
1.功能描述
通过该接口可在小工单系统创建客户价目表信息
2.接口定义
2.1请求地址：/api/dytin/external/sale/customer/product/price/add
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
customerCode
string
是
客户编号
productCode 
string
是
产品编号
price
Number
是
价格
customerProductCode
string
否
客户产品编号
customerProductName
string
否
客户产品名称
customerProductSpec
string
否
客户产品规格
notes
string
否
注意事项
remark
string
否
备注

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
客户价目表id

更新客户价目表接口
1.功能描述
通过该接口可在小工单系统更新客户价目表信息 
通过客户编号和产品编号来确认唯一的价目表信息
2.接口定义
2.1请求地址：/api/dytin/external/sale/customer/product/price/modify
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
customerCode
string
是
客户编号
productCode 
string
是
产品编号
price
Number
是
价格
customerProductCode
string
否
客户产品编号
customerProductName
string
否
客户产品名称
customerProductSpec
string
否
客户产品规格
notes
string
否
注意事项
remark
string
否
备注

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
客户价目表id

删除客户价目表接口
1.功能描述
通过该接口可在小工单系统删除客户价目表信息
2.接口定义
2.1请求地址：/api/dytin/external/sale/customer/product/price/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
ids
number[]
否
客户价目表id集合

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述

查询客户价目表接口
1.功能描述
通过该接口可在小工单系统查询客户价目表信息，请求参数不支持全部为空
2.接口定义
2.1请求地址：/api/dytin/external/sale/customer/product/price/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
customerCode
string
否
客户编号
productCode 
string
是
产品编号
customerProductCode
Number
否
客户产品编号
createdAtStart

String
否
客户价目表创建时间起始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间大于等于"2021-09-30 23:59:59"的客户价目表。
createdAtEnd

String
否
客户价目表创建时间结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回创建时间小于等于"2021-09-30 23:59:59"的客户价目表。
updatedAtStart

String
否
客户价目表更新时间起始时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间大于等于"2021-09-30 23:59:59"的客户价目表。
updatedAtEnd
String
否
客户价目表更新时间结束时间，格式为：yyyy-MM-dd HH:mm:ss。如"2021-09-30 23:59:59"，返回更新时间小于等于"2021-09-30 23:59:59"的客户价目表。
page

Object

否

不传 默认每页 10条数据,最大限度每页100条数据  "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50}  每页 50条

2.3返回参数
参数名
类型
描述
code
string
成功与否  成功：01000000 ，其他为失败，                          
data
object
返回数据
msg
string
返回消息
page
Object
返回分页信息     "page": {"pageNum": 1, "pageSize": 10, "total": 477,  }


参数名
类型
描述
code
String
成功与否  成功：01000000 ，其他为失败，                          
data
Object
返回信息
msg
String
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "process17235164190354": { }}}

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
参数名
字段类型
描述
id
Number
客户价目表id
customerName
string
客户名称
productCode
string
产品编号
productName
string
产品名称
productSpec
Object[]
产品规格
price
Number
单价
customerProductCode
string
客户产品编号
customerProductName
string
客户产品名称
customerProductSpec
string
客户产品规格
notes
string
注意事项
remark
string
要求备注
createdAt
localdatatime
创建时间
updatedAt
localdatatime
更新时间

🎯 应收单
创建应收单
1.功能描述
外部系统通过此接口创建应收单，支持最多1000行应收明细。其中应收明细，仅应收类型为销售应收时支持填写
2.接口定义
2.1请求地址：POST /api/dytin/external/receivable/add
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
orderNo
string
否
应收单号
receiptDate 
string
是
开票日期 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
receivableType
Number
是
应收类型 10-销售应收 20-其他应收
customerId
Number
否
结算客户id 和结算客户名称二选一 必填其中一个
customerName
string
否
结算客户名称 和结算客户Id二选一 必填其中一个
bizUserId
Number
否
业务员id 和 业务员名称二选一
bizUserName
string
否
业务员名称 和业务员id二选一
receiptAmount
Number
是
开票金额
receiveWay
string
否
收款方式
receiveEndDate
string
否
收款到期日 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
receiptNo
string
否
发票号
advance
Number
否
使用预收
roundOff
Number
否
抹零：默认 0
discount
Number
否
整单折扣  默认 100 限制整数最多四位，小数最多两位
contactName
string
否
联系人
contactNumber
string
否
联系电话
contactAddr
string
否
联系地址
remark
string
否
备注
attachment
object[]
否
应收附件(需要先调用上传附件接口，根据接口返回值来传参)
 id
Number
是
附件Id
 url
string
是
附件url
 originalFileName
string
是
附件原始文件名
detail
object[]
否
应收明细 仅应收类型为销售应收时支持填写明细
seq
string
是
序号
bizType
Number
是
应收类别 10-发货 20-退货 30-其他
receiptQty
Number
是
开票数量
unitPrice
Number
是
单价
discount
Number
否
折扣 % 默认 100 限制整数最多四位，小数最多两位
taxRate
Number
否
税率 % 默认值0
remark
String
否
明细备注
stockOrderNo
String
否
库存单编号 与 库存单ID 二选一 必须填写其中一个 
仅支持填写 销售出库，销售退货入库类型的单据号，且库存单的客户与上面填写的客户信息一致
产品和销售订单信息从库存单据中带出
stockOrderDetailSeq
String
否
库存单明细序号 与 库存单明细ID 二选一 必须填写其中一个
stockOrderId

Number
否
库存单ID 与 库存单编号二选一 必须填写其中一个
仅支持填写 销售出库，销售退货入库类型的库存单id，且库存单的客户与上面填写的客户信息一致 
产品和销售订单信息从库存单据中带出
stockOrderDetailId
Number
否
库存单明细ID 与 库存单明细序号 二选一 必须填写其中一个

请求参数示例：
{
    "receiptDate": "2026-01-30 10:00:00",
    "receivableType": 10,
    "customerName": "测试客户",
    "bizUserId": 1001,
    "receiptAmount": 10000.00,
    "receiveWay": "银行转账",
    "receiveEndDate": "2026-02-28 23:59:59",
    "remark": "测试应收单",
    "detail": [
        {
            "seq": 1,
            "bizType": 10,
            "receiptQty": 100,
            "unitPrice": 100.00，
            "stockOrderId": 111111,
            "stockOrderDetailId": 211122
        }
    ]
}

2.3返回参数
参数名
类型
描述
id
Number
应收单ID
orderNo
string
应收单号

2.4错误代码
错误码
描述
0000001
客户ID和客户名称不能同时为空
0000001
客户名称【{0}】不存在


编辑应收单
1.功能描述
外部系统通过此接口编辑应收单，支持id和orderNo二选一方式指定要编辑的应收单。支持最多1000行应收明细。其中应收明细，仅应收类型为销售应收时支持填写
2.接口定义
2.1请求地址：POST /api/dytin/external/receivable/edit
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
应收单ID（与orderNo二选一必填）
orderNo
string
否
应收单号
receiptDate 
string
是
开票日期 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
receivableType
Number
是
应收类型 10-销售应收 20-其他应收
customerId
Number
否
结算客户id 和结算客户名称二选一 必须填写一个
customerName
string
否
结算客户名称 和结算客户Id二选一 必须填写一个
bizUserId
Number
否
业务员id 和 业务员名称二选一
bizUserName
string
否
业务员名称 和业务员id二选一
receiptAmount
Number
是
开票金额
receiveWay
string
否
收款方式
receiveEndDate
string
否
收款到期日 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
receiptNo
string
否
发票号
advance
Number
否
使用预收
roundOff
Number
否
抹零：默认 0
discount
Number
否
整单折扣 % 默认 100 限制整数最多四位，小数最多两位
realReceivableAmount
Number
否
实际应收金额
contactName
string
否
联系人
contactNumber
string
否
联系电话
contactAddr
string
否
联系地址
remark
string
否
备注
attachment
object[]
否
应收附件(需要先调用上传附件接口，根据接口返回值来传参)
 id
Number
是
附件Id
 url
string
是
附件url
 originalFileName
string
是
附件原始文件名
detail
object[]
否
应收明细 明细编辑时为全删全增
seq
string
是
序号
bizType
Number
是
应收类别 10-发货 20-退货 30-其他
receiptQty
Number
是
开票数量
unitPrice
Number
是
单价 
discount
Number
否
折扣 % 默认100 限制整数最多四位，小数最多两位
taxRate
Number
否
税率 % 默认值0
remark
String
否
明细备注
stockOrderNo
String
否
库存单编号 与 库存单ID 二选一 必须填写其中一个 
仅支持填写 销售出库，销售退货入库类型的单据号，且库存单的客户与上面填写的客户信息一致
产品和销售订单信息从库存单据中带出
stockOrderDetailSeq
String
否
库存单明细序号 与 库存单明细ID 二选一 必须填写其中一个
stockOrderId
Number
否
库存单ID 与 库存单编号二选一 必须填写其中一个
仅支持填写 销售出库，销售退货入库类型的库存单id，且库存单的客户与上面填写的客户信息一致 
产品和销售订单信息从库存单据中带出
stockOrderDetailId
Number
否
库存单明细ID 与 库存单明细序号 二选一 必须填写其中一个

请求参数示例：
{
    "orderNo": "YS202601300001",
    "receiptDate": "2026-01-30 10:00:00",
    "receivableType": 10,
    "customerId": 1001,
    "bizUserId": 1002,
    "receiptAmount": 12000.00,
    "remark": "更新备注信息",
    "detail": [
        {
            "seq": 1,
            "bizType": 10,
            "receiptQty": 120,
            "unitPrice": 100.00,
            "stockOrderId": 3001,
            "stockOrderDetailId": 3002
        }
    ]
}

2.3返回参数
参数名
类型
描述
id
Number
应收单ID
orderNo
string
应收单号

2.4错误代码
错误码
描述
0000001
应收单ID和应收单号不能同时为空
0000001
应收单号【{0}】不存在

删除应收单
1.功能描述
外部系统通过此接口删除应收单，需传入应收单ID。
2.接口定义
2.1请求地址：POST /api/dytin/external/receivable/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
应收单ID（与orderNo二选一）
orderNo
string
否

应收单号 （与id二选一必填，如果传入orderNo会转换为id；如果同时传入id和orderNo，以id为准）

请求参数示例：
{
    "id": 10001
}

2.3返回参数
参数名
类型
描述
id
Number
应收单ID


查询应收单详情
1.功能描述
外部系统通过此接口查询应收单详情信息，包括应收明细和附件等信息。
2.接口定义
2.1请求地址：POST /api/dytin/external/receivable/order/detail
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
应收单ID（与orderNo二选一）
orderNo
string
否

应收单号 （与id二选一必填，如果传入orderNo会转换为id；如果同时传入id和orderNo，以id为准）

请求参数示例：
{
    "id": 10001
}

2.3返回参数
参数名
类型
描述
id
Number
应收单ID
orderNo
String
应收单号
receiptDate
String
开票日期
receivableType
Integer
应收类型 
receivableTypeDesc
String
应收类型描述
customerId
Number
结算客户ID
customerName
String
客户名称
customerCode
String
客户编码
customer
CustomerVO
结算客户对象
bizUserId
Number
业务员
bizUserName
String
业务员名称
receiptAmount
Number
开票金额
receiveWay
String
收款方式
receiveEndDate
String
收款到期日
receiptNo
String
发票号
advance
Number
使用预收
roundOff
Number
抹零
discount
Number
整单折扣%
realReceivableAmount
Number
实际应收金额
contactName
String
联系人
contactNumber
String
联系电话
contactAddr
String
联系地址
remark
String
应收备注
settlementFinishDay
String
结清日期
overdueStatus
Integer
逾期状态
overdueStatusDisplay
String
逾期状态描述
overdueDays
Integer
逾期天数
settlementAmount
Number
累计结款金额
pendingReceiveAmount
Number
待收款金额
attachment
Object[]
应收附件
 id
Number
附件Id
 url
string
附件url
 originalFileName
string
附件原始文件名
detail
Object[]

Id
Number
应收明细id
seq
string
序号
bizType
Number
应收类别
bizTypeDesc
string
应收类别描述
productCode
string
产品编号 
productId
Number
产品id 
productName
string
产品名称
productSpec
string
产品规格
expectReceiptQty
Number
应开票数量
totalReceiptQty
Number
累计开票数量
pendingReceiptQty
Number
待开票数量
receiptQty
Number
开票数量
unitPrice
Number
单价
discount
Number
折扣 %
discountAmount
Number
折扣额
taxRate
Number
税率 %
taxUnitPrice
Number
含税单价
quotationAmount
Number
报价金额
totalAmount
Number
不含税金额
taxAmount
Number
税额
taxTotalAmount
Number
含税金额
remark
String
明细备注
stockOrderNO
String
库存单号 
saleManageOrderNO
String
销售管理单编号
stockOrderId
Number
库存单ID 与 库存单编号二选一 必须填写其中一个
stockOrderDetailId
Number
库存单明细ID 与 库存单明细序号 二选一 必须填写其中一个
saleManageOrderId
Number
销售管理单ID 与 销售管理单编号 二选一 必须填写其中一个
saleManageOrderDetailId
Number
销售管理单明细ID 与 销售管理单 明细序号 二选一 必须填写其中一个

查询应收单列表
1.功能描述
外部系统通过此接口分页查询应收单列表
2.接口定义
2.1请求地址：POST /api/dytin/external/receivable/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
应收单ID
receivableType
Integer
否
应收类型
receiptDateStart
String

否
开票日期-起始 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
receiptDateEnd
String
否
开票日期 -结束 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
orderNo
String
否
应收单号（模糊查询）
remark
String
否
应收备注（模糊查询）
customerId
Number
否
结算客户ID
customerName
String
否
客户名称（模糊查询）
createdAtStart
String
否
创建时间-起始 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
createdAtEnd
String
否
创建时间-结束 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
updatedAtStart
String
否
更新时间-起始 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
updatedAtEnd
String
否
更新时间-结束 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
page

Object
否
不传 默认每页 10条数据 "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50} 每页 50条

2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
返回信息
msg
String
返回消息
reference



data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
参数名
类型
描述
id
Number
应收单ID
orderNo
String
应收单号
receiptDate
String
开票日期
receivableType
Integer
应收类型
receivableTypeDesc
String
应收类型描述
customerId
Number
结算客户ID
customerName
String
结算客户名称
customerCode
String
结算客户编码
bizUserId
Number
业务员
bizUserName
String
业务员名称
receiptAmount
Number
开票金额
receiveWay
String
收款方式
receiveEndDate
String
收款到期日
receiptNo
String
发票号
advance
Number
使用预收
roundOff
Number
抹零
discount
Number
整单折扣%
realReceivableAmount
Number
实际应收金额
contactName
String
联系人
contactNumber
String
联系电话
contactAddr
String
联系地址
remark
String
应收备注
attachment
Object[]
应收附件
settlementAmount
Number
累计结款金额
pendingReceiveAmount
Number
待收款金额
settlementFinishDay
String
结清日期
overdueStatus
Integer
逾期状态
overdueDays
Integer
逾期天数

🎯 收款单
创建收款单
1.功能描述
外部系统通过此接口创建收款单，支持最多1000条收款明细。仅销售收款支持填写明细。客户信息可通过customerId或customerName二选一方式传入。
2.接口定义
2.1请求地址：POST /api/dytin/external/receipt/add
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
code
String
否
收款单号
receiveDate
String
是
收款日期 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
type
Integer
是
收款类型（1-销售收款 2-预收款 3-其他收款）
mode
String
否
结算方式
customerId
Number

否
结算客户ID（与customerName二选一必填）
customerName
String
否
结算客户名称（与customerId二选一必填）
contact
String
否
联系人
contactNumber
String
否
联系电话
contactAddress
String
否
联系地址
realReceiveAmount
Number
是
实际收款金额
advanceAmount
Number
否
预收
discountAmount
Number
否
折让
realSettlementAmount
Number
是
实际结款金额
remark
String
否
收款备注
attachment
Object[]
否
收款附件
 id
Number
是
附件Id
 url
string
是
附件url
 originalFileName
string
是
附件原始文件名
details
Object[]
否
收款明细（最多1000条）
seq
Number
是
序号
receivableOrderId
Number

否
应收单id 和 应收单号 二选一 必须填写一个
receivableOrderNo
string
否
应收单号 和应收单id二选一 必须填写一个
settlementAmount
Number
是
结款金额
remark
string
否
明细备注

2.3返回参数
参数名
类型
描述
id
Number
收款单ID

2.4错误代码
错误码
描述
0000001
客户ID和客户名称不能同时为空
0000001
客户名称【{0}】不存在
0000001
客户名称【{0}】存在多个，请使用客户ID

请求参数示例：
{
    "receiveDate": "2026-01-30 14:30:00",
    "type": 1,
    "mode": "银行转账",
    "customerName": "测试客户",
    "contact": "张三",
    "contactNumber": "13800138000",
    "realReceiveAmount": 10000.00,
    "realSettlementAmount": 10000.00,
    "remark": "测试收款单",
    "details": [
        {
            "seq": 1,
            "receivableOrderId": 10001,
            "settlementAmount": 10000.00,
            "remark": "结清应收单"
        }
    ]
}


编辑收款单
1.功能描述
外部系统通过此接口编辑收款单，支持最多1000条收款明细。仅销售收款支持填写明细。客户信息可通过customerId或customerName二选一方式传入。
2.接口定义
2.1请求地址：POST /api/dytin/external/receipt/edit
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
收款单ID（与code二选一必填）
code
String
否
收款单号（与id二选一必填，如果传入code会转换为id；如果同时传入id和code，以id为准）
receiveDate
String
是
收款日期 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
type
Integer
是
收款类型（1-销售收款 2-预收款 3-其他收款）
mode
String
否
结算方式
customerId
Number
否

结算客户ID（与customerName二选一必填）
customerName
String
否
结算客户名称（与customerId二选一必填）
contact
String
否
联系人
contactNumber
String
否
联系电话
contactAddress
String
否
联系地址
realReceiveAmount
Number
是
实际收款金额
advanceAmount
Number
否
预收
discountAmount
Number
否
折让
realSettlementAmount
Number
是
实际结款金额
remark
String
否
收款备注
attachment
Object[]
否
收款附件
 id
Number
是
附件Id
 url
string
是
附件url
 originalFileName
string
是
附件原始文件名
details
Object[]
否
收款明细（最多1000条）
seq
Number
是
序号
receivableOrderId
Number
否
应收单id 和 应收单号 二选一 必须填写一个
receivableOrderNo
string
否
应收单号 和应收单id二选一 必须填写一个
settlementAmount
Number
是
结款金额
remark
string
否
明细备注

2.3返回参数
参数名
类型
描述
id
Number
收款单ID

2.4错误代码
错误码
描述
0000001
收款单ID和收款单号不能同时为空
0000001
收款单号【{0}】不存在

请求参数示例：
{
    "code": "SK202601300001",
    "receiveDate": "2026-01-30 14:30:00",
    "type": 1,
    "mode": "银行转账",
    "customerId": 1001,
    "realReceiveAmount": 12000.00,
    "realSettlementAmount": 12000.00,
    "remark": "更新收款备注",
    "details": [
        {
            "seq": 1,
            "receivableOrderId": 10002,
            "settlementAmount": 12000.00
        }
    ]
}

删除收款单
1.功能描述
外部系统通过此接口删除收款单
2.接口定义
2.1请求地址：POST /api/dytin/external/receipt/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
收款单ID 与 收款单编号 二选一
code
String
否
收款单编号 与收款单ID二选一

2.3返回参数
参数名
类型
描述
id
Number
收款单ID

请求参数示例：
{
    "id": 20001
}

查询收款单列表
1.功能描述
外部系统通过此接口分页查询收款单列表
2.接口定义
2.1请求地址：POST /api/dytin/external/receipt/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
收款单ID
type
Integer
否
收款类型
receiveDateStart
String
否
收款日期-起始
receiveDateEnd
String
否
收款日期-结束
code
String
否
收款单号（模糊查询）
remark
String
否
收款备注（模糊查询）
mode
String
否
结算方式（模糊查询）
customerId
Number
否
结算客户ID
customerName
String
否
客户名称（模糊查询）
createdAtStart
String
否
创建时间-起始
createdAtEnd
String
否
创建时间-结束
updatedAtStart
String
否
更新时间-起始
updatedAtEnd
String
否
更新时间-结束
page

Object
否

不传 默认每页 10条数据 "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50} 每页 50条

请求参数示例：
{
    "customerName": "测试",
    "receiveDateStart": "2026-01-30 00:00:00"
}

2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
返回信息
msg
String
返回消息
reference



data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
参数名
类型
描述
id
Number
收款单ID
code
String
收款单号
receiveDate
String
收款日期 
type
Integer
收款类型（1-销售收款 2-预收款 3-其他收款）
mode
String
结算方式
customerId
Number
结算客户ID
customerName
String
结算客户名称
contact
String
联系人
contactNumber
String
联系电话
contactAddress
String
联系地址
realReceiveAmount
Number
实际收款金额
advanceAmount
Number
预收
discountAmount
Number
折让
realSettlementAmount
Number
实际结款金额
remark
String
收款备注
creatorId
Number
创建人id
creatorName
String
创建人名称
updatorId
Number
更新人id
updatorName
String
更新人名称
createdAt
String
创建时间
updatedAt
String
更新时间
attachment
Object[]
收款附件
 id
Number
附件Id
 url
string
附件url
 originalFileName
string
附件原始文件名

🎯 销售工作台
客户账本查询
1.功能描述
外部系统通过此接口查询销售工作台的客户账本信息，包括客户基础信息和财务相关字段，
2.接口定义
2.1请求地址：POST /api/dytin/external/querySaleWorkbenchCustomerLedger
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
客户ID
code
String
否
客户编号（模糊查询）
name
String
否
客户名称（模糊查询）
fullName
String
否
客户全称（模糊查询）
contact
String
否
客户联系人（模糊查询）
phone
String
否
客户联系电话（模糊查询）
address
String
否
客户联系地址（模糊查询）
createdAtStart
String
否
客户创建时间-起始
createdAtEnd
String
否
客户创建时间-结束
updatedAtStart
String
否
客户更新时间-起始
updatedAtEnd
String
否
客户更新时间-结束
page
Integer
否
页码（从1开始）
pageSize
Integer
否
每页大小
orders
Object[]
否
排序条件

2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
返回信息
msg
String
返回消息
reference



data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
字段名
类型
描述
id
Number
客户ID
orgId
Number
工厂ID
code
String
客户编号
name
String
客户名称
fullName
String
客户全称
contact
String
联系人
phone
String
联系电话
address
String
联系地址
receivableDays
Integer
收款期限
updatorId
Number
更新人ID
updatedAt
String
更新时间
creatorId
Number
创建人ID
createdAt
String
创建时间
creatorName
String
创建人名称
updatorName
String
更新人名称
responsibleUserIds
Object[]
负责人ID列表
responsibleGroupIds
Object[]
负责部门ID列表
responsibleUsers
Object[]
负责人列表
responsibleGroups
Object[]
负责部门列表
contractAmount
Number
合同中金额
pendingShipmentAmount
Number
待发货金额
pendingInvoiceAmount
Number
待开票金额
pendingReceiveAmount
Number
待收款金额
receivedAmount
Number
已收款金额

🎯 应付单
创建应付单
1.功能描述
外部系统通过此接口创建应付单，支持最多1000行应付明细。其中应付明细，仅应付类型为应付时支持填写
2.接口定义
2.1请求地址：POST /api/dytin/external/payable/add
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
orderNo
string
否
应付单号
receiptDate 
string
是
开票日期 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
payableType
Number
是
10-采购应付, 20-其它应付
vendorId
Number
否
结算供应商ID (与vendorShortName二选一)
vendorShortName
string
否
结算供应商名称 (与vendorId二选一)
bizUserId
Number
否
采购员id 和 采购员名称二选一
bizUserName
string
否
采购员名称
receiptAmount
Number
否
开票金额（关联了采购明细的时候不支持填写，用明细里的开票数量+单价计算；其他类型的单据手动填写）
paymentWay
string
否
付款方式
paymentEndDate
string
否
付款到期日 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
receiptNo
string
否
发票号
advance
Number
否
使用预付
roundOff
Number
否
抹零：默认 0
discount
Number
否
整单折扣 % 默认 100 整数位最多四位，小数位最多两位
contactName
string
否
联系人
contactNumber
string
否
联系电话
contactAddr
string
否
联系地址
remark
string
否
备注
attachment
object[]
否
应付附件(需要先调用上传附件接口，根据接口返回值来传参)
 id
Number
是
附件Id
 url
string
是
附件url
 originalFileName
string
是
附件原始文件名
detail
object[]
否
应付明细 明细编辑时为全删全增
seq
string
是
序号
bizType
Number
是
应付类别 10-发货 20-退货 30-其他
receiptQty
Number
是
开票数量
unitPrice
Number
否
单价：默认0
discount
Number
否
折扣 % ：默认100， 整数位最多四位，小数位最多两位
taxRate
Number
否
税率 %：默认0
remark
String
否
明细备注
stockOrderNo
String
否
库存单编号 与 库存单ID 二选一 必须填写其中一个
stockOrderDetailSeq
String
否
库存单明细序号 与 库存单明细ID 二选一 必须填写其中一个
stockOrderId
Number
否
库存单ID 与 库存单编号 二选一 必须填写其中一个 
stockOrderDetailId
Number
否
库存单 明细ID 与 库存单 明细序号 二选一 必须填写其中一个

请求参数示例：
{
  "orderNo": "YF20260204001",
  "receiptDate": "2026-02-04 10:00:00",
  "payableType": 10,
  "vendorId": 1001,
  "vendorShortName": "测试供应商",
  "bizUserId": 2001,
  "bizUserName": "采购员张三",
  "receiptAmount": 50000.00,
  "paymentWay": "银行转账",
  "paymentEndDate": "2026-03-04 23:59:59",
  "receiptNo": "INV20260204001",
  "advance": 5000.00,
  "roundOff": 0.50,
  "discount": 100,
  "contactName": "李四",
  "contactNumber": "13800138000",
  "contactAddr": "上海市浦东新区XX路XX号",
  "remark": "本月采购应付",
  "attachment": [
    {
      "id": 10001,
      "url": "https://example.com/files/contract.pdf",
      "originalFileName": "采购合同.pdf"
    }
  ],
  "detail": [
    {
      "seq": 1,
      "bizType": 10,
      "receiptQty": 100,
      "unitPrice": 200.00,
      "discount": 95,
      "taxRate": 13,
      "remark": "产品A采购",
      "stockOrderNo": "RK20260204001",
      "stockOrderDetailSeq": 1,
      "stockOrderId": 5001,
      "stockOrderDetailId": 5101
    },
    {
      "seq": 2,
      "bizType": 10,
      "receiptQty": 50,
      "unitPrice": 300.00,
      "discount": 100,
      "taxRate": 13,
      "remark": "产品B采购",
      "stockOrderNo": "RK20260204001",
      "stockOrderDetailSeq": 2,
      "stockOrderId": 5001,
      "stockOrderDetailId": 5102
    }
  ]
}

2.3返回参数
参数名
类型
描述
id
Number
应付单ID
orderNo
string
应付单号


编辑应付单
1.功能描述
外部系统通过此接口编辑应付单，支持id和orderNo二选一方式指定要编辑的应付单。支持最多1000行应付明细。其中应付明细，仅应付类型为采购应付时支持填写
2.接口定义
2.1请求地址：POST /api/dytin/external/payable/edit
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
应付单ID（与orderNo二选一必填）
orderNo
string
否
应付单号
receiptDate 
string
是
开票日期 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
payableType
Number
是
10-采购应付, 20-其它应付
vendorId
Number
否
结算供应商ID (与vendorShortName二选一)
vendorShortName
string
否
结算供应商名称 (与vendorId二选一)
bizUserId
Number
否
采购员id 和 采购员名称二选一
bizUserName
string
否
采购员名称
receiptAmount
Number
否
开票金额（关联了采购明细的时候不支持填写，用明细里的开票数量+单价计算；其他类型的单据手动填写）
paymentWay
string
否
付款方式
paymentEndDate
string
否
付款到期日 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
receiptNo
string
否
发票号
advance
Number
否
使用预付
roundOff
Number
否
抹零：默认 0
discount
Number
否
整单折扣 % 默认 100
contactName
string
否
联系人
contactNumber
string
否
联系电话
contactAddr
string
否
联系地址
remark
string
否
备注
attachment
object[]
否
应付附件(需要先调用上传附件接口，根据接口返回值来传参)
 id
Number
是
附件Id
 url
string
是
附件url
 originalFileName
string
是
附件原始文件名
detail
object[]
否
应付明细 仅应付类型为采购应付时支持填写明细
seq
string
是
序号
bizType
Number
是
应付类别 10-付货 20-退货 30-其他
receiptQty
Number
是
开票数量
unitPrice
Number
否
单价：默认0
discount
Number
否
折扣 % ：默认100， 整数位最多四位，小数位最多两位
taxRate
Number
否
税率 %：默认0
remark
String
否
明细备注
stockOrderNo

String
否
库存单编号 与 库存单ID 二选一 必须填写其中一个 
仅支持填写 采购入库，采购退货出库类型的单据号，且库存单的供应商与上面填写的供应商信息一致
产品和采购订单信息从库存单据中带出
stockOrderDetailSeq
String
否
库存单明细序号 与 库存单明细ID 二选一 必须填写其中一个
stockOrderId
Number
否
库存单编号 与 库存单ID 二选一 必须填写其中一个 
仅支持填写 采购入库，采购退货出库类型的单据号，且库存单的供应商与上面填写的供应商信息一致
产品和采购订单信息从库存单据中带出
stockOrderDetailId
Number
否
库存单 明细ID 与 库存单 明细序号 二选一 必须填写其中一个

请求参数示例：


2.3返回参数
参数名
类型
描述
id
Number
应付单ID
orderNo
string
应付单号

删除应付单
1.功能描述
外部系统通过此接口删除应付单，需传入应付单ID。
2.接口定义
2.1请求地址：POST /api/dytin/external/payable/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
应付单ID（与orderNo二选一）
orderNo
string
否

应付单号 （与id二选一必填，如果传入orderNo会转换为id；如果同时传入id和orderNo，以id为准）

请求参数示例：
{
    "id": 10001
}

2.3返回参数
参数名
类型
描述
id
Number
应付单ID


查询应付单详情
1.功能描述
外部系统通过此接口查询应付单详情信息，包括应付明细和附件等信息。
2.接口定义
2.1请求地址：POST /api/dytin/external/payable/order/detail
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
应付单ID（与orderNo二选一）
orderNo
string
否

应付单号 （与id二选一必填，如果传入orderNo会转换为id；如果同时传入id和orderNo，以id为准）

请求参数示例：
{
    "id": 10001
}

2.3返回参数
参数名
类型
描述
id
Number
应付单ID
orderNo
String
应付单号
receiptDate
String
开票日期
payableType
Integer
应付类型 
payableTypeDesc
String
应付类型描述
vendorId
Number
结算供应商ID
vendorName
String
供应商名称
vendorCode
String
供应商编码
vendor
PayableVendorVO
结算供应商对象
bizUserId
Number
采购员
bizUserName
String
采购员名称
receiptAmount
Number
开票金额
paymentWay
String
付款方式
paymentEndDate
String
付款到期日
receiptNo
String
发票号
advance
Number
使用预付
roundOff
Number
抹零
discount
Number
整单折扣%
realPayableAmount
Number
实际应付金额
contactName
String
联系人
contactNumber
String
联系电话
contactAddr
String
联系地址
remark
String
应付备注
settlementFinishDay
String
结清日期
overdueStatus
Integer
逾期状态
overdueStatusDisplay
String
逾期状态描述
overdueDays
Integer
逾期天数
settlementAmount
Number
累计结款金额
pendingPaymentAmount
Number
待付款金额
attachment
Object[]
应付附件
 id
Number
附件Id
 url
string
附件url
 originalFileName
string
附件原始文件名
detail
Object[]
应付明细
Id
Number
应付明细id
seq
string
序号
bizType
Number
应付类别
bizTypeDesc
string
应付类别描述
productCode
string
产品编号 
productId
Number
产品id 
productName
string
产品名称
productSpec
string
产品规格
expectReceiptQty
Number
应开票数量
totalReceiptQty
Number
累计开票数量
pendingReceiptQty
Number
待开票数量
receiptQty
Number
开票数量
unitPrice
Number
单价
discount
Number
折扣 %
discountAmount
Number
折扣额
taxRate
Number
税率 %
taxUnitPrice
Number
含税单价
quotationAmount
Number
报价金额
totalAmount
Number
不含税金额
taxAmount
Number
税额
taxTotalAmount
Number
含税金额
remark
String
明细备注
stockOrderId
Number
库存单ID 
stockOrderNO
String
库存单号 
stockOrderDetailId
Number
库存单明细ID
purchaseOrderId
Number
采购订理单ID
purchaseOrderNO
String
采购订单编号
purchaseOrderDetailId
Number
采购订单明细ID 

查询应付单列表
1.功能描述
外部系统通过此接口分页查询应付单列表
2.接口定义
2.1请求地址：POST /api/dytin/external/payable/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
应付单ID
payableType
Integer
否
应付类型
receiptDateStart
String

否
开票日期-起始 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
receiptDateEnd
String
否
开票日期 -结束 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
orderNo
String
否
应付单号（模糊查询）
remark
String
否
应付备注（模糊查询）
vendorId
Number
否
结算供应商ID
vendorShortName
String
否
供应商名称（模糊查询）
createdAtStart
String
否
创建时间-起始 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
createdAtEnd
String
否
创建时间-结束 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
updatedAtStart
String
否
更新时间-起始 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
updatedAtEnd
String
否
更新时间-结束 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
page

Object
否
不传 默认每页 10条数据 "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50} 每页 50条

2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
返回信息
msg
String
返回消息
reference



data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
参数名
类型
描述
id
Number
应付单ID
orderNo
String
应付单号
receiptDate
String
开票日期
payableType
Integer
应付类型 
payableTypeDesc
String
应付类型描述
vendorId
Number
结算供应商ID
vendorName
String
供应商名称
vendorCode
String
供应商编码
bizUserId
Number
采购员
bizUserName
String
采购员名称
receiptAmount
Number
开票金额
paymentWay
String
付款方式
paymentEndDate
String
付款到期日
receiptNo
String
发票号
advance
Number
使用预付
roundOff
Number
抹零
discount
Number
整单折扣%
realPayableAmount
Number
实际应付金额
contactName
String
联系人
contactNumber
String
联系电话
contactAddr
String
联系地址
remark
String
应付备注
attachment
Object[]
应付附件
settlementAmount
Number
累计结款金额
pendingPaymentAmount
Number
待付款金额
settlementFinishDay
String
结清日期
overdueStatus
Integer
逾期状态
overdueDays
Integer
逾期天数


🎯 付款单
创建付款单
1.功能描述
外部系统通过此接口创建付款单，支持最多1000条付款明细。仅采购付款支持填写明细。供应商信息可通过vendorId或vendorName二选一方式传入。
2.接口定义
2.1请求地址：POST /api/dytin/external/payment/add
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
code
String
否
付款单号
payDate
String
是
付款日期 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
type
Integer
是
付款类型（1-采购付款 2-预付款 3-其他付款）
mode
String
否
结算方式
vendorId
Number

否
结算供应商ID（与vendorShortName二选一必填）
vendorShortName

String
否
结算供应商名称（与vendorId二选一必填）
contact
String
否
联系人
contactNumber
String
否
联系电话
contactAddress
String
否
联系地址
realPayAmount
Number
是
实际付款金额
advanceAmount
Number
否
预付
discountAmount
Number
否
折让
realSettlementAmount
Number
是
实际结款金额：实际付款金额+使用预付+折让
remark
String
否
付款备注
attachment
Object[]
否
付款附件
 id
Number
是
附件Id
 url
string
是
附件url
 originalFileName
string
是
附件原始文件名
details
Object[]
否
付款明细（最多1000条）
seq
Number
是
序号
payableOrderId
Number
否
 应付单id 与应付单号二选一，必填其一
payableOrderNo
string
否
 应付单id 与应付单号二选一，必填其一
settlementAmount
Number
是
结款金额
remark
string
否
明细备注

2.3返回参数
参数名
类型
描述
id
Number
付款单ID

2.4 请求参数示例：
{
    "receiveDate": "2026-01-30 14:30:00",
    "type": 1,
    "mode": "银行转账",
    "customerName": "测试供应商",
    "contact": "张三",
    "contactNumber": "13800138000",
    "realReceiveAmount": 10000.00,
    "realSettlementAmount": 10000.00,
    "remark": "测试付款单",
    "details": [
        {
            "seq": 1,
            "receivableOrderId": 10001,
            "settlementAmount": 10000.00,
            "remark": "结清应付单"
        }
    ]
}

编辑付款单
1.功能描述
外部系统通过此接口编辑付款单，支持最多1000条付款明细。仅采购付款支持填写明细。供应商信息可通过vendorId或vendorShortName二选一方式传入。
2.接口定义
2.1请求地址：POST /api/dytin/external/payment/edit
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
付款单ID（与code二选一必填）
code
String
否
付款单号（与id二选一必填，如果传入code会转换为id；如果同时传入id和code，以id为准）
payDate
String
是
付款日期 格式"yyyy-MM-dd HH:mm:ss" 例如 "2025-07-22 00:00:00"
type
Integer
是
付款类型（1-采购付款 2-预付款 3-其他付款）
mode
String
否
结算方式
vendorId
Number
否

结算供应商ID（与vendorShortName二选一必填）
vendorShortName
String
否
结算供应商ID（与vendorId二选一必填）
contact
String
否
联系人
contactNumber
String
否
联系电话
contactAddress
String
否
联系地址
realPayAmount
Number
是
实际付款金额
advanceAmount
Number
否
预付
discountAmount
Number
否
折让
realSettlementAmount
Number
是
实际付款金额
remark
String
否
付款备注
attachment
Object[]
否
付款附件
 id
Number
是
附件Id
 url
string
是
附件url
 originalFileName
string
是
附件原始文件名
details
Object[]
否
付款明细（最多1000条）
seq
Number
是
序号
payableOrderId
Number
否
应付单id 和 应付单号 二选一 必须填写一个
payableOrderNo
string
否
应付单号 和应付单id二选一 必须填写一个
settlementAmount
Number
是
结款金额
remark
string
否
明细备注

2.3返回参数
参数名
类型
描述
id
Number
付款单ID

2.4错误代码
错误码
描述
0000001
付款单ID和付款单号不能同时为空
0000001
付款单号【{0}】不存在

请求参数示例：


删除付款单
1.功能描述
外部系统通过此接口删除付款单
2.接口定义
2.1请求地址：POST /api/dytin/external/payment/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
付款单ID 与 付款单编号 二选一
code
String
否
付款单编号 与付款单ID二选一

2.3返回参数
参数名
类型
描述
id
Number
付款单ID

请求参数示例：
{
    "id": 20001
}

查询付款单列表
1.功能描述
外部系统通过此接口分页查询付款单列表
2.接口定义
2.1请求地址：POST /api/dytin/external/payment/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
付款单ID
type
Integer
否
付款类型
payDateStart
String
否
付款日期-起始
payDateEnd
String
否
付款日期-结束
code
String
否
付款单号（模糊查询）
remark
String
否
付款备注（模糊查询）
mode
String
否
结算方式（模糊查询）
vendorShortName
Number
否
结算供应商ID
vendorShortName
String
否
供应商名称（模糊查询）
createdAtStart
String
否
创建时间-起始
createdAtEnd
String
否
创建时间-结束
updatedAtStart
String
否
更新时间-起始
updatedAtEnd
String
否
更新时间-结束
page

Object
否

不传 默认每页 10条数据 "page":{"pageNum":1,"pageSize":10}
例如 "page":{"pageNum":1,"pageSize":50} 每页 50条

请求参数示例：


2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
返回信息
msg
String
返回消息
reference



data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
参数名
类型
描述
id
Number
付款单ID
code
String
付款单号
receiveDate
String
付款日期 
type
Integer
付款类型（1-采购付款 2-预付款 3-其他付款）
mode
String
结算方式
vendorId
Number
结算供应商id
vendorName
String
结算供应商全称
vendorShortName
String
结算供应商名称
vendorCode
String
结算供应商编号
contact
String
联系人
contactNumber
String
联系电话
contactAddress
String
联系地址
realReceiveAmount
Number
实际付款金额
advanceAmount
Number
预付
discountAmount
Number
折让
realSettlementAmount
Number
实际结款金额
remark
String
付款备注
creatorId
Number
创建人id
creatorName
String
创建人名称
updatorId
Number
更新人id
updatorName
String
更新人名称
createdAt
String
创建时间
updatedAt
String
更新时间
attachment
Object[]
付款附件
 id
Number
附件Id
 url
string
附件url
 originalFileName
string
附件原始文件名
details
Object[]
付款明细（最多1000条）
id
Number
明细id
seq
Number
序号
payableOrderId
Number
应付单id
payableInfo
Object
采购应付信息
settlementAmount
Number
结款金额
remark
string
明细备注

🎯 采购工作台
供应商账本查询
1.功能描述
外部系统通过此接口查询采购工作台的供应商账本信息，包括供应商基础信息和财务相关字段，
2.接口定义
2.1请求地址：POST /api/dytin/external/queryPurchaseWorkbenchVendorLedger
通信协议：HTTPS
请求方式：POST
请求header参数：
X-AUTH，值就是登录接口拿到的token。
Content-Type:application/json
2.2请求参数
参数名
类型
是否必填
描述
id
Number
否
供应商ID
code
String
否
供应商编号（模糊查询）
name
String
否
供应商名称（模糊查询）
fullName
String
否
供应商全称（模糊查询）
contact
String
否
供应商联系人（模糊查询）
phone
String
否
供应商联系电话（模糊查询）
address
String
否
供应商联系地址（模糊查询）
createdAtStart
String
否
供应商创建时间-起始
createdAtEnd
String
否
供应商创建时间-结束
updatedAtStart
String
否
供应商更新时间-起始
updatedAtEnd
String
否
供应商更新时间-结束
page
Integer
否
页码（从1开始）
pageSize
Integer
否
每页大小
orders
Object[]
否
排序条件

2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
返回信息
msg
String
返回消息
reference



data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]：
字段名
类型
描述
id
Number
供应商ID
orgId
Number
工厂ID
vendorCode
String
供应商编号
shortName
String
供应商名称
vendorName
String
供应商全称
contactPerson
String
联系人
contactPhone
String
联系电话
contactAddress
String
联系地址
payableDays
Integer
付款期限
updatorId
Number
更新人ID
updatedAt
String
更新时间
creatorId
Number
创建人ID
createdAt
String
创建时间
creator
Object
创建人信息
updator
Object
更新人信息
purchaseAmount
Number
合同中金额
pendingReceiveAmount
Number
待发货金额
pendingInvoiceAmount
Number
待开票金额
pendingPaymentAmount
Number
待付款金额
paidAmount
Number
已付款金额

🎯 待开票列表
  待开票列表查询
  1.功能描述
  外部系统通过此接口查询采购工作台和销售工作台的待开票列表。
  2.接口定义
  2.1请求地址：POST /api/dytin/external/queryInvoicingList
  通信协议：HTTPS
  请求方式：POST
  请求header参数：
  X-AUTH，值就是登录接口拿到的token。
  Content-Type:application/json
  2.2请求参数
参数名
类型
是否必填
描述
vendorIds
ListLong
否
供应商ID列表（采购侧）
customerIds
ListLong
否
客户ID列表（销售侧）
bizTypes
ListInteger
否
业务类型列表。默认值：[12, 25, 15, 22]12-采购入库, 25-采购退货15-销售退货入库, 22-销售出库
stockOrderStatuses
ListInteger
否
库存单状态列表0-待审批, 1-已审批
bizAtStart
String
否
业务时间-起始格式：yyyy-MM-dd HH:mm:ss
bizAtEnd
String
否
业务时间-结束格式：yyyy-MM-dd HH:mm:ss
orderNo
String
否
订单号（模糊查询）
orderTypes
ListInteger
否
订单类型列表10-入库单, 20-出库单
remark
String
否
备注（模糊查询）
creatorIds
ListLong
否
创建人ID列表
createdAtStart
String
否
创建时间-起始格式：yyyy-MM-dd HH:mm:ss
createdAtEnd
String
否
创建时间-结束格式：yyyy-MM-dd HH:mm:ss
updatorIds
ListLong
否
更新人ID列表
updatedAtStart
String
否
更新时间-起始格式：yyyy-MM-dd HH:mm:ss
updatedAtEnd
String
否
更新时间-结束格式：yyyy-MM-dd HH:mm:ss
productIds
ListLong
否
产品ID列表
productCode
String
否
产品编码（模糊查询）
productName
String
否
产品名称（模糊查询）
productSpec
String
否
产品规格（模糊查询）
excludeZeroRestInvoiceQty
Boolean
否
是否排除剩余开票数量为0的记录true-排除, false-不排除

page
Object
否
分页信息
page.pageNum
Integer
否
页码，默认值：1
page.pageSize
Integer
否
每页数量，默认值：10，最大值：100
orders
ListObject
否
排序信息
orders[].orderBy
String
否
排序字段：biz_at（业务时间）、seq（序号）、order_no（订单号）、created_at（创建时间）、updated_at（更新时间）等
orders[].sort
String
否
排序方式：asc（升序）、desc（降序）

  2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Object
返回信息
msg
String
返回消息
reference



  data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

  data[]：
参数名
类型
描述
master
Object
库存单据信息
id
Long
单据ID
orderNo
String
单号
orderType
Integer
单据类型
orderTypeDisplay
String
单据类型展示
bizType
Integer
业务类型
bizTypeDisplay
String
业务类型展示
bizAt
String
业务时间（收货时间、出货时间）
warehouseId
Long
仓库ID
warehouseSimpleVO
WarehouseSimpleVO
仓库信息对象
customerVO
CustomerVO
客户信息对象（销售侧）
vendorVO
VendorVO
供应商信息对象（采购侧）
orderStatus
Integer
单据状态
orderStatusDisplay
String
单据状态展示
approverId
Long
审批人ID
approverName
String
审批人名称
approvedAt
String
审批时间
applierId
Long
申请人ID
applierName
String
申请人名称
appliedAt
String
申请时间
remark
String
备注
stockOriginType
Integer
来源单据类型
stockOriginTypeDesc
String
来源单据类型描述
originIdList
Array
来源单据ID列表
originCodeList
Array
来源单据编号列表
originCodeListDisplay
String
来源单据展示
createdAt
String
创建时间
creatorName
String
创建人名称
creatorId
Long
创建人ID
updatedAt
String
更新时间
updatorName
String
更新人名称
updatorId
Long
更新人ID
detail
Object
库存明细信息
seq
Integer
明细序号
detailId
Long
库存子表ID
qty
Number
单据数量
changeQty
Number
库存变更数量
productId
Long
产品ID
productCode
String
产品编号
productName
String
产品名称
productSpec
String
产品规格
unitId
Long
单位ID
unitName
String
单位名称
originDetailId
Long
来源单据明细ID
associatedBizOrderId
Long
关联业务单据ID
associatedBizOrderType
Integer
关联业务单据类型
associatedBizOrderCode
String
关联业务单据编号
originDetailDisplayInfo
String
来源明细展示信息
saleOrderCode
String
销售订单编号
totalInvoiceQty
Number
应开票数量
realInvoiceQty
Number
累计开票数量
restInvoiceQty
Number
待开票数量
restInvoiceAmount
Number
待开票金额
detailWarehouseId
Long
仓库ID
detailWarehouse
WarehouseSimpleVO
明细仓库信息对象
saleInfo
Object
销售明细信息（销售侧）
unitPrice
Number
单价
discount
Number
折扣%
taxRate
Number
税率%
remark
String
销售明细备注
orderNo
String
销售订单编号
contractNo
String
销售订单合同编号
purchaseInfo
Object
采购明细信息（采购侧）
unitPrice
Number
单价
discount
Number
折扣%
taxRate
Number
税率%
remark
String
采购明细备注
orderNo
String
采购订单编号

🎯 质量管理
创建检验项
1.功能描述
通过该接口可在小工单系统创建检验项信息
2.接口定义
2.1请求地址：/api/dytin/external/inspectionItem/create
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
code
string
否
编号（长度不能超过 120 个字符）
name
string
是
名称（不能为空，长度不能超过 120 个字符）
inspectionType
integer
是
类型（1 - 数值 2 - 文本 3 - 单选框）
standardValue
string
条件必填
标准值（类型为数值时不能为空）
upperLimit
string
条件必填
上限值（类型为数值时需存在上限或下限中的一个）
lowerLimit
string
条件必填
下限值（类型为数值时需存在上限或下限中的一个）
optionValueList
object[]
条件必填
选项值（类型为单选框时不能为空）
defaultValue：1 表示该选项为默认值，且只能有一个默认值，
qualified：1 表示该选项为合格，
seq：为数字， 不能重复
示例：
"optionValueList": [
  {
    "optionValue": "选项值 1",    "defaultValue": 1,    "seq": 0,    "qualified": 1  },
  {
    "optionValue": "选项值 2",    "defaultValue": 0,    "seq": 1,    "qualified": 1  }
],
required
integer
否
是否必填（0 否 1 是）
inspectionTools
string
否
检验工具（长度不能超过 1500 个字符）
inspectionContent
string
否
检验内容（长度不能超过 1500 个字符）
customFieldValues
object[]
否
自定义字段值（不支持关联属性）
示例：
[{"name":"名称 1","value":"文本 - LQ 产品 01"},{"name":"数字型 2","value":174237},{"name":"名称 3","value":"1"}]
如果自定义字段类型是关联对象，value 传值设定根据关联的不同对象类型，分别为:
用户对象 value 传名称
单位对象 value 传名称
产品对象 value 传产品编号
工艺路线 value 传工艺路线名称
工序 value 传工序编号
不良品 value 传不良品编号
部门 value 传部门编号
客户 value 传客户编号
供应商 value 传供应商编号
仓库 value 传供仓库编号
设备 value 传供设备编号
检验规范 value 传供检验规范编号
检验项 value 传供检验项编号

2.3返回参数
参数名
类型
描述
code
string
成功与否 成功：01000000 ，其他为失败， 
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
检验项id

2.4错误代码
01000002
自定义字段名称不能为空
自定义字段【XXX】不存在"
自定义字段【XXX】值有问题：【XXX】
编号长度不能超过120个字符
名称不能为空
名称长度不能超过120个字符
检验项类型传参有误
检验项创建总数已达到10000条上限，请清理数据后重新创建
标准值不能为空
上限值与下限值必须填写一个
上限值必须大于等于下限值
标准值必须大于等于下限值，小于等于上限值
标准值，上限值和下限值必须为数字
检验项编号重复
选项值不能为空
选项值只能有一个默认值
检验项选项值序号重复，请检查后重新提交
检验工具长度不能超过1500个字符
检验内容长度不能超过1500个字符

更新检验项
1.功能描述
通过该接口可在小工单系统更新检验项信息
❗️此接口为覆盖更新逻辑，未传值的自定义字段内容将会被置空
2.接口定义
2.1请求地址：/api/dytin/external/inspectionItem/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
code
string
是
编号（长度不能超过 120 个字符）
name
string
是
名称（不能为空，长度不能超过 120 个字符）
inspectionType
integer
是
类型（1 - 数值 2 - 文本 3 - 单选框）
standardValue
string
条件必填
标准值（类型为数值时不能为空）
upperLimit
string
条件必填
上限值（类型为数值时需存在上限或下限中的一个）
lowerLimit
string
条件必填
下限值（类型为数值时需存在上限或下限中的一个）
optionValueList
object[]
条件必填
选项值（类型为单选框时不能为空）
defaultValue：1 表示该选项为默认值，且只能有一个默认值，
qualified：1 表示该选项为合格，
seq：为数字， 不能重复
示例：
"optionValueList": [
  {
    "optionValue": "选项值 1",    "defaultValue": 1,    "seq": 0,    "qualified": 1  },
  {
    "optionValue": "选项值 2",    "defaultValue": 0,    "seq": 1,    "qualified": 1  }
],
required
integer
否
是否必填（0 否 1 是）
inspectionTools
string
否
检验工具（长度不能超过 1500 个字符）
inspectionContent
string
否
检验内容（长度不能超过 1500 个字符）
customFieldValues
object[]
否
自定义字段值（不支持关联属性）
示例：
[{"name":"名称 1","value":"文本 - LQ 产品 01"},{"name":"数字型 2","value":174237},{"name":"名称 3","value":"1"}]
如果自定义字段类型是关联对象，value 传值设定根据关联的不同对象类型，分别为:
用户对象 value 传名称
单位对象 value 传名称
产品对象 value 传产品编号
工艺路线 value 传工艺路线名称
工序 value 传工序编号
不良品 value 传不良品编号
部门 value 传部门编号
客户 value 传客户编号
供应商 value 传供应商编号
仓库 value 传供仓库编号
设备 value 传供设备编号
检验规范 value 传供检验规范编号
检验项 value 传供检验项编号

2.3返回参数
参数名
类型
描述
code
string
成功与否 成功：01000000 ，其他为失败， 
data
boolean
返回成功（true）或者失败（false）
msg
string
返回消息

2.4错误代码
01000002
自定义字段名称不能为空
自定义字段【XXX】不存在"
自定义字段【XXX】值有问题：【XXX】
编号长度不能超过120个字符
名称不能为空
名称长度不能超过120个字符
检验项类型传参有误
检验项创建总数已达到10000条上限，请清理数据后重新创建
标准值不能为空
上限值与下限值必须填写一个
上限值必须大于等于下限值
标准值必须大于等于下限值，小于等于上限值
标准值，上限值和下限值必须为数字
编号不能为空
选项值不能为空
选项值只能有一个默认值
检验项选项值序号重复，请检查后重新提交
检验工具长度不能超过1500个字符
检验内容长度不能超过1500个字符

查询检验项
1.功能描述
通过该接口可在小工单系统查询检验项信息
2.接口定义
2.1请求地址：/api/dytin/external/inspectionItem/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必参
传参示例
字段解释
code
string
否
"code": "编号"
模糊匹配检验编号（长度不能超过 120 个字符）
name
string
否
"code": "名称"
模糊匹配检验名称（不能为空，长度不能超过 120 个字符）
inspectionType
integer[]
否
"inspectionType": [1]
类型（1 - 数值 2 - 文本 3 - 单选框）
required
integer[]
否
"required": [1]
是否必填（0 否 1 是）
inspectionTools
string
否
"inspectionTools": "工具"
模糊匹配检验工具（长度不能超过 1500 个字符）
createdAtGte
string
是

"createdAtGte":"2021-09-30 23:59:59"
创建时间大于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
createdAtLte
string
是

"createdAtLte":"2021-09-30 23:59:59"
创建时间小于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
updatedAtGte
string
否
"updatedAtGte":"2021-09-30 23:59:59"
更新时间大于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
updatedAtLte
string
否
"updatedAtLte": "2021-09-30 23:59:59"
更新时间小于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
page

Object
否
"page":{"pageNum":1,"pageSize":10}
不传 默认每页 10条数据,最大限度每页100条数据 "page":{"pageNum":1,"pageSize":10}
customFieldSearchConditions

Object

否
"customFieldSearchConditions":
[
 {
 "name": "字段名",
 "queryOperator": "equal",
 "fieldValue": [
 "测试"
 ]
 }
 ],
 自定义字段查询 具体传参说明请看文档描述 「自定义字段传参说明」

2.3返回参数
参数名
类型
描述
code
string
成功与否 成功：01000000 ，其他为失败， 
data
object
返回数据
msg
string
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "process17235164190354": { }}}

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]
参数名
字段类型
字段解释
id
number
检验项 id
code
string
编号
name
string
名称
inspectionType
integer
类型（1 - 数值 2 - 文本 3 - 单选框）
standardValue
string
标准值（类型为数值时不能为空）
upperLimit
string
上限值（类型为数值时需存在上限或下限中的一个）
lowerLimit
string
下限值（类型为数值时需存在上限或下限中的一个）
optionValueList
object[]
选项值（类型为单选框时不能为空）
defaultValue：1 表示该选项为默认值，且只有一个默认值，
qualified：1 表示该选项为合格，
seq：为数字， 不会重复
示例：
"optionValueList": [
  {
    "optionValue": "选项值 1",    "defaultValue": 1,    "seq": 0,    "qualified": 1  },
  {
    "optionValue": "选项值 2",    "defaultValue": 0,    "seq": 1,    "qualified": 1  }
],
required
integer
是否必填（0 否 1 是）
inspectionTools
string
检验工具（长度不能超过 1500 个字符）
inspectionContent
string
检验内容（长度不能超过 1500 个字符）
customFieldValues
object[]
自定义字段值
createdAt
String
创建时间 例如"2025-09-01 12:00:00"
creatorName
String
创建人
updatedAt
String
更新时间 例如"2025-09-01 12:00:00"
updatorName
String
更新人

2.4错误代码
1078
查询时间范围不能超过2年
01000002
创建开始时间筛选项不能为空

删除检验项
1.功能描述
通过该接口可在小工单系统批量删除检验项信息
2.接口定义
2.1请求地址：/api/dytin/external/inspectionItem/batch/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
 codes
String[]
否
编号列表；与id列表，二选一不能同时为空
ids
Number[]
否
id列表；编号列表，二选一不能同时为空

2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Number
返回 删除数量
msg
String
返回消息

请求参数示例：
{
    "codes": [
        "QID0001",
        "QID0002"
    ]
}

2.4错误代码
01000002
检验项ID列表或编号列表不能都为空

创建检验规范
1.功能描述
通过该接口可在小工单系统创建检验规范信息
2.接口定义
2.1请求地址：/api/dytin/external/inspectionSpecifications/create
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
code
string
是
编号（长度不能超过 120 个字符）
name
string
否
名称（不能为空，长度不能超过 120 个字符）
remark
integer
否
备注 （长度不能超过1500个字符）
inspectionSpecificationsItemList
objects[]
是
检验规范明细 见 下 【检验规范检验明细传参明细】
示例：
"inspectionSpecificationsItemList": [
 {
 "seq": 0,
 "code": "QID0001",
 "standardValue": null,
 "upperLimit": null,
 "lowerLimit": null,
 "optionValueList": [
 {
 "seq": 0,
 "optionValue": "无",
 "defaultValue": 0,
 "qualified": 1
 },
 {
 "seq": 1,
 "optionValue": "有",
 "defaultValue": 0,
 "qualified": 0
 }
 ],
 "required": 0,
 "inspectionTools": null,
 "inspectionContent": null,
 "customFieldValues": [
 {
 "name": 752640,
 "value": true
 }
 ]
 }
 ]
customFieldValues
object[]
否
自定义字段值（不支持关联属性）
示例：
[{"name":"名称 1","value":"文本 - LQ 产品 01"},{"name":"数字型 2","value":174237},{"name":"名称 3","value":"1"}]
如果自定义字段类型是关联对象，value 传值设定根据关联的不同对象类型，分别为:
用户对象 value 传名称
单位对象 value 传名称
产品对象 value 传产品编号
工艺路线 value 传工艺路线名称
工序 value 传工序编号
不良品 value 传不良品编号
部门 value 传部门编号
客户 value 传客户编号
供应商 value 传供应商编号
仓库 value 传供仓库编号
设备 value 传供设备编号
检验规范 value 传供检验规范编号
检验项 value 传供检验项编号

检验规范检验明细传参明细
参数名
字段类型
是否必填
字段解释
seq
integer
是
序号，不能重复
code
string
是
编号（长度不能超过 120 个字符）
standardValue
string
条件必填
标准值（类型为数值时不能为空）
upperLimit
string
条件必填
上限值（类型为数值时需存在上限或下限中的一个）
lowerLimit
string
条件必填
下限值（类型为数值时需存在上限或下限中的一个）
optionValueList
object[]
条件必填
选项值（类型为单选框时不能为空）
defaultValue：1 表示该选项为默认值，且只能有一个默认值，
qualified：1 表示该选项为合格，
seq：为数字， 不能重复
示例：
"optionValueList": [
  {
    "optionValue": "选项值 1",    "defaultValue": 1,    "seq": 0,    "qualified": 1  },
  {
    "optionValue": "选项值 2",    "defaultValue": 0,    "seq": 1,    "qualified": 1  },
  {
    "optionValue": "选项值 3",    "defaultValue": 0,    "seq": 2,    "qualified": 0  }
],
required
integer
否
是否必填（0 否 1 是）
inspectionTools
string
否
检验工具（长度不能超过 1500 个字符）
inspectionContent
string
否
检验内容（长度不能超过 1500 个字符）
customFieldValues
object[]
否
自定义字段值（不支持关联属性）
示例：
[{"name":"名称 1","value":"文本 - LQ 产品 01"},{"name":"数字型 2","value":174237},{"name":"名称 3","value":"1"}]
如果自定义字段类型是关联对象，value 传值设定根据关联的不同对象类型，分别为:
用户对象 value 传名称
单位对象 value 传名称
产品对象 value 传产品编号
工艺路线 value 传工艺路线名称
工序 value 传工序编号
不良品 value 传不良品编号
部门 value 传部门编号
客户 value 传客户编号
供应商 value 传供应商编号
仓库 value 传供仓库编号
设备 value 传供设备编号
检验规范 value 传供检验规范编号
检验项 value 传供检验项编号

2.3返回参数
参数名
类型
描述
code
string
成功与否 成功：01000000 ，其他为失败， 
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
检验规范id

2.4错误代码
01000002
编号长度不能超过120个字符
名称长度不能超过120个字符
名称不能为空
备注长度不能超过1500个字符
检验规范明细列表不能为空
检验规范编号重复
检验规范检验明细中检验项编号不存在
检验规范检验明细中检验项编号不能为空
自定义字段名称不能为空
自定义字段【XXX】不存在"
自定义字段【XXX】值有问题：【XXX】
标准值不能为空
上限值与下限值必须填写一个
上限值必须大于等于下限值
标准值必须大于等于下限值，小于等于上限值
标准值，上限值和下限值必须为数字
选项值不能为空
选项值只能有一个默认值
检验项选项值序号重复，请检查后重新提交

更新检验规范
1.功能描述
通过该接口可在小工单系统更新检验规范信息
❗️此接口为覆盖更新逻辑，未传值的自定义字段内容将会被置空
2.接口定义
2.1请求地址：/api/dytin/external/inspectionSpecifications/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
code
string
是
编号（长度不能超过 120 个字符）
name
string
是
名称（不能为空，长度不能超过 120 个字符）
remark
integer
否
备注 （名称长度不能超过1500个字符）
inspectionSpecificationsItemList
objects[]
是
【明细全删全插】检验规范明细 见 下 【检验规范检验明细传参明细】
示例：
"inspectionSpecificationsItemList": [
 {
 "seq": 0,
 "code": "QID0001",
 "standardValue": null,
 "upperLimit": null,
 "lowerLimit": null,
 "optionValueList": [
 {
 "seq": 0,
 "optionValue": "无",
 "defaultValue": 0,
 "qualified": 1
 },
 {
 "seq": 1,
 "optionValue": "有",
 "defaultValue": 0,
 "qualified": 0
 }
 ],
 "required": 0,
 "inspectionTools": null,
 "inspectionContent": null,
 "customFieldValues": [
 {
 "name": 752640,
 "value": true
 }
 ]
 }
 ]
customFieldValues
object[]
否
自定义字段值（不支持关联属性）
示例：
[{"name":"名称 1","value":"文本 - LQ 产品 01"},{"name":"数字型 2","value":174237},{"name":"名称 3","value":"1"}]
如果自定义字段类型是关联对象，value 传值设定根据关联的不同对象类型，分别为:
用户对象 value 传名称
单位对象 value 传名称
产品对象 value 传产品编号
工艺路线 value 传工艺路线名称
工序 value 传工序编号
不良品 value 传不良品编号
部门 value 传部门编号
客户 value 传客户编号
供应商 value 传供应商编号
仓库 value 传供仓库编号
设备 value 传供设备编号
检验规范 value 传供检验规范编号
检验项 value 传供检验项编号

检验规范检验明细传参明细
参数名
字段类型
是否必填
字段解释
seq
integer
是
 序号，不能重复
code
string
是
编号（长度不能超过 120 个字符）
standardValue
string
条件必填
标准值（类型为数值时不能为空）
upperLimit
string
条件必填
上限值（类型为数值时需存在上限或下限中的一个）
lowerLimit
string
条件必填
下限值（类型为数值时需存在上限或下限中的一个）
optionValueList
object[]
条件必填
选项值（类型为单选框时不能为空）
defaultValue：1 表示该选项为默认值，且只能有一个默认值，
qualified：1 表示该选项为合格，
seq：为数字， 不能重复
示例：
"optionValueList": [
  {
    "optionValue": "选项值 1",    "defaultValue": 1,    "seq": 0,    "qualified": 1  },
  {
    "optionValue": "选项值 2",    "defaultValue": 0,    "seq": 1,    "qualified": 1  },
  {
    "optionValue": "选项值 3",    "defaultValue": 0,    "seq": 2,    "qualified": 0  }
],
required
integer
否
是否必填（0 否 1 是）
inspectionTools
string
否
检验工具（长度不能超过 1500 个字符）
inspectionContent
string
否
检验内容（长度不能超过 1500 个字符）
customFieldValues
object[]
否
自定义字段值（不支持关联属性）
示例：
[{"name":"名称 1","value":"文本 - LQ 产品 01"},{"name":"数字型 2","value":174237},{"name":"名称 3","value":"1"}]
如果自定义字段类型是关联对象，value 传值设定根据关联的不同对象类型，分别为:
用户对象 value 传名称
单位对象 value 传名称
产品对象 value 传产品编号
工艺路线 value 传工艺路线名称
工序 value 传工序编号
不良品 value 传不良品编号
部门 value 传部门编号
客户 value 传客户编号
供应商 value 传供应商编号
仓库 value 传供仓库编号
设备 value 传供设备编号
检验规范 value 传供检验规范编号
检验项 value 传供检验项编号

2.3返回参数
参数名
类型
描述
code
string
成功与否 成功：01000000 ，其他为失败， 
data
boolean
返回成功（true）或者失败（false）
msg
string
返回消息

2.4错误代码
01000002
编号不能为空
检验规范不存在
编号长度不能超过120个字符
名称长度不能超过120个字符
名称不能为空
备注长度不能超过1500个字符
检验规范明细列表不能为空
检验规范编号重复
检验规范检验明细中检验项编号不存在
检验规范检验明细中检验项编号不能为空
自定义字段名称不能为空
自定义字段【XXX】不存在"
自定义字段【XXX】值有问题：【XXX】
标准值不能为空
上限值与下限值必须填写一个
上限值必须大于等于下限值
标准值必须大于等于下限值，小于等于上限值
标准值，上限值和下限值必须为数字
选项值不能为空
选项值只能有一个默认值
检验项选项值序号重复，请检查后重新提交

查询检验规范
1.功能描述
通过该接口可在小工单系统查询检验规范信息
2.接口定义
2.1请求地址：/api/dytin/external/inspectionItem/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必参
传参示例
字段解释
code
string
否
"code": "编号"
模糊匹配检验规范编号
name
string
否
"code": "名称"
模糊匹配检验规范名称
createdAtGte
string
是
"createdAtGte":"2021-09-30 23:59:59"
创建时间大于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
createdAtLte
string
是
"createdAtLte":"2021-09-30 23:59:59"
创建时间小于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
updatedAtGte
string
否
"updatedAtGte":"2021-09-30 23:59:59"
更新时间大于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
updatedAtLte
string
否
"updatedAtLte": "2021-09-30 23:59:59"
更新时间小于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
page

Object
否
"page":{"pageNum":1,"pageSize":10}
不传 默认每页 10条数据,最大限度每页100条数据 "page":{"pageNum":1,"pageSize":10}
customFieldSearchConditions

Object

否
"customFieldSearchConditions":
[
 {
 "name": "字段名",
 "queryOperator": "equal",
 "fieldValue": [
 "测试"
 ]
 }
 ],
 自定义字段查询 具体传参说明请看文档描述 「自定义字段传参说明」

2.3返回参数
参数名
类型
描述
code
string
成功与否 成功：01000000 ，其他为失败， 
data
object
返回数据
msg
string
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "process17235164190354": { }}}

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]
参数名
字段类型
字段解释
id
number
检验规范 id
code
string
编号
name
string
名称
remark
string
备注
inspectionSpecificationsItemList
object[]
检验规范明细 见下文 【检验明细返回明细】
customFieldValues
object[]
自定义字段值
createdAt
String
创建时间 例如"2025-09-01 12:00:00"
creatorName
String
创建人
updatedAt
String
更新时间 例如"2025-09-01 12:00:00"
updatorName
String
更新人

检验明细返回明细
参数名
字段类型
字段解释
id
number
检验规范检验项 id
seq
number
序号
code
string
编号
name
string
名称
inspectionType
integer
类型（1 - 数值 2 - 文本 3 - 单选框）
standardValue
string
标准值（类型为数值时不能为空）
upperLimit
string
上限值（类型为数值时需存在上限或下限中的一个）
lowerLimit
string
下限值（类型为数值时需存在上限或下限中的一个）
optionValueList
object[]
选项值（类型为单选框时不能为空）
defaultValue：1 表示该选项为默认值，且只有一个默认值，
qualified：1 表示该选项为合格，
seq：为数字， 不会重复
示例：
"optionValueList": [
  {
    "optionValue": "选项值 1",    "defaultValue": 1,    "seq": 0,    "qualified": 1  },
  {
    "optionValue": "选项值 2",    "defaultValue": 0,    "seq": 1,    "qualified": 1  },
  {
    "optionValue": "选项值 3",    "defaultValue": 0,    "seq": 2,    "qualified": 0  }
],
required
integer
是否必填（0 否 1 是）
inspectionTools
string
检验工具（长度不能超过 1500 个字符）
inspectionContent
string
检验内容（长度不能超过 1500 个字符）
customFieldValues
object[]
自定义字段值
createdAt
String
创建时间 例如"2025-09-01 12:00:00"
creatorName
String
创建人
updatedAt
String
更新时间 例如"2025-09-01 12:00:00"
updatorName
String
更新人

2.4错误代码
01000002
创建开始时间筛选项不能为空
1078
查询时间范围不能超过2年

删除检验规范
1.功能描述
通过该接口可在小工单系统删除检验规范信息
2.接口定义
2.1请求地址：/api/dytin/external/inspectionSpecifications/batch/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
 codes
String[]
否
编号列表；与id列表，二选一不能同时为空
ids
Number[]
否
id列表；编号列表，二选一不能同时为空

2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Number
返回 删除数量
msg
String
返回消息

请求参数示例：
{
    "codes": [
        "QID0001",
        "QID0002"
    ]
}

2.4错误代码
01000002
检验规范ID列表或编号列表不能都为空

创建检验单
1.功能描述
通过该接口可在小工单系统创建检验单信息
2.接口定义
2.1请求地址：/api/dytin/external/inspection/order/create
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
orderType

integer
是

检验单类型
* 1: 来料检验* 2: 成品入库检验* 3: 销售发货检验* 4: 其他检验
code
string
否
检验单编号（长度不能超过 120 个字符），为空时系统生成检验单编号
productCode
string
是
检验单产品信息-产品编号（长度不能超过 120 个字符）
orderStatus
integer
否
检验单初始状态 ：0 未开始（不传默认 未开始）、1 进行中
inspectionSpecificationsCode
string
否

检验单检验规范-检验规范编号

submittedInspectionQty
integer
是
送检数量
remark
string
否
检验单备注，最大 长度1500
planFinishedTime
string
是
计划完成时间 ，示例："planFinishedTime":"2021-09-30 23:59:59"
customFieldValues
object[]
否
自定义字段值（不支持关联属性）
示例：
[{"name":"名称 1","value":"文本 - LQ 产品 01"},{"name":"数字型 2","value":174237},{"name":"名称 3","value":"1"}]
如果自定义字段类型是关联对象，value 传值设定根据关联的不同对象类型，分别为:
用户对象 value 传名称
单位对象 value 传名称
产品对象 value 传产品编号
工艺路线 value 传工艺路线名称
工序 value 传工序编号
不良品 value 传不良品编号
部门 value 传部门编号
客户 value 传客户编号
供应商 value 传供应商编号
仓库 value 传供仓库编号
设备 value 传供设备编号
检验规范 value 传供检验规范编号
检验项 value 传供检验项编号

2.3返回参数
参数名
类型
描述
code
string
成功与否 成功：01000000 ，其他为失败， 
data
object
返回数据
msg
string
返回消息

data
参数名
字段类型
描述
id
Long
检验单id

2.4错误代码
01000002

检验类型不能为空
检验单类型传参有误
送检数量不能为空
送检数量不能小于 0
送检数量整数位支持最多10位,小数位最多6位
编号最大支持120个字符
检验单编号已存在
产品编码不能为空
产品编码最大支持120个字符
检验单状态传参有误
产品编号不存在
检验规范编号不存在
计划完成时间不能为空

更新检验单
1.功能描述
通过该接口可在小工单系统更新检验单信息，【接口不支持操作检验结果】
❗️此接口为覆盖更新逻辑，未传值的自定义字段内容将会被置空
2.接口定义
2.1请求地址：/api/dytin/external/inspection/order/update
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
orderType

integer
是

检验单类型
* 1: 来料检验* 2: 成品入库检验* 3: 销售发货检验* 4: 其他检验
code
string
是
检验单编号（长度不能超过 120 个字符），为空时系统生成检验单编号
productCode
string
是
检验单产品信息-产品编号（长度不能超过 120 个字符）
orderStatus
integer
否
检验单初始状态 ：0 未开始（不传默认 未开始）、1 进行中
inspectionSpecificationsCode
string
否

检验单检验规范-检验规范编号

submittedInspectionQty
integer
是
送检数量
remark
string
否
检验单备注，最大 长度1500
planFinishedTime
string
是
计划完成时间 ，示例："planFinishedTime":"2021-09-30 23:59:59"
customFieldValues
object[]
否
自定义字段值（不支持关联属性）
示例：
[{"name":"名称 1","value":"文本 - LQ 产品 01"},{"name":"数字型 2","value":174237},{"name":"名称 3","value":"1"}]
如果自定义字段类型是关联对象，value 传值设定根据关联的不同对象类型，分别为:
用户对象 value 传名称
单位对象 value 传名称
产品对象 value 传产品编号
工艺路线 value 传工艺路线名称
工序 value 传工序编号
不良品 value 传不良品编号
部门 value 传部门编号
客户 value 传客户编号
供应商 value 传供应商编号
仓库 value 传供仓库编号
设备 value 传供设备编号
检验规范 value 传供检验规范编号
检验项 value 传供检验项编号

2.3返回参数
参数名
类型
描述
code
string
成功与否 成功：01000000 ，其他为失败， 
data
boolean
返回成功（true）或者失败（false）
msg
string
返回消息

2.4错误代码
01000002

检验类型不能为空
检验单编号不存在
检验单编号不能为空
检验单类型传参有误
送检数量不能为空
送检数量不能小于 0
送检数量整数位支持最多10位,小数位最多6位
编号最大支持120个字符
产品编码不能为空
产品编码最大支持120个字符
检验单状态传参有误
产品编号不存在
检验规范编号不存在
计划完成时间不能为空
检验单的检验规范不可修改

查询检验单
1.功能描述
通过该接口可在小工单系统查询检验单信息
2.接口定义
2.1请求地址：/api/dytin/external/inspection/queryList
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
orderType

integer
是
检验单类型
* 1: 来料检验* 2: 成品入库检验* 3: 销售发货检验* 4: 其他检验
code
string
否
检验单编号
productCode
string
否
检验单产品信息-产品编号
productName
string
否
检验单产品信息-产品名称
inspectionOrderStatus
integer[]
否
检验单状态 ：0 未开始（不传默认 未开始）、1 进行中,2 结束
inspectionSpecificationsName
string
否
检验单检验规范-检验规范名称
remark
string
否
检验单备注，最大 长度1500
planFinishedTimeGte
string
否
计划完成时间大于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
planFinishedTimeLte
string

否
计划完成时间小于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
createdAtGte
string
是
创建时间大于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29"
与创建时间小于间隔最大 1 年
createdAtLte

string
是
创建时间小于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
与创建时间大于间隔最大 1 年
updatedAtGte
string
否
更新时间大于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
updatedAtLte
string
否
更新时间小于 格式为：yyyy-MM-dd HH:mm:ss 例如"2025-08-26 11:12:29" 
page

Object
否
不传 默认每页 10条数据,最大限度每页100条数据 "page":{"pageNum":1,"pageSize":10}
customFieldSearchConditions

Object

否
 自定义字段查询  具体传参说明请看文档描述 「自定义字段传参说明」
"customFieldSearchConditions":
[
 {
 "name": "字段名",
 "queryOperator": "equal",
 "fieldValue": [
 "测试"
 ]
 }
 ],

2.3返回参数
参数名
类型
描述
code
string
成功与否 成功：01000000 ，其他为失败， 
data
object
返回数据
msg
string
返回消息
reference
Object
自定义字段Metadata信息 
"reference":{"allCustomFieldMetadata": { "process17235164190354": { }}}

data
参数名
类型
描述
data
data[] 
返回具体信息
pageNum
Number
当前页码
pageSize
Number
每页条数
total
Number
总数

data[]
参数名
字段类型
字段解释
sourceType
number
来源类型 如：
(0, "采购收货"),
(1, "成品入库"),
(2, "销售发货"),
(3, "工单报工"),
(4, "入库单"),
(5, "出库单"),
sourceId
number
来源单ID（如：订单ID）
sourceSubId
number
来源子单ID（如：订单明细ID）
sourceOrderNO
String
来源单据编号
orderType

number

检验单类型
* 1: 来料检验* 2: 成品入库检验* 3: 销售发货检验* 4: 其他检验
orderStatus
number
检验单状态：0 未开始、1 进行中,2 结束
planFinishedTime
LocalDateTime
计划完成时间
inspectionSpecificationsId
number
检验规范ID
inspectionSpecificationsRemark
String
检验规范备注
inspectionSpecificationsCode
String
检验规范编码
inspectionSpecificationsName
String
检验规范名称
submittedInspectionQty
number
送检数量
inspectionQty
number
检验数量
qualifiedQty
number
合格数量
unqualifiedQty
number
不合格数量
remark
String
检验单备注
createdAt
LocalDateTime
创建时间
updatedAt
LocalDateTime
更新时间
creatorName
String
创建人名称
updatorName
String
更新人名称
hasInspectionOrderResultFlag
Boolean
是否有过检验结果
customFieldValues
object[]
自定义字段值
inspectionOrderItemVOS
object[]
检验明细
inspectionOrderResult
object[]
检验结果

检验明细
字段名
类型
描述
id
Long
检验项 id
seq
Integer
序号
inspectionOrderId
Long
检验单ID
inspectionItemId
Long
检验明细id
inspectionSpecificationsItemId
Long
检验规范明细ID
code
String
检验单规范明细code
name
String
检验单规范明细名称
inspectionType
Integer
检验类型
upperLimit
BigDecimal
上限值
lowerLimit
BigDecimal
下限值
standardValue
BigDecimal
标准值（用于比对的理想值）
optionValueList
object[]
可选值（如选项型检验用）
required
Integer
是否必检（0-否，1-是）
inspectionTools
String
使用的检验工具
inspectionContent
String
检验内容描述
createdAt
LocalDateTime
创建时间
customFieldValues
object[]
自定义字段值

检验结果
字段名称
类型
描述
id
Long

inspectionOrderId
Long
检验单ID
seq
Long
序号
qualifiedQty
BigDecimal
良品数
unqualifiedQty
BigDecimal
不良品数量
inspectionQty
BigDecimal
检验数量
inspectionUserName
String
检验人名称
inspectionTime
LocalDateTime
检验时间
inspectionOrderResultDetailVOList
object[]
检验结果详情 

检验结果详情 
字段名称
字段类型
字段说明
id
Long
检验结果ID
inspectionOrderId
Long
检验单ID（关联主单）
inspectionOrderItemId
Long
检验单规范明细ID
inspectionResultId
Long
检验结果ID
judgementResult
Integer
判定结果
customFieldValues
object[]
自定义字段值

示例返回 json
{
    "code": "01000000",
    "msg": "成功",
    "data": {
        "id": 1665,
        "customFieldValues": [
            {
                "customFieldMetadata": {
                    "$ref": "#/reference/allCustomFieldMetadata/inspection_specifications17587842390091"
                },
                "fieldId": 752641,
                "value": null,
                "associatedObjectValueDetail": null
            },
            {
                "customFieldMetadata": {
                    "$ref": "#/reference/allCustomFieldMetadata/inspection_specifications175878325854157"
                },
                "fieldId": 752639,
                "value": null,
                "associatedObjectValueDetail": null
            }
        ],
        "code": "IO202602250002",
        "productId": 190774,
        "productName": "1429 新名称",
        "productCode": "1429 新编号",
        "sourceType": null,
        "sourceId": null,
        "sourceSubId": null,
        "sourceOrderNO": null,
        "orderType": 2,
        "orderStatus": 1,
        "planFinishedTime": "2026-02-25 23:59:59",
        "inspectionSpecificationsId": 106,
        "inspectionSpecificationsRemark": null,
        "inspectionSpecificationsCode": "QIS0002",
        "inspectionSpecificationsName": "macctest260225001",
        "submittedInspectionQty": 44.000000,
        "inspectionQty": 2.000000,
        "qualifiedQty": 2.000000,
        "unqualifiedQty": 2.000000,
        "remark": null,
        "createdAt": "2026-02-25 11:15:50",
        "updatedAt": "2026-02-27 15:06:12",
        "creatorId": 13308,
        "creatorName": "系统管理员",
        "updatorId": 13308,
        "updatorName": "系统管理员",
        "hasInspectionOrderResultFlag": true,
        "inspectionOrderResult": [
            {
                "id": 125,
                "inspectionOrderId": 1665,
                "seq": 0,
                "qualifiedQty": 2.000000,
                "unqualifiedQty": 2.000000,
                "inspectionQty": 2.000000,
                "inspectionUserId": 13308,
                "inspectionUserName": "系统管理员",
                "inspectionTime": "2026-02-25 11:24:58",
                "inspectionOrderResultDetailVOList": [
                    {
                        "id": 165,
                        "customFieldValues": [
                            {
                                "customFieldMetadata": {
                                    "$ref": "#/reference/allCustomFieldMetadata/inspection_item17587842389910"
                                },
                                "fieldId": 752640,
                                "value": null,
                                "associatedObjectValueDetail": null
                            },
                            {
                                "customFieldMetadata": {
                                    "$ref": "#/reference/allCustomFieldMetadata/inspection_item175878324322056"
                                },
                                "fieldId": 752638,
                                "value": null,
                                "associatedObjectValueDetail": null
                            }
                        ],
                        "inspectionResultId": 125,
                        "inspectionOrderId": 1665,
                        "name": null,
                        "code": null,
                        "inspectionType": null,
                        "upperLimit": null,
                        "lowerLimit": null,
                        "standardValue": null,
                        "optionValue": null,
                        "required": null,
                        "inspectionTools": null,
                        "inspectionContent": null,
                        "inspectionResult": "无",
                        "judgementResult": 0,
                        "inspectionOrderItemId": 1787,
                        "inspectionItemId": null,
                        "inspectionSpecificationsItemId": null
                    }
                ]
            }
        ],
        "inspectionOrderItemVOS": [
            {
                "id": 1787,
                "customFieldValues": [
                    {
                        "customFieldMetadata": {
                            "$ref": "#/reference/allCustomFieldMetadata/inspection_item17587842389910"
                        },
                        "fieldId": 752640,
                        "value": null,
                        "associatedObjectValueDetail": null
                    },
                    {
                        "customFieldMetadata": {
                            "$ref": "#/reference/allCustomFieldMetadata/inspection_item175878324322056"
                        },
                        "fieldId": 752638,
                        "value": null,
                        "associatedObjectValueDetail": null
                    }
                ],
                "seq": 0,
                "inspectionOrderId": 1665,
                "inspectionItemId": 10123,
                "inspectionSpecificationsItemId": 2237,
                "code": "QID0001",
                "name": "毛刺",
                "inspectionType": 3,
                "upperLimit": null,
                "lowerLimit": null,
                "standardValue": null,
                "optionValueList": [
                    {
                        "seq": 0,
                        "optionValue": "无",
                        "qualified": 1,
                        "defaultValue": 0
                    },
                    {
                        "seq": 1,
                        "optionValue": "有",
                        "qualified": 0,
                        "defaultValue": 0
                    }
                ],
                "required": 0,
                "inspectionTools": null,
                "inspectionContent": null,
                "createdAt": "2026-02-25 11:15:50"
            }
        ]
    },
    "reference": {
        "allCustomFieldMetadata": {
            "inspection_item17587842389910": {
                "id": 752640,
                "orgId": 572,
                "targetType": "INSPECTION_ITEM",
                "targetTypeDisplayName": "检验项",
                "name": "项-11${1222",
                "fieldName": "inspection_item17587842389910",
                "type": 1,
                "businessObjectType": null,
                "displayAttributeName": null,
                "displayAttribute": [],
                "displayAttributeDetail": null,
                "associatedBusinessObjectFilter": null,
                "associatedViewId": null,
                "associatedViewName": null,
                "dataFillRule": [],
                "dataFillRuleDetail": null,
                "associatedBusinessObjectName": null,
                "associatedBusinessObjectField": null,
                "associatedBusinessObjectFieldDetail": null,
                "options": [],
                "optionColors": [],
                "expression": null,
                "blScanCodeInput": false,
                "blModifyScanResult": false,
                "blRepeatInput": null,
                "tips": null,
                "description": null,
                "blReadonly": false,
                "blThousandSeparator": null,
                "lineNumber": null,
                "decimalLength": -1,
                "hidden": [],
                "defaultValue": null,
                "blRequired": false,
                "allowAddOption": false,
                "deletedAt": 0,
                "creatorId": 13308,
                "creator": {
                    "id": 13308,
                    "username": "admin",
                    "name": "系统管理员",
                    "spell": "XITONGGUANLIYUAN",
                    "fake": false,
                    "active": true,
                    "phone": "18321021237"
                },
                "createdAt": "2025-09-25 15:10:38",
                "updatorId": 13308,
                "updator": {
                    "id": 13308,
                    "username": "admin",
                    "name": "系统管理员",
                    "spell": "XITONGGUANLIYUAN",
                    "fake": false,
                    "active": true,
                    "phone": "18321021237"
                },
                "updatedAt": "2026-02-26 10:47:50",
                "sort": 2,
                "blTextArea": false,
                "blSearch": false,
                "dateTimeDisplayType": null,
                "showType": 0
            },
            "inspection_item175878324322056": {
                "id": 752638,
                "orgId": 572,
                "targetType": "INSPECTION_ITEM",
                "targetTypeDisplayName": "检验项",
                "name": "项-1",
                "fieldName": "inspection_item175878324322056",
                "type": 1,
                "businessObjectType": null,
                "displayAttributeName": null,
                "displayAttribute": [],
                "displayAttributeDetail": null,
                "associatedBusinessObjectFilter": null,
                "associatedViewId": null,
                "associatedViewName": null,
                "dataFillRule": [],
                "dataFillRuleDetail": null,
                "associatedBusinessObjectName": null,
                "associatedBusinessObjectField": null,
                "associatedBusinessObjectFieldDetail": null,
                "options": [],
                "optionColors": [],
                "expression": null,
                "blScanCodeInput": false,
                "blModifyScanResult": false,
                "blRepeatInput": null,
                "tips": null,
                "description": null,
                "blReadonly": false,
                "blThousandSeparator": null,
                "lineNumber": null,
                "decimalLength": -1,
                "hidden": [],
                "defaultValue": null,
                "blRequired": false,
                "allowAddOption": false,
                "deletedAt": 0,
                "creatorId": 13308,
                "creator": {
                    "id": 13308,
                    "username": "admin",
                    "name": "系统管理员",
                    "spell": "XITONGGUANLIYUAN",
                    "fake": false,
                    "active": true,
                    "phone": "18321021237"
                },
                "createdAt": "2025-09-25 14:54:03",
                "updatorId": 13308,
                "updator": {
                    "id": 13308,
                    "username": "admin",
                    "name": "系统管理员",
                    "spell": "XITONGGUANLIYUAN",
                    "fake": false,
                    "active": true,
                    "phone": "18321021237"
                },
                "updatedAt": "2026-02-26 10:47:54",
                "sort": 1,
                "blTextArea": false,
                "blSearch": false,
                "dateTimeDisplayType": null,
                "showType": 0
            },
            "inspection_specifications175878325854157": {
                "id": 752639,
                "orgId": 572,
                "targetType": "INSPECTION_SPECIFICATIONS",
                "targetTypeDisplayName": "检验规范",
                "name": "规范-1",
                "fieldName": "inspection_specifications175878325854157",
                "type": 1,
                "businessObjectType": null,
                "displayAttributeName": null,
                "displayAttribute": [],
                "displayAttributeDetail": null,
                "associatedBusinessObjectFilter": null,
                "associatedViewId": null,
                "associatedViewName": null,
                "dataFillRule": [],
                "dataFillRuleDetail": null,
                "associatedBusinessObjectName": null,
                "associatedBusinessObjectField": null,
                "associatedBusinessObjectFieldDetail": null,
                "options": [],
                "optionColors": [],
                "expression": null,
                "blScanCodeInput": false,
                "blModifyScanResult": false,
                "blRepeatInput": null,
                "tips": null,
                "description": null,
                "blReadonly": false,
                "blThousandSeparator": null,
                "lineNumber": null,
                "decimalLength": -1,
                "hidden": [],
                "defaultValue": null,
                "blRequired": false,
                "allowAddOption": false,
                "deletedAt": 0,
                "creatorId": 13308,
                "creator": {
                    "id": 13308,
                    "username": "admin",
                    "name": "系统管理员",
                    "spell": "XITONGGUANLIYUAN",
                    "fake": false,
                    "active": true,
                    "phone": "18321021237"
                },
                "createdAt": "2025-09-25 14:54:18",
                "updatorId": 13308,
                "updator": {
                    "id": 13308,
                    "username": "admin",
                    "name": "系统管理员",
                    "spell": "XITONGGUANLIYUAN",
                    "fake": false,
                    "active": true,
                    "phone": "18321021237"
                },
                "updatedAt": "2026-02-26 10:47:40",
                "sort": 1,
                "blTextArea": false,
                "blSearch": false,
                "dateTimeDisplayType": null,
                "showType": 0
            },
            "inspection_specifications17587842390091": {
                "id": 752641,
                "orgId": 572,
                "targetType": "INSPECTION_SPECIFICATIONS",
                "targetTypeDisplayName": "检验规范",
                "name": "规范-11${1222",
                "fieldName": "inspection_specifications17587842390091",
                "type": 1,
                "businessObjectType": null,
                "displayAttributeName": null,
                "displayAttribute": [],
                "displayAttributeDetail": null,
                "associatedBusinessObjectFilter": null,
                "associatedViewId": null,
                "associatedViewName": null,
                "dataFillRule": [],
                "dataFillRuleDetail": null,
                "associatedBusinessObjectName": null,
                "associatedBusinessObjectField": null,
                "associatedBusinessObjectFieldDetail": null,
                "options": [],
                "optionColors": [],
                "expression": null,
                "blScanCodeInput": false,
                "blModifyScanResult": false,
                "blRepeatInput": null,
                "tips": null,
                "description": null,
                "blReadonly": false,
                "blThousandSeparator": null,
                "lineNumber": null,
                "decimalLength": -1,
                "hidden": [],
                "defaultValue": null,
                "blRequired": false,
                "allowAddOption": false,
                "deletedAt": 0,
                "creatorId": 13308,
                "creator": {
                    "id": 13308,
                    "username": "admin",
                    "name": "系统管理员",
                    "spell": "XITONGGUANLIYUAN",
                    "fake": false,
                    "active": true,
                    "phone": "18321021237"
                },
                "createdAt": "2025-09-25 15:10:39",
                "updatorId": 13308,
                "updator": {
                    "id": 13308,
                    "username": "admin",
                    "name": "系统管理员",
                    "spell": "XITONGGUANLIYUAN",
                    "fake": false,
                    "active": true,
                    "phone": "18321021237"
                },
                "updatedAt": "2026-02-26 10:47:34",
                "sort": 2,
                "blTextArea": false,
                "blSearch": false,
                "dateTimeDisplayType": null,
                "showType": 0
            }
        }
    }
}

2.4错误代码
01000002
创建开始时间筛选项不能为空

删除检验单
1.功能描述
通过该接口可在小工单系统删除检验单信息
2.接口定义
2.1请求地址：/api/dytin/external/inspection/order/batch/delete
通信协议：HTTPS
请求方式：POST
请求header参数：
- X-AUTH，值就是登录接口拿到的token。
- Content-Type:application/json
2.2请求参数
参数名
字段类型
是否必填
字段解释
 codes
String[]
否
编号列表；与id列表，二选一不能同时为空
ids
Number[]
否
id列表；编号列表，二选一不能同时为空

2.3返回参数
参数名
类型
描述
code
String
成功与否 成功：01000000 ，其他为失败， 
data
Number
返回 删除数量
msg
String
返回消息

请求参数示例：
{
    "codes": [
        "IO202602250002",
        "IO202602250002"
    ]
}

2.4错误代码
01000002
检验单ID列表或编号列表不能都为空

