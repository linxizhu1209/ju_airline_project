package org.example.paymenttest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FlightSchedule> flightSchedules;

}
