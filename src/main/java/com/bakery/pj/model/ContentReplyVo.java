package com.bakery.pj.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentReplyVo {

	
	private long id;
	
	private long userKey;
	
	private long contentsKey;
	
	private String content;
	
	private String nickname;
	
	private String create_date;
	
	private String update_date;
}
