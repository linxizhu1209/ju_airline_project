package org.example.paymenttest.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.paymenttest.entity.Airport;
import org.example.paymenttest.entity.Flight;
import org.example.paymenttest.entity.FlightSchedule;

import java.util.List;
@Builder
@Setter
@Getter
public class FlightSearchResponse{

    private String airportId;
    private List<FlightResponse> flightResponse;


}
