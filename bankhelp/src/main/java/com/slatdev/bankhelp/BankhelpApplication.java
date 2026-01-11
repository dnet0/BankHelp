package com.slatdev.bankhelp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class BankhelpApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankhelpApplication.class, args);
	}

}