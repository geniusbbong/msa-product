package com.bk.msa.price;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProductPriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductPriceApplication.class, args);
	}

}
