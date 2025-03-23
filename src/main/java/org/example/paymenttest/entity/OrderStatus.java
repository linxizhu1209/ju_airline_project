package org.example.paymenttest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public enum OrderStatus {

    PENDING,
    CONFIRMED,
    CANCELLED
}
