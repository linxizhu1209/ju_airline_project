package org.example.paymenttest.repository;

import org.example.paymenttest.entity.FlightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightScheduleRepository extends JpaRepository<FlightSchedule, Long> {

    // 특정 출발지와 도착지에 해당하는 항공편 스케줄 검색
    @Query("SELECT fs FROM FlightSchedule fs WHERE fs.flight.departureAirport.airportId = :departureAirportId " +
            "AND fs.flight.arrivalAirport.airportId = :arrivalAirportId " +
            "ORDER BY fs.departureTime ASC")
    List<FlightSchedule> findFlightSchedules(@Param("departureAirportId") String departureAirportId,
                                             @Param("arrivalAirportId") String arrivalAirportId);

}
