package org.example.paymenttest.service.mapper;

import org.example.paymenttest.dto.response.AirportResponse;
import org.example.paymenttest.dto.response.FlightResponse;
import org.example.paymenttest.dto.response.FlightScheduleResponse;
import org.example.paymenttest.entity.Airport;
import org.example.paymenttest.entity.Flight;
import org.example.paymenttest.entity.FlightSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FlightMapper {

    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @Mapping(target="departureAirport", source="departureAirport")
    @Mapping(target="arrivalAirport", source="arrivalAirport")
    @Mapping(target="flightScheduleResponse", source="flightSchedules")
    FlightResponse flightEntityToRes(Flight flight);

    List<FlightResponse> flightEntitiesToResponses(List<Flight> flights);

    AirportResponse airportEntityToRes(Airport airport);

    List<AirportResponse> airportEntitiesToResponses(List<Airport> airports);

    // ✅ FlightSchedule → FlightScheduleResponse 변환
    @Mapping(target = "scheduleId", source = "schedule.scheduleId")
    @Mapping(target = "departureTime", source = "schedule.departureTime")
    @Mapping(target = "arrivalTime", source = "schedule.arrivalTime")
    FlightScheduleResponse scheduleEntityToRes(FlightSchedule schedule);

    List<FlightScheduleResponse> scheduleEntitiesToResponses(List<FlightSchedule> schedules);


}
