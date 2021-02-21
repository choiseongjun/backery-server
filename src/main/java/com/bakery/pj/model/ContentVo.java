package com.bakery.pj.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContentVo {

	private long id;
	
	private long userKey;
	
	private String title;
	
	private String content;
	
	private long imageId;
	
	private long categoryKey;
	
	private String url;
	
	private String nickname;//유저 닉네임
	
	private String create_date;
	
	private String update_date;
	
}
