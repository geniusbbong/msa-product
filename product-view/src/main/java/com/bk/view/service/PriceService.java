package com.bk.view.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class PriceService {

	@Autowired
	WebClient.Builder webClientBuilder;

	@Retry(name = "price")
	@CircuitBreaker(name = "price", fallbackMethod = "fallback")
	public Response get(String productId) {
		return webClientBuilder.baseUrl("http://product-price").build().get() // get 방식
				.uri("/" + productId).retrieve() // body를 가지고 온다는 뜻
				.bodyToMono(Response.class).block();
	}

	@CircuitBreaker(name = "price", fallbackMethod = "fallback")
	public Response getFail(String productId) {
		return webClientBuilder.baseUrl("http://product-price").build().get() // get 방식
				.uri("/fail/" + productId).retrieve() // body를 가지고 온다는 뜻
				.bodyToMono(Response.class).block();
	}

	@Retry(name = "price")
	@CircuitBreaker(name = "price", fallbackMethod = "fallback")
	public Response getRandomFail(String productId) {
		return webClientBuilder.baseUrl("http://product-price").build().get() // get 방식
				.uri("/random/fail/" + productId).retrieve() // body를 가지고 온다는 뜻
				.bodyToMono(Response.class).block();
	}

	@Retry(name = "price")
	@CircuitBreaker(name = "price", fallbackMethod = "fallback2")
	public Response getRandomDelay(String productId, Integer delay) {

		if (delay != null && delay > 0)
			return webClientBuilder.baseUrl("http://product-price").build().get()
					.uri("/random/delay/" + productId + "/" + delay).retrieve().bodyToMono(Response.class).block();

		return webClientBuilder.baseUrl("http://product-price").build().get().uri("/random/delay/" + productId)
				.retrieve().bodyToMono(Response.class).block();
	}

	@Retry(name = "price")
	@CircuitBreaker(name = "price", fallbackMethod = "fallback")
	public Response getWithInventory(String productId) {
		return webClientBuilder.baseUrl("http://product-price").build().get() // get 방식
				.uri("/withinventory/" + productId).retrieve() // body를 가지고 온다는 뜻
				.bodyToMono(Response.class).block();
	}

	@Retry(name = "price")
	@CircuitBreaker(name = "price", fallbackMethod = "fallback")
	public Response getWithInventoryFail(String productId) {
		return webClientBuilder.baseUrl("http://product-price").build().get() // get 방식
				.uri("/withinventory/fail/" + productId).retrieve() // body를 가지고 온다는 뜻
				.bodyToMono(Response.class).block();
	}

	@Retry(name = "price")
	@CircuitBreaker(name = "price", fallbackMethod = "fallback")
	public Response getWithInventoryRandomFail(String productId) {
		return webClientBuilder.baseUrl("http://product-price").build().get() // get 방식
				.uri("/withinventory/random/fail/" + productId).retrieve() // body를 가지고 온다는 뜻
				.bodyToMono(Response.class).block();
	}

	@Retry(name = "price")
	@CircuitBreaker(name = "price", fallbackMethod = "fallback2")
	public Response getWithInventoryRandomDelay(String productId, Integer delay) {

		if (delay != null && delay > 0)
			return webClientBuilder.baseUrl("http://product-price").build().get()
					.uri("/withinventory/random/delay/" + productId + "/" + delay).retrieve().bodyToMono(Response.class)
					.block();

		return webClientBuilder.baseUrl("http://product-price").build().get()
				.uri("/withinventory/random/delay/" + productId).retrieve().bodyToMono(Response.class).block();
	}

//	public Mono<String> priceRetry(String productId) {
//		return webClientBuilder.baseUrl("http://product-price").build().get() // get 방식
//				.uri("/" + productId).retrieve() // body를 가지고 온다는 뜻
//				.bodyToMono(String.class).retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)));
//	}

	public Response fallback(String productId, Exception e) {
		System.out.println(String.format("Inside retryfallback, cause – {}", e.toString()));
		return Response.builder().serviceName("product-price").productId(productId).errorMsg("fallback").build();
	}

	public Response fallback2(String productId, Integer delay, Exception e) {
		System.out.println(String.format("Inside retryfallback, cause – {}", e.toString()));
		return Response.builder().serviceName("product-price").productId(productId).errorMsg("fallback").build();
	}
}
