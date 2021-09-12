package com.bk.view.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class PriceService {

	@Autowired
	WebClient.Builder webClientBuilder;

	@io.github.resilience4j.retry.annotation.Retry(name = "testRetry", fallbackMethod = "fallback")
	public Mono<String> price(String productId) {
		return webClientBuilder.baseUrl("http://product-price").build().get() // get 방식
				.uri("/" + productId).retrieve() // body를 가지고 온다는 뜻
				.bodyToMono(String.class);
	}

	@io.github.resilience4j.retry.annotation.Retry(name = "testRetry", fallbackMethod = "fallback2")
	public String priceBlock(String productId) {
		return webClientBuilder.baseUrl("http://product-price").build().get() // get 방식
				.uri("/" + productId).retrieve() // body를 가지고 온다는 뜻
				.bodyToMono(String.class).block();
	}

	public Mono<String> priceRetry(String productId) {
		return webClientBuilder.baseUrl("http://product-price").build().get() // get 방식
				.uri("/" + productId).retrieve() // body를 가지고 온다는 뜻
				.bodyToMono(String.class).retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)));
	}

	public Mono<String> fallback(Exception e) {
		System.out.println(String.format("Inside retryfallback, cause – {}", e.toString()));
		return Mono.just("fallback");
	}

	public String fallback2(Exception e) {
		System.out.println(String.format("Inside retryfallback, cause – {}", e.toString()));
		return "fallback2";
	}
}
