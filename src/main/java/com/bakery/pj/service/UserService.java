package com.bakery.pj.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakery.pj.model.user.UserDao;

@Service
public class UserService {

	@Autowired
	SqlSessionTemplate sqlSession;

	public UserDao saveUser(UserDao newUser) {
		sqlSession.insert("com.bakery.pj.mapper.UserMapper.saveUser", newUser);
		return newUser;
	}

	public UserDao selectUserId(String username) {
		return sqlSession.selectOne("com.bakery.pj.mapper.UserMapper.selectUser",username);
	}

	public int selectByUserId(String userId) {
		return sqlSession.selectOne("com.bakery.pj.mapper.UserMapper.countuserId",userId);
	}

	public long countContents(long id) {
		return sqlSession.selectOne("com.bakery.pj.mapper.UserMapper.countContents",id);
	}
}
