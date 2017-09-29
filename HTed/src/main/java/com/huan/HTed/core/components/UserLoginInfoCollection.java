package com.huan.HTed.core.components;

import com.huan.HTed.account.dto.User;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.dto.UserLogin;
import com.huan.HTed.system.mapper.UserLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;


@Component
public class UserLoginInfoCollection implements IAuthenticationSuccessListener {

    @Autowired
    UserLoginMapper userLoginMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response) {

        String ipAddress = getIpAddress(request);
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId((Long)request.getSession(false).getAttribute(User.FIELD_USER_ID));
        userLogin.setReferer(request.getHeader("Referer"));
        userLogin.setUserAgent(request.getHeader("User-Agent"));
        userLogin.setIp(ipAddress);
        userLogin.setLoginTime(new Date());

        userLoginMapper.insertSelective(userLogin);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute(IRequest.FIELD_LOGIN_ID, userLogin.getLoginId());
        }
    }

    @Override
    public int getOrder() {
        return 999;
    }

    public String getIpAddress(HttpServletRequest request) {

        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;

    }
}
