package org.example.paymenttest.controller;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.example.paymenttest.exception.ExpiredQrCodeException;
import org.example.paymenttest.exception.InvalidQrCodeException;
import org.example.paymenttest.service.QRCodeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @PostMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@RequestBody Map<String, String> request){
        String reservationId = request.get("reservationId");
        try {
            byte[] qrImage = qrCodeService.generateSecureQRCodeImage(reservationId);
            return ResponseEntity.ok(qrImage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping("/decode")
    public ResponseEntity<Map<String,Object>> decodeQRCode(@RequestBody Map<String, String> request){
        String encryptedData = request.get("encryptedData");
        try {
            String reservationId = qrCodeService.decodeAndVerifyQrCodeData(encryptedData);
            Map<String, Object> response = Map.of(
                    "message", "Success",
                    "reservationId", reservationId
            );
            return ResponseEntity.ok(response);
        } catch (ExpiredQrCodeException e){
            return ResponseEntity.badRequest().body(
                    Map.of("error", "expired_qr", "message", "QR 코드가 만료되었습니다."));
        } catch (InvalidQrCodeException e){
            return ResponseEntity.badRequest().body(
                    Map.of("error", "invalid_qr", "message", "잘못된 qr 코드 입니다.")
            );
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("message", "비정상적인 접근입니다."));
        }
    }

}
