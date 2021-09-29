package com.bk.scg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ScgApplication {

	public static void main(String[] args) {

//		String regString = "/cart/(?<path>.*),/$\\{path}";
//		String inputString = "http://123.3.3.3:8080/cart/a/b/c";
//		String[] restlt = inputString.split(regString);
//		for(String str : restlt)
//			System.out.println(str);

	    System.setProperty("reactor.netty.http.server.accessLogEnabled", "true");

		SpringApplication.run(ScgApplication.class, args);
	}

}
