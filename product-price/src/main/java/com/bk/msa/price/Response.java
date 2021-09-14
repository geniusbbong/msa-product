package com.bk.msa.price;

import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response {

	String serviceName;
	String ipaddress;
	String productId;
	Integer price;
	Integer inventory;
	Integer delay = 0;

	public Response() {
		this.serviceName = DataController.SERVICE_NAME;

		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			this.ipaddress = String.format("%s:%d", ip, DataController.PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
