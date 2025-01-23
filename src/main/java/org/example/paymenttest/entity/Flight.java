package org.example.paymenttest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Flight {

    @Id
    @Column(length=10)
    private String flightId;

    @Column(nullable = false, length = 50)
    private String airline;

    @ManyToOne
    @JoinColumn(name="departure_airport_id", nullable = false)
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name="arrival_airport_id", nullable = false)
    private Airport arrivalAirport;
}
