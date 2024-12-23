package org.example.paymenttest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name="payment_id")
    private String paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name="amount")
    private Double amount;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
