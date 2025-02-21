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

    private String airportId;
     // todo 두개의 공항이름을 주면, 그 공항(airportId)에서 다른 공항(airportId)으로 가는 항공편(Flight)을 찾을테니까
    private List<FlightResponse> flightResponse;
    // todo flightResponse에 flightSchedule까지 함께 있어야함

}
