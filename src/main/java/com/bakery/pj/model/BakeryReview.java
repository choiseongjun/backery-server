package com.bakery.pj.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BakeryReview {

	private long id;
	
	private long breadStoreKey;
	
	private long star;
	
	private String content;
	
	private long userKey;
	
	private String create_date;
	
	private String update_date;
	
	private String nickname;
	
	private String[] imgId;
	
	
}
