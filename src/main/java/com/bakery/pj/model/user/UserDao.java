package com.bakery.pj.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDao {

	
	private long id;
	
	private String email;
	
	private String nickname;
	
	private String password;
	
	private String role;
	
}
