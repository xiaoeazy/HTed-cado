package com.huan.HTed.cado.service;

import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

import java.util.List;

import com.huan.HTed.cado.dto.Catalog;

public interface ICatalogService extends IBaseService<Catalog>, ProxySelf<ICatalogService>{
		public void delete(IRequest requestCtx,List<Catalog> dto);
}