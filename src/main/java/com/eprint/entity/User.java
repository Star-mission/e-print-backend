package com.eprint.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_username", columnList = "username", unique = true),
    @Index(name = "idx_email", columnList = "email", unique = true)
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ======== 基础账号信息 ========
    @Column(nullable = false, unique = true)
    private String userId;  // 用户唯一标识符 (UUID或工号)

    @Column(nullable = false, unique = true)
    private String username;  // 登录账号名

    @Column(nullable = false, unique = true)
    private String email;  // 电子邮箱

    @Column(nullable = false)
    private String passwordHash;  // 加密后的密码哈希值

    @Column(nullable = false)
    private String fullName;  // 用户真实姓名

    @Column(nullable = false)
    private Boolean isActive = true;  // 账号是否启用

    // ======== 提交与审核权限 (流程控制) ========
    @Column(nullable = false)
    private Boolean orderSubmit = false;  // 订单提交权限 (业务员)

    @Column(nullable = false)
    private Boolean orderAudit = false;  // 订单审核权限 (审单员/主管)

    @Column(nullable = false)
    private Boolean workSubmit = false;  // 工程单提交权限 (制单员)

    @Column(nullable = false)
    private Boolean workAudit = false;  // 工程单审核权限 (工程主管)

    // ======== 查看和修改权限 (模块准入) ========
    @Column(nullable = false)
    private Boolean orderCheck = false;  // 订单查看权限

    @Column(nullable = false)
    private Boolean workCheck = false;  // 工程单查看权限

    @Column(nullable = false)
    private Boolean pmcCheck = false;  // PMC(生产排期)查看权限

    @Column(nullable = false)
    private Boolean pmcEdit = false;  // PMC(生产排期)修改权限

    // ======== 查看和修改发货 (物流权限) ========
    @Column(nullable = false)
    private Boolean delieveCheck = false;  // 发货/出库记录查看权限

    @Column(nullable = false)
    private Boolean delieveEdit = false;  // 发货/出库单据编辑权限

    // ======== 部门访问权限 ========
    @Column(nullable = false)
    private Boolean isSAL = false;  // 是否能查看销售部页面

    @Column(nullable = false)
    private Boolean isPUR = false;  // 是否能查看采购部页面

    @Column(nullable = false)
    private Boolean isOUT = false;  // 是否能查看外发部页面

    @Column(nullable = false)
    private Boolean isMNF = false;  // 是否能查看生产部页面

    @Column(nullable = false)
    private Boolean isADM = false;  // 是否能查看办公室页面

    // ======== 系统辅助字段 ========
    private LocalDateTime lastLogin;  // 最后登录时间

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
