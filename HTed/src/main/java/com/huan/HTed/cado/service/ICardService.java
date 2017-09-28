package com.huan.HTed.cado.service;

import com.huan.HTed.cado.dto.Card;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ICardService extends IBaseService<Card>, ProxySelf<ICardService>{
	 public List<Card> queryByCard(IRequest request, Card condition, int pageNum, int pageSize) ;
	 public Card submitByCard(HttpServletRequest request,IRequest iRequest, Card condition)  throws Exception;
}