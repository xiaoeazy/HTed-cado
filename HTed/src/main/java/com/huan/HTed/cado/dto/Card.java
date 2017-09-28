package com.huan.HTed.cado.dto;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.huan.HTed.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
@ExtensionAttribute(disable=true)
@Table(name = "cado_card")
public class Card extends BaseDTO  {

     public static final String FIELD_CARD_ID = "cardId";
     public static final String FIELD_CARD_NAME = "cardName";
     public static final String FIELD_CARD_IMAGE_PATH = "cardImagePath";


     @Id
     @GeneratedValue
     private Long cardId;

     @NotEmpty
     @Length(max = 45)
     private String cardName;

     @NotEmpty
     @Length(max = 200)
     private String cardImagePath;


     public void setCardId(Long cardId){
         this.cardId = cardId;
     }

     public Long getCardId(){
         return cardId;
     }

     public void setCardName(String cardName){
         this.cardName = cardName;
     }

     public String getCardName(){
         return cardName;
     }

     public void setCardImagePath(String cardImagePath){
         this.cardImagePath = cardImagePath;
     }

     public String getCardImagePath(){
         return cardImagePath;
     }

     }
