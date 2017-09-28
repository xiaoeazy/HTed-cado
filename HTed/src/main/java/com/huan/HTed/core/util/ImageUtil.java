package com.huan.HTed.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {
	  /** 日志记录. **/
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	
	
	public static void fileCopy(String oriPath ,String toPath){
			FileInputStream fis=null;
			FileOutputStream fos= null;
		  	try {
		  		fis = new FileInputStream(oriPath);
		  		fos = new FileOutputStream(toPath);
				//我们用read(byte[]b,int off,int len)函数来读-确保两张图片大小一致，且效率还高
				byte[] b=new byte[1024];
				int num=0;
			    while((num=fis.read(b,10,1000))!=-1){
				        fos.write(b,10,num);
			    }
				   
			} catch (FileNotFoundException e) {
				logger.error("error in fileCopy",e);
			} catch (IOException e) {
				logger.error("error in fileCopy",e);
			}finally{
				try {
					fis.close();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
}
