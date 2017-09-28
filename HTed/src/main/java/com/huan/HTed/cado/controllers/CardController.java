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

import com.huan.HTed.cado.dto.Card;
import com.huan.HTed.cado.service.ICardService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.system.dto.ResponseData;

    @Controller
    public class CardController extends BaseController{

    @Autowired
    private ICardService service;


    @RequestMapping(value = "/card/query")
    @ResponseBody
    public ResponseData query(Card dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

//    @RequestMapping(value = "/card/submit")
//    @ResponseBody
//	public ResponseData update(@RequestBody List<Card> dto, BindingResult result, HttpServletRequest request){
//    	getValidator().validate(dto, result);
//		if (result.hasErrors()) {
//			ResponseData responseData = new ResponseData(false);
//			responseData.setMessage(getErrorMessage(result, request));
//			return responseData;
//		}
//        IRequest requestCtx = createRequestContext(request);
//        return new ResponseData(service.batchUpdate(requestCtx, dto));
//    }

    @RequestMapping(value = "/card/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<Card> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    
    
    
    
    
    @RequestMapping(value = "/card/queryByCard")
    @ResponseBody
    public ResponseData queryByCard(Card dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
    	 String url = request.getScheme() +"://" + request.getServerName()   + ":" +request.getServerPort() +""+request.getContextPath();
        IRequest requestContext = createRequestContext(request);
        List<Card> list =   service.queryByCard(requestContext,dto,page,pageSize);
        for(Card card:list){
        	card.setCardImagePath(url+card.getCardImagePath());
        }
        return new ResponseData(list);
    }
    
    
    @RequestMapping(value = "/card/submit")
    @ResponseBody
	public ResponseData submitByCard(@RequestBody List<Card> dtoList, BindingResult result, HttpServletRequest request){
    	ResponseData rd = new ResponseData();
    	try {
			IRequest requestCtx = createRequestContext(request);
			Card dto = dtoList.get(0);
			Card card = service.submitByCard(request,requestCtx, dto);
			rd.setSuccess(true);
			rd.setMessage("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			rd.setSuccess(false);
			rd.setMessage(e.getMessage());
		}
        return rd;
    }

    }