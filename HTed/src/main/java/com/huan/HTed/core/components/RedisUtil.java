package com.huan.HTed.core.components;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class RedisUtil {
	private static final int CAPTCHA_EXPIRE = 60 * 60 * 24 * 30;
	public static final String LOGIN_KEY = "loginKey";
	 
    /**
     * 登录失败过期时间,一個月.
     */
    private Integer expire = CAPTCHA_EXPIRE;
    
	 @Autowired
     private RedisTemplate<String, String> redisTemplate;
	 /**
     * 从redis中 重置这次 登录失败记录及过期时间
     */
    public void resetLoginFailureInfo(HttpServletRequest request,HttpServletResponse response) {
        Cookie cookie = WebUtils.getCookie(request, LOGIN_KEY);
        if (cookie != null) {
            cookie.setMaxAge(this.expire);
            response.addCookie(cookie);
            redisTemplate.opsForValue().set(cookie.getValue(),"0",expire,TimeUnit.SECONDS);
        }
    }
}
