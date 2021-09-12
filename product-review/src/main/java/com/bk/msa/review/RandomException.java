package com.bk.msa.review;

public class RandomException extends Exception {

	private static final long serialVersionUID = -8567966331066022084L;

	public RandomException(String productId) {
		super("Random Excetion...[" + productId + "]");
	}
}
