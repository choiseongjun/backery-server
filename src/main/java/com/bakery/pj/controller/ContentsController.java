package com.bakery.pj.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bakery.pj.model.ContentReplyVo;
import com.bakery.pj.model.ContentVo;
import com.bakery.pj.model.user.UserDao;
import com.bakery.pj.service.ContentsService;
import com.bakery.pj.service.UserService;

@RestController
public class ContentsController {

	@Autowired
	private ContentsService contentsService;
	@Autowired
	private UserService userService;
	/*
	 * 컨텐츠 작성 
	 * */
	@PostMapping("/contents/write")
	@Transactional
    public ResponseEntity<?> contentWrite(@RequestBody ContentVo contentVo,Principal principal) throws IOException {
	 
		
		try {
			UserDao user = userService.selectUserId(principal.getName());
			contentVo.setUserKey(user.getId());
			contentsService.insertContent(contentVo);	
			  
			return new ResponseEntity<>("성공하였습니다.",HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}

    }
	/*
	 * 컨텐츠 댓글 작성
	 * */
	@PostMapping("/contents/reply/write/{id}")
	@Transactional
    public ResponseEntity<?> contentReplyWrite(@RequestBody ContentReplyVo contentReplyVo
    											,@PathVariable long id
    											,Principal principal) throws IOException {
	 
		
		try {
			UserDao user = userService.selectUserId(principal.getName());
			contentReplyVo.setUserKey(user.getId());
			contentReplyVo.setNickname(user.getNickname());
			contentReplyVo.setContentsKey(id);
			contentsService.insertContentReply(contentReplyVo);	
			  
			return new ResponseEntity<>(contentReplyVo,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}

    }
	/*
	 * 컨텐츠 댓글 조회 
	 * */
	@GetMapping("/contents/reply/{id}")
	public ResponseEntity<?> selectContentReply(@PathVariable long id) {
		
		try { 
			List<ContentReplyVo> contentReplyList = contentsService.selectContentReply(id);
			return new ResponseEntity<>(contentReplyList,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
	/*
	 * 컨텐츠 리스트 조회
	 * */
	@GetMapping("/contentList/{id}") 
	public ResponseEntity<?> contentList(@PathVariable long id){
		
				
		try {
			List<ContentVo> contentList = contentsService.contentsList(id);	
			return new ResponseEntity<>(contentList,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
	/*
	 * 컨텐츠 디테일 조회
	 * */
	@GetMapping("/contentDetail/{id}") 
	public ResponseEntity<?> contentDetail(@PathVariable long id){
		
		try {
			ContentVo content = contentsService.contentsDetail(id);			
			return new ResponseEntity<>(content,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
	/*
	 * 컨텐츠 디테일 이전글다음글 
	 * */
	@GetMapping("/contentDetailNextPrev/{id}") 
	public ResponseEntity<?> contentDetailNextPrev(@PathVariable long id){
		
		try { 
			List<ContentVo> content = contentsService.contentDetailNextPrev(id);			
			return new ResponseEntity<>(content,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
}
