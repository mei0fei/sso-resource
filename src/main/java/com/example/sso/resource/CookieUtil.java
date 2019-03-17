package com.example.sso.resource;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//该类用于读写cookie
public class CookieUtil {
    
	//把cookie存储到浏览器中
	public static void create(HttpServletResponse httpServletResponse, String name, String value, Boolean secure, Integer maxAge, String domain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(secure);//仅在 HTTPS 上工作.
        cookie.setHttpOnly(true); //对于 JavaScript 不可以用.
        cookie.setMaxAge(maxAge);//maxAge=0: cookie立刻过期。 maxAge<0:  cookie 在浏览器退出时过期
        cookie.setDomain(domain); //仅对  domain 域名有效.
        cookie.setPath("/"); //对所有路径都可见.
        httpServletResponse.addCookie(cookie);
    }

	//清除浏览器中特定的cookie
    public static void clear(HttpServletResponse httpServletResponse, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
    }

    //获取cookie的值
    public static String getValue(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
        return cookie != null ? cookie.getValue() : null;
    }
}
