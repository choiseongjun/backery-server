package com.bakery.pj.service;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakery.pj.model.BackeryVo;

@Service
public class BackeryService {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	public List<BackeryVo> listbackery() {
		return sqlSession.selectList("com.bakery.pj.mapper.BackeryMapper.selectBackery");
	}

}
