package com.USSD.USSD_SERVICE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
public class UssdServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UssdServiceApplication.class, args);
	}

}
