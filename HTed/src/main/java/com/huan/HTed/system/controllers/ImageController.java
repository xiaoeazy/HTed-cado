
package com.huan.HTed.system.controllers;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huan.HTed.cado.service.impl.CardServiceImpl;
import com.huan.HTed.system.dto.ResponseData;

/**
 * 图片上传.
 * 
 */
@Controller
public class ImageController extends BaseController {

    /** 日志记录. **/
    private static Logger logger = LoggerFactory.getLogger(ImageController.class);

    @RequestMapping(value="/image/upload",method=RequestMethod.POST)  
    @ResponseBody
    public ResponseData fildUpload(HttpServletRequest request,@RequestParam(value="file",required=false) MultipartFile file){  
        //基本表单  
        //获得物理路径webapp所在路径
    	ResponseData rd = new ResponseData();
        try {
			String pathRoot = request.getSession().getServletContext().getRealPath("");  
			String path="";  
			if(!file.isEmpty()){  
			    //生成uuid作为文件名称  
			    String uuid = UUID.randomUUID().toString().replaceAll("-","");  
			    //获得文件类型（可以判断如果不是图片，禁止上传）  
			    String contentType=file.getContentType();  
			    //获得文件后缀名称  
			    String imageName=contentType.substring(contentType.indexOf("/")+1);  
			    path=CardServiceImpl.UPLOADPATH+uuid+"."+imageName;  
			    file.transferTo(new File(pathRoot+path));  
			}
			rd.setSuccess(true);
			rd.setMessage(path);
		} catch (Exception e) {
			logger.error("上传出错"+e.getMessage());
			rd.setSuccess(false);
			rd.setMessage(e.getMessage());
		}  
        return rd;
    }  
    
   
  

}
