package com.eprint.dto;

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
}
