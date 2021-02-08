package com.bakery.pj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bakery.pj.model.user.UserDTO;
import com.bakery.pj.model.user.UserDao;
import com.bakery.pj.security.jwt.CustomUserDetailsService;

@RestController
public class UserController {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@PostMapping(value = "/user/register")
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		UserDao returnUser = userDetailsService.save(user);
		return ResponseEntity.ok(returnUser);
	}
}
