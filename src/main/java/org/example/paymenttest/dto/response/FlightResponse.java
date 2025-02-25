package org.example.paymenttest.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.paymenttest.entity.Airline;
import org.example.paymenttest.entity.Flight;

import java.util.List;

@Builder
@Getter
@Setter
public class FlightResponse {

    private String flightId;
    private Airline airline;

    private AirportResponse departureAirport;
    private AirportResponse arrivalAirport;

    private List<FlightScheduleResponse> flightScheduleResponse;
}
