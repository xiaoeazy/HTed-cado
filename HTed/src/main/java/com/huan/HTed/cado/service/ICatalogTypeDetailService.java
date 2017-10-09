package com.huan.HTed.cado.service;

import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

import java.util.List;

import com.huan.HTed.cado.dto.CatalogTypeDetail;
import com.huan.HTed.cado.dto.Type;

public interface ICatalogTypeDetailService extends IBaseService<CatalogTypeDetail>, ProxySelf<ICatalogTypeDetailService>{
	 public List<Type> queryNotHave(IRequest request, CatalogTypeDetail condition);
	 public List<CatalogTypeDetail> queryHave(IRequest request, CatalogTypeDetail condition);
	 
}