package com.eprint.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkOrderDTO {

    private String work_id;
    private Integer work_ver;
    private String work_unique;
    private String workorderstatus;
    private String work_clerk;
    private String work_audit;
    private String customer;
    private String customerPO;
    private String productName;

    private Integer chuYangShuLiang;
    private Integer chaoBiLiShuLiang;

    private String beiZhu;

    private List<IntermediaMaterialDTO> intermedia;
    private List<AttachmentDTO> attachments;
    private List<AuditLogDTO> auditLogs;
}
