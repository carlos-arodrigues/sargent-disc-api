package com.sargentdisc.rodrigues.carlos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.sargentdisc.domain.model.userfile")
public class SargentDiscApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SargentDiscApiApplication.class, args);
	}
}
