package com.eprint.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkOrderDTO {

    private String work_id;
    private String work_ver;  // 前端为 string 类型（如 "V1"）
    private String work_unique;
    private String workorderstatus;
    private String work_clerk;
    private String clerkDate;
    private String work_audit;
    private String auditDate;
    private String customer;
    private String customerPO;
    private String productName;
    private String chanPinGuiGe;
    private String gongDanLeiXing;
    private String caiLiao;
    private String chanPinLeiXing;
    private String zhiDanShiJian;

    private Integer dingDanShuLiang;
    private Integer chuYangShuLiang;
    private Integer chaoBiLiShuLiang;
    private Integer benChangFangSun;
    private String chuYangRiqiRequired;
    private String chuHuoRiqiRequired;

    // 生产进度字段
    private Integer zhuangDingJianShu;  // 已装订件数
    private String head_MNF;  // 生产负责人

    private String beiZhu;

    private List<IntermediaMaterialDTO> intermedia;
    private List<AttachmentDTO> attachments;
    private List<AuditLogDTO> auditLogs;
}
