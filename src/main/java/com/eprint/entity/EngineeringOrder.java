package com.eprint.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "engineering_orders", indexes = {
    @Index(name = "idx_work_unique", columnList = "workUnique", unique = true)
})
public class EngineeringOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String engineeringOrderId;

    private String workId;
    private Integer workVer;

    @Column(unique = true)
    private String workUnique;

    @Enumerated(EnumType.STRING)
    private OrderStatus reviewStatus;

    private String workClerk;
    private LocalDateTime clerkDate;
    private String workAudit;
    private LocalDateTime auditDate;
    private String keHu;
    private String po;
    private String chengPinMingCheng;
    private String chanPinGuiGe;
    private String gongDanLeiXing;
    private String caiLiao;
    private String chanPinLeiXing;
    private LocalDateTime zhiDanShiJian;

    private Integer dingDanShuLiang;
    private Integer chuYangShu;
    private Integer chaoBiLi;
    private Integer benChangFangSun;
    private LocalDateTime chuYangRiqiRequired;
    private LocalDateTime chuHuoRiqiRequired;

    // 新增字段 - 来自 Change.md
    private Integer zhuangDingJianShu;  // 已装订件数
    private LocalDateTime zhuangDingStart;
    private LocalDateTime zhuangDingEnd;
    private String headMNF;  // 生产负责人

    @Column(length = 2000)
    private String beiZhu;

    @Column(nullable = false)
    private String isDeleted = "否";  // 是否删除（软删除标记，默认否）

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "engineeringOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialLine> materialLines = new ArrayList<>();

    @OneToMany(mappedBy = "engineeringOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum OrderStatus {
        草稿, 待审核, 通过, 驳回, 生产中, 完成, 取消
    }
}
