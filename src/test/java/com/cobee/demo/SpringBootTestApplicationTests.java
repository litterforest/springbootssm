package com.cobee.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.cobee.demo.dao.SecureUserMapper;
import com.cobee.demo.entity.SecureUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootTestApplicationTests {

	@Autowired
	private SecureUserMapper secureUserMapper;
	
	@Test
	public void contextLoads() {
		
	}

	@Test
	public void test1()
	{
		SecureUser secureUser = new SecureUser();
		List<SecureUser> secureUserList = secureUserMapper.list(secureUser);
		if (!CollectionUtils.isEmpty(secureUserList))
		{
			for (SecureUser user : secureUserList)
			{
				System.out.println(user);
			}
		}
	}
	
}
