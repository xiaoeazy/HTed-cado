package com.huan.HTed.intergration.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import com.huan.HTed.system.dto.BaseDTO;

//@MultiLanguage
@ExtensionAttribute(disable=true)
@Table(name = "SYS_IF_CONFIG_LINE_B")
public class HapInterfaceLine extends BaseDTO {

    @Id
    @Column
    private String lineId;
    @Column
    private String headerId;
    @Column
    private String lineCode;
    @Column
    private String iftUrl;
    @Column
    private String enableFlag;

    @Column
    //@MultiLanguageField
    private String lineName;

    @Column
    //@MultiLanguageField
    private String lineDescription;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String lang;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getHeaderId() {
        return headerId;
    }

    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getIftUrl() {
        return iftUrl;
    }

    public void setIftUrl(String iftUrl) {
        this.iftUrl = iftUrl;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineDescription() {
        return lineDescription;
    }

    public void setLineDescription(String lineDescription) {
        this.lineDescription = lineDescription;
    }
}