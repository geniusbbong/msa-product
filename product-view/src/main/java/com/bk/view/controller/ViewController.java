package com.bk.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {


	@GetMapping(path = {"", "/index"} )
	public String index() {
		return "/index";
	}

	@GetMapping("/product-view")
	public String view() {
		return "/product-view";
	}

}
