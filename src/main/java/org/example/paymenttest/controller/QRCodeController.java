package org.example.paymenttest.controller;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
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
            byte[] qrImage = qrCodeService.generateQRCodeImage(reservationId);
            return ResponseEntity.ok(qrImage);
        } catch (WriterException | IOException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
