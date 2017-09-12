
package com.huan.HTed.core.interceptor;

import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.huan.HTed.account.dto.User;
import com.huan.HTed.core.BaseConstants;
import com.huan.HTed.core.util.TimeZoneUtil;

public class MonitorInterceptor extends HandlerInterceptorAdapter {

    private static ThreadLocal<Long> holder = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        fillMDC(request);
        holder.set(System.currentTimeMillis());
        HttpSession session = request.getSession(false);
        if (session != null) {
            String tz = (String) session.getAttribute(BaseConstants.PREFERENCE_TIME_ZONE);
            if (StringUtils.isNotEmpty(tz)) {
                TimeZoneUtil.setTimeZone(TimeZone.getTimeZone(tz));
            }
        }
//        SecurityTokenInterceptor.LOCAL_SECURITY_KEY.set(TokenUtils.getSecurityKey(session));待定
        return true;
    }

    private void fillMDC(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Long userId = (Long) session.getAttribute(User.FIELD_USER_ID);
            String uuid = UUID.randomUUID().toString().replace("-", "");
            if (userId != null) {
                MDC.put("userId", userId.toString());
            }
            MDC.put("requestId", uuid);
            MDC.put("sessionId", session.getId());
        }
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        long end = System.currentTimeMillis();
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Logger logger = LoggerFactory.getLogger(method.getBeanType());
            if (logger.isTraceEnabled()) {
                logger.trace(method.toString() + " - " + (end - holder.get()) + " ms");
            }
        }
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }
}
