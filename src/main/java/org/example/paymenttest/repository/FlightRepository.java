package org.example.paymenttest.repository;

import org.example.paymenttest.entity.Airport;
import org.example.paymenttest.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

//    List<Flight> findFlightsByDepartureAirportAndArrivalAirport(String departureAirport, String destinationAirport);

    @Query("SELECT f FROM Flight f "+
            "join fetch f.departureAirport da " +
            "join fetch f.arrivalAirport aa " +
            "JOIN FETCH f.flightSchedules fs "
            + "WHERE da.airportId = :departureAirportId " +
            "AND aa.airportId = :arrivalAirportId ")
    List<Flight> findFlightsWithSchedule(@Param("departureAirportId") String departureAirportId,
                                         @Param("arrivalAirportId") String arrivalAirportId);

    @Query("SELECT DISTINCT f.arrivalAirport from Flight f where f.departureAirport.airportId = :departureAirportId " +
            "UNION "+
            "SELECT  DISTINCT f.departureAirport from Flight f where f.arrivalAirport.airportId = :departureAirportId")
    List<Airport> findDesAirport(String departureAirportId);
}
