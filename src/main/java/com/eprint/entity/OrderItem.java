package com.eprint.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String productName;
    private Integer quantity;
    private String specification;
    private String unit;
    private Double unitPrice;
    private Double totalPrice;

    @Column(length = 1000)
    private String notes;
}
