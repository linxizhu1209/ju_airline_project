package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.User;
import org.example.paymenttest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/commerce/signup")
public class SignupController {

    private final UserService userService;
    @PostMapping
    public String signup(@ModelAttribute User user){
        userService.signup(user);
        return "redirect:/commerce/user/login";
    }

    @GetMapping
    public String signupPage(){
        return "signup";
    }

}
