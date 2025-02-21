package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.response.AirportResponse;
import org.example.paymenttest.entity.Airport;
import org.example.paymenttest.repository.AirportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AirportService {

    private final AirportRepository airportRepository;

    public ResponseEntity<List<AirportResponse>> getAirports() {
        List<Airport> airportList = airportRepository.findAll();
        List<AirportResponse> airportResponses = airportList.stream()
                .map(airport -> new AirportResponse(airport.getAirportId(), airport.getCity(), airport.getCountry(),airport.getName()))
                .toList();
        return ResponseEntity.ok(airportResponses);
    }
}
