
package com.huan.HTed.adaptor.impl;

import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import com.huan.HTed.account.dto.User;
import com.huan.HTed.account.exception.UserException;
import com.huan.HTed.account.service.IUserService;
import com.huan.HTed.adaptor.ILoginAdaptor;
import com.huan.HTed.core.BaseConstants;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.components.RedisUtil;
import com.huan.HTed.core.exception.BaseException;
import com.huan.HTed.core.impl.RequestHelper;
import com.huan.HTed.core.util.TimeZoneUtil;
import com.huan.HTed.security.TokenUtils;
import com.huan.HTed.security.captcha.ICaptchaManager;
import com.huan.HTed.system.dto.ResponseData;

/**
 * 默认登陆代理类.
 */
@Component
public class DefaultLoginAdaptor implements ILoginAdaptor {

    // TODO:验证码改成可配置
    private static final boolean VALIDATE_CAPTCHA = true;
    // 跳转
    protected static final String REDIRECT = "redirect:";

    // 校验码
    private static final String KEY_VERIFICODE = "verifiCode";

    // 默认主页
    private static final String VIEW_INDEX = "/";

    // 默认的登录页
    private static final String VIEW_LOGIN = "/login";

    
    // 默认修改密码页面
    private static final String VIEW_UPDATE_PASSWORD = "sys/um/sys_update_password";

    @Autowired
    private ICaptchaManager captchaManager;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IUserService userService;

    
    
     
 
    public ModelAndView doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
       
        ModelAndView view = new ModelAndView();
        Locale locale = RequestContextUtils.getLocale(request);
        view.setViewName(getLoginView(request));
        try {
            beforeLogin(view, user, request, response);
            /*checkCaptcha(view, user, request, response);*/
            user = userService.login(user);
            HttpSession session = request.getSession(true);
            session.setAttribute(User.FIELD_USER_ID, user.getUserId());
            session.setAttribute(User.FIELD_USER_NAME, user.getUserName());
            session.setAttribute(IRequest.FIELD_LOCALE, locale.toString());
            setTimeZoneFromPreference(session, user.getUserId());
            generateSecurityKey(session);
            afterLogin(view, user, request, response);
        } catch (UserException e) {
            view.addObject("msg", messageSource.getMessage(e.getCode(), e.getParameters(), locale));
            view.addObject("code", e.getCode());
            processLoginException(view, user, e, request, response);
        }
        return view;
    }

    private void setTimeZoneFromPreference(HttpSession session, Long accountId) {
        // SysPreferences para = new SysPreferences();
        // para.setUserId(accountId);
        // para.setPreferencesLevel(10L);
        // para.setPreferences(BaseConstants.TIME_ZONE);
        // SysPreferences pref =
        // preferencesService.querySysPreferencesLine(RequestHelper.newEmptyRequest(),
        // para);
        // String tz = pref == null ? null : pref.getPreferencesValue();
        String tz = "GMT+0800";
        if (StringUtils.isBlank(tz)) {
            tz = TimeZoneUtil.toGMTFormat(TimeZone.getDefault());
        }
        session.setAttribute(BaseConstants.PREFERENCE_TIME_ZONE, tz);
    }

    private String generateSecurityKey(HttpSession session) {
        return TokenUtils.setSecurityKey(session);
    }

    /**
     * 登陆前逻辑.
     *
     * @param view
     *            视图
     * @param account
     *            账号
     * @param request
     *            请求
     * @param response
     *            响应
     * @throws UserException
     *             异常
     */
    protected void beforeLogin(ModelAndView view, User account, HttpServletRequest request,
            HttpServletResponse response) throws UserException {

    }

    /**
     * 处理登陆异常.
     *
     * @param view
     * @param account
     * @param e
     * @param request
     * @param response
     */
    protected void processLoginException(ModelAndView view, User account, UserException e, HttpServletRequest request,
            HttpServletResponse response) {

    }



    /**
     * 账号登陆成功后处理逻辑.
     *
     * @param view
     *            视图
     * @param user
     *            账号
     * @param request
     *            请求
     * @param response
     *            响应
     * @throws UserException
     *             异常
     */
    protected void afterLogin(ModelAndView view, User user, HttpServletRequest request, HttpServletResponse response)
            throws UserException {
      /*  view.setViewName(REDIRECT + getRoleView(request));*/
        Cookie cookie = new Cookie(User.FIELD_USER_NAME, user.getUserName());
        cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

  

    /**
     * 获取主界面.
     * 
     * @param request
     * @return 视图
     */
    protected String getIndexView(HttpServletRequest request) {
        return VIEW_INDEX;
    }

    /**
     * 获取登陆界面.
     * 
     * @param request
     * @return 视图
     */
    protected String getLoginView(HttpServletRequest request) {
        return VIEW_LOGIN;
    }
    
    /**
     * 获取强制修改密码界面.
     * 
     * @param request
     * @return 视图
     */
    protected String getUpdatePwdView(HttpServletRequest request) {
        return VIEW_UPDATE_PASSWORD;
    }
    
  

    /**
     * 集成类中可扩展此方法实现不同的userService.
     * 
     * @return IUserService
     */
    public IUserService getUserService() {
        return userService;
    }

    @Override
    public ModelAndView indexView(HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            // 获取user
//            Long userId = (Long) session.getAttribute(User.FIELD_USER_ID);
//            if (userId != null ) {
//                if(session.getAttribute(User.PASSWORD_EXPIRE_VERIFY) !=null){
//                    User user = new User();
//                    user.setUserId(userId);
//                    user = userService.selectByPrimaryKey(RequestHelper.createServiceRequest(request), user);
//                    ModelAndView mv = new ModelAndView(getUpdatePwdView(request));
//                    mv.addObject("user", user);
//                    return mv;
//                }
//            }else {
//                return new ModelAndView(REDIRECT + getLoginView(request));
//            }
//        }
        
        String sysTitle = "cado";
        ModelAndView mav =  indexModelAndView(request, response);
        mav.addObject("SYS_TITLE", sysTitle);
        return mav;
    }
    
    /**
     * 默认登陆页面.
     * 
     * @param request
     * @param response
     * @return 视图
     */
    public ModelAndView indexModelAndView(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index");
    }

    @Override
    public ModelAndView loginView(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        // 有session 则不再登录
        if( session != null && session.getAttribute(User.FIELD_USER_ID)!=null){
            ModelAndView mv = indexView(request, response);
            if(! getUpdatePwdView(request).equals(mv.getViewName()))
                mv.setViewName(REDIRECT+mv.getViewName());
            return mv;
        }
        ModelAndView view = new ModelAndView(getLoginView(request));
        // 配置3次以后开启验证码
          Cookie cookie =WebUtils.getCookie(request, RedisUtil.LOGIN_KEY);
          if(cookie==null){
        	  String uuid =UUID.randomUUID().toString();
              cookie = new Cookie(RedisUtil.LOGIN_KEY, uuid);
              cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
              response.addCookie(cookie);
          }
         
          /* if(captchaConfig.getWrongTimes() >0){
            if(cookie == null){
               String uuid =UUID.randomUUID().toString();
               cookie = new Cookie(CaptchaConfig.LOGIN_KEY, uuid);
               cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
               cookie.setMaxAge(captchaConfig.getExpire());
               response.addCookie(cookie);
               captchaConfig.updateLoginFailureInfo(cookie);
            }
        }*/
        
        //向前端传递是否开启验证码
        /*view.addObject("ENABLE_CAPTCHA",captchaConfig.isEnableCaptcha(cookie));*/
        
        Boolean error = (Boolean) request.getAttribute("error");
        Throwable exception = (Exception) request.getAttribute("exception");

        while (exception != null && exception.getCause() != null) {
            exception = exception.getCause();
        }
        String code = UserException.ERROR_USER_PASSWORD;
        if (exception instanceof BaseException) {
            code = ((BaseException) exception).getDescriptionKey();
        }

        if (error != null && error) {
            String msg = null;
            Locale locale = RequestContextUtils.getLocale(request);
            msg = messageSource.getMessage(code, null, locale);
            view.addObject("msg", msg);
        }
              
          return view;
    }

    

    protected void addCookie(String cookieName, String cookieValue, HttpServletRequest request,
            HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    @Override
    public ResponseData sessionExpiredLogin(User account, HttpServletRequest request, HttpServletResponse response) {
        ResponseData data = new ResponseData();
        ModelAndView view = this.doLogin(account, request, response);
        ModelMap mm = view.getModelMap();
        if (mm.containsAttribute("code")) {
            data.setSuccess(false);
            data.setCode((String) mm.get("code"));
            data.setMessage((String) mm.get("msg"));
        } else {
            Object userIdObj = request.getParameter(User.FIELD_USER_ID);
            if (userIdObj != null ) {
                Long userId = Long.valueOf(userIdObj.toString());
                HttpSession session = request.getSession();
                session.setAttribute(User.FIELD_USER_ID, userId);
            }
        }
        return data;
    }

	
}
