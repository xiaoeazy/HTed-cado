package com.huan.HTed.cado.controllers;

import org.springframework.stereotype.Controller;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.dto.ResponseData;
import com.huan.HTed.cado.dto.CatalogTypeDetail;
import com.huan.HTed.cado.dto.Product;
import com.huan.HTed.cado.dto.Type;
import com.huan.HTed.cado.dto.TypeProductDetail;
import com.huan.HTed.cado.service.ICatalogTypeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class CatalogTypeDetailController extends BaseController{

    @Autowired
    private ICatalogTypeDetailService service;


    @RequestMapping(value = "/catalog/type/detail/query")
    @ResponseBody
    public ResponseData query(CatalogTypeDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/catalog/type/detail/submit")
    @ResponseBody
	public ResponseData update(@RequestBody List<CatalogTypeDetail> dto, BindingResult result, HttpServletRequest request){
//		getValidator().validate(dto, result);
//		if (result.hasErrors()) {
//			ResponseData responseData = new ResponseData(false);
//			responseData.setMessage(getErrorMessage(result, request));
//			return responseData;
//		}
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/catalog/type/detail/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<CatalogTypeDetail> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    
    
    @RequestMapping(value = "/catalog/type/detail/queryHave")
    @ResponseBody
    public ResponseData queryHave(CatalogTypeDetail dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<CatalogTypeDetail> list =   service.queryHave(requestContext, dto);
        return new ResponseData(list);
    }
    
    @RequestMapping(value = "/catalog/type/detail/queryNotHave")
    @ResponseBody
    public ResponseData queryNotHave(CatalogTypeDetail dto, HttpServletRequest request) {
          IRequest requestContext = createRequestContext(request);
          List<Type> list =   service.queryNotHave(requestContext, dto);
        return new ResponseData(list);
    }
    
    
    }