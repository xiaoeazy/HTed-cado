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
import com.huan.HTed.cado.service.IProductService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.system.dto.ResponseData;

    @Controller
    public class ProductController extends BaseController{

    @Autowired
    private IProductService service;


    @RequestMapping(value = "/product/query")
    @ResponseBody
    public ResponseData query(Product dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/product/submit")
    @ResponseBody
	public ResponseData update(@RequestBody List<Product> dto, BindingResult result, HttpServletRequest request){
		getValidator().validate(dto, result);
		if (result.hasErrors()) {
			ResponseData responseData = new ResponseData(false);
			responseData.setMessage(getErrorMessage(result, request));
			return responseData;
		}
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/product/remove")
    @ResponseBody
    public ResponseData remove(HttpServletRequest request,@RequestBody List<Product> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    
    
    @RequestMapping(value = "/product/delete")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<Product> dto){
    	IRequest requestCtx = createRequestContext(request);
    	service.delete(requestCtx, dto);
    	return new ResponseData(true);
    }
    }