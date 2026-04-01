package com.eprint.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    // ======== 基础账号信息 ========
    private String userId;  // 用户唯一标识符 (UUID或工号)
    private String username;  // 登录账号名
    private String email;  // 电子邮箱
    private String fullName;  // 用户真实姓名
    private Boolean isActive;  // 账号是否启用

    // ======== 提交与审核权限 (流程控制) ========
    private Boolean order_submit;  // 订单提交权限 (业务员)
    private Boolean order_audit;  // 订单审核权限 (审单员/主管)
    private Boolean work_submit;  // 工程单提交权限 (制单员)
    private Boolean work_audit;  // 工程单审核权限 (工程主管)

    // ======== 查看和修改权限 (模块准入) ========
    private Boolean order_check;  // 订单查看权限
    private Boolean work_check;  // 工程单查看权限
    private Boolean pmc_check;  // PMC(生产排期)查看权限
    private Boolean pmc_edit;  // PMC(生产排期)修改权限

    // ======== 查看和修改发货 (物流权限) ========
    private Boolean delieve_check;  // 发货/出库记录查看权限
    private Boolean delieve_edit;  // 发货/出库单据编辑权限

    // ======== 部门访问权限 ========
    private Boolean isSAL;  // 是否能查看销售部页面
    private Boolean isPUR;  // 是否能查看采购部页面
    private Boolean isOUT;  // 是否能查看外发部页面
    private Boolean isMNF;  // 是否能查看生产部页面
    private Boolean isADM;  // 是否能查看办公室页面

    // ======== 系统辅助字段 ========
    private LocalDateTime lastLogin;  // 最后登录时间

    // 注意：不包含 passwordHash，安全考虑
}
