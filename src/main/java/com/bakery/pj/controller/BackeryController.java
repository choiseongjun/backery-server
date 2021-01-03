package com.bakery.pj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bakery.pj.model.BackeryVo;
import com.bakery.pj.service.BackeryService;

@RestController
public class BackeryController {

	@Autowired
	BackeryService backeryService;
	
	@GetMapping("/backery") 
	public ResponseEntity<?> listbackery(){
		List<BackeryVo> backeryList = backeryService.listbackery();
		System.out.println(backeryList);
		try {
			
			return new ResponseEntity<>(backeryList,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
}
