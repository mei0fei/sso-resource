package com.example.sso.resource;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

//参考  https://jwt.io/introduction/， 了解jwt
//该类用于操作java web token
public class JwtUtil {
	
	//生成token，  
    public static String generateToken(String signingKey, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject) //设置jwt的subject
                .setIssuedAt(now)    //设置jwt的时间
                .signWith(SignatureAlgorithm.HS256, signingKey); //使用算法HS256，进行数字签名 

        return builder.compact();
    }

    //获得jwt的subject
    public static String getSubject(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey){
        //从cookie中获取token
    	String token = CookieUtil.getValue(httpServletRequest, jwtTokenCookieName);
        if(token == null) return null;
        //通过signingKey， 获取token中携带的subject
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
    }
}