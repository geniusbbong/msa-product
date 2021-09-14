package com.bk.msa.inventory;

public class RandomException extends Exception{

	private static final long serialVersionUID = -6386618946434223355L;

	public RandomException(String productId) {
		super("Random Exception...[" + productId + "]");
	}
}
