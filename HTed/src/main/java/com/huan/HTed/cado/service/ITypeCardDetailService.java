package com.huan.HTed.cado.service;

import java.util.List;

import com.huan.HTed.cado.dto.Card;
import com.huan.HTed.cado.dto.TypeCardDetail;
import com.huan.HTed.cado.dto.TypeProductDetail;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

public interface ITypeCardDetailService extends IBaseService<TypeCardDetail>, ProxySelf<ITypeCardDetailService>{
	 List<TypeCardDetail> queryHave(IRequest request, TypeCardDetail condition);
	 List<Card> queryNotHave(IRequest request, TypeCardDetail condition);
	 List<TypeCardDetail> submit(IRequest request, List<TypeCardDetail> condition);
}