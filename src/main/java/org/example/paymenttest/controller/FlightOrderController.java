package org.example.paymenttest.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.RequiredArgsConstructor;
import org.example.paymenttest.dto.request.FlightOrderRequest;
import org.example.paymenttest.dto.response.FlightOrderResponse;
import org.example.paymenttest.entity.FlightOrder;
import org.example.paymenttest.service.FlightOrderService;
import org.example.paymenttest.service.QRCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class FlightOrderController {

    private final FlightOrderService flightOrderService;
    private final QRCodeService qrCodeService;

    @PostMapping("/flight")
    public ResponseEntity<Map<String, Object>> saveFlightOrder(@RequestBody FlightOrderRequest flightOrderRequest){
       return flightOrderService.saveFlightOrder(flightOrderRequest);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Map<String,Object>> confirmPayment(@RequestBody Map<String, Object> paymentData){
        Number flightOrderIdNumber = (Number) paymentData.get("flightOrderId");
        Long flightOrderId = flightOrderIdNumber.longValue();
        flightOrderService.confirmPayment(flightOrderId, paymentData.get("status"));
        FlightOrder flightOrder = flightOrderService.getFlightOrderById(flightOrderId);
        if (flightOrder == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid Flight Order"));
        }

        try {
            byte[] qrImageBytes = qrCodeService.generateSecureQRCodeImage(flightOrder.getTossOrderId());
            String base64Qr = Base64.getEncoder().encodeToString(qrImageBytes);

            Map<String, Object> response = Map.of(
                    "message", "Confirm payment successful",
                    "flightOrderId", flightOrderId,
                    "qrCodeBase64", base64Qr
            );
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
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
