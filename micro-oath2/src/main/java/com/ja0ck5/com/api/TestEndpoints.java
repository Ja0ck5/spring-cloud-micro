package com.ja0ck5.com.api;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEndpoints {

	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}

}