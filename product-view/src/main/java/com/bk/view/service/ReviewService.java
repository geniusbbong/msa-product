package com.bk.view.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
public class ReviewService {

	@Autowired
	RestTemplate restTemplate;

	@Retry(name = "testRetry", fallbackMethod = "fallback")
	public String review(String productId) {
		return restTemplate.getForObject("http://product-review/" + productId, String.class);
	}

	//@TimeLimiter(name = "testCircuitBreaker") //reactor에서만 사용 가능
	@Retry(name = "testCircuitBreaker")
	@CircuitBreaker(name = "testCircuitBreaker", fallbackMethod = "fallback")
	public String inventory(String productId) {
		return restTemplate.getForObject("http://product-inventory/" + productId, String.class);
	}

	public String fallback(String data, Throwable t) {
		System.out.println(data);
		System.out.println(String.format("Inside retryfallback, cause – {}", t.toString()));
		return "fallback";
	}

}
