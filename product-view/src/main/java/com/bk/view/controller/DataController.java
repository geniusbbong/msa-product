package com.bk.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bk.view.ProductClient;
import com.bk.view.service.PriceService;
import com.bk.view.service.ReviewService;
import com.bk.view.stopwatch.StopWatch;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/data")
public class DataController {

	@Autowired
	PriceService webClientService;

	@Autowired
	ReviewService templateService;

	@Autowired
	ProductClient productClient;

	@GetMapping("/test")
	@StopWatch
	public String test(@PathVariable String productId) {
		return "test...";
	}
	
	@GetMapping("/test2")
	@StopWatch
	public String test2(@PathVariable String productId) {
		return "test2...";
	}

	@GetMapping("/product/{productId}")
	@StopWatch
	public String productInfo(@PathVariable String productId) {
		return productClient.productInfo(productId);
	}

	@GetMapping("/price/{productId}")
	@StopWatch
	public Mono<String>price1(@PathVariable String productId) {
		return webClientService.price(productId);
	}

	@GetMapping("/price2/{productId}")
	@StopWatch
	public String price2(@PathVariable String productId) {
		return webClientService.priceBlock(productId);
	}

	@GetMapping("/retry/price/{productId}")
	@StopWatch
	public Mono<String> retry_price(@PathVariable String productId) {
		return webClientService.priceRetry(productId);
	}

	@GetMapping("/review/{productId}")
	@StopWatch
	public String review(@PathVariable String productId){
		return templateService.review(productId);
	}

	@GetMapping("/inventory/{productId}")
	@StopWatch
	public String inventory(@PathVariable String productId) {
		return templateService.inventory(productId);
	}

}
