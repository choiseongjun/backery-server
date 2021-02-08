package com.bakery.pj.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakery.pj.common.Search;
import com.bakery.pj.model.BackeryVo;

@Service
public class BackeryService {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	public List<BackeryVo> listbackery(Search search) {
		return sqlSession.selectList("com.bakery.pj.mapper.BakeryMapper.selectBackery",search);
	}

	public BackeryVo detailBakery(long id) {
		return sqlSession.selectOne("com.bakery.pj.mapper.BakeryMapper.selectOneBackery",id);
	}

	public int getBakeryListCnt() {
		return sqlSession.selectOne("com.bakery.pj.mapper.BakeryMapper.selectBakeryListCnt");
	}

}
