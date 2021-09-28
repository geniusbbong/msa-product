package com.bk.view;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bk.view.service.Response;

@FeignClient(name = "product-info")
//@LoadBalancerClient(name = "product", configuration = RandomLoadBalancerConfiguration.class)
public interface ProductClient {

	@GetMapping("/{productId}")
	public Response get(@PathVariable String productId);

	@GetMapping("/{productId}")
	public Response getFail(@PathVariable String productId);

	@GetMapping("/random/fail/{productId}")
	public Response getRandomFail(@PathVariable String productId);

	@GetMapping("/random/delay/{productId}")
	public Response getRandomDelay(@PathVariable String productId);

}
