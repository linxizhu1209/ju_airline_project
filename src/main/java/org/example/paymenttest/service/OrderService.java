package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.Order;
import org.example.paymenttest.entity.PaymentStatus;
import org.example.paymenttest.entity.Product;
import org.example.paymenttest.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void saveOrder(Product product, Map<String,String> paymentData){
        Order order = new Order();
        order.setProduct(product);
        order.setAmount(product.getPrice());
        order.setStatus(PaymentStatus.PAYMENT);
        order.setPaymentId(paymentData.get("transactionId"));
        orderRepository.save(order);

    }
}
