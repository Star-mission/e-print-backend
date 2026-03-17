package com.eprint.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "engineering_order_material_lines")
public class MaterialLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engineering_order_id", nullable = false)
    private EngineeringOrder engineeringOrder;

    private Integer intermediaIndex;  // 在 intermedia 数组中的索引位置

    private String materialName;
    private String specification;
    private Integer quantity;
    private String unit;

    private LocalDateTime kaiShiShiJian;
    private LocalDateTime jieShuShiJian;
    private String dangQianJinDu;  // 改为 String 类型（从 Change.md）

    // 新增字段 - 来自 Change.md
    private Integer yiGouJianShu;  // 已采购件数
    private String headPUR;  // 采购负责人
    private String headOUT;  // 外发负责人

    @Column(length = 1000)
    private String notes;
}
