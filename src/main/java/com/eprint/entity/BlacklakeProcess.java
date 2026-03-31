package com.eprint.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "blacklake_processes", indexes = {
    @Index(name = "idx_blacklake_id", columnList = "blacklakeId", unique = true)
})
public class BlacklakeProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long blacklakeId;

    private String code;

    private String name;

    private Double outputRate;

    private Boolean outputBlAll;

    private String creatorName;

    private String updatorName;

    private String blacklakeCreatedAt;

    private String blacklakeUpdatedAt;

    @Column(columnDefinition = "TEXT")
    private String customFieldValues;

    // Custom field columns (split from customFieldValues)
    private Double cfUnitPrice;  // 单价 fieldId=199685
    private Double cfLineNo;     // 行号 fieldId=212103
    private Double cfCard2;      // 卡2 fieldId=293509
    private Double cfBox2;       // 箱2 fieldId=293510
    private Double cfBook2;      // 本2 fieldId=293511
    private Double cfCard1;      // 卡1 fieldId=293512
    private Double cfBox1;       // 箱1 fieldId=293513
    private Double cfBook1;      // 本1 fieldId=293514
    private Double cfDiscount;   // 打折 fieldId=334650

    private LocalDateTime syncedAt;

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
