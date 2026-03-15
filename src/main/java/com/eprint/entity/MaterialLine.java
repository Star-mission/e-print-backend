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

    private String materialName;
    private String specification;
    private Integer quantity;
    private String unit;

    private LocalDateTime kaiShiShiJian;
    private LocalDateTime jieShuShiJian;
    private Integer dangQianJinDu;

    @Column(length = 1000)
    private String notes;
}
