package com.bk.msa.price;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class DataController {

	@Autowired
	DataService dataService;

	public static Integer PORT;

	@Value("${server.port}")
	public void setPort(Integer port) {
		DataController.PORT = port;
	}

	public static String SERVICE_NAME;

	@Value("${spring.application.name}")
	public void setServiceName(String serviceName) {
		DataController.SERVICE_NAME = serviceName;
	}

	@GetMapping("/{id}")
	public Response info(@PathVariable(value = "id") String productId)
			throws UnknownHostException, InterruptedException {

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());

		int price = 1000 + random.nextInt(100) * 10;
		Response res = new Response();
		res.setProductId(productId);
		res.setPrice(price);

		return res;
	}

	@GetMapping("/fail/{id}")
	public Response fail(@PathVariable(value = "id") String productId) throws Exception {
		throw new Exception("Excpetion method called - " + productId);
	}

	@GetMapping(path = { "/random/delay/{id}", "/random/delay/{id}/{maxSec}" })
	public Response randomDelay(@PathVariable(value = "id") String productId,
			@PathVariable(value = "maxSec", required = false) Optional<Integer> maxSec)
			throws UnknownHostException, InterruptedException {

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());

		int maxdelay = maxSec.orElse(1) * 1000;
		int delay = random.nextInt(maxdelay);
		Thread.sleep(delay);

		int price = 1000 + random.nextInt(100) * 10;

		Response res = new Response();
		res.setProductId(productId);
		res.setPrice(price);
		res.setDelay(delay);
		return res;
	}

	@GetMapping("/random/fail/{id}")
	public Response randomFail(@PathVariable(value = "id") String productId) throws Exception {

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());

		if (random.nextInt(2) % 2 == 0)
			throw new RandomException(productId);

		int price = 1000 + random.nextInt(100) * 10;

		Response res = new Response();
		res.setProductId(productId);
		res.setPrice(price);

		return res;
	}

	@GetMapping("/inventory/{id}")
	public Mono<Response> getInventory(@PathVariable(value = "id") String productId) {
		return dataService.getInfo(productId);
	}

	@GetMapping("/withinventory/{id}")
	public Mono<Response> getWithInventory(@PathVariable(value = "id") String productId) {
		return dataService.getInfo(productId).map(ret -> {
			Random random = new Random();
			random.setSeed(System.currentTimeMillis());

			int price = 1000 + random.nextInt(100) * 10;
			ret.setPrice(price);

			ret.setServiceName2(SERVICE_NAME);

			try {
				String ip = InetAddress.getLocalHost().getHostAddress();
				ret.setIpaddress2(String.format("%s:%d", ip, DataController.PORT));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			return ret;
		}).onErrorResume(e -> Mono.just(fallback(productId, e.getMessage())));
	}

	@GetMapping("/withinventory/fail/{id}")
	public Mono<Response> getWithInventoryF(@PathVariable(value = "id") String productId) {
		return dataService.getFail(productId).map(ret -> {
			Random random = new Random();
			random.setSeed(System.currentTimeMillis());

			int price = 1000 + random.nextInt(100) * 10;
			ret.setPrice(price);

			ret.setServiceName2(SERVICE_NAME);

			try {
				String ip = InetAddress.getLocalHost().getHostAddress();
				ret.setIpaddress2(String.format("%s:%d", ip, DataController.PORT));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			return ret;
		}).onErrorResume(e -> Mono.just(fallback(productId, e.getMessage())));
	}

	@GetMapping("/withinventory/random/fail/{id}")
	public Mono<Response> getWithInventoryRandomFail(@PathVariable(value = "id") String productId) {
		return dataService.getRandomFail(productId).map(ret -> {
			Random random = new Random();
			random.setSeed(System.currentTimeMillis());

			int price = 1000 + random.nextInt(100) * 10;
			ret.setPrice(price);

			ret.setServiceName2(SERVICE_NAME);

			try {
				String ip = InetAddress.getLocalHost().getHostAddress();
				ret.setIpaddress2(String.format("%s:%d", ip, DataController.PORT));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			return ret;
		}).onErrorResume(e -> Mono.just(fallback(productId, e.getMessage())));
	}

	@GetMapping(path = { "/withinventory/random/delay/{id}", "/withinventory/random/delay/{id}/{maxSec}" })
	public Mono<Response> getWithInventoryRandomDelay(@PathVariable(value = "id") String productId,
			@PathVariable(value = "maxSec", required = false) Optional<Integer> delay) {
		return dataService.getRandomDelay(productId, delay.orElse(null)).map(ret -> {
			Random random = new Random();
			random.setSeed(System.currentTimeMillis());

			int price = 1000 + random.nextInt(100) * 10;
			ret.setPrice(price);

			ret.setServiceName2(SERVICE_NAME);

			try {
				String ip = InetAddress.getLocalHost().getHostAddress();
				ret.setIpaddress2(String.format("%s:%d", ip, DataController.PORT));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			return ret;
		}).onErrorResume(e -> Mono.just(fallback(productId, e.getMessage())));
	}

	public Response fallback(String productId, String errMsg){
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());

		int price = 1000 + random.nextInt(100) * 10;
		Response res = new Response();
		res.setProductId(productId);
		res.setPrice(price);
		res.setErrorMsg(errMsg);

		return res;
	}

}
