package com.bakery.pj.security.jwt;

import lombok.Data;

@Data
public class Token {
	private String accessToken;
	private String refreshToken;
}
