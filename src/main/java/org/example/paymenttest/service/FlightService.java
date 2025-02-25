package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.request.FlightSearchRequest;
import org.example.paymenttest.dto.response.FlightResponse;
import org.example.paymenttest.dto.response.FlightScheduleResponse;
import org.example.paymenttest.dto.response.FlightSearchResponse;
import org.example.paymenttest.entity.Airport;
import org.example.paymenttest.entity.Flight;
import org.example.paymenttest.entity.FlightSchedule;
import org.example.paymenttest.repository.FlightRepository;
import org.example.paymenttest.repository.FlightScheduleRepository;
import org.example.paymenttest.service.mapper.FlightMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightScheduleRepository flightScheduleRepository;

    public FlightSearchResponse searchFlights(String departureAirportId,String arrivalAirportId) {
        List<Flight> flights = flightRepository.findFlightsWithSchedule(departureAirportId, arrivalAirportId);
        List<FlightResponse> flightResponses = FlightMapper.INSTANCE.flightEntitiesToResponses(flights);

        return FlightSearchResponse.builder()
                .airportId(departureAirportId)
                .flightResponse(flightResponses)
                .build();
    }

    public ResponseEntity<List<Airport>> searchDesFlights(String departureAirportId) {
        List<Airport> airportList = flightRepository.findDesAirport(departureAirportId);
        return ResponseEntity.ok(airportList);
    }

    public ResponseEntity<List<FlightScheduleResponse>> findFlightSchedules(String departureAirportId, String arrivalAirportId) {
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findFlightSchedules(departureAirportId, arrivalAirportId);

        List<FlightScheduleResponse> responseList = flightSchedules.stream()
                .map(FlightScheduleResponse::new).toList();
        return ResponseEntity.ok(responseList);
    }
}
