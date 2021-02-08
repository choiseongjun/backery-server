package com.bakery.pj.model.user;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class UserDTO {
	private String email;
	private String nickname;
	private String password;
	private String role;

}
