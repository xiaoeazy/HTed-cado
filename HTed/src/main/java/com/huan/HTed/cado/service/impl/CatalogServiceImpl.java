package com.huan.HTed.cado.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cado.dto.Catalog;
import com.huan.HTed.cado.dto.CatalogTypeDetail;
import com.huan.HTed.cado.service.ICatalogService;
import com.huan.HTed.cado.service.ICatalogTypeDetailService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class CatalogServiceImpl extends BaseServiceImpl<Catalog> implements ICatalogService{
	@Autowired
	private ICatalogTypeDetailService  iCatalogTypeDetailService;
	
	public void delete(IRequest requestCtx,List<Catalog> dto){
		this.batchDelete(dto);
		List<CatalogTypeDetail> list  = new ArrayList<CatalogTypeDetail>();
		for(Catalog c:dto){
			CatalogTypeDetail ctd = new CatalogTypeDetail();
			ctd.setCatalogId(c.getCatalogId());
			ctd.set__status("delete");
			list.add(ctd);
		}
		iCatalogTypeDetailService.batchDelete(list);
	}
}