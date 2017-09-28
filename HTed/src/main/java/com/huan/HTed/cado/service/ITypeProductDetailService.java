package com.huan.HTed.cado.service;

import java.util.List;

import com.huan.HTed.cado.dto.Product;
import com.huan.HTed.cado.dto.TypeProductDetail;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

public interface ITypeProductDetailService extends IBaseService<TypeProductDetail>, ProxySelf<ITypeProductDetailService>{
	 List<TypeProductDetail> queryHave(IRequest request, TypeProductDetail condition);
	 List<Product> queryNotHave(IRequest request, TypeProductDetail condition);
	 
	 List<TypeProductDetail> submit(IRequest request, List<TypeProductDetail> condition);
}