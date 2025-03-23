package org.example.paymenttest.service.mapper;

import org.example.paymenttest.dto.response.FlightOrderResponse;
import org.example.paymenttest.entity.FlightOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FlightOrderMapper {

    FlightOrderMapper INSTANCE = Mappers.getMapper(FlightOrderMapper.class);

    @Mapping(target = "flightScheduleId", source = "flightSchedule.scheduleId")
    @Mapping(target = "airlineName", source = "flightSchedule.flight.airline.name")
    @Mapping(target = "departureAirport", source = "flightSchedule.flight.departureAirport.name")
    @Mapping(target = "arrivalAirport", source = "flightSchedule.flight.arrivalAirport.name")
    @Mapping(target="departureTime", source = "flightSchedule.departureTime")
    @Mapping(target="arrivalTime", source = "flightSchedule.arrivalTime")
    @Mapping(target = "flightId", source = "flightSchedule.flight.flightId")
    @Mapping(target = "user", source = "user")
    FlightOrderResponse toFlightOrderResponse(FlightOrder flightOrder);

}
