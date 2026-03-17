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

    // 新增字段 - 来自 Change.md
    private Integer zhuangDingJianShu;  // 已装订件数
    private String head_MNF;  // 生产负责人

    private String beiZhu;

    private List<IntermediaMaterialDTO> intermedia;
    private List<AttachmentDTO> attachments;
    private List<AuditLogDTO> auditLogs;
}
