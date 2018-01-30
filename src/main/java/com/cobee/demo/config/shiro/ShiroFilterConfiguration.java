package com.cobee.demo.config.shiro;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
		RememberMeManager rememberMeManager = defaultWebSecurityManager.getRememberMeManager();
		if (rememberMeManager != null && rememberMeManager instanceof CookieRememberMeManager)
		{
			CookieRememberMeManager cookieRememberMeManager = (CookieRememberMeManager)rememberMeManager;
			Cookie cookie = cookieRememberMeManager.getCookie();
			if (cookie != null)
			{
				cookie.setMaxAge(604800);
			}
		}
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
	public FilterRegistrationBean createShiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager)
	{
		
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/home");
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
		StringBuilder chainDefinitions = new StringBuilder();
		chainDefinitions.append("/SecureUser/list = anon");
		chainDefinitions.append(System.getProperty("line.separator")).append("/** = authc");
		shiroFilterFactoryBean.setFilterChainDefinitions(chainDefinitions.toString());
		
		FilterRegistrationBean registration = new FilterRegistrationBean();
		//注入过滤器
        try {
			registration.setFilter((Filter) shiroFilterFactoryBean.getObject());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
        //拦截规则
        registration.addUrlPatterns("/*");
        //过滤器名称
        registration.setName("shiroFilter");
        //是否自动注册 false 取消Filter的自动注册
//        registration.setEnabled(false);
        //过滤器顺序
        registration.setOrder(1);
		return registration;
	}
	
	
	
}
