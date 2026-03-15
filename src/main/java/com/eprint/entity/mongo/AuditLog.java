package com.eprint.entity.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "audit_logs")
public class AuditLog {

    @Id
    private String id;

    @Indexed
    private String action;

    private String actionDescription;

    @Indexed
    private String userId;

    @Indexed
    private String entityType;

    @Indexed
    private String entityId;

    @Indexed
    private String orderNumber;

    private String oldValue;
    private String newValue;

    private String ipAddress;

    @Indexed
    private LocalDateTime time;

    public AuditLog() {
        this.time = LocalDateTime.now();
    }
}
