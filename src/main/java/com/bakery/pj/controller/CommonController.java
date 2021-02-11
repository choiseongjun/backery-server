package com.bakery.pj.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bakery.pj.model.ContentImageVo;
import com.bakery.pj.service.ContentsService;
import com.bakery.pj.service.S3Service;

@RestController
public class CommonController {

	@Autowired
	private S3Service s3Service;
	@Autowired
	private ContentsService contentsService;
	
	@PostMapping("/common/content/photo")
    public ResponseEntity<?> execWrite(@RequestParam("photo") MultipartFile file) throws IOException {
	  
	  
	 
		try {
			String imgPath = s3Service.upload(file);
		        
			ContentImageVo contentImg = contentsService.insertPhoto(imgPath,file);
			 
			return new ResponseEntity<>(contentImg,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
       

    }
}
