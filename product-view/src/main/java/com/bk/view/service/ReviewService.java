package com.bk.view.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class ReviewService {

	@Autowired
	RestTemplate restTemplate;

	@Retry(name = "review")
	@CircuitBreaker(name = "review", fallbackMethod = "fallback")
	public Response get(String productId) {
		return restTemplate.getForObject("http://product-review/" + productId, Response.class);
	}

	@CircuitBreaker(name = "review", fallbackMethod = "fallback")
	public Response getFail(String productId) {
		return restTemplate.getForObject("http://product-review/fail/" + productId, Response.class);
	}

	//@TimeLimiter(name = "review") //reactor에서만 사용 가능
	@Retry(name = "review")
	@CircuitBreaker(name = "review", fallbackMethod = "fallback")
	public Response randomFail(String productId) {
		return restTemplate.getForObject("http://product-review/random/fail/" + productId, Response.class);
	}

	@Retry(name = "review")
	@CircuitBreaker(name = "review", fallbackMethod = "fallback2")
	public Response randomDelay(String productId, Integer delay) {
		if( delay !=null && delay > 0)
			return restTemplate.getForObject("http://product-review/random/delay/" + productId + "/" + delay, Response.class);

		return restTemplate.getForObject("http://product-review/random/delay/" + productId, Response.class);
	}

	public Response fallback(String data, Throwable t) {
		System.out.println(String.format("Inside retryfallback, cause – {}", t.toString()));
		return Response.builder()
				.serviceName("product-review")
				.productId(data)
				.errorMsg(t.getMessage()).build();
	}

	public Response fallback2(String data, Integer delay, Throwable t) {
		System.out.println(String.format("Inside retryfallback, cause – {}", t.toString()));
		return Response.builder()
				.serviceName("product-review")
				.productId(data)
				.errorMsg(t.getMessage()).build();
	}

}
