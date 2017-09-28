package com.huan.HTed.cado.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cado.dto.Product;
import com.huan.HTed.cado.dto.Type;
import com.huan.HTed.cado.dto.TypeProductDetail;
import com.huan.HTed.cado.mapper.TypeProductDetailMapper;
import com.huan.HTed.cado.service.IProductService;
import com.huan.HTed.cado.service.ITypeProductDetailService;
import com.huan.HTed.cado.service.ITypeService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.dto.BaseDTO;
import com.huan.HTed.system.dto.DTOStatus;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class TypeProductDetailServiceImpl extends BaseServiceImpl<TypeProductDetail> implements ITypeProductDetailService{
	 @Autowired
	 private TypeProductDetailMapper typeProductDetailMapper;
	 @Autowired
	 private ITypeService typeService;
	 @Autowired
	 private IProductService productService;
	 
	 public List<Product> queryNotHave(IRequest request, TypeProductDetail condition){
		 return typeProductDetailMapper.queryNotHave(condition);
	 }
	 
	 public List<TypeProductDetail> queryHave(IRequest request, TypeProductDetail condition){
		 return typeProductDetailMapper.queryHave(condition);
	 }
	 
	 public List<TypeProductDetail> submit(IRequest request, List<TypeProductDetail> condition){
		  int i=0;
		  for (TypeProductDetail t : condition) {
	            switch (((BaseDTO) t).get__status()) {
	            case DTOStatus.ADD:{
	            	 if(i==0){
	            		 Type s = new Type();
	            		 s.setTypeId(t.getTypeId());
	            		 Type type = typeService.selectByPrimaryKey(request, s);
	            		 if(type==null){
	            			 throw new RuntimeException("该类型已经不存在");
	            		 }
	            	 }
	            	 Product p = new Product();
            		 p.setPid(t.getPid());
            		 Product product = productService.selectByPrimaryKey(request, p);
            		 if(product==null){
            			 throw new RuntimeException("产品已经不存在");
            		 }
	            	 i++;
	            	 break;
	            }
	            case DTOStatus.UPDATE:{
	            	 if(i==0){
	            		 Type s = new Type();
	            		 s.setTypeId(t.getTypeId());
	            		 Type type = typeService.selectByPrimaryKey(request, s);
	            		 if(type==null){
	            			 throw new RuntimeException("该类型已经不存在");
	            		 }
	            	 }
	            	 Product p = new Product();
            		 p.setPid(t.getPid());
            		 Product product = productService.selectByPrimaryKey(request, p);
            		 if(product==null){
            			 throw new RuntimeException("该产品已经不存在");
            		 }
	            	 i++;
	            	 break;
	            }
	            case DTOStatus.DELETE:{
	            	 break;
	            }
	            default:
	                break;
	            }
	        }
		  return this.batchUpdate(request, condition);
	 }
}