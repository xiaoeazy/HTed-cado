package com.huan.HTed.cado.service;

import java.util.List;

import com.huan.HTed.cado.dto.Product;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

public interface IProductService extends IBaseService<Product>, ProxySelf<IProductService>{
		public void delete ( IRequest requestCtx,List<Product> dto);
}