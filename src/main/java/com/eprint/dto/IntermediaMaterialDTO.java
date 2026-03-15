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
    private Integer dangQianJinDu;
    private String notes;
}
