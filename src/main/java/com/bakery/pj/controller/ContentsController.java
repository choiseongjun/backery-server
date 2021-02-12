package com.bakery.pj.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bakery.pj.model.BackeryVo;
import com.bakery.pj.model.ContentImageVo;
import com.bakery.pj.model.ContentVo;
import com.bakery.pj.service.ContentsService;

@RestController
public class ContentsController {

	@Autowired
	private ContentsService contentsService;
	
	@PostMapping("/contents/write")
    public ResponseEntity<?> contentWrite(@RequestBody ContentVo contentVo,Principal principal) throws IOException {
	 
		
		System.out.println(principal.getName());
		
		try {
			//contentsService.insertContent(contentVo);	
			 
			return new ResponseEntity<>("성공하였습니다.",HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
       

    }
	/*
	 * 컨텐츠 리스트 조회
	 * */
	@PostMapping("/contentList") 
	public ResponseEntity<?> contentList(){
		
		try {
			List<BackeryVo> backeryList = contentsService.contentsList();		
			return new ResponseEntity<>(backeryList,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
	/*
	 * 컨텐츠 디테일 조회
	 * */
	@GetMapping("/contentDetail") 
	public ResponseEntity<?> contentDetail(){
		
		try {
			List<BackeryVo> backeryList = contentsService.contentsDetail();		
			return new ResponseEntity<>(backeryList,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
}
