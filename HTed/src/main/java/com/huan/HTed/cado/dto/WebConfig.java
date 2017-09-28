package com.huan.HTed.cado.dto;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.huan.HTed.system.dto.BaseDTO;
@ExtensionAttribute(disable=true)
@Table(name = "cado_web_config")
public class WebConfig extends BaseDTO {

     public static final String FIELD_CONFIG_ID = "configId";
     public static final String FIELD_APP_ID = "appId";
     public static final String FIELD_APP_SECRET = "appSecret";
     public static final String FIELD_MCH_ID = "mchId";
     public static final String FIELD_APP_KEY = "appKey";


     @Id
     @GeneratedValue
     private String configId;

     @Length(max = 100)
     private String configKey; 

     @Length(max = 100)
     private String configValue; 

     @Length(max = 100)
     private String configCommit; 



     public void setConfigId(String configId){
         this.configId = configId;
     }

     public String getConfigId(){
         return configId;
     }

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getConfigCommit() {
		return configCommit;
	}

	public void setConfigCommit(String configCommit) {
		this.configCommit = configCommit;
	}

   

     }
