package org.example.paymenttest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {

    private static final String SECRET_KEY = "my-secret-key";

    public byte[] generateSecureQRCodeImage(String text) throws Exception {
        long expiryTime = System.currentTimeMillis() + (5*60*1000); // 5분 후 만료

        Map<String, Object> qrData = new HashMap<>();
        qrData.put("text", text);
        qrData.put("exp", expiryTime);

        String signature = generateSignature(text, expiryTime);
        qrData.put("signature", signature);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(qrData);

        String base63Encoded = Base64.getEncoder().encodeToString(jsonString.getBytes());

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(base63Encoded, BarcodeFormat.QR_CODE, 200,200 );

        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        }
    }

    private String generateSignature(String text, long expiryTime) throws Exception {
        String data = text + ":" + expiryTime;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secretKeySpec);
        byte[] hash = sha256_HMAC.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public String decodeAndVerifyQrCodeData(String encryptedData) throws Exception {

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        String jsonString = new String(decodedBytes);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> qrData = objectMapper.readValue(jsonString, Map.class);

        String reservationId = (String) qrData.get("reservationId");
        Number expNumber = (Number) qrData.get("exp");
        String signature = (String) qrData.get("signature");

        if(reservationId == null || expNumber == null || signature == null){
            throw new IllegalArgumentException("QR 데이터 형식 오류");
        }

        long expiryTime = expNumber.longValue();

        if(System.currentTimeMillis() > expiryTime){
            throw new IllegalArgumentException("qr 코드 만료됨"); // todo qr코드 만료로 다시 접속하라고 안내문
        }

        String expectedSignature = generateSignature(reservationId, expiryTime);
        if(!signature.equals(expectedSignature)){
            throw new IllegalArgumentException("QR 코드 위변조 감지됨");
        }

        return reservationId;

    }
}
