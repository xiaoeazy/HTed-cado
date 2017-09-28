package com.huan.HTed.cado.dto;


import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import com.huan.HTed.system.dto.BaseDTO;
@ExtensionAttribute(disable=true)
@Table(name = "cado_type_product_detail")
public class TypeProductDetail extends BaseDTO {

     public static final String FIELD_TYPE_ID = "typeId";
     public static final String FIELD_PID = "pid";
     public static final String FIELD_ORDER = "order";


     @Id
     private Long typeId;
     
     @Id
     @Length(max = 20)
     private Long pid;

     @Length(max = 20)
     private Long orders;
     
     @Transient
     private Product product;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
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

	public static String getFieldPid() {
		return FIELD_PID;
	}

	public static String getFieldOrder() {
		return FIELD_ORDER;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}



     }
