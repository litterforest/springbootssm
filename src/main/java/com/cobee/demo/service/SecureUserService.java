package com.cobee.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cobee.demo.dao.SecureUserMapper;
import com.cobee.demo.entity.SecureUser;

@Service
@Transactional(readOnly = true)
public class SecureUserService {
	
	@Autowired
	private SecureUserMapper secureUserMapper;
	
	public List<SecureUser> listAll(){
		SecureUser secureUserQuery = new SecureUser();
		return secureUserMapper.list(secureUserQuery);
	}
	
	public List<SecureUser> list(SecureUser obj)
	{
		return secureUserMapper.list(obj);
	}
	
	@Transactional(readOnly = false)
	public Integer deleteByLogic(Integer id)
	{
		return secureUserMapper.deleteByLogic(id);
	}
	
}
