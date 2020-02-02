package com.sun.erpbackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 夜流歌
 * 测试HelloWorld
 */

@CrossOrigin
@RestController
@RequestMapping("/erp")
public class HelloWorlController {
	
	@GetMapping("/api/hello")
    public String Hello(){
        return "Hello World";
    }
	
	@GetMapping("/hello")
    public String Hello2(){
        return "Hello World2";
    }
}
