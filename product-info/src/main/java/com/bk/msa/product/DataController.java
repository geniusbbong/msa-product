package com.bk.msa.product;

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
	public Response info(@PathVariable(value = "id") String productId) throws UnknownHostException {
		Response res = new Response();
		res.setProductId(productId);
		res.setInfo(productId);
		return res;
	}

	@GetMapping("/fail/{id}")
	public Response fail(@PathVariable(value = "id") String productId) throws Exception {
		throw new Exception("Excpetion method called - " + productId);
	}

	@GetMapping(path = {"/random/delay/{id}", "/random/delay/{id}/{maxSec}"})
	public Response randomdelay(@PathVariable(value = "id") String productId
			, @PathVariable(value = "maxSec", required = false) Optional<Integer> maxSec) throws UnknownHostException, InterruptedException {

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());

		int maxdelay= maxSec.orElse(1) * 1000;

		int delay = random.nextInt(maxdelay);
		Thread.sleep(delay);

		Response res = new Response();
		res.setProductId(productId);
		res.setInfo(productId);
		return res;
	}

	@GetMapping("/random/fail/{id}")
	public Response randomfail(@PathVariable(value = "id") String productId) throws Exception {

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());

		if(random.nextInt(2) % 2 == 0)
			throw new RandomException(productId);

		Response res = new Response();
		res.setProductId(productId);
		res.setInfo(productId);
		return res;
	}

}
