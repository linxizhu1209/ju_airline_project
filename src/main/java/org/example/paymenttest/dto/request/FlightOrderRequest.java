package org.example.paymenttest.dto.request;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightOrderRequest {

    @NotNull
    private String tossOrderId;

    @NotNull
    private Long flightScheduleId;

    @NotNull
    private int quantity;

    @NotNull
    private double totalPrice;

    @NotNull
    private String userEmail;

}
