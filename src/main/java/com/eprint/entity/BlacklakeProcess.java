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
