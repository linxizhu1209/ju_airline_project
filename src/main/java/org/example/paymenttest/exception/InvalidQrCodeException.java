package org.example.paymenttest.exception;

public class InvalidQrCodeException extends RuntimeException {
    public InvalidQrCodeException() {
        super("잘못된 QR 코드입니다.");
    }

    public InvalidQrCodeException(String message) {
        super(message);
    }
}
