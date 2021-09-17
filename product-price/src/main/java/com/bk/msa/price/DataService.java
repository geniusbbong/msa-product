package com.bk.msa.price;

import java.awt.Adjustable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import reactor.core.publisher.Mono;

@Service
public class DataService {

	private static final String TEST_CIRCUIT_BREKER = "testCircuitBreaker";

	@Autowired
	WebClient.Builder webClientBuilder;

	public Mono<Response> getInfo(String productId) {

		return webClientBuilder.baseUrl("http://product-inventory").build().get().uri("/" + productId).retrieve()
				.bodyToMono(Response.class);
	}

	@Retry(name = TEST_CIRCUIT_BREKER)
	@CircuitBreaker(name = TEST_CIRCUIT_BREKER)
	public Mono<Response> getRandomFail(String productId) {

		return webClientBuilder.baseUrl("http://product-inventory/random/fail").build().get().uri("/" + productId)
				.retrieve().bodyToMono(Response.class);
	}

	@TimeLimiter(name = TEST_CIRCUIT_BREKER)
	@Retry(name = TEST_CIRCUIT_BREKER)
	@CircuitBreaker(name = TEST_CIRCUIT_BREKER)
	public Mono<Response> getRandomDelay(String productId, Integer delay) {

		StringBuilder sb = new StringBuilder("/" + productId);
		if (delay != null && delay > 0)
			sb.append("/" + delay);

		return webClientBuilder.baseUrl("http://product-inventory/random/delay").build().get().uri(sb.toString())
				.retrieve()
				.onStatus(httpstatus -> httpstatus != HttpStatus.OK, clientResponse -> {
					return clientResponse.createException()
					         .flatMap(it -> Mono.error(new RuntimeException("code : " + clientResponse.statusCode())));
					}
				)
				.bodyToMono(Response.class);
	}

	public Mono<String> fallback(String data, Throwable t) {
		System.out.println(data);
		System.out.println(String.format("Inside retryfallback, cause – {}", t.toString()));
		return Mono.just("fallback");
	}

	public Mono<String> randomDelayfallback(String data, Integer delay, Throwable t) {
		System.out.println(data + " / " + delay);
		System.out.println(String.format("Inside retryfallback, cause – {}", t.toString()));
		return Mono.just("fallback");
	}

}
