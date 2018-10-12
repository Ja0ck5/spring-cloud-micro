package com.ja0ck5.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jack on 2017/10/19.
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigClientController {

//	@Value("${profile}")
//	private String profile;
//
//	/**
//	 *
//	 * @return
//	 */
//	@GetMapping("/profile")
//	public String getProfile() {
//		return profile;
//	}

}
