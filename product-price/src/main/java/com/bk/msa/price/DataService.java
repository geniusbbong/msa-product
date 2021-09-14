package com.bk.msa.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import reactor.core.publisher.Mono;

@Service
public class DataService {

	private static final String TEST_CIRCUIT_BREKER = "testCircuitBreaker";

	@Autowired
	WebClient.Builder webClientBuilder;

	public Mono<Response> getInfo(String productId) {

		return webClientBuilder.baseUrl("http://product-inventory")
				.build()
				.get()
				.uri("/" + productId)
				.retrieve()
				.bodyToMono(Response.class);
	}

	@Retry(name = TEST_CIRCUIT_BREKER, fallbackMethod = "fallback")
	public Mono<Response> getRandomFail(String productId) {

		return webClientBuilder.baseUrl("http://product-inventory/random/fail")
				.build()
				.get()
				.uri("/" + productId)
				.retrieve()
				.bodyToMono(Response.class);
	}

	@TimeLimiter(name = TEST_CIRCUIT_BREKER)
	@Retry(name = TEST_CIRCUIT_BREKER)
	public Mono<Response> getRandomDelay(String productId, Integer delay) {

		StringBuilder sb = new StringBuilder("/" + productId);
		if(delay != null && delay > 0)
			sb.append( "/" + delay);

		return webClientBuilder.baseUrl("http://product-inventory/random/delay")
				.build()
				.get()
				.uri(sb.toString())
				.retrieve()
				.bodyToMono(Response.class);
	}

	public Mono<String> fallback(String data, Throwable t) {
		System.out.println(data);
		System.out.println(String.format("Inside retryfallback, cause – {}", t.toString()));
		return Mono.just("fallback");
	}

}
