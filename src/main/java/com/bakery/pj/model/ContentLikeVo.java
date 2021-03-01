package com.bakery.pj.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContentLikeVo {

	private long id;
	
	private long userKey;
	
	private long contentKey;
	
	private long cnt;
	
	private boolean likeCheck;
	
	private String create_date;
	
	private String update_date;
}
