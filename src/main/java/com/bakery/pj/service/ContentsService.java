package com.bakery.pj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bakery.pj.model.BakeryVo;
import com.bakery.pj.model.ContentImageVo;
import com.bakery.pj.model.ContentLikeVo;
import com.bakery.pj.model.ContentReplyVo;
import com.bakery.pj.model.ContentVo;

@Service
public class ContentsService {

	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	public ContentImageVo insertPhoto(String imgPath, MultipartFile file) {
		
		
		ContentImageVo contentImg = new ContentImageVo();
		contentImg.setFilename(file.getOriginalFilename());
		contentImg.setUrl(imgPath);
		contentImg.setFilesize(file.getSize());
		contentImg.setImageExtension(file.getContentType());

		sqlSession.insert("com.bakery.pj.mapper.ContentMapper.saveContentImage", contentImg);
		return contentImg;
	}

	@Transactional
	public void insertContent(ContentVo contentVo) {
		
		Map<String, Long> map = new HashMap<String, Long>();
		sqlSession.insert("com.bakery.pj.mapper.ContentMapper.saveContents", contentVo);
		
		long imageId =contentVo.getImageId();
		map.put("imageId",imageId);
		map.put("content_id",contentVo.getId());
		sqlSession.update("com.bakery.pj.mapper.ContentMapper.updateContentsImage", map);
		
		
	}

	public List<ContentVo> contentsList(long id) {
		return sqlSession.selectList("com.bakery.pj.mapper.ContentMapper.selectContentList",id);
	}

	public ContentVo contentsDetailUser(long id, long userKey) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("id",id);
		map.put("userKey",userKey);
		return sqlSession.selectOne("com.bakery.pj.mapper.ContentMapper.selectContentDetailUser",map);
	}
	public ContentVo contentsDetail(long id) {
		return sqlSession.selectOne("com.bakery.pj.mapper.ContentMapper.selectContentDetail",id);
	}

	public List<ContentVo> contentDetailNextPrev(long id) {
		return sqlSession.selectList("com.bakery.pj.mapper.ContentMapper.selectContentPrevNext",id);
	}

	public void insertContentReply(ContentReplyVo contentReplyVo) {
		sqlSession.insert("com.bakery.pj.mapper.ContentMapper.saveContentsReply", contentReplyVo);
	}

	public List<ContentReplyVo> selectContentReply(long id) {
		return sqlSession.selectList("com.bakery.pj.mapper.ContentMapper.selectContentsReply",id);
	}

	public void contentDetailReplyDelete(long id) {
		sqlSession.update("com.bakery.pj.mapper.ContentMapper.deleteContentReply",id);		
	}

	public void contentDetailReplyUpdate(ContentReplyVo contentReplyVo) {
		sqlSession.update("com.bakery.pj.mapper.ContentMapper.updateContentReply",contentReplyVo);				
	}

	public List<ContentVo> contentBakeryBlogListAll() {
		return sqlSession.selectList("com.bakery.pj.mapper.ContentMapper.selectContentBakeryBlogListAll");
	}

	public void updateBakeryReview(String id, long bakeryReviewId) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("bakeryReviewId",bakeryReviewId);
		map.put("id",Long.parseLong(id));
		sqlSession.update("com.bakery.pj.mapper.ContentMapper.updateBakeryReviewImageId",map);
		
	}

	@Transactional
	public long insertContentLike(ContentLikeVo contentLikeVo) {
		sqlSession.insert("com.bakery.pj.mapper.ContentMapper.saveContentLike", contentLikeVo);
		return sqlSession.selectOne("com.bakery.pj.mapper.ContentMapper.cntContentLike",contentLikeVo);		
	}

	public long deleteContentLike(ContentLikeVo contentLikeVo) {
		sqlSession.delete("com.bakery.pj.mapper.ContentMapper.deleteContentLike", contentLikeVo);
		return sqlSession.selectOne("com.bakery.pj.mapper.ContentMapper.cntContentLike",contentLikeVo);		
	}


	public ContentReplyVo selectContentReplyOne(ContentReplyVo contentReplyVo) {
		return sqlSession.selectOne("com.bakery.pj.mapper.ContentMapper.selectContentReplyOne",contentReplyVo);
	}

	

}
