package org.example.paymenttest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="airline")
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airlineId;

    @Column(nullable = false, unique = true, length = 100)
    private String airlineName;

    @Column(nullable = false, length = 255)
    private String imageUrl; // 항공사 로고 이미지 URL
}
