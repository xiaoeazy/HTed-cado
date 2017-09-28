package com.huan.HTed.cado.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huan.HTed.cado.dto.Product;
import com.huan.HTed.cado.dto.TypeProductDetail;
import com.huan.HTed.cado.service.ITypeProductDetailService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.system.dto.ResponseData;

    @Controller
    public class TypeProductDetailController extends BaseController{

    @Autowired
    private ITypeProductDetailService service;


    @RequestMapping(value = "/type/product/detail/query")
    @ResponseBody
    public ResponseData query(TypeProductDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/type/product/detail/submit")
    @ResponseBody
	public ResponseData update(@RequestBody List<TypeProductDetail> dto, BindingResult result, HttpServletRequest request){
//		getValidator().validate(dto, result);
//		if (result.hasErrors()) {
//			ResponseData responseData = new ResponseData(false);
//			responseData.setMessage(getErrorMessage(result, request));
//			return responseData;
//		}
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submit(requestCtx, dto));
    }

    @RequestMapping(value = "/type/product/detail/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<TypeProductDetail> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    
    
    @RequestMapping(value = "/type/product/detail/queryHave")
    @ResponseBody
    public ResponseData queryHave(TypeProductDetail dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<TypeProductDetail> list =   service.queryHave(requestContext, dto);
        return new ResponseData(list);
    }
    
    @RequestMapping(value = "/type/product/detail/queryNotHave")
    @ResponseBody
    public ResponseData queryNotHave(TypeProductDetail dto, HttpServletRequest request) {
          IRequest requestContext = createRequestContext(request);
          List<Product> list =   service.queryNotHave(requestContext, dto);
        return new ResponseData(list);
    }
    
    }