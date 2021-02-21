package com.bakery.pj.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Search extends Pagination{

	private String searchType;

	private String keyword;	
	
	private double xposIo;
	
	private double yposIa;

}



