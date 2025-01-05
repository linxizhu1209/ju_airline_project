package org.example.paymenttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserService userService;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // todo 만약 유저테이블에 이미 있는 사람이라면 바로 로그인되게끔하고,
        // todo 유저테이블에 없는 사람이라면, 해당 유저정보를 가지고 비밀번호 등 입력하여 회원가입하는 곳으로 이동하게 함
        // todo 이후 회원가입이 완료되면 다시 로그인해주세요!라는 창을 띄우고, 구글 로그인을 하면 유저테이블에 있을테니까 로그인 성공

        userService.saveUser(email,name);
        return oAuth2User;
    }

}
