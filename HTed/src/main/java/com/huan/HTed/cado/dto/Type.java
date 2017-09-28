package com.huan.HTed.cado.dto;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.huan.HTed.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
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
