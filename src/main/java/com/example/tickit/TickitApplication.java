package com.example.tickit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TickitApplication {
	public static void main(String[] args) {
		SpringApplication.run(TickitApplication.class, args);
	}
}
