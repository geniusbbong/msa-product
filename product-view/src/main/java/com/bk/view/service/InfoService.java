package com.bk.view.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class InfoService {

	@Autowired
	RestTemplate restTemplate;

	@Retry(name = "info")
	@CircuitBreaker(name = "info", fallbackMethod = "fallback")
	public Response get(String productId) {
		return restTemplate.getForObject("http://product-info/" + productId, Response.class);
	}

	@CircuitBreaker(name = "info", fallbackMethod = "fallback")
	public Response getFail(String productId) {
		return restTemplate.getForObject("http://product-info/fail/" + productId, Response.class);
	}

	@Retry(name = "info")
	@CircuitBreaker(name = "info", fallbackMethod = "fallback")
	public Response randomFail(String productId) {
		return restTemplate.getForObject("http://product-info/random/fail/" + productId, Response.class);
	}

	@Retry(name = "info")
	@CircuitBreaker(name = "info", fallbackMethod = "fallback2")
	public Response randomDelay(String productId, Integer delay) {
		if( delay !=null && delay > 0)
			return restTemplate.getForObject("http://product-info/random/delay/" + productId + "/" + delay, Response.class);

		return restTemplate.getForObject("http://product-info/random/delay/" + productId, Response.class);
	}

	public Response fallback(String data, Throwable t) {
		System.out.println(data);
		System.out.println(String.format("Inside retryfallback, cause – {}", t.toString()));
		return Response.builder()
				.serviceName("product-info")
				.productId(data)
				.errorMsg(t.getMessage()).build();
	}

	public Response fallback2(String data, Integer delay, Throwable t) {
		System.out.println(data);
		System.out.println(String.format("Inside retryfallback, cause – {}", t.toString()));
		return Response.builder()
				.serviceName("product-info")
				.productId(data)
				.errorMsg(t.getMessage()).build();
	}

}
