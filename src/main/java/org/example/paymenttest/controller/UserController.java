package org.example.paymenttest.controller;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.service.KakaoService;
import org.example.paymenttest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    @PostMapping("/login")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> request){
        String idToken = request.get("idToken");
        if(idToken == null){
            return ResponseEntity.badRequest().body("Invalid request: No idToken provided");
        }

        Map<String,Object> loginResponse = userService.loginOrRegisterUser(idToken);
        if(loginResponse.containsKey("error")){
            return ResponseEntity.badRequest().body(loginResponse);
        }
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login/admin")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> request){
        Map<String,Object> loginResponse = userService.login(request.get("email"), request.get("password"));
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> request){
        String accessToken = request.get("accessToken");
        if(accessToken == null){
            return ResponseEntity.badRequest().body("Invalid request: No accessToken provided");
        }

        Map<String, Object> kakaoUser = kakaoService.getUserInfo(accessToken);
        if(kakaoUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("카카오 사용자 인증 싪패");
        }
        Map<String, Object> result = userService.loginOrRegisterUserFromKakao(kakaoUser);
        return ResponseEntity.ok(result);
    }

}
