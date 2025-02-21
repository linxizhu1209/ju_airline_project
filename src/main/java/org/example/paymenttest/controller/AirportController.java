package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.response.AirportResponse;
import org.example.paymenttest.service.AirportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airport")
public class AirportController {

    private final AirportService airportService;

    @GetMapping("/list")
    public ResponseEntity<List<AirportResponse>> getAirports(){
        return airportService.getAirports();
    }

}
