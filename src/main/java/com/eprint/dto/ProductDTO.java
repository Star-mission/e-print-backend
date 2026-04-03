package com.eprint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDTO {

    private String productName;
    private Integer quantity;
    private String specification;
    private String unit;
    private Double unitPrice;
    private Double totalPrice;
    private String notes;
    private String beiZhu;  // 备注字段

    // 对齐前端 IProduct 字段
    private String neiWen;
    private String yongZhiChiCun;
    private Double houDu;
    private Double keZhong;
    private String chanDi;
    private String pinPai;
    private String zhiLei;

    @JsonProperty("FSC")
    private String FSC;
    private Integer yeShu;
    private String yinSe;
    private String zhuanSe;
    private String biaoMianChuLi;
    private String zhuangDingGongYi;
}
