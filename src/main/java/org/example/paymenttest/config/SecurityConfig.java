package org.example.paymenttest.config;

import org.example.paymenttest.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception {
       http
                       .authorizeHttpRequests(auth -> auth
                               .requestMatchers("/","/commerce/user/login","/oauth2/**", "/commerce/home", "/chat/**", "/js/**", "/images/**", "/flight/**", "/airport/**", "/payment/**",
                                       "/order/**", "/login/**", "/auth/**", "/ws-chat/**", "/app/**", "/topic/**","/chat.sendMessage","/api/**").permitAll()
                               .requestMatchers("/chat/admin/**").hasRole("ADMIN")
                               .requestMatchers("/commerce/product/**").authenticated()
                               .anyRequest().authenticated()
                       )
                               .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                               .oauth2Login(oauth2 -> oauth2
                                       .defaultSuccessUrl("/commerce/home",true)
                                       .failureUrl("/commerce/home?error=true")
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
