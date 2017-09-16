
package com.huan.HTed.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.huan.HTed.message.profile.SystemConfigListener;

public class PasswordManager  implements InitializingBean,SystemConfigListener {
	private String defaultPassword = "123456";
	
	public boolean matches(String password1,String password2){
		if(password1.equals(password2))
			return true;
		else 
			return false;
	}
	
    public String encode(String password){
    	MessageDigest md5;
    	String md5str =null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		
	    	 // 2 将消息变成byte数组  
	        byte[] input = password.getBytes();  
	  
	        // 3 计算后获得字节数组,这就是那128位了  
	        byte[] buff = md5.digest(input);  
	  
	        // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串  
	         md5str = bytesToHex(buff); 
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
    	return md5str;
    }
    
    
    private static String bytesToHex(byte[] bytes) {  
        StringBuffer md5str = new StringBuffer();  
        // 把数组每一字节换成16进制连成md5字符串  
        int digital;  
        for (int i = 0; i < bytes.length; i++) {  
            digital = bytes[i];  
      
            if (digital < 0) {  
            digital += 256;  
            }  
            if (digital < 16) {  
            md5str.append("0");  
            }  
            md5str.append(Integer.toHexString(digital));  
        }  
        return md5str.toString().toUpperCase();  
    }


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}  
	
	 public String getDefaultPassword() {
	        return defaultPassword;
    }

	@Override
	public List<String> getAcceptedProfiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateProfile(String profileName, String profileValue) {
		// TODO Auto-generated method stub
		
	}
}
