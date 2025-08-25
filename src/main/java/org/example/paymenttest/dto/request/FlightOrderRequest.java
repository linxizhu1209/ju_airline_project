package org.example.paymenttest.dto.request;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FlightOrderRequest {

    @NotNull
    private String tossOrderId;

    @NotNull
    private int quantity;

    @NotNull
    private double totalPrice;

    @NotNull
    private double usdPrice;


    private String airlineName;
    private String airlineImageUrl;

    @NotNull
    private String departureAirportCode;

    @NotNull
    private String arrivalAirportCode;

    @NotNull
    private LocalDateTime departureDateTime;

    @NotNull
    private LocalDateTime arrivalDateTime;

    private String flightNumber;

    @NotNull
    private String userEmail;

    private String rawFlightOffer;

}
