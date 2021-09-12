package com.bk.msa.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProductReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductReviewApplication.class, args);
	}

}
