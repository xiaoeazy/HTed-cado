package com.huan.HTed.cado.dto;


import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import com.huan.HTed.system.dto.BaseDTO;
@ExtensionAttribute(disable=true)
@Table(name = "cado_type")
public class Type extends BaseDTO {

     public static final String FIELD_TYPE_ID = "typeId";
     public static final String FIELD_TYPE_NAME = "typeName";


     @Id
     @GeneratedValue
     private Long typeId;

     @NotEmpty
     @Length(max = 45)
     private String typeName;
     
     @Transient
     private List<Card> cardList;

     

     public List<Card> getCardList() {
		return cardList;
	}

	 public void setCardList(List<Card> cardList) {
		this.cardList = cardList;
	}

	 public void setTypeId(Long typeId){
         this.typeId = typeId;
     }

     public Long getTypeId(){
         return typeId;
     }

     public void setTypeName(String typeName){
         this.typeName = typeName;
     }

     public String getTypeName(){
         return typeName;
     }

     }
