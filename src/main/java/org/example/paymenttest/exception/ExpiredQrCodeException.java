package org.example.paymenttest.exception;

public class ExpiredQrCodeException extends RuntimeException {
    public ExpiredQrCodeException() {
        super("QR 코드가 만료되었습니다."); // 기본 에러 메시지
    }

    public ExpiredQrCodeException(String message) {
        super(message); // 필요하면 메시지 바꿀 수도 있게
    }
}
