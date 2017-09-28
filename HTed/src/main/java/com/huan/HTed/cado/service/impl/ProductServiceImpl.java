package com.huan.HTed.cado.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cado.dto.Product;
import com.huan.HTed.cado.dto.TypeProductDetail;
import com.huan.HTed.cado.mapper.TypeProductDetailMapper;
import com.huan.HTed.cado.service.IProductService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService{
	@Autowired
	TypeProductDetailMapper typeProductDetailMapper;
	
	public void delete ( IRequest requestCtx,List<Product> dto){
		this.batchDelete(dto);
		List<TypeProductDetail> list =new ArrayList<TypeProductDetail>();
		for(Product p:dto){
			TypeProductDetail pd = new TypeProductDetail();
			pd.setPid(p.getPid());
			list.add(pd);
		}
		typeProductDetailMapper.deleteByPidList(list);
		
	}
}