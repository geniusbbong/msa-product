package com.bk.msa.product;

public class RandomException extends Exception{

	private static final long serialVersionUID = 7089918159080818975L;

	public RandomException(String productId) {
		super("Random Exception...[" + productId + "]");
	}
}
