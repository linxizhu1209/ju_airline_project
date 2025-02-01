package org.example.paymenttest.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.paymenttest.entity.Airport;
import org.example.paymenttest.entity.Flight;

import java.util.List;

@Builder
@Setter
@Getter
public class FlightSearchResponse{

    private List<AirportResponse> airport;
    private List<FlightResponse> flightResponse;
    private List<FlightScheduleResponse> flightScheduleResponse;

}
