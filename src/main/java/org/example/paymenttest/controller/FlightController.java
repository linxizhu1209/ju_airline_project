package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.request.FlightSearchRequest;
import org.example.paymenttest.dto.response.FlightResponse;
import org.example.paymenttest.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/search")
    public ResponseEntity<List<FlightResponse>> searchFlights(@RequestBody FlightSearchRequest request) {
        List<FlightResponse> flights = flightService.searchFlights(request);
        return ResponseEntity.ok(flights);
    }




}
