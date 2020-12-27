package com.example.jwtserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class JwtServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtServerApplication.class, args);
	}

	@GetMapping("/")
	public void m1(){
    System.out.println("hi");
	}

}
