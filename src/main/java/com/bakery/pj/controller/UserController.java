package com.bakery.pj.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bakery.pj.model.user.AuthenticationRequest;
import com.bakery.pj.model.user.AuthenticationResponse;
import com.bakery.pj.model.user.UserDTO;
import com.bakery.pj.model.user.UserDao;
import com.bakery.pj.security.jwt.CustomUserDetailsService;
import com.bakery.pj.security.jwt.JwtUtil;

@RestController
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@PostMapping(value = "/user/register")
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		UserDao returnUser = userDetailsService.save(user);
		return ResponseEntity.ok(returnUser);
	}
	@PostMapping(value = "/user/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		}
		catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		String token = jwtUtil.generateToken(userDetails);
		String resfreshToken = jwtUtil.generateRefreshToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(token,resfreshToken));
	}
	@GetMapping("/hellouser")
	public String getUser(Principal principal)
	{
		System.out.println("1111");
		System.out.println(principal.getName());
		return "Hello User";
	}
	
	@GetMapping("/helloadmin")
	public String getAdmin()
	{
		return "Hello Admin";
	}
}
