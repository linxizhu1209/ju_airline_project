package org.example.paymenttest.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightSearchRequest {

    private String airportId; // 출발지 id
    private String arrivalId; // 도착지 id
    private String departureDate; // 출발 날짜
    private String arrivalDate; // 돌아오는 날짜
    private int passengers;
}
