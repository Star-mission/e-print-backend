package com.eprint.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class IntermediaMaterialDTO {

    private String materialName;
    private String specification;
    private Integer quantity;
    private String unit;
    private LocalDateTime kaiShiRiQi;
    private LocalDateTime yuQiJieShu;
    private String dangQianJinDu;  // 改为 String 类型

    // 新增字段 - 来自 Change.md
    private Integer yiGouJianShu;  // 已采购件数
    private String head_PUR;  // 采购负责人
    private String head_OUT;  // 外发负责人

    private String notes;
}
