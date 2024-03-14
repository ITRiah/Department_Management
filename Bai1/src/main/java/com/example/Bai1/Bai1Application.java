package com.example.Bai1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //Kết hợp Listener
@EnableFeignClients
public class Bai1Application {

	public static void main(String[] args) {
		SpringApplication.run(Bai1Application.class, args);
	}
}
