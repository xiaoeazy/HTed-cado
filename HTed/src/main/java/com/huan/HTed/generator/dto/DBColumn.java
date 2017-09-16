package com.huan.HTed.generator.dto;


public class DBColumn {

    private String name;

    private boolean isId=false;

    private boolean isNotEmpty=false;

    private  boolean isNotNull=false;

    private String type;

    private boolean isMultiLanguage=false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMultiLanguage() {
        return isMultiLanguage;
    }

    public void setMultiLanguage(boolean multiLanguage) {
        isMultiLanguage = multiLanguage;
    }

    public boolean isNotEmpty() {
        return isNotEmpty;
    }

    public void setNotEmpty(boolean notEmpty) {
        isNotEmpty = notEmpty;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull(boolean notNull) {
        isNotNull = notNull;
    }
}
