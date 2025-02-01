package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.request.FlightSearchRequest;
import org.example.paymenttest.dto.response.FlightResponse;
import org.example.paymenttest.dto.response.FlightSearchResponse;
import org.example.paymenttest.entity.Flight;
import org.example.paymenttest.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public List<FlightResponse> searchFlights(FlightSearchRequest request) {
        String airportId = request.getAirportId();
        String arrivalAirportId = request.getArrivalId();
        List<Flight> flights = flightRepository.findFlightsByDepartureAirportAndArrivalAirport(airportId, arrivalAirportId);

        // todo Mapper로 만들어서 dto 변환하기
        FlightSearchResponse.builder().flightResponse(flights).flightScheduleResponse().airportResponse().build();
    }
}
