package com.bakery.pj.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bakery.pj.model.BackeryVo;
import com.bakery.pj.model.ImageFile;
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
	@PostMapping(value = "/bakery/upload")
    public Map<String, Object> upload(@RequestParam("file") ImageFile multipartFile) throws IOException {
		
		//System.out.println("sfddf"+multipartFile.getMime());
        Map<String, Object> m = new HashMap<>();
        m.put("errorCode", 10); 
        return m;
    }
	@PostMapping(value="/bakery/test")
	public Map<String, Object> uploadtest(@RequestParam("photo") MultipartFile multipartFile) throws IOException {
		
		//System.out.println("sfddf"+multipartFile.getMime());
		System.out.println(multipartFile.getOriginalFilename());
		System.out.println(multipartFile.getSize());
		System.out.println(multipartFile.getName());
//		File targetFile = new File(multipartFile.getUri());
//		
		//file.transferTo(new File(filePathAndName));
		//fileUpload(file, moimImagePath+"/"+imageName+"."+fileExtension);
		fileUpload(multipartFile, "/Users/choiseongjun/git/backery-server/src/main/resources/static/imgs/");
        Map<String, Object> m = new HashMap<>();
        m.put("errorCode", 10); 
        return m;
    }
	public void fileUpload(MultipartFile file, String filePathAndName) throws IOException {
        file.transferTo(new File(filePathAndName));
    }
	

}
