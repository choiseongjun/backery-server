package com.bakery.pj.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bakery.pj.model.BackeryVo;
import com.bakery.pj.model.ContentImageVo;
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
		System.out.println("contentVo.getId()@#!@#!@#"+contentVo.getId());
		map.put("imageId",imageId);
		map.put("content_id",contentVo.getId());
		sqlSession.update("com.bakery.pj.mapper.ContentMapper.updateContentsImage", map);
		
		
	}

	public List<ContentVo> contentsList(long id) {
		return sqlSession.selectList("com.bakery.pj.mapper.ContentMapper.selectContentList",id);
	}

	public ContentVo contentsDetail(long id) {
		return sqlSession.selectOne("com.bakery.pj.mapper.ContentMapper.selectContentDetail",id);
	}

	public List<ContentVo> contentDetailNextPrev(long id) {
		return sqlSession.selectList("com.bakery.pj.mapper.ContentMapper.selectContentPrevNext",id);
	}

}
