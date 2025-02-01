package org.example.paymenttest.repository;

import org.example.paymenttest.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

    List<Flight> findFlightsByDepartureAirportAndArrivalAirport(String departureAirport, String destinationAirport);

}
