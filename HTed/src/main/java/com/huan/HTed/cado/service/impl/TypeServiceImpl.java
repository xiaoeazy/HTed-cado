package com.huan.HTed.cado.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cado.dto.Type;
import com.huan.HTed.cado.mapper.TypeMapper;
import com.huan.HTed.cado.service.ITypeService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class TypeServiceImpl extends BaseServiceImpl<Type> implements ITypeService{
	
	@Autowired
	private TypeMapper typeMapper;
	
	public List<Type> queryTypeInfo(IRequest request){
		return typeMapper.queryTypeInfo();
	}
}