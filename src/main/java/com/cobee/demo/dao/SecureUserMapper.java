package com.cobee.demo.dao;

import java.util.List;

import com.cobee.demo.entity.SecureUser;

public interface SecureUserMapper {
	
	List<SecureUser> list(SecureUser obj);

	SecureUser get(Integer id);

	void insertBySelective(SecureUser obj);

	Integer delete(Integer id);

	Integer deleteByLogic(Integer id);

	Integer updateBySelective(SecureUser obj);

	Integer queryByCount(SecureUser obj);
	
}
