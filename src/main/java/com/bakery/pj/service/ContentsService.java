package com.bakery.pj.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bakery.pj.model.ContentImageVo;

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

}
