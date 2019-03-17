package com.example.sso.resource;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 在springboot中定义filters 
//使用@Component ，默认的filters被注册应用到所有的 URL 请求上。
//@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final String jwtTokenCookieName = "JWT-TOKEN";
    private static final String signingKey = "signingKey";

    //filter的工作原理
    //  code1   ===>  执行 servlet (使用 chain.doFilter())   ===>    code2
    //  通常filter可以被多次执行的。
    
    //OncePerRequestFilter 仅被执行一次（不会多也不会少）。 这种行为可以用于安全认证。
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //从request中的cookie中获取token， 并从token中提取 username
    	String username = JwtUtil.getSubject(httpServletRequest, jwtTokenCookieName, signingKey);
    	
        if(username == null){//如果username为空， 
        	//从 filter的初始化参数中 中读取services.auth
            String authService = this.getFilterConfig().getInitParameter("services.auth");
            //跳转到登录页面
            httpServletResponse.sendRedirect(authService + "?redirect=" + httpServletRequest.getRequestURL());
            
        } else{
        	//在request中设置username
            httpServletRequest.setAttribute("username", username);
            //继续对用户的请求进行处理
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}