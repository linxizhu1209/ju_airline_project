package org.example.paymenttest.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.paymenttest.entity.FlightSchedule;
import org.example.paymenttest.entity.OrderStatus;
import org.example.paymenttest.entity.User;

@Getter
@Setter
public class FlightOrderResponse {
    private Long flightOrderId;

    private String tossOrderId;

    private int quantity;

    private double totalPrice;

    private OrderStatus orderStatus;

    private User user;

    private String flightScheduleId;

    private String departureAirport;

    private String arrivalAirport;

    private String departureTime;

    private String arrivalTime;

    private String airlineName;

    private String flightId;
}
