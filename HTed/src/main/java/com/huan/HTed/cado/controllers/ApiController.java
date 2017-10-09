package com.huan.HTed.cado.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huan.HTed.cado.service.ITypeService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.system.dto.ResponseData;

    @Controller
    public class ApiController extends BaseController{

    @Autowired
    private ITypeService service;


    @RequestMapping(value = "/api/type/query")
    @ResponseBody
    public ResponseData queryTypeInfo( HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryTypeInfo(requestContext));
    }

    }