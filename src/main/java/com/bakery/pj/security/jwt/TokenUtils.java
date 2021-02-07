package com.bakery.pj.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {

	@Value("${jwt.signingKey}")
	private String signingKey;
	@Value("${jwt.subject}")
	private String subject;

	public String createAccessToken(UserDetails userDetails) {
		return Jwts.builder()
			.setId(UUID.randomUUID().toString())
			.signWith(SignatureAlgorithm.HS512, signingKey.getBytes())
			.setSubject(subject)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
			.claim("username", userDetails.getUsername())
			.claim("roles", 
				userDetails.getAuthorities()
					.stream()
					.map(r -> ((GrantedAuthority) r).toString())
					.collect(Collectors.toList()))
			.compact();
	}
	public String createRefreshToken(UserDetails userDetails) {
		return Jwts.builder()
			.setId(UUID.randomUUID().toString())
			.signWith(SignatureAlgorithm.HS512, signingKey.getBytes())
			.setSubject(subject)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
			.claim("username", userDetails.getUsername())
			.claim("roles",
				userDetails.getAuthorities()
					.stream()
					.map(r -> ((GrantedAuthority) r).toString())
					.collect(Collectors.toList()))
			.compact();
	}
	public String getUsername(String jwt) {
		return (String) claims(jwt)
			.get("username");
	}

	public String getId(String jwt) {
		return claims(jwt)
			.getId();
	}

	public List<String> getAuthorities(String jwt) {
		return (List<String>) claims(jwt)
			.get("roles");
	}

	private Claims claims(String jwt) {
		return Jwts.parser()
			.setSigningKey(signingKey.getBytes())
			.parseClaimsJws(jwt)
			.getBody();
	}


}
