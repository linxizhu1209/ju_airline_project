package org.example.paymenttest.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamportConfig {

    @Value("${iamport.key}")
    private String impKey;

    @Value("${iamport.secret}")
    private String impSecret;

    @Bean
    public IamportClient iamportClient(){
        return new IamportClient(impKey,impSecret);
    }


}
