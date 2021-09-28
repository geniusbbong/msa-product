package com.bk.view.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Response {

	String serviceName;
	String ipaddress;

	String serviceName2;
	String ipaddress2;

	String productId;
	Integer price;
	Integer inventory;

	@Builder.Default
	Integer delay = 0;
	String info;
	String review;

	@Builder.Default
	String errorMsg = "";

}
