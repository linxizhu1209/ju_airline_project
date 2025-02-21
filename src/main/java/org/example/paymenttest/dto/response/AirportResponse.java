package org.example.paymenttest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.paymenttest.entity.Airport;

@Getter
@Setter
@AllArgsConstructor
public class AirportResponse {
    private String airportId;
    private String city;
    private String country;
    private String name;
}
