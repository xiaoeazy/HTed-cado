package com.huan.HTed.cado.mapper;

import java.util.List;

import com.huan.HTed.cado.dto.Card;
import com.huan.HTed.cado.dto.TypeCardDetail;
import com.huan.HTed.mybatis.common.Mapper;

public interface TypeCardDetailMapper extends Mapper<TypeCardDetail>{
	 List<Card> queryNotHave( TypeCardDetail condition);
	 List<TypeCardDetail> queryHave( TypeCardDetail condition);
	
	
}