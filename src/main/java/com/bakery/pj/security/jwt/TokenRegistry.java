package com.bakery.pj.security.jwt;

import java.util.HashMap;
import java.util.Map;

public class TokenRegistry {
	private Map<String, String> registry;

	public TokenRegistry() {
		this.registry = new HashMap<>();
	}

	public boolean exists(String username) {
		return this.registry.get(username) != null;
	}

	public void add(String username, String refreshToken) {
		this.registry.put(username, refreshToken);
	}

	public String get(String username) {
		return this.registry.get(username);
	}

	public void remove(String username) {
		this.registry.remove(username);
	}

	public Map<String, String> getRegistry() {
		return this.registry;
	}
}
