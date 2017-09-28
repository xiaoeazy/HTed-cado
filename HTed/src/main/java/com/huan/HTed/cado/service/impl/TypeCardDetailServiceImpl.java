package com.huan.HTed.cado.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cado.dto.Card;
import com.huan.HTed.cado.dto.Type;
import com.huan.HTed.cado.dto.TypeCardDetail;
import com.huan.HTed.cado.mapper.TypeCardDetailMapper;
import com.huan.HTed.cado.service.ICardService;
import com.huan.HTed.cado.service.ITypeCardDetailService;
import com.huan.HTed.cado.service.ITypeService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.system.dto.BaseDTO;
import com.huan.HTed.system.dto.DTOStatus;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class TypeCardDetailServiceImpl extends BaseServiceImpl<TypeCardDetail> implements ITypeCardDetailService {
	@Autowired
	private TypeCardDetailMapper typeCardDetailMapper;

	@Autowired
	private ITypeService typeService;
	@Autowired
	private ICardService cardService;

	public List<Card> queryNotHave(IRequest request, TypeCardDetail condition) {
		return typeCardDetailMapper.queryNotHave(condition);
	}

	public List<TypeCardDetail> queryHave(IRequest request, TypeCardDetail condition) {
		return typeCardDetailMapper.queryHave(condition);
	}

	public List<TypeCardDetail> submit(IRequest request, List<TypeCardDetail> condition) {
		int i = 0;
		for (TypeCardDetail t : condition) {
			switch (((BaseDTO) t).get__status()) {
			case DTOStatus.ADD: {
				if (i == 0) {
					Type s = new Type();
					s.setTypeId(t.getTypeId());
					Type type = typeService.selectByPrimaryKey(request, s);
					if (type == null) {
						throw new RuntimeException("该类型已经不存在");
					}
				}
				Card c = new Card();
				c.setCardId(t.getCardId());
				Card card = cardService.selectByPrimaryKey(request, c);
				if (card == null) {
					throw new RuntimeException("卡片已经不存在");
				}
				i++;
				break;
			}
			case DTOStatus.UPDATE: {
				if (i == 0) {
					Type s = new Type();
					s.setTypeId(t.getTypeId());
					Type type = typeService.selectByPrimaryKey(request, s);
					if (type == null) {
						throw new RuntimeException("该类型已经不存在");
					}
				}
				Card c = new Card();
				c.setCardId(t.getCardId());
				Card card = cardService.selectByPrimaryKey(request, c);
				if (card == null) {
					throw new RuntimeException("卡片已经不存在");
				}
				i++;
				break;
			}
			case DTOStatus.DELETE: {
				break;
			}
			default:
				break;
			}
		}
		return this.batchUpdate(request, condition);
	}
}