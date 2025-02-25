package org.example.paymenttest.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.paymenttest.entity.FlightSchedule;

import java.time.LocalTime;

@Getter
@Setter
//@Builder
public class FlightScheduleResponse {
    private Long scheduleId;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String direction; // 방향
    private Double price;
    // 🚨 `FlightResponse` 대신 flightId만 포함
    private String flightId; // 가져올때 flightId가 같은 스케쥴을 다 가져올거고


    private String airlineName;
    private String airlineLogo;
    private String departureAirport;
    private String arrivalAirport;

    public FlightScheduleResponse(FlightSchedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.airlineName = schedule.getFlight().getAirline().getAirlineName();
        this.airlineLogo = schedule.getFlight().getAirline().getImageUrl();
        this.departureAirport = schedule.getFlight().getDepartureAirport().getCity();
        this.arrivalAirport = schedule.getFlight().getArrivalAirport().getCity();
        this.departureTime = schedule.getDepartureTime();
        this.arrivalTime = schedule.getArrivalTime();
        this.direction = schedule.getDirection();
        this.price = schedule.getPrice();
    }




}
