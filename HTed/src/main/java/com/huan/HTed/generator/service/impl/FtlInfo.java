package com.huan.HTed.generator.service.impl;

import java.util.List;


public class FtlInfo {
    private String fileName;
    private String packageName;
    private List<String> importName;

    private String dir;
    private String projectPath;
    private String htmlModelName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getImportName() {
        return importName;
    }

    public void setImportName(List<String> importName) {
        this.importName = importName;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getHtmlModelName() {
        return htmlModelName;
    }

    public void setHtmlModelName(String htmlModelName) {
        this.htmlModelName = htmlModelName;
    }
}
