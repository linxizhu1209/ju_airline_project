package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.request.FlightSearchRequest;
import org.example.paymenttest.dto.response.FlightResponse;
import org.example.paymenttest.dto.response.FlightScheduleResponse;
import org.example.paymenttest.dto.response.FlightSearchResponse;
import org.example.paymenttest.dto.response.SeatMapResponse;
import org.example.paymenttest.entity.Airport;
import org.example.paymenttest.entity.FlightSchedule;
import org.example.paymenttest.service.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/search")
    public ResponseEntity<FlightSearchResponse> searchFlights(
            @RequestParam String departureAirportId,
            @RequestParam String arrivalAirportId
    ) {
        FlightSearchResponse flights = flightService.searchFlights(departureAirportId,arrivalAirportId);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/available-destination")
    public ResponseEntity<List<Airport>> searchDesFlights(
            @RequestParam String departureAirportId
    ){
        return flightService.searchDesFlights(departureAirportId);
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<FlightScheduleResponse>> searchFlightSchedules(
            @RequestParam String departureAirportId,
            @RequestParam String arrivalAirportId
    ) {
        return flightService.findFlightSchedules(departureAirportId,arrivalAirportId);
    }

//    @GetMapping("/amadeus/seatmap")
//    public ResponseEntity<?> getSeatMap(
//            @RequestParam String flightNumber,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
//            @RequestHeader("Authorization") String authorizationHeader
//            ) {
//
//        try{
//            String accessToken = authorizationHeader.replace("Bearer ", "");
//            SeatMapResponse response = flightService.getSeatMap(flightNumber, departureDate, accessToken);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
//        }
//
//    }

}
