package org.example.paymenttest.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.paymenttest.entity.FlightSchedule;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class FlightScheduleResponse {
    private Long scheduleId;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String direction;

    // 🚨 `FlightResponse` 대신 flightId만 포함
    private String flightId;
}
