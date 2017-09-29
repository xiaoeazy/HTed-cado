
package com.huan.HTed.system.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.huan.HTed.account.dto.User;
import com.huan.HTed.adaptor.ILoginAdaptor;
import com.huan.HTed.core.exception.BaseException;
import com.huan.HTed.system.dto.ResponseData;

/**
 * 用户控制层.
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private ILoginAdaptor loginAdaptor;

    /**
     * 显示登陆界面.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return view
     */
    @RequestMapping(value = { "/login.html", "/login" })
    public ModelAndView loginView(final HttpServletRequest request, final HttpServletResponse response) {
        return getLoginAdaptor().loginView(request, response);
    }

    

    /**
     * 显示主界面.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return view
     */
    @RequestMapping(value ={ "/","/index.html"}, method = RequestMethod.GET)
    public ModelAndView indexView(final HttpServletRequest request, final HttpServletResponse response) {        
        return getLoginAdaptor().indexView(request, response);
    }
    
    

    private ILoginAdaptor getLoginAdaptor() {
        return loginAdaptor;
    }

    
    
    /**
     * 超时重新登陆.
     * 
     * @param account
     * @param request
     * @param response
     * @return ResponseData
     * @throws BaseException 
     */
    @RequestMapping(value = "/sessionExpiredLogin", method = RequestMethod.POST)
    public ResponseData sessionExpiredLogin(final User account, final HttpServletRequest request,
                                            final HttpServletResponse response) throws BaseException {
        return getLoginAdaptor().sessionExpiredLogin(account, request, response);
    }
    
    
    /**
     * 退出登录
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return view
     */
    @RequestMapping(value ="/logout")
    public ModelAndView logout(final HttpServletRequest request, final HttpServletResponse response) {
    	return new ModelAndView("forward:/login");
    }
}
