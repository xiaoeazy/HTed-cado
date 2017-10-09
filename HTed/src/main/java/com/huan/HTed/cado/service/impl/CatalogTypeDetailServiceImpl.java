package com.huan.HTed.cado.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cado.dto.CatalogTypeDetail;
import com.huan.HTed.cado.dto.Type;
import com.huan.HTed.cado.mapper.CatalogTypeDetailMapper;
import com.huan.HTed.cado.service.ICatalogTypeDetailService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class CatalogTypeDetailServiceImpl extends BaseServiceImpl<CatalogTypeDetail> implements ICatalogTypeDetailService{
	
	 @Autowired
	 private CatalogTypeDetailMapper CatalogTypeDetailMapper;
	
	 public List<Type> queryNotHave(IRequest request, CatalogTypeDetail condition){
		 return CatalogTypeDetailMapper.queryNotHave(condition);
	 }
	 
	 public List<CatalogTypeDetail> queryHave(IRequest request, CatalogTypeDetail condition){
		 return CatalogTypeDetailMapper.queryHave(condition);
	 }
}