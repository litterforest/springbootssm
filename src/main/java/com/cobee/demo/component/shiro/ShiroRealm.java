package com.cobee.demo.component.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cobee.demo.entity.SecureUser;
import com.cobee.demo.service.SecureUserService;

public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private SecureUserService secureUserService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();

		// 登录失败次数太多，用户被锁定
		// TODO

		// 获取数据库用户信息
		SecureUser baseUser = new SecureUser();
		baseUser.setUsername(username);
		List<SecureUser> baseUserList = secureUserService.list(baseUser);
		if (CollectionUtils.isEmpty(baseUserList)) {
			throw new AuthenticationException("用户名或密码错误");
		}

		SecureUser dbBaseUser = baseUserList.get(0);
		ByteSource salt = ByteSource.Util.bytes(dbBaseUser.getUsername());

		return new SimpleAuthenticationInfo(dbBaseUser, dbBaseUser.getPassword(), salt, getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		Set<String> roles = new HashSet<>();
		Set<String> stringPermissions = new HashSet<>();
		// 管理员拥有所有角色和权限
		simpleAuthorizationInfo.setRoles(roles);
		simpleAuthorizationInfo.setStringPermissions(stringPermissions);
		return simpleAuthorizationInfo;
	}

}
