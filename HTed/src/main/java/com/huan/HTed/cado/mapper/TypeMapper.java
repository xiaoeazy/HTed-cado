package com.huan.HTed.cado.mapper;

import java.util.List;

import com.huan.HTed.cado.dto.Type;
import com.huan.HTed.mybatis.common.Mapper;

public interface TypeMapper extends Mapper<Type>{
	public List<Type> queryTypeInfo();
}