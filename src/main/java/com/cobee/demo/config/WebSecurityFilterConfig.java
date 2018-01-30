package com.cobee.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;

/**
 * 防止csrf攻击的过滤器
 * @author Administrator
 *
 */
@Configuration
public class WebSecurityFilterConfig {
	
	@Bean("csrfFilter")
    public FilterRegistrationBean createFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfFilter csrfFilter = new CsrfFilter(httpSessionCsrfTokenRepository);
        //注入过滤器
        registration.setFilter(csrfFilter);
        //拦截规则
        registration.addUrlPatterns("/*");
        //过滤器名称
        registration.setName("csrfFilter");
        //是否自动注册 false 取消Filter的自动注册
//        registration.setEnabled(false);
        //过滤器顺序
        registration.setOrder(2);
        return registration;
    }
	
	@Bean("requestDataValueProcessor")
	public CsrfRequestDataValueProcessor createRequestDataValueProcessor()
	{
		CsrfRequestDataValueProcessor csrfRequestDataValueProcessor = new CsrfRequestDataValueProcessor();
		return csrfRequestDataValueProcessor;
	}
	
}
