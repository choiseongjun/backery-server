package com.bakery.pj.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContentVo {

	private long id;
	
	private String userId;
	
	private String title;
	
	private String content;
	
	private String[] imageId;
	
	private String category;
	
}
