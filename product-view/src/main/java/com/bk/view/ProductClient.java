package com.bk.view;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-info")
//@LoadBalancerClient(name = "product", configuration = RandomLoadBalancerConfiguration.class)
public interface ProductClient {

	@GetMapping("/{productId}")
	public String productInfo(@PathVariable String productId);

}
