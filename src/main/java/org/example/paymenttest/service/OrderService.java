package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.Orders;
import org.example.paymenttest.entity.PaymentStatus;
import org.example.paymenttest.entity.Product;
import org.example.paymenttest.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void saveOrder(Product product, Map<String, String> paymentData){
        Orders order = new Orders();
        order.setProduct(product);
        order.setAmount(product.getPrice());
        order.setStatus(PaymentStatus.PAYMENT);
        order.setCreatedAt(LocalDateTime.now());
        order.setPaymentId(paymentData.get("payment_id"));
        orderRepository.save(order);
    }
}
