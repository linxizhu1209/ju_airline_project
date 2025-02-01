package org.example.paymenttest.service.mapper;

import org.example.paymenttest.dto.response.FlightResponse;
import org.example.paymenttest.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlightMapper {

    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @Mapping(target="flightId",source="flightId")
    @Mapping(target="airline",source="airline")
    @Mapping(target="arrivalAirportId",source="arrivalAirportId")
    @Mapping(target="departureAirportId",source="departureAirportId")
    FlightResponse flightEntityToRes(Flight flight);



}
