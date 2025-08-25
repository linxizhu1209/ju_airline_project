package org.example.paymenttest.service;

import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.request.FlightSearchRequest;
import org.example.paymenttest.dto.response.FlightResponse;
import org.example.paymenttest.dto.response.FlightScheduleResponse;
import org.example.paymenttest.dto.response.FlightSearchResponse;
import org.example.paymenttest.dto.response.SeatMapResponse;
import org.example.paymenttest.entity.Airport;
import org.example.paymenttest.entity.Flight;
import org.example.paymenttest.entity.FlightSchedule;
import org.example.paymenttest.repository.FlightRepository;
import org.example.paymenttest.repository.FlightScheduleRepository;
import org.example.paymenttest.service.mapper.FlightMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightScheduleRepository flightScheduleRepository;
//    private final RestTemplate restTemplate;

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

//    public SeatMapResponse getSeatMap(String flightNumber, LocalDate departureDate, String accessToken) {
//        Map<String, Object> requestBody = createSeatMapRequestBody(flightNumber, departureDate);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//        ResponseEntity<SeatMapResponse> response = restTemplate.exchange(
//                "https://test.api.amadeus.com/v1/shopping/seatmaps",
//                HttpMethod.POST,
//                entity,
//                SeatMapResponse.class
//        );
//        return response.getBody();
//    }
}
