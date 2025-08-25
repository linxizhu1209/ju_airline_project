package org.example.paymenttest.service.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.paymenttest.dto.response.FlightOrderResponse;
import org.example.paymenttest.entity.FlightOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface FlightOrderMapper {

    FlightOrderMapper INSTANCE = Mappers.getMapper(FlightOrderMapper.class);

    @Mapping(target = "bookingDate", source = "createdAt")
    @Mapping(target = "rawFlightOffer", expression = "java(parseRawFlightOffer(flightOrder.getRawFLightOffer()))")
    FlightOrderResponse toFlightOrderResponse(FlightOrder flightOrder);

    List<FlightOrderResponse> toFlightOrderResponses(List<FlightOrder> flightOrders);


    default Map<String, Object> parseRawFlightOffer(String rawJson) {
        if (rawJson == null || rawJson.isEmpty()) return null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(rawJson, new TypeReference<>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
