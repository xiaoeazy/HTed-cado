package com.huan.HTed.generator.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.huan.HTed.core.ProxySelf;
import com.huan.HTed.generator.dto.DBColumn;
import com.huan.HTed.generator.dto.DBTable;
import com.huan.HTed.system.controllers.BaseController;
import com.huan.HTed.system.dto.BaseDTO;
import com.huan.HTed.system.service.IBaseService;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Component
public class FileUtil {

    public static enum pType {Controller, MapperXml, Mapper, Service, Impl, Html}
    private GeneratorInfo generatorInfo;
    private List<String> allClassFiles;
    private List<String> allXmlFiles;
    private List<String> allHtmlFiles;

    //初始化util信息
    public void setGeneratorInfo(GeneratorInfo info) {
        this.generatorInfo = info;
    }

    public void getFileArray() {
        this.allClassFiles = new ArrayList<>();
        this.allXmlFiles = new ArrayList<>();
        this.allHtmlFiles = new ArrayList<>();
    }

/*    public String toCamelCase(String str) {
        str = str.substring(str.indexOf("_") + 1);
        str = str.toUpperCase();
        if (str.endsWith("_B")) {
            str = str.substring(0, str.lastIndexOf("_B"));
        }
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        String s1 = sb.toString();
        s1 = s1.substring(0, 1).toUpperCase() + s1.substring(1);
        return s1;
    }*/

    public String columnToCamel(String str) {
        Pattern linePattern = Pattern.compile("_(\\w)");
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        String s = sb.toString();
        if (s.endsWith("_")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public void createDto(DBTable table) throws IOException {
        //key 是否需要引入相对包
        boolean needUtil = false;
        boolean needSql = false;
        boolean needNotNull = false;
        boolean needNotEmpty = false;

       // String name = toCamelCase(table.getName());
        String name=generatorInfo.getDtoName().substring(0,generatorInfo.getDtoName().indexOf("."));
        String projectPath = generatorInfo.getProjectPath();
        String parentPackagePath = generatorInfo.getParentPackagePath();
        String packagePath = generatorInfo.getPackagePath();
        String directory = projectPath + "/src/main/java/" + parentPackagePath + "/" + packagePath + "/dto/" + generatorInfo.getDtoName();

        File file = new File(directory);
        createFileDir(file);
        file.createNewFile();
        List<DBColumn> columns = table.getColumns();
        //判断是否需要引入包
        for (DBColumn s : columns) {
            s.setName(columnToCamel(s.getName()));
            if (s.getType().equals("DATETIME")) {
                s.setType("Date");
                needUtil = true;
            } else if (s.getType().equals("IMAGE") || s.getType().equals("TEXT")) {
                needSql = true;
                s.setType("String");
            } else if (s.getType().equals("BIGINT") || s.getType().equals("INT") || s.getType().equals("DOUBLE")) {
                s.setType("Long");
            } else if (s.getType().equals("VARCHAR")) {
                s.setType("String");
            } else if (s.getType().equals("DECIMAL")) {
                s.setType("Long");
            } else if (s.getType().equals("TIMESTAMP")) {
                s.setType("Date");
                needUtil = true;
            }

            if (s.isNotNull()) {
                needNotNull = true;
            } else if (s.isNotEmpty()) {
                needNotEmpty = true;
            }
        }
        //生成属性
        StringBuffer sb = new StringBuffer();
        String dir1 = parentPackagePath + "/" + packagePath + "/dto";
        dir1 = dir1.replaceAll("/", ".");
        sb.append("package " + dir1 + ";\r\n\r\n");
        sb.append("import javax.persistence.GeneratedValue;\r\n");
        sb.append("import javax.persistence.Id;\r\n");
        sb.append("import javax.persistence.Table;\r\n");
        String d = BaseDTO.class.getName();
        sb.append("import " + d + ";\r\n");
        if (needUtil) {
            sb.append("import java.util.Date;\r\n");
        }
        if (needSql) {
            sb.append("import java.sql.*;\r\n");
        }
        if (needNotNull) {
            sb.append("import javax.validation.constraints.NotNull;\r\n");
        }
        if (needNotEmpty) {
            sb.append("import org.hibernate.validator.constraints.NotEmpty;\r\n");
        }
        sb.append("@Table(name = " + "\"" + table.getName() + "\")\r\n");
        sb.append("public class " + name + " extends BaseDTO {\r\n\r\n");
        //生成属性
        for (DBColumn cl : columns) {
            if (cl.isId()) {
                sb.append("@Id\r\n@GeneratedValue\r\n");
            } else {
                if (cl.isNotEmpty()) {
                    sb.append("@NotEmpty\r\n");
                } else if (cl.isNotNull()) {
                    sb.append("@NotNull\r\n");
                }
            }
            if (cl.isMultiLanguage()) {
                sb.append("@MultiLanguageField\r\n");
            }
            String str = "private " + cl.getType() + " " + cl.getName() + ";\r\n\r\n";
            sb.append(str);
        }
        //生成get 和 set方法
        sb.append("\r\n\r\n\r\n");
        for (DBColumn cl : columns) {
            String name1 = cl.getName();
            String name2 = name1.substring(0, 1).toUpperCase() + name1.substring(1);
            sb.append("public void set" + name2 + "(" + cl.getType() + " " + name1 + "){\r\n");
            sb.append("this." + name1 + " = " + name1 + ";\r\n");
            sb.append("}\r\n\r\n");
            sb.append("public " + cl.getType() + " get" + name2 + "(){\r\n");
            sb.append("return " + name1 + ";\r\n");
            sb.append("}\r\n\r\n");
        }
        sb.append("}\r\n");

        PrintWriter p = new PrintWriter(new FileOutputStream(file));
        p.write(sb.toString());
        p.close();

    }


    public void createFtlInfoByType(pType type, DBTable table) throws IOException, TemplateException {
        String projectPath = generatorInfo.getProjectPath();
        String parentPackagePath = generatorInfo.getParentPackagePath();
        String packagePath = generatorInfo.getPackagePath();
        String htmlModelName = generatorInfo.getHtmlModelName();
        String pac = parentPackagePath + "/" + packagePath;
        FtlInfo info = new FtlInfo();
        String directory = null;
        List<String> importPackages = new ArrayList<>();
        if (type == pType.Controller) {
            directory = projectPath + "/src/main/java/" + pac + "/controllers/" + generatorInfo.getControllerName();
            importPackages.add("org.springframework.stereotype.Controller;");
            importPackages.add(BaseController.class.getName());
        } else if (type == pType.Mapper) {
            directory = projectPath + "/src/main/java/" + pac + "/mapper/" + generatorInfo.getMapperName();
            importPackages.add("com.huan.HTed.mybatis.common.Mapper");
        } else if (type == pType.MapperXml) {
            directory = projectPath + "/src/main/resources/" + pac + "/mapper/" + generatorInfo.getMapperXmlName();
        } else if (type == pType.Service) {
            directory = projectPath + "/src/main/java/" + pac + "/service/" + generatorInfo.getServiceName();
            importPackages.add(ProxySelf.class.getName());
            importPackages.add(IBaseService.class.getName());
        } else if (type == pType.Impl) {
            directory = projectPath + "/src/main/java/" + pac + "/service/impl/" + generatorInfo.getImplName();
            importPackages.add(BaseServiceImpl.class.getName());
            importPackages.add("org.springframework.stereotype.Service");
        } else if (type == pType.Html) {
            directory = projectPath + "/src/main/webapp/WEB-INF/view/" + packagePath + "/" + generatorInfo.getHtmlName();
        }
        pac = pac.replaceAll("/", ".");
        info.setPackageName(pac);
        info.setDir(directory);
        info.setProjectPath(projectPath);
       // info.setFileName(name);
        info.setImportName(importPackages);
        info.setHtmlModelName(htmlModelName);
        createFtl(info, type);
    }

    public void createFtl(FtlInfo ftlInfo, pType type) throws IOException, TemplateException {
        Configuration cfg = new Configuration();
        Template template = null;
        Map<String, Object> map = new HashMap<String, Object>();
        cfg.setDirectoryForTemplateLoading(new File(ftlInfo.getProjectPath() + "/src/main/webapp/WEB-INF/view/generator/ftl"));
        if (type == pType.Controller) {
            template = cfg.getTemplate("controllers.ftl");
        } else if (type == pType.MapperXml) {
            template = cfg.getTemplate("mapperxml.ftl");
        } else if (type == pType.Mapper) {
            template = cfg.getTemplate("mapper.ftl");
        } else if (type == pType.Service) {
            template = cfg.getTemplate("service.ftl");
        } else if (type == pType.Impl) {
            template = cfg.getTemplate("impl.ftl");
        } else if (type == pType.Html) {
            template = cfg.getTemplate(ftlInfo.getHtmlModelName());
        }
        template.setEncoding("UTF-8");
        File file = new File(ftlInfo.getDir());
        createFileDir(file);
        OutputStream out = new FileOutputStream(file);
        map.put("package", ftlInfo.getPackageName());
        map.put("import", ftlInfo.getImportName());
        map.put("name", ftlInfo.getFileName());
        map.put("dtoName",generatorInfo.getDtoName().substring(0,generatorInfo.getDtoName().indexOf(".")));
        map.put("controllerName",generatorInfo.getControllerName().substring(0,generatorInfo.getControllerName().indexOf(".")));
        map.put("implName",generatorInfo.getImplName().substring(0,generatorInfo.getImplName().indexOf(".")));
        map.put("serviceName",generatorInfo.getServiceName().substring(0,generatorInfo.getServiceName().indexOf(".")));
        map.put("mapperName",generatorInfo.getMapperName().substring(0,generatorInfo.getMapperName().indexOf(".")));
        map.put("xmlName",generatorInfo.getMapperXmlName().substring(0,generatorInfo.getMapperXmlName().indexOf(".")));
        template.process(map, new OutputStreamWriter(out));
        out.flush();
        out.close();
    }

    //判断文件目录是否存在不存在则创建
    public void createFileDir(File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    //判断文件是否已经存在
    public int isFileExist() {
        int rs = 0;
        getFileArray();
        String classDir = generatorInfo.getProjectPath() + "/src/main/java/" + generatorInfo.getParentPackagePath();
        String xmlDir = generatorInfo.getProjectPath() + "/src/main/resources/" + generatorInfo.getParentPackagePath();
        String htmlDir = generatorInfo.getProjectPath() + "/src/main/webapp/WEB-INF/view";
        getFileList(classDir, classDir);
        //判断有没有重复的java文件

        for (String name : allClassFiles) {
            if (name.equals(generatorInfo.getDtoName())) {
                if (generatorInfo.getDtoStatus().equals("Create")) {
                    rs = 1;
                    break;
                } else if (generatorInfo.getDtoStatus().equals("Cover")) {
                    File file1 = new File(classDir + "/" + generatorInfo.getPackagePath() + "/dto/" + generatorInfo.getDtoName());
                    if (!file1.exists()) {
                        rs = 1;
                        break;
                    }
                }
            }
        }

        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getServiceName())) {
                    if (generatorInfo.getServiceStatus().equals("Create")) {
                        rs = 2;
                        break;
                    } else if (generatorInfo.getServiceStatus().equals("Cover")) {
                        File file1 = new File(classDir + "/" + generatorInfo.getPackagePath() + "/service/" + generatorInfo.getServiceName());
                        if (!file1.exists()) {
                            rs = 2;
                            break;
                        }
                    }
                }
            }
        }

        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getImplName())) {
                    if (generatorInfo.getImplStatus().equals("Create")) {
                        rs = 3;
                        break;
                    } else if (generatorInfo.getImplStatus().equals("Cover")) {
                        File file1 = new File(classDir + "/" + generatorInfo.getPackagePath() + "/service/impl/" + generatorInfo.getImplName());
                        if (!file1.exists()) {
                            rs = 3;
                            break;
                        }
                    }
                }
            }
        }


        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getControllerName())) {
                    if (generatorInfo.getControllerStatus().equals("Create")) {
                        rs = 4;
                        break;
                    } else if (generatorInfo.getControllerStatus().equals("Cover")) {
                        File file1 = new File(classDir + "/" + generatorInfo.getPackagePath() + "/controllers/" + generatorInfo.getControllerName());
                        if (!file1.exists()) {
                            rs = 4;
                            break;
                        }
                    }
                }
            }
        }
        if (rs == 0) {
            for (String name : allClassFiles) {
                if (name.equals(generatorInfo.getMapperName())) {
                    if (generatorInfo.getMapperStatus().equals("Create")) {
                        rs = 5;
                        break;
                    } else if (generatorInfo.getMapperStatus().equals("Cover")) {
                        File file1 = new File(classDir + "/" + generatorInfo.getPackagePath() + "/mapper/" + generatorInfo.getMapperName());
                        if (!file1.exists()) {
                            rs = 5;
                            break;
                        }
                    }
                }
            }
        }

        //判断有没有重复的xml文件
        if (rs == 0) {
            getFileList(xmlDir, xmlDir);
            for (String name : allXmlFiles) {
                if (name.equals(generatorInfo.getMapperXmlName())) {
                    if (generatorInfo.getMapperXmlStatus().equals("Create")) {
                        rs = 6;
                        break;
                    } else if (generatorInfo.getMapperXmlStatus().equals("Cover")) {
                        File file1 = new File(xmlDir + "/" + generatorInfo.getPackagePath() + "/mapper/" + generatorInfo.getMapperXmlName());
                        if (!file1.exists()) {
                            rs = 6;
                            break;
                        }
                    }
                }
            }
        }
        //判断有没有重复的html文件
        if (rs == 0) {
            getFileList(htmlDir, htmlDir);
            for (String name : allHtmlFiles) {
                if (name.equals(generatorInfo.getHtmlName())) {
                    if (generatorInfo.getHtmlStatus().equals("Create")) {
                        rs = 7;
                        break;
                    } else if (generatorInfo.getHtmlStatus().equals("Cover")) {
                        File file1 = new File(htmlDir + "/" + generatorInfo.getPackagePath() + "/" + generatorInfo.getHtmlName());
                        if (!file1.exists()) {
                            rs = 7;
                            break;
                        }
                    }
                }
            }
        }
        return rs;
    }

    //获取文件夹下所有文件列表
    public void getFileList(String basePath, String directory) {
        File dir = new File(basePath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) {
                    getFileList(files[i].getAbsolutePath(), directory);
                } else {
                    if (directory.equals(generatorInfo.getProjectPath() + "/src/main/java/" + generatorInfo.getParentPackagePath())) {
                        allClassFiles.add(fileName);
                    } else if (directory.equals(generatorInfo.getProjectPath() + "/src/main/resources/" + generatorInfo.getParentPackagePath())) {
                        allXmlFiles.add(fileName);
                    } else if (directory.equals(generatorInfo.getProjectPath() + "/src/main/webapp/WEB-INF/view")) {
                        allHtmlFiles.add(fileName);
                    }
                }
            }
        }

    }


}
