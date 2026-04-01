package com.eprint.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private String productName;
    private Integer quantity;
    private String specification;
    private String unit;
    private Double unitPrice;
    private Double totalPrice;
    private String notes;
}
