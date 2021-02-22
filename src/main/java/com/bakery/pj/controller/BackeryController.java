package com.bakery.pj.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bakery.pj.common.Search;
import com.bakery.pj.model.BackeryVo;
import com.bakery.pj.model.BakeryReview;
import com.bakery.pj.model.ImageFile;
import com.bakery.pj.model.user.UserDao;
import com.bakery.pj.service.BackeryService;
import com.bakery.pj.service.UserService;


@RestController
public class BackeryController {

	@Autowired
	BackeryService backeryService;
	@Autowired
	UserService userService;
	/*
	 * 빵집리스트
	 * */
	@GetMapping("/bakery") 
	public ResponseEntity<?> listbackery(@RequestParam(required = false, defaultValue = "1") int page
										,@RequestParam(required = false, defaultValue = "1") int range
										, @RequestParam(required = false) String keyword
										, @RequestParam(required = false) double xpos
										, @RequestParam(required = false) double ypos){
	
			try {
				Search search = new Search();

				search.setKeyword(keyword);
				search.setXposIo(xpos);
				search.setYposIa(ypos);

				int listCnt = backeryService.getBakeryListCnt();
		
				search.pageInfo(page, range, listCnt);
				List<BackeryVo> backeryList = backeryService.listbackery(search);	
				return new ResponseEntity<>(backeryList,HttpStatus.OK);
			}catch(Exception e) {  
				return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
			}
	}
	/*
	 * 위치(지도)기반 빵집리스트 조회
	 * */
	@PostMapping("/bakerylocation") 
	public ResponseEntity<?> maplistbackery(@RequestBody BackeryVo bakeryVo){
		
		try {
			List<BackeryVo> backeryList = backeryService.maplistbackery(bakeryVo);		
			return new ResponseEntity<>(backeryList,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
	/*
	 * 빵집디테일
	 * */
	@GetMapping("/bakery/{id}") 
	public ResponseEntity<?> detailBackery(@PathVariable long id){
		
		try {
			BackeryVo bakeryDetail = backeryService.detailBakery(id);	
			return new ResponseEntity<>(bakeryDetail,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
	/*
	 * 빵집디테일 메뉴 조회
	 * */
	@GetMapping("/bakery/menu/{id}") 
	public ResponseEntity<?> detailBackeryMenu(@PathVariable long id){
		List<BackeryVo> bakeryDetailMenu = backeryService.detailBakeryMenu(id);
		try {
			
			return new ResponseEntity<>(bakeryDetailMenu,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	
	}
	/*
	 * 빵집디테일 리뷰 조회 
	 * */
	@GetMapping("/bakery/review/{id}") 
	public ResponseEntity<?> selectReviewlBackery(@PathVariable long id){
		
		try {
			List<BakeryReview> bakeryReview = backeryService.selectReviewlBackery(id);	
			return new ResponseEntity<>(bakeryReview,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	
	}
	@DeleteMapping("/bakery/review/{id}") 
	public ResponseEntity<?> deleteReviewlBackery(@PathVariable long id){
		
		try {
			backeryService.deleteReviewlBackery(id);	
			return new ResponseEntity<>(id,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
	
	/*
	 * 빵집디테일 리뷰 글쓰기
	 * */
	@PostMapping("/bakery/review/{id}")
	public ResponseEntity<?> writeReviewlBackery(@RequestBody BakeryReview bakeryReview
												 ,@PathVariable long id
												 ,Principal principal){
		
	
		
		try {
			UserDao user = userService.selectUserId(principal.getName());
			bakeryReview.setUserKey(user.getId());
			bakeryReview.setBreadStoreKey(id);
			backeryService.writeReview(bakeryReview);
			return new ResponseEntity<>(bakeryReview,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
	@PostMapping("/bakery/imageupload")
	public ResponseEntity<?> writeContentlBackery(@RequestParam("photo") MultipartFile file){

			System.out.println(file.getName()+file.getOriginalFilename()+','+file.getSize());
			try {
			
			return new ResponseEntity<>("성공",HttpStatus.OK);
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
