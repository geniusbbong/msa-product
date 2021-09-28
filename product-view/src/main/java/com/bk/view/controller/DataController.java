package com.bk.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bk.view.ProductClient;
import com.bk.view.service.InfoService;
import com.bk.view.service.InventoryService;
import com.bk.view.service.PriceService;
import com.bk.view.service.Response;
import com.bk.view.service.ReviewService;
import com.bk.view.stopwatch.StopWatch;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/data")
public class DataController {

	@Autowired
	PriceService priceService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	InfoService infoService;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	ProductClient productInfoFClientnet;

	@GetMapping("/test")
	@StopWatch
	public String test(@PathVariable String productId) {
		return "trigger_test...";
	}

	@GetMapping("/info/{productId}")
	@StopWatch
	public Response getInfo(@PathVariable String productId) {
		return productInfoFClientnet.get(productId);
	}

	@GetMapping("/info/fail/{productId}")
	@StopWatch
	public Response getInfoF(@PathVariable String productId) {
		return productInfoFClientnet.getFail(productId);
	}

	@GetMapping("/info/random/fail/{productId}")
	@StopWatch
	public Response getInfoRF(@PathVariable String productId) {
		return productInfoFClientnet.getRandomFail(productId);
	}

	@GetMapping("/info/random/delay/{productId}")
	@StopWatch
	public Response getInfoRD(@PathVariable String productId) {
		return productInfoFClientnet.getRandomDelay(productId);
	}

	@GetMapping("/review/{productId}")
	@StopWatch
	public Response getReview(@PathVariable String productId){
		return reviewService.get(productId);
	}

	@GetMapping("/review/fail/{productId}")
	@StopWatch
	public Response getReviewF(@PathVariable String productId){
		return reviewService.getFail(productId);
	}

	@GetMapping("/review/random/fail/{productId}")
	@StopWatch
	public Response getReviewRF(@PathVariable String productId){
		return reviewService.randomFail(productId);
	}

	@GetMapping("/review/random/delay/{productId}")
	@StopWatch
	public Response getReviewRD(@PathVariable String productId){
		return reviewService.randomDelay(productId, null);
	}

	@GetMapping("/inventory/{productId}")
	@StopWatch
	public Response getInventory(@PathVariable String productId){
		return inventoryService.get(productId);
	}

	@GetMapping("/inventory/fail/{productId}")
	@StopWatch
	public Response getInventoryF(@PathVariable String productId){
		return inventoryService.getFail(productId);
	}

	@GetMapping("/inventory/random/fail/{productId}")
	@StopWatch
	public Response getInventoryRF(@PathVariable String productId){
		return inventoryService.randomFail(productId);
	}

	@GetMapping("/inventory/random/delay/{productId}")
	@StopWatch
	public Response getInventoryRD(@PathVariable String productId){
		return inventoryService.randomDelay(productId, null);
	}


	@GetMapping("/price/{productId}")
	@StopWatch
	public Response getPrice(@PathVariable String productId) {
		return priceService.get(productId);
	}

	@GetMapping("/price/fail/{productId}")
	@StopWatch
	public Response getPriceF(@PathVariable String productId) {
		return priceService.getFail(productId);
	}

	@GetMapping("/price/random/fail/{productId}")
	@StopWatch
	public Response getPriceRF(@PathVariable String productId){
		return priceService.getRandomFail(productId);
	}

	@GetMapping("/price/random/delay/{productId}")
	@StopWatch
	public Response getPriceRD(@PathVariable String productId){
		return priceService.getRandomDelay(productId, null);
	}

	@GetMapping("/price/inventory/{productId}")
	@StopWatch
	public Response getPriceInventory(@PathVariable String productId) {
		return priceService.getWithInventory(productId);
	}

	@GetMapping("/price/inventory/fail/{productId}")
	@StopWatch
	public Response getPriceInventoryF(@PathVariable String productId){
		return priceService.getWithInventoryFail(productId);
	}

	@GetMapping("/price/inventory/random/fail/{productId}")
	@StopWatch
	public Response getPriceInventoryRF(@PathVariable String productId){
		return priceService.getWithInventoryRandomFail(productId);
	}

	@GetMapping("/price/inventory/random/delay/{productId}")
	@StopWatch
	public Response getPriceInventoryRD(@PathVariable String productId){
		return priceService.getWithInventoryRandomDelay(productId, null);
	}



}
