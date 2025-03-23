package org.example.paymenttest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="flight_order")
public class FlightOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightOrderId;

    @Column(nullable = true)
    private String tossOrderId;

    @ManyToOne
    @JoinColumn(name = "flightScheduledId", nullable = false)
    private FlightSchedule flightSchedule;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING; // 기본값을 대기상태로

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
