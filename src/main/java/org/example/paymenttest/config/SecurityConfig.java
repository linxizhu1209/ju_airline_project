package org.example.paymenttest.config;

import org.example.paymenttest.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
                       .authorizeHttpRequests(auth -> auth
                               .requestMatchers("/","/commerce/user/login","/oauth2/**", "/commerce/home", "/css/**", "/js/**", "/images/**").permitAll()
                               .requestMatchers("/commerce/product/**", "/commerce/payment/**").authenticated()
                               .anyRequest().authenticated()
                       )
                               .oauth2Login(oauth2 -> oauth2
                                       .loginPage("/commerce/user/login")
                                       .defaultSuccessUrl("/commerce/home",true)
                                       .failureUrl("/commerce/user/login?error=true")
                                       .userInfoEndpoint(userInfo -> userInfo
                                               .userService(customOAuth2UserService())
                                       )
                               ).csrf(AbstractHttpConfigurer::disable);
       return http.build();
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService(){
        return new CustomOAuth2UserService();
    }
}
