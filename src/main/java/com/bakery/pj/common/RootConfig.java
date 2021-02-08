package com.bakery.pj.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RootConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}


}
