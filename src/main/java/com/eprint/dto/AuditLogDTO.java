package com.eprint.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditLogDTO {

    private String action;
    private String actionDescription;
    private String userId;
    private String oldValue;
    private String newValue;
    private LocalDateTime time;
}
