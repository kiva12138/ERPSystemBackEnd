package com.sun.erpbackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.erpbackend.response.GeneralResponse;


@CrossOrigin
@RestController
public class Login {
	@GetMapping("/erp/login_page")
	public GeneralResponse loginPage() {
		GeneralResponse response = new GeneralResponse();
		response.setCode(999);
		response.setMessage("您尚未登录，或者登录已经失效。");
		return response;
	}
}