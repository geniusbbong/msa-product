package com.bk.view;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
//@LoadBalancerClients(defaultConfiguration = RandomLoadBalancerConfiguration.class)
//@LoadBalancerClient(value = "price", configuration = StickyLoadBalancerConfiguration.class)
public class WebClientConfigurationProvider {

	/**
	 * Bean의로 등록하고 Loadbalanced 설정을 미리 해둬야...Eureka application 연동 가능
	 */
	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


}
