package com.sun.erpbackend.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.erpbackend.response.UserResponse;
import com.sun.erpbackend.service.user.UserService;

/**
 * 用户操作的控制器
 * @author 夜流歌
 *
 */

@CrossOrigin
@RestController
@RequestMapping("/erp/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	/*
	 * Update User Name
	 * Return:
	 * 	code:
	 * 		1 Correct
	 * 		2 Internal Error
	 * 		3 Invalid Properties
	 * 	message:
	 * 		meta info
	 */
	@PostMapping("/updatename")
	UserResponse updateUserName(int id, String name) {
		UserResponse response = new UserResponse();
		if (id == 0 && name=="") {
			response.setCode(3);
			response.setMessage("Invalid Id Or Name");
		} else {
			int result = this.userService.updateName(id, name);
			if (result == 1) {
				response.setCode(1);
				response.setMessage("Success");
			}else {
				response.setCode(2);
				response.setMessage("Internal Error");
			}
		}
		return response;
	}
	
	/*
	 * Update User Password
	 * Return:
	 * 	code:
	 * 		1 Correct
	 * 		2 Internal Error
	 * 		3 Invalid Properties
	 * 	message:
	 * 		meta info
	 */
	@PostMapping("/updatepassword")
	UserResponse updateUserPassword(int id, String password) {
		UserResponse response = new UserResponse();
		if (id == 0 && password=="") {
			response.setCode(3);
			response.setMessage("Invalid Id Or Password");
		} else {
			int result = this.userService.updatePassword(id, password);
			if (result == 1) {
				response.setCode(1);
				response.setMessage("Success");
			}else {
				response.setCode(2);
				response.setMessage("Internal Error");
			}
		}
		return response;
	}
}
