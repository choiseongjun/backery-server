package com.bakery.pj.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakery.pj.common.Search;
import com.bakery.pj.model.BackeryVo;
import com.bakery.pj.model.BackeryVo2;

@Service
public class BackeryService {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	public List<BackeryVo> listbackery(Search search) {
		return sqlSession.selectList("com.bakery.pj.mapper.BakeryMapper.selectBackery",search);
	}

	public List<BackeryVo2> listbackery2() {
		return sqlSession.selectList("com.bakery.pj.mapper.BakeryMapper.selectBackery2");
	}

	public BackeryVo detailBakery(long id) {
		return sqlSession.selectOne("com.bakery.pj.mapper.BakeryMapper.selectOneBackery",id);
	}

	public int updateBakery(BackeryVo2 backeryVo2) {
		return sqlSession.update("com.bakery.pj.mapper.BakeryMapper.updateBakery", backeryVo2);
	}

	public int insertBakeryMenu(BackeryVo2 backeryVo2) {
		return sqlSession.insert("com.bakery.pj.mapper.BakeryMapper.insertBakeryMenu", backeryVo2);
	}

	public int getBakeryListCnt() {
		return sqlSession.selectOne("com.bakery.pj.mapper.BakeryMapper.selectBakeryListCnt");
	}

}
