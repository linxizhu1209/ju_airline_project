package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.Product;
import org.example.paymenttest.service.OrderService;
import org.example.paymenttest.service.PaymentService;
import org.example.paymenttest.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/commerce/purchasse")
public class PurchaseController {

    private final ProductService productService;
    private final PaymentService paymentService;
    private final OrderService orderService;
    @PostMapping("/{id}")
    public String initiatePayment(@PathVariable Long id, Model model){
        Product product = productService.findProductById(id);
        String paymentUrl = paymentService.getPaymentUrl(product);

        model.addAttribute("paymentUrl", paymentUrl);
        return "redirect:" + paymentUrl; //pg사 결제페이지로 리다이렉션
    }

    @PostMapping("/confirmation")
    public String paymentConfirmation(@RequestParam Map<String,String> paymentData){
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

    @GetMapping("/success")
    public String purchaseSuccess(){
        return "order-complete";
    }
    @GetMapping("/failure")
    public String purchaseFailure(){
        return "order-failure";
    }

}
