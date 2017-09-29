package com.huan.HTed.core.components;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.huan.HTed.account.dto.User;
import com.huan.HTed.account.service.IUserService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.security.PasswordManager;
import com.huan.HTed.security.TokenUtils;

@Component
public class DefaultAuthenticationSuccessListener implements IAuthenticationSuccessListener {
    @Autowired
    IUserService userService;

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    PasswordManager passwordManager;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response) {
        Locale locale = RequestContextUtils.getLocale(request);
        User user = userService.selectByUserName("admin");
        HttpSession session = request.getSession(true);
        session.setAttribute(User.FIELD_USER_ID, user.getUserId());
        session.setAttribute(User.FIELD_USER_NAME, user.getUserName());
        session.setAttribute(IRequest.FIELD_LOCALE, locale.toString());
        generateSecurityKey(session);
        redisUtil.resetLoginFailureInfo(request,response);
    }

  

    public int daysBetween(Date smdate,Date bdate){    
       int between_days=(int) ((bdate.getTime()-smdate.getTime())/(1000*3600*24));  
       return between_days;           
    } 
    
    private String generateSecurityKey(HttpSession session) {
        return TokenUtils.setSecurityKey(session);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
