package com.eprint.dto;

import lombok.Data;

@Data
public class AuditLogDTO {

    private String action;
    private String comment;       // 前端字段名（对应后端 actionDescription）
    private String operator;      // 前端字段名（对应后端 userId）
    private String oldValue;
    private String newValue;
    private String time;          // 前端为 string 类型
}
