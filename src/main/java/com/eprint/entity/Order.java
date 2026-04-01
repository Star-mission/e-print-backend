package com.eprint.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单实体类
 *
 * 对应数据库表：orders
 *
 * 业务说明：
 * - 管理印刷订单的完整生命周期
 * - 支持草稿保存和正式提交
 * - 订单通过审核后自动创建工单
 * - 使用版本号（orderVer）支持订单修改历史
 *
 * 关键字段：
 * - orderNumber: 订单号（自动生成，格式：AUTO-时间戳-随机数）
 * - orderUnique: 唯一标识（orderNumber + "_" + orderVer）
 * - status: 订单状态（草稿、待审核、通过、驳回、生产中、完成、取消）
 *
 * 关联关系：
 * - orderItems: 订单明细（一对多）
 * - documents: 附件文档（一对多）
 *
 * @author E-Print Team
 */
@Data  // Lombok 注解：自动生成 getter/setter/toString/equals/hashCode
@Entity  // JPA 注解：标记为实体类
@Table(name = "orders", indexes = {
    @Index(name = "idx_order_unique", columnList = "orderUnique", unique = true)  // 唯一索引
})
public class Order {

    // ==========================================
    // 主键和标识字段
    // ==========================================

    /**
     * 主键 ID（数据库自增）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单号（业务主键）
     * 格式：AUTO-1710501234567-a1b2c3d4
     * 自动生成，全局唯一
     */
    @Column(nullable = false, unique = true)
    private String orderNumber;

    /**
     * 订单版本号
     * 用于追踪订单修改历史，初始版本为 1
     */
    private Integer orderVer;

    /**
     * 订单唯一标识
     * 格式：orderNumber + "_" + orderVer
     * 例如：AUTO-1710501234567-a1b2c3d4_1
     */
    @Column(unique = true)
    private String orderUnique;

    /**
     * 订单状态
     * 枚举值：草稿、待审核、通过、驳回、生产中、完成、取消
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // ==========================================
    // 人员信息
    // ==========================================

    /**
     * 业务员姓名
     */
    private String sales;

    /**
     * 审核员姓名
     */
    private String audit;

    /**
     * 客户名称
     */
    private String customer;

    // ==========================================
    // 产品信息
    // ==========================================

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 客户采购订单号（Customer PO）
     */
    private String customerPO;

    /**
     * ISBN 编号（图书类产品）
     */
    private String isbn;

    // ==========================================
    // 数量信息
    // ==========================================

    /**
     * 订单数量（定单数量）
     */
    private Integer dingDanShuLiang;

    /**
     * 出样数量
     */
    private Integer chuYangShuLiang;

    /**
     * 超比例数量
     */
    private Integer chaoBiLiShuLiang;

    // ==========================================
    // 规格尺寸（单位：毫米）
    // ==========================================

    /**
     * 规格高度（毫米）
     */
    private Double guigeGaoMm;

    /**
     * 规格宽度（毫米）
     */
    private Double guigeKuanMm;

    /**
     * 规格厚度（毫米）
     */
    private Double guigeHouMm;

    // ==========================================
    // 说明和备注信息
    // ==========================================

    /**
     * 辅料说明
     * 最大长度：2000 字符
     */
    @Column(length = 2000)
    private String fuLiaoShuoMing;

    /**
     * 物料说明
     * 最大长度：2000 字符
     */
    @Column(length = 2000)
    private String wuLiaoShuoMing;

    /**
     * 质量要求
     * 最大长度：2000 字符
     */
    @Column(length = 2000)
    private String zhiLiangYaoQiu;

    /**
     * 备注信息
     * 最大长度：2000 字符
     */
    @Column(length = 2000)
    private String beiZhu;

    /**
     * 客来信息
     * 最大长度：2000 字符
     */
    @Column(length = 2000)
    private String keLaiXinXi;

    // ==========================================
    // 时间节点
    // ==========================================

    /**
     * 下资料袋日期 1
     */
    private LocalDateTime xiaZiliaodaiRiqi1;

    /**
     * 下资料袋日期 2
     */
    private LocalDateTime xiaZiliaodaiRiqi2;

    /**
     * 印章日期 1
     */
    private LocalDateTime yinzhangRiqi1;

    /**
     * 印章日期 2
     */
    private LocalDateTime yinzhangRiqi2;

    /**
     * 交货日期 1
     */
    private LocalDateTime jiaoHuoRiQi1;

    /**
     * 交货日期 2
     */
    private LocalDateTime jiaoHuoRiQi2;

    // ==========================================
    // 其他人员信息
    // ==========================================

    /**
     * 业务代表分级
     */
    private String yeWuDaiBiaoFenJi;

    /**
     * 审核人
     */
    private String shenHeRen;

    /**
     * 旧编码
     */
    private String jiuBianMa;

    /**
     * 其他识别
     */
    private String qiTaShiBie;

    @Column(length = 1000)
    private String fenBanShuoMing2;
    @Column(length = 1000)
    private String yinShuaGenSeYaoQiu;
    @Column(length = 1000)
    private String zhuangDingShouGongYaoQiu;
    @Column(length = 1000)
    private String qiTa;
    @Column(length = 1000)
    private String keHuFanKui;
    @Column(length = 1000)
    private String teShuYaoQiu;
    @Column(length = 1000)
    private String kongZhiFangFa;
    @Column(length = 1000)
    private String dingDanTeBieShuoMing;
    @Column(length = 1000)
    private String yangPinPingShenXinXi;
    @Column(length = 1000)
    private String dingDanPingShenXinXi;

    private LocalDateTime yeWuRiqi;
    private LocalDateTime shenHeRiqi;
    private LocalDateTime daYinRiqi;

    /**
     * 打印人
     */
    private String daYinRen;

    // ==========================================
    // 系统字段
    // ==========================================

    /**
     * 是否删除（软删除标记，默认否）
     */
    @Column(nullable = false)
    private String isDeleted = "否";

    /**
     * 创建时间（不可更新）
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 更新时间（每次更新自动刷新）
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ==========================================
    // 关联关系
    // ==========================================

    /**
     * 订单明细列表（一对多）
     * - cascade = ALL: 级联所有操作（保存、更新、删除）
     * - orphanRemoval = true: 自动删除孤儿记录
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * 附件文档列表（一对多）
     * - cascade = ALL: 级联所有操作
     * - orphanRemoval = true: 自动删除孤儿记录
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    // ==========================================
    // JPA 生命周期回调方法
    // ==========================================

    /**
     * 持久化前回调
     * 自动设置创建时间和更新时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 更新前回调
     * 自动刷新更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // 枚举定义
    // ==========================================

    /**
     * 订单状态枚举
     *
     * 状态流转：
     * 草稿 -> 待审核 -> 通过 -> 生产中 -> 完成
     *              |
     *              +-> 驳回
     *              |
     *              +-> 取消
     */
    public enum OrderStatus {
        草稿,      // 初始状态，可以继续编辑
        待审核,    // 已提交，等待审核
        通过,      // 审核通过，自动创建工单
        驳回,      // 审核不通过
        生产中,    // 正在生产
        完成,      // 订单完成
        取消       // 订单取消
    }
}
