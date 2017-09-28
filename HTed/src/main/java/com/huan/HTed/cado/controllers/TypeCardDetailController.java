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
import com.huan.HTed.cado.dto.TypeCardDetail;
import com.huan.HTed.cado.service.ITypeCardDetailService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.system.dto.ResponseData;

    @Controller
    public class TypeCardDetailController extends BaseController{

    @Autowired
    private ITypeCardDetailService service;


    @RequestMapping(value = "/type/card/detail/query")
    @ResponseBody
    public ResponseData query(TypeCardDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/type/card/detail/submit")
    @ResponseBody
	public ResponseData update(@RequestBody List<TypeCardDetail> dto, BindingResult result, HttpServletRequest request){
//		getValidator().validate(dto, result);
//		if (result.hasErrors()) {
//			ResponseData responseData = new ResponseData(false);
//			responseData.setMessage(getErrorMessage(result, request));
//			return responseData;
//		}
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.submit(requestCtx, dto));
    }

    @RequestMapping(value = "/type/card/detail/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<TypeCardDetail> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    
    @RequestMapping(value = "/type/card/detail/queryHave")
    @ResponseBody
    public ResponseData queryHave(TypeCardDetail dto, HttpServletRequest request) {
        String url = request.getScheme() +"://" + request.getServerName()   + ":" +request.getServerPort() +""+request.getContextPath();
        IRequest requestContext = createRequestContext(request);
        List<TypeCardDetail> list =   service.queryHave(requestContext, dto);
        for(TypeCardDetail typeCard:list){
        	Card card = typeCard.getCard();
        	card.setCardImagePath(url+card.getCardImagePath());
        }
        return new ResponseData(list);
    }
    
    @RequestMapping(value = "/type/card/detail/queryNotHave")
    @ResponseBody
    public ResponseData queryNotHave(TypeCardDetail dto, HttpServletRequest request) {
    	  String url = request.getScheme() +"://" + request.getServerName()   + ":" +request.getServerPort() +""+request.getContextPath();
          IRequest requestContext = createRequestContext(request);
          List<Card> list =   service.queryNotHave(requestContext, dto);
          for(Card card:list){
          	card.setCardImagePath(url+card.getCardImagePath());
          }
        return new ResponseData(list);
    }
    }