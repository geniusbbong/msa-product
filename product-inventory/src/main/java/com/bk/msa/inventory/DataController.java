package com.bk.msa.inventory;

import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

	public static Integer PORT;

	@Value("${server.port}")
    public void setPort(Integer port){
		DataController.PORT = port;
    }

	public static String SERVICE_NAME;

	@Value("${spring.application.name}")
	public void setServiceName(String serviceName) {
		DataController.SERVICE_NAME = serviceName;
	}

	@GetMapping("/{id}")
	public Response info(@PathVariable(value = "id") String productId) throws UnknownHostException, InterruptedException {

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());

		int inventory = random.nextInt(100);
		Response res = new Response();
		res.setProductId(productId);
		res.setInventory(inventory);

		return res;
	}

	@GetMapping(path = {"/random/delay/{id}", "/random/delay/{id}/{maxSec}"})
	public Response randomDelay(@PathVariable(value = "id") String productId
			, @PathVariable(value = "maxSec", required = false) Optional<Integer> maxSec) throws UnknownHostException, InterruptedException {

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());

		int maxdelay= maxSec.orElse(1) * 1000;
		int delay = random.nextInt(maxdelay);
		Thread.sleep(delay);

		int inventory = random.nextInt(100);
		Response res = new Response();
		res.setProductId(productId);
		res.setInventory(inventory);
		res.setDelay(delay);
		return res;
	}


	@GetMapping("/random/fail/{id}")
	public Response randomFail(@PathVariable(value = "id") String productId) throws Exception {

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());

		if(random.nextInt(2) % 2 == 0)
			throw new RandomException(productId);

		int inventory = random.nextInt(100);

		Response res = new Response();
		res.setProductId(productId);
		res.setInventory(inventory);

		return res;
	}

}
