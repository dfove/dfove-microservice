package com.microservice.authorization.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class HomeController {
	
	@RequestMapping("")
	public String index() {
		return "OK";
	}
}
