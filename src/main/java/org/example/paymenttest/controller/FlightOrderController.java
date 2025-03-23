package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.request.FlightOrderRequest;
import org.example.paymenttest.dto.response.FlightOrderResponse;
import org.example.paymenttest.service.FlightOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class FlightOrderController {

    private final FlightOrderService flightOrderService;

    @PostMapping("/flight")
    public ResponseEntity<Map<String, Object>> saveFlightOrder(@RequestBody FlightOrderRequest flightOrderRequest){
       return flightOrderService.saveFlightOrder(flightOrderRequest);
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody Map<String, Object> paymentData){
        Number flightOrderIdNumber = (Number) paymentData.get("flightOrderId");
        Long flightOrderId = flightOrderIdNumber.longValue();
        flightOrderService.confirmPayment(flightOrderId, paymentData.get("status"));
        return ResponseEntity.ok("Confirm payment successful");
    }
    @GetMapping("/list")
    public ResponseEntity<List<FlightOrderResponse>> getOrderListByUserEmail(@RequestParam String userEmail) {
        List<FlightOrderResponse> bookings = flightOrderService.getOrderListByUserEmail(userEmail);
        if(bookings.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookings);
    }

}
