package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.example.paymenttest.entity.User;
import org.example.paymenttest.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void signup(User user) {
        userRepository.save(user);
    }

    public boolean login(User user) {
        if(userRepository.existsByEmailAndPassword(user.getEmail(),user.getPassword())){
            System.out.println("로그인 성공");
            return true;
        }
        else {
            System.out.println("로그인 실패");
            return false;
        }
    }
}
