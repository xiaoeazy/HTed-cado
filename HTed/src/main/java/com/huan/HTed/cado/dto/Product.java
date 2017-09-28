package com.huan.HTed.cado.dto;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.huan.HTed.system.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;
@ExtensionAttribute(disable=true)
@Table(name = "cado_product")
public class Product extends BaseDTO {

     public static final String FIELD_PID = "pid";
     public static final String FIELD_PRODUCT_NAME = "productName";
     public static final String FIELD_PRICE = "price";


     @Id
     @GeneratedValue
     private Long pid;

     @NotEmpty
     @Length(max = 45)
     private String productName;

     private Long price;


     public void setPid(Long pid){
         this.pid = pid;
     }

     public Long getPid(){
         return pid;
     }

     public void setProductName(String productName){
         this.productName = productName;
     }

     public String getProductName(){
         return productName;
     }

     public void setPrice(Long price){
         this.price = price;
     }

     public Long getPrice(){
         return price;
     }

     }
