package org.example.paymenttest.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.paymenttest.entity.FlightSchedule;
import org.example.paymenttest.entity.OrderStatus;
import org.example.paymenttest.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class FlightOrderResponse {
    private Long flightOrderId;
    private String tossOrderId;

    private String airlineName;
    private String airlineImageUrl;

    private String departureAirportCode;
    private String arrivalAirportCode;

    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;

    private String flightNumber;

    private int quantity;
    private double totalPrice;
    private OrderStatus orderStatus;

    private LocalDateTime bookingDate; // createdAt
}
