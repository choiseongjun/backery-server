package com.bakery.pj.service;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakery.pj.common.Pagination;
import com.bakery.pj.model.BackeryVo;

@Service
public class BackeryService {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	public List<BackeryVo> listbackery(Pagination pagination) {
		return sqlSession.selectList("com.bakery.pj.mapper.BakeryMapper.selectBackery",pagination);
	}

	public BackeryVo detailBakery(long id) {
		return sqlSession.selectOne("com.bakery.pj.mapper.BakeryMapper.selectOneBackery",id);
	}

	public int getBakeryListCnt() {
		return sqlSession.selectOne("com.bakery.pj.mapper.BakeryMapper.selectBakeryListCnt");
	}

}
