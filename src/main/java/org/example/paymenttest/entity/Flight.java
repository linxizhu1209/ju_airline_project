package org.example.paymenttest.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne
    @JoinColumn(name= "airline_id",nullable = false)
    private Airline airline;

    @ManyToOne
    @JoinColumn(name="departure_airport_id", nullable = false)
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name="arrival_airport_id", nullable = false)
    private Airport arrivalAirport;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<FlightSchedule> flightSchedules;

}
