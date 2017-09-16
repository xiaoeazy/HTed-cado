
package com.huan.HTed.adaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.huan.HTed.account.dto.User;
import com.huan.HTed.core.exception.BaseException;
import com.huan.HTed.system.dto.ResponseData;

/**
 * 登陆代理接口类.
 * 
 */
public interface ILoginAdaptor {

    /**
     * 超时登陆逻辑.
     * 
     * @param account
     *            登陆账号对象
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ResponseData
     * @throws BaseException
     */
    ResponseData sessionExpiredLogin(User account, HttpServletRequest request, HttpServletResponse response);


    /**
     * 显示主界面.
     *
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return view
     */
    ModelAndView indexView(HttpServletRequest request, HttpServletResponse response);

    /**
     * 登陆界面.
     * 
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return view
     */
    ModelAndView loginView(HttpServletRequest request, HttpServletResponse response);

    
}
