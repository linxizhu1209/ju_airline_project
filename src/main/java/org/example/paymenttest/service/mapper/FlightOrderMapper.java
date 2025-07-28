package org.example.paymenttest.service.mapper;

import org.example.paymenttest.dto.response.FlightOrderResponse;
import org.example.paymenttest.entity.FlightOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightOrderMapper {

    FlightOrderMapper INSTANCE = Mappers.getMapper(FlightOrderMapper.class);

    @Mapping(target = "bookingDate", source = "createdAt")
    FlightOrderResponse toFlightOrderResponse(FlightOrder flightOrder);

    List<FlightOrderResponse> toFlightOrderResponses(List<FlightOrder> flightOrders);

}
