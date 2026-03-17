WorkOrder 工程单的核心数据修改如下：

import { reactive } from 'vue'
import { type IWorkOrder, OrderStatus, formatYMD } from '@/types/workorder'

/**
 * 创建一个全字段初始化的 IWorkOrder 对象
 * 包含完整的嵌套数组对象：intermedia, auditLogs, attachments
 * 注释格式：<变量类型> + <原注释>
 */
const createFullWorkOrderTemplate = (): IWorkOrder => {
  return {
    // ======== 基础信息 ========
    work_id: 'ORD-20260201-001_W',                // string: 工程单号，在order通过审核时自动创建，order_id+"_W"
    work_ver: 'V1',                         // string: 版本号，和order_ver相同
    work_unique:'ORD-20260201-001_W_V1'//string:后端唯一索引，在order通过审核时自动创建,是work_id+"_"+work_ver.
    work_clerk: 'CZ001-王小美',              // string: 制单员名称或者工号
    work_audit: 'SH005-张经理',              // string: 工程单审核员名称或者工号
    gongDanLeiXing: '翻单',                  // string: 工单类型
    caiLiao: '250g哑粉纸',                  // string: 普通材料
    chanPinLeiXing: '精品礼盒',               // string: 产品类型
    zhiDanShiJian: '2026-02-01 10:30:00',   // string: 制单时间
    customer: '环球贸易有限公司',             // string: 客户
    customerPO: 'PO-HK-9921',               // string: 客户PO
    productName: '奢华巧克力包装盒',           // string: 成品名称
    chanPinGuiGe: '300 * 200 * 50 mm',      // string: 产品规格：似乎是页面大小

    // ======== 订单过继信息 ========
    dingDanShuLiang: 10000,                 // number: 订单数量
    chuYangShuLiang: 10,                    // number: 出样数量
    chaoBiLiShuLiang: 100,                  // number: 超比例数量
    benChangFangSun: '2.0%',                // string: 本厂放损
    chuYangRiqiRequired: '2026-02-10',      // string: 出样日期要求
    chuHuoRiqiRequired: '2026-03-15',       // string: 出货日期要求
    workorderstatus: WorkOrderStatus.PENDING_REVIEW,// OrderStatus: 订单状态
    zhuangDingJianShu: '26',//number: 已经装订件数，zhuangDingJianShu/dingDanShuLiang=装订进度
    head_MNF: 'Alice',//string: 该工单的生产负责人
    // ======== 中间物料详单 (IIM[]) ========
    intermedia: [
      {
        buJianMingCheng: '盒身',             // string: 部件名称
        yinShuaYanSe: '5C+1C',              // string: 印刷颜色
        wuLiaoMingCheng: '157g铜版纸裱2.0灰板', // string: 物料名称
        pinPai: '万国',                     // string: 品牌
        caiLiaoGuiGe: '787 * 1092',         // string: 材料规格
        FSC: 'FSC Mix',                     // string: FSC
        kaiShu: 4,                          // number: 开数
        shangJiChiCun: '540 * 390',         // string: 上机尺寸
        paiBanMuShu: 1,                     // number: 排版模数
        yinChuShu: 10200,                   // number: 印出数
        yinSun: 300,                        // number: 印损
        lingLiaoShu: 10500,                 // number: 领料数（张）
        biaoMianChuLi: '触感膜+局部UV',        // string: 表面处理
        yinShuaBanShu: 6,                   // number: 印刷版数目
        shengChanLuJing: '印刷-覆膜-V槽-糊盒',  // string: 生产路径
        paiBanFangShi: '自翻'                // string: 排版方式
        
        //不在工程单申请上的，但是后期会渲染进度
        yiGouJianShu: '25' //number：已经采购的件数，caiGouJianShu/lingLiaoShu=采购进度
        head_PUR:'Marc'//string: 该工序的采购负责人 
        kaiShiRiQi: '2026-02-01' //string：工序开始日期
        yuQiJieShu: '2026-03-01'// string：工序预期结束日期
        dangQianJinDu: '85%' //string: 工序当前进度，由技工手动输入
        head_OUT: '张三' //string：该工序的外发负责人
      }
    ],


    // ======== 审批日志 (IAuditLog[]) ========
    auditLogs: [
      {
        time: '2026-02-01 09:00:00',        // string: 记录时间
        operator: 'CZ001-王小美',            // string: 业务员或者审核人，后期以工号替代
        action: 'save_draft',               // string: 操作动作
        comment: '保存草稿，等待附件上传'       // string: 备注
      },
      {
        time: '2026-02-01 10:00:00',
        operator: 'SH005-张经理',
        action: 'reject',
        comment: '放损比例填写过低，请核实后重交' // 模拟驳回备注
      }
    ],

    // ======== 附件条目 (IAttachment[]) ========
    attachments: [
      {
        category: '设计稿',                  // string: 附件分类
        fileName: 'chocolate_box_final.ai', // string: 文件名
        url: 'http://cdn.eprint.com/files/ai/123.ai' // string: 可选：用于查看阶段的服务器下载链接
      }
      // 注意：file 对象通常在上传阶段由 input 赋值，初始化时一般为 undefined
    ]
  }
}
export enum WorkOrderStatus {
  DRAFT = '草稿',
  PENDING_REVIEW = '待审核',
  APPROVED = '通过',
  REJECTED = '驳回',
  IN_PRODUCTION = '生产中',
  COMPLETED = '完成',
  CANCELLED = '取消',
}
// 在 Vue 中创建该全量响应式对象
const workOrder = reactive<IWorkOrder>(createFullWorkOrderTemplate())

IUser修改如下：
export interface IUser {
  // ======== 基础账号信息 ========
  userId: admin // string: 用户唯一标识符 (UUID或工号)
  username: admin // string: 登录账号名
  email: admin@admin.com // string: 电子邮箱
  passwordHash: admin // string: 加密后的密码哈希值 (前端不存储明文)
  fullName: admin // string: 用户真实姓名
  isActive: True // boolean: 账号是否启用

  // ======== 提交与审核权限 (流程控制) ========
  order_submit: True // boolean: 订单提交权限 (业务员)
  order_audit: True // boolean: 订单审核权限 (审单员/主管)
  work_submit: True // boolean: 工程单提交权限 (制单员)
  work_audit: True // boolean: 工程单审核权限 (工程主管)

  // ======== 查看和修改权限 (模块准入) ========
  order_check: True // boolean: 订单查看权限
  work_check: True // boolean: 工程单查看权限
  pmc_check: True // boolean: PMC(生产排期)查看权限
  pmc_edit: True // boolean: PMC(生产排期)修改权限

  // ======== 查看和修改发货 (物流权限) ========
  delieve_check: True // boolean: 发货/出库记录查看权限
  delieve_edit: True // boolean: 发货/出库单据编辑权限
  
  isSAL: True //boolean: 是否能查看销售部页面
  isPUR: True //boolean: 是否能查看采购部页面
  isOUT: True //boolean: 是否能查看外发部页面
  isMNF: True //boolean: 是否能查看生产部页面
  isADM: True //boolean: 是否能查看办公室页面

  // ======== 系统辅助字段 ========
  lastLogin?: 2026-02-01 // string: 最后登录时间 (yyyy-mm-dd HH:mm:ss)
}
