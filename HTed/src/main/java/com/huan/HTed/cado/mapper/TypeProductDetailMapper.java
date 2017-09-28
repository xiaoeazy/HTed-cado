package com.huan.HTed.cado.mapper;

import java.util.List;

import com.huan.HTed.cado.dto.Product;
import com.huan.HTed.cado.dto.TypeProductDetail;
import com.huan.HTed.mybatis.common.Mapper;

public interface TypeProductDetailMapper extends Mapper<TypeProductDetail>{
	public void deleteByPidList ( List<TypeProductDetail> dto);
	
	
	 List<Product> queryNotHave( TypeProductDetail condition);
	 List<TypeProductDetail> queryHave( TypeProductDetail condition);
}