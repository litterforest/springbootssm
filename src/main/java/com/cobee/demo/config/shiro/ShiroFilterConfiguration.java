package com.cobee.demo.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cobee.demo.component.shiro.ShiroRealm;

/**
 * shiro安全框架的配置类
 * @author Administrator
 *
 */
//@Configuration
@SpringBootConfiguration
public class ShiroFilterConfiguration {
	
	@Bean("cacheManager")
	public EhCacheManager createEhCacheManager()
	{
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache.xml");
		return ehCacheManager;
	}
	
	@Bean("shiroRealm")
	public ShiroRealm createShiroRealm()
	{
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		hashedCredentialsMatcher.setHashIterations(128);
		ShiroRealm shiroRealm = new ShiroRealm();
		shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
		return shiroRealm;
	}
	
	@Bean("securityManager")
	public DefaultWebSecurityManager createSecurityManager(@Qualifier("cacheManager") EhCacheManager cacheManager, @Qualifier("shiroRealm") ShiroRealm shiroRealm)
	{
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setCacheManager(cacheManager);
		defaultWebSecurityManager.setRealm(shiroRealm);
		return defaultWebSecurityManager;
	}
	
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor createLifecycleBeanPostProcessor()
	{
		LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
		return lifecycleBeanPostProcessor;
	}
	
	@Bean
	public DefaultAdvisorAutoProxyCreator createDefaultAdvisorAutoProxyCreator()
	{
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		return defaultAdvisorAutoProxyCreator;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor createAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager)
	{
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean createShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager)
	{
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/home");
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
		StringBuilder chainDefinitions = new StringBuilder();
//		chainDefinitions.append("/** = authc");
		shiroFilterFactoryBean.setFilterChainDefinitions(chainDefinitions.toString());
		return shiroFilterFactoryBean;
	}
	
}
