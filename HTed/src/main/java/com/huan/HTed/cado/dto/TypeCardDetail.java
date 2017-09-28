package com.huan.HTed.cado.dto;


import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import com.huan.HTed.system.dto.BaseDTO;
@ExtensionAttribute(disable=true)
@Table(name = "cado_type_card_detail")
public class TypeCardDetail extends BaseDTO {

     public static final String FIELD_TYPE_ID = "typeId";
     public static final String FIELD_CARD_ID = "cardId";
     public static final String FIELD_ORDER = "order";


     @Id
     private Long typeId;

     @Id
     @Length(max = 20)
     private Long cardId;

     @Length(max = 20)
     private Long orders;
     
     
     @Transient
     private Card card;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public Long getOrders() {
		return orders;
	}

	public void setOrders(Long orders) {
		this.orders = orders;
	}

	public static String getFieldTypeId() {
		return FIELD_TYPE_ID;
	}

	public static String getFieldCardId() {
		return FIELD_CARD_ID;
	}

	public static String getFieldOrder() {
		return FIELD_ORDER;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}


    

     }
