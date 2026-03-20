package com.eprint.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "audit_logs", indexes = {
    @Index(name = "idx_audit_action", columnList = "action"),
    @Index(name = "idx_audit_user_id", columnList = "userId"),
    @Index(name = "idx_audit_entity_type", columnList = "entityType"),
    @Index(name = "idx_audit_entity_id", columnList = "entityId"),
    @Index(name = "idx_audit_order_number", columnList = "orderNumber"),
    @Index(name = "idx_audit_time", columnList = "time")
})
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;  // 操作类型（如：CREATE_ORDER、UPDATE_STATUS）

    @Column(length = 500)
    private String actionDescription;  // 操作描述

    private String userId;  // 操作用户ID

    private String entityType;  // 实体类型（Order / EngineeringOrder）

    private String entityId;  // 实体业务主键

    private String orderNumber;  // 订单号

    @Column(length = 2000)
    private String oldValue;  // 变更前的值

    @Column(length = 2000)
    private String newValue;  // 变更后的值

    private String ipAddress;  // 操作来源IP地址

    private LocalDateTime time;  // 操作时间

    public AuditLog() {
        this.time = LocalDateTime.now();
    }
}
