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