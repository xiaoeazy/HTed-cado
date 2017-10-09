package com.huan.HTed.cado.mapper;

import com.huan.HTed.mybatis.common.Mapper;

import java.util.List;

import com.huan.HTed.cado.dto.CatalogTypeDetail;
import com.huan.HTed.cado.dto.Type;
import com.huan.HTed.core.IRequest;

public interface CatalogTypeDetailMapper extends Mapper<CatalogTypeDetail>{
	public List<Type> queryNotHave( CatalogTypeDetail condition);
	public List<CatalogTypeDetail> queryHave(CatalogTypeDetail condition);
}