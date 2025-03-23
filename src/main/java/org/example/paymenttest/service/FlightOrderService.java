package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.request.FlightOrderRequest;
import org.example.paymenttest.dto.response.FlightOrderResponse;
import org.example.paymenttest.entity.*;
import org.example.paymenttest.repository.FlightOrderRepository;
import org.example.paymenttest.repository.FlightScheduleRepository;
import org.example.paymenttest.repository.UserRepository;
import org.example.paymenttest.service.mapper.FlightOrderMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FlightOrderService {

    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightOrderRepository flightOrderRepository;
    private final UserRepository userRepository;
    private final FlightOrderMapper flightOrderMapper;

    public List<FlightOrderResponse> getOrderListByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        List<FlightOrder> flightOrder = flightOrderRepository.findFlightOrderListByUserId(user.getId());
        return flightOrder.stream()
                .map(flightOrderMapper::toFlightOrderResponse)
                .toList();
    }

    public ResponseEntity<Map<String, Object>> saveFlightOrder(FlightOrderRequest flightOrderRequest) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(flightOrderRequest.getFlightScheduleId()).orElse(null);
        User user = userRepository.findByEmail(flightOrderRequest.getUserEmail()).orElse(null);
        FlightOrder flightOrder
                = FlightOrder.builder()
                .tossOrderId(flightOrderRequest.getTossOrderId())
                .flightSchedule(flightSchedule)
                .totalPrice(flightOrderRequest.getTotalPrice())
                .quantity(flightOrderRequest.getQuantity())
                .orderStatus(OrderStatus.PENDING)
                .user(user)
                .build();

        FlightOrder fo = flightOrderRepository.save(flightOrder);
        Map<String, Object> response = new HashMap<>();
        response.put("flightOrderId", fo.getFlightOrderId());

        return ResponseEntity.ok(response);
    }

    public void confirmPayment(Long flightOrderId, Object status) {
        FlightOrder flightOrder = flightOrderRepository.findById(flightOrderId).orElse(null);
        if(status instanceof String){
            try {
                OrderStatus orderStatus = OrderStatus.valueOf(status.toString().toUpperCase());
                if(orderStatus == OrderStatus.CONFIRMED){
                    flightOrder.setOrderStatus(OrderStatus.CONFIRMED);
                }
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }

        if(flightOrder != null) {
            flightOrderRepository.save(flightOrder);
        }
    }
}
