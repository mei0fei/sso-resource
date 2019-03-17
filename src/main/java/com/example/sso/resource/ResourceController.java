package com.example.sso.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ResourceController {
    private static final String jwtTokenCookieName = "JWT-TOKEN";

    //用户的请求受到保护的资源， 会触发 filter 的执行
    @RequestMapping("/")
    public String home() {
        return "redirect:/protected-resource";
    }

    @RequestMapping("/protected-resource")
    public String protectedResource() {
        return "protected-resource";
    }

    //用户登出， 清除在cookie中的token， 并跳转到登录页面
    @RequestMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse) {
        CookieUtil.clear(httpServletResponse, jwtTokenCookieName);
        return "redirect:/";
    }
    
}
