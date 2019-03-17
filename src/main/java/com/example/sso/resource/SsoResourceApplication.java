package com.example.sso.resource;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SsoResourceApplication  {

	//从配置文件中获取 services.auth 的值， 并注入到 authService 中
	@Value("${services.auth}")
    private String authService;

	@Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        //建立一个 filter注册对象 
		final FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<JwtFilter>();
        //使用 registrationBean 注册 JwtFilter
        registrationBean.setFilter(new JwtFilter());
        //设置初始化参数
        registrationBean.setInitParameters(Collections.singletonMap("services.auth", authService));
        //设置url， 在其上 filter 执行过滤功能 
        registrationBean.addUrlPatterns("/protected-resource");

        return registrationBean;
    }

	public static void main(String[] args) {
		SpringApplication.run(SsoResourceApplication.class, args);
	}

}
