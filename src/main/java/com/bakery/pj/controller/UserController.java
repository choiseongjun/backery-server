package com.bakery.pj.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bakery.pj.model.request.LoginRequest;
import com.bakery.pj.security.jwt.Token;
import com.bakery.pj.security.jwt.TokenRegistry;
import com.bakery.pj.security.jwt.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class UserController {

	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private TokenUtils tokenUtils;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private TokenRegistry tokenRegistry;

	@PostMapping(path = "/token")
	public ResponseEntity token(@RequestBody LoginRequest loginRequest) {
		String result;
		try {
			UsernamePasswordAuthenticationToken authToken =
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
			System.out.println("authToken"+loginRequest.getUsername()+ loginRequest.getPassword());
			Authentication auth = authenticationManager.authenticate(authToken);
			UserDetails userDetails = (UserDetails) auth.getPrincipal();

			Map<String, String> tokens = new HashMap<>();
			String refreshToken = tokenUtils.createRefreshToken(userDetails);
			tokens.put("accessToken", tokenUtils.createAccessToken(userDetails));
			tokens.put("refreshToken", refreshToken);

			String username = userDetails.getUsername();
			if (tokenRegistry.exists(username))
				tokenRegistry.remove(username);
			tokenRegistry.add(userDetails.getUsername(), refreshToken);

			result = objectMapper.writeValueAsString(tokens);

		} catch (Exception e) {
			e.printStackTrace();
			return badRequest().body(e.getMessage());
		}

		return ok(result);
	}
	@PostMapping(path = "/refresh_token")
	public ResponseEntity refreshToken(@RequestBody Token token) {
		String result = null;
		try {
			String refreshToken = token.getRefreshToken();
			String username = tokenUtils.getUsername(refreshToken);
			String savedToken = tokenRegistry.get(username);
			if (tokenUtils.getId(refreshToken).equals(tokenUtils.getId(savedToken)))
				result = tokenUtils.createAccessToken(
					User
						.builder()
						.username(username)
						.password("")
						.authorities(
							tokenUtils.getAuthorities(refreshToken)
								.stream()
								.map(SimpleGrantedAuthority::new)
								.collect(Collectors.toList()))
						.build());
		} catch (Exception e) {
			e.printStackTrace();
			return badRequest().body(e.getMessage());
		}
		return ok(result);
	}
}
