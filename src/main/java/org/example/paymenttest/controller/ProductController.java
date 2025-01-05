package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.Product;
import org.example.paymenttest.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/commerce/product")
public class ProductController {

    private final ProductService productService;
    @GetMapping
    public String productPage(@AuthenticationPrincipal OAuth2User user, Model model){
        if(user!=null) {
            List<Product> products = productService.findAll();
            model.addAttribute("products", products);
            return "products";
        }
        return "로그인이 필요한 사이트입니다";
    }

    @GetMapping("/{id}")
    public String productDetail(Model model, @PathVariable Long id){
        Product product = productService.findProductById(id);
        model.addAttribute("product",product);
        return "product-detail";
    }
}
