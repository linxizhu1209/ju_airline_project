package org.example.paymenttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PaymentTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentTestApplication.class, args);
	}

}
