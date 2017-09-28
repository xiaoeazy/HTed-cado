package com.huan.HTed.cado.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cado.dto.Type;
import com.huan.HTed.cado.service.ITypeService;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class TypeServiceImpl extends BaseServiceImpl<Type> implements ITypeService{

}