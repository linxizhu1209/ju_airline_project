package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.config.JwtUtil;
import org.example.paymenttest.entity.Role;
import org.example.paymenttest.entity.User;
import org.example.paymenttest.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> loginOrRegisterUser(String idToken) {
        String googleTokenInfoUrl = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + idToken;

        // google id 토큰 검증 요청
        ResponseEntity<Map> response = restTemplate.getForEntity(googleTokenInfoUrl, Map.class);
        Map<String, Object> userInfo = response.getBody();

        if(userInfo == null || !userInfo.containsKey("email")){
            return Map.of("error", "Invalid Google ID Token");
        }
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        Optional<User> existingUser = userRepository.findByEmail(email);

        User user;
        if(existingUser.isPresent()){
            user = existingUser.get();
        } else {
            user = User.builder().username(name).email(email).role(Role.USER).build();
            userRepository.save(user);
        }

        String jwtToken = jwtUtil.generateToken(user);

        Map<String,Object> loginResponse = new HashMap<>();
        loginResponse.put("token", jwtToken);
        loginResponse.put("user", user);

        return loginResponse;
    }

}
