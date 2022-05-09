package com.api.cambiobitcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CambioBitcoinApplication {

	public static void main(String[] args) {
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		SpringApplication.run(CambioBitcoinApplication.class, args);
	}

	@GetMapping("/")
	public String index(){
		return "Hello";
	}


}
