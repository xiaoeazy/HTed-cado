package com.huan.HTed.cado.service;

import java.util.List;

import com.huan.HTed.cado.dto.Type;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.system.service.IBaseService;

public interface ITypeService extends IBaseService<Type>, ProxySelf<ITypeService>{
	 List<Type> queryTypeInfo(IRequest request);
}