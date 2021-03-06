package com.bakery.pj.security.jwt;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bakery.pj.model.user.UserDTO;
import com.bakery.pj.model.user.UserDao;
import com.bakery.pj.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;
		
			
		UserDao user = userService.selectUserId(username);
		if (user != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
			return new User(user.getUserId(), user.getPassword(), roles);
		}
		throw new UsernameNotFoundException("User not found with the name " + username);	
	}
	
	public UserDao save(UserDTO user) {
		UserDao newUser = new UserDao();
		newUser.setUserId(user.getUserId());
		newUser.setEmail(user.getEmail());
		newUser.setNickname(user.getNickname());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setRole("ROLE_USER");
		return userService.saveUser(newUser);
	}

	

}