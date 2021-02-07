package com.bakery.pj.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired private TokenUtils tokenUtils;
	@Autowired private AuthenticationManager authenticationManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String header = request.getHeader("Authorization");

		System.out.println("header"+header);
		try {

			if(header!=null) {
				String jwt = header.substring(7);
				String username = tokenUtils.getUsername(jwt);
				
				List<GrantedAuthority> authorities = tokenUtils.getAuthorities(jwt)
					.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
				UserDetails userDetails = User.builder()
					.username(username)
					.password("")
					.authorities(authorities)
					.build();

				UsernamePasswordAuthenticationToken token =
					new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(token);	
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		filterChain.doFilter(request, response);
	}

}