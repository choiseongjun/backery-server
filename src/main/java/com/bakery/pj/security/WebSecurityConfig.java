package com.bakery.pj.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bakery.pj.security.jwt.TokenAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired private TokenAuthenticationFilter tokenAuthenticationFilter;

	@Override
	public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
		web
			.ignoring()
			.antMatchers(HttpMethod.OPTIONS, "/**")
			.antMatchers("/resources/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
			.withUser("system")
			.password("{noop}system")
			.roles("ADMIN", "USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.httpBasic().disable()
			.headers().frameOptions().disable()
			.and()

			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint())
			.and()

			.authorizeRequests()
			.antMatchers("/**/**", "/").permitAll()
			.anyRequest().authenticated()
			.and()

			.formLogin()
			.successHandler(authenticationSuccessHandler())
			.failureHandler(authenticationFailureHandler())
			.permitAll()
			.and()

			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()

			.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return (req, resp, ex) -> {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		};
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return (req, resp, auth) -> {
			resp.sendError(HttpServletResponse.SC_OK);
		};
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return (req, resp, ex) -> {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		};
	}

	@Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
