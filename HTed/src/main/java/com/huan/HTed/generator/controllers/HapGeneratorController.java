package com.huan.HTed.generator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huan.HTed.generator.service.IHapGeneratorService;
import com.huan.HTed.generator.service.impl.GeneratorInfo;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.system.dto.ResponseData;


@Controller
@RequestMapping(value = "/generator")
public class HapGeneratorController extends BaseController{
    @Autowired
    IHapGeneratorService service;

    @RequestMapping(value = "/alltables",method = RequestMethod.GET)
    @ResponseBody
    public ResponseData showTables(){
        return new ResponseData(service.showTables());
    }

    @RequestMapping(value = "/newtables")
    @ResponseBody
    public int generatorTables(GeneratorInfo generatorInfo){

      /*  GeneratorInfo info=new GeneratorInfo();
        info.setProjectPath("D:/JetBrains/workspaces/hap-parent/hap");
        info.setParentPackagePath("com/hand/hap");
        info.setPackagePath("tt");
        info.setTargetName("sys_file");

        info.setControllerName("FileController.java");
        info.setDtoName("File.java");
        info.setImplName("FileServiceImpl.java");
        info.setMapperName("FileMapper.java");
        info.setMapperXmlName("FileMapper.xml");
        info.setServiceName("IFileService.java");
        info.setHtmlName("file.html");
        info.setHtmlModelName("htmlemptymodel.ftl");
        info.setHtmlStatus("Create");

        info.setControllerStatus("Create");
        info.setDtoStatus("Create");
        info.setImplStatus("Create");
        info.setMapperStatus("Create");
        info.setMapperXmlStatus("NotOperation");
        info.setServiceStatus("Create");
        info.setHtmlStatus("Create");*/

        int rs=service.generatorFile(generatorInfo);
        return rs;
    }


}
