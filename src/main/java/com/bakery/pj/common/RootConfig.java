package com.bakery.pj.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bakery.pj.security.jwt.TokenRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RootConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public TokenRegistry tokenRegistry() {
		return new TokenRegistry();
	}

}
