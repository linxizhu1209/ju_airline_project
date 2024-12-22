package org.example.paymenttest.controller;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.Product;
import org.example.paymenttest.service.OrderService;
import org.example.paymenttest.service.PaymentService;
import org.example.paymenttest.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/commerce/payment")
public class PaymentController {

    private final ProductService productService;
    private final PaymentService paymentService;
    private final OrderService orderService;

    @PostMapping("/confirmation/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) {
        return paymentService.validateIamport(imp_uid);
    }

    @PostMapping("/order")
    public String saveOrder(@RequestParam Map<String,String> paymentData){
        boolean paymentSuccess = paymentData.get("status").equals("SUCCESS");

        if(paymentSuccess){
            Long productId = Long.parseLong(paymentData.get("productId"));
            Product product = productService.findProductById(productId);
            orderService.saveOrder(product, paymentData);
            return "redirect:/commerce/purchase/success";
        } else {
            return "redirect:/commerce/purchase/failure";
        }
    }
//
//    @GetMapping("/success")
//    public String purchaseSuccess(){
//        return "order-complete";
//    }
//    @GetMapping("/failure")
//    public String purchaseFailure(){
//        return "order-failure";
//    }

}
