package com.bakery.pj.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bakery.pj.model.ContentLikeVo;
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
	 
		UserDao user = userService.selectUserId(principal.getName());
		contentReplyVo.setUserKey(user.getId());
		contentReplyVo.setNickname(user.getNickname());
		contentReplyVo.setContentsKey(id);
		contentsService.insertContentReply(contentReplyVo);	
		ContentReplyVo returnContentReplyVo = contentsService.selectContentReplyOne(contentReplyVo);
		try {
			
			  
			return new ResponseEntity<>(returnContentReplyVo,HttpStatus.OK);
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
	 * 컨텐츠 디테일 댓글 수정 
	 * */
	@PatchMapping("/contents/reply/{id}") 
	public ResponseEntity<?> contentDetailReplyUpdate(@PathVariable long id,@RequestBody ContentReplyVo contentReplyVo){
		
		try {
			contentReplyVo.setId(id);
			contentsService.contentDetailReplyUpdate(contentReplyVo);			
			return new ResponseEntity<>(id,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
	/*
	 * 컨텐츠 디테일 댓글 삭제 
	 * */
	@DeleteMapping("/contents/reply/{id}") 
	public ResponseEntity<?> contentDetailReplyDelete(@PathVariable long id){
		
		try {
			contentsService.contentDetailReplyDelete(id);			
			return new ResponseEntity<>(id,HttpStatus.OK);
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
	 * 컨텐츠 빵집 후기리스트 조회
	 * */
	@GetMapping("/contentList/blogList") 
	public ResponseEntity<?> contentBakeryBlogListAll(){
		
				
		try {
			List<ContentVo> contentList = contentsService.contentBakeryBlogListAll();	
			return new ResponseEntity<>(contentList,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}
	}
	/*
	 * 컨텐츠 디테일 조회
	 * */
	@GetMapping("/contentDetail/{id}") 
	public ResponseEntity<?> contentDetail(@PathVariable long id,Principal principal){
		
		if(principal!=null) {
			System.out.println("id 존재");
			UserDao user = userService.selectUserId(principal.getName());
			ContentVo content = contentsService.contentsDetailUser(id,user.getId());		
			return new ResponseEntity<>(content,HttpStatus.OK);
		}else {
			System.out.println("id 존재 안함");
			ContentVo content = contentsService.contentsDetail(id);		
			return new ResponseEntity<>(content,HttpStatus.OK);
		}	
//		try {
//				
//			
//		}catch(Exception e) {  
//			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
//		}
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
	/*
	 * 컨텐츠 댓글 좋아요
	 * */
	@PostMapping("/contents/like/{id}")
	@Transactional
    public ResponseEntity<?> contentLike(@PathVariable long id
    									,Principal principal) throws IOException {
	 
		
		try {
			
			UserDao user = userService.selectUserId(principal.getName());
			ContentLikeVo contentLikeVo = new ContentLikeVo();
			contentLikeVo.setUserKey(user.getId());
			contentLikeVo.setContentKey(id);
			
			long likeCnt =  contentsService.insertContentLike(contentLikeVo);	
			contentLikeVo.setCnt(likeCnt);
			contentLikeVo.setLikeCheck(true);	  
			return new ResponseEntity<>(contentLikeVo,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}

    }
	/*
	 * 컨텐츠 댓글 좋아요 취소
	 * */
	@DeleteMapping("/contents/like/{id}")
	@Transactional
    public ResponseEntity<?> deleteContentLike(@PathVariable long id
    									,Principal principal) throws IOException {
	 
		
		try {
			
			UserDao user = userService.selectUserId(principal.getName());
			ContentLikeVo contentLikeVo = new ContentLikeVo();
			contentLikeVo.setUserKey(user.getId());
			contentLikeVo.setContentKey(id);
			
			long likeCnt =  contentsService.deleteContentLike(contentLikeVo);	
			contentLikeVo.setCnt(likeCnt);
			contentLikeVo.setLikeCheck(false);	  
			return new ResponseEntity<>(contentLikeVo,HttpStatus.OK);
		}catch(Exception e) {  
			return new ResponseEntity<>("실패하였습니다.새로고침후 다시 시도해주세요",HttpStatus.BAD_REQUEST);	
		}

    }
}
