package com.bk.msa.price;

public class RandomException extends Exception {

	private static final long serialVersionUID = 5285106468652958063L;

	public RandomException(String productId) {
		super("Random Exception...[" + productId + "]");
	}
}
