package com.huan.HTed.cado.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cado.dto.Card;
import com.huan.HTed.cado.service.ICardService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.util.ImageUtil;
import com.huan.HTed.system.dto.DTOStatus;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class CardServiceImpl extends BaseServiceImpl<Card> implements ICardService {
	public static final String UPLOADPATH = "/resources/card/upload/";
	public static final String CARDPATH = "/resources/card/";

	public List<Card> queryByCard(IRequest request, Card condition, int pageNum, int pageSize) {
		List<Card> list = this.select(request, condition, pageNum, pageSize);
		return list;
	}

	public Card submitByCard(HttpServletRequest request, IRequest iRequest, Card condition) throws Exception {
		String pathRoot = request.getSession().getServletContext().getRealPath("");
		String oriPath = pathRoot + condition.getCardImagePath();
		switch (condition.get__status()) {
		case DTOStatus.ADD: {
			String fileName = oriPath.substring(oriPath.lastIndexOf("/") + 1);
			String newPath = CARDPATH + fileName;
			String toPath = pathRoot + newPath;
			ImageUtil.fileCopy(oriPath, toPath);
			condition.setCardImagePath(newPath);
			int i = mapper.insertSelective(condition);
			break;
		}
		case DTOStatus.UPDATE: {
			Card card = mapper.selectByPrimaryKey(condition);
			if (card == null) {
				throw new Exception("该卡片已经不存在");
			}
			if (!condition.getCardImagePath().equals(card.getCardImagePath())) {
				String fileName = oriPath.substring(oriPath.lastIndexOf("/") + 1);
				String newPath = CARDPATH + fileName;
				String toPath = pathRoot + newPath;
				condition.setCardImagePath(newPath);
				ImageUtil.fileCopy(oriPath, toPath);
			}
			int i = mapper.updateByPrimaryKeySelective(condition);
			break;
		}
		default:
			break;

		}

		return condition;
	}
}