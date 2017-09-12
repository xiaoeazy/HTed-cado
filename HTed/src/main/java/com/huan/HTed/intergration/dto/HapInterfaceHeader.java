package com.huan.HTed.intergration.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.huan.HTed.core.annotation.Children;
import com.huan.HTed.mybatis.annotation.ExtensionAttribute;
import com.huan.HTed.system.dto.BaseDTO;

/**
 * @author huanghuan
 * @version 2016/11/01
 */
@Table(name = "SYS_IF_CONFIG_HEADER_B")
@ExtensionAttribute(disable=true)
//@MultiLanguage
public class HapInterfaceHeader extends BaseDTO {

  @Id
  @Column
  private String  headerId;


  @Column
  @NotNull
  private String interfaceCode;

  // 接口类型
  @Column
  @NotNull
  private String interfaceType;

  // SOAP报文前缀
  @Column
  private String bodyHeader;
  // SOAP报文后缀
  @Column
  private String bodyTail;

  // SOAP命名空间
  @Column
  private String namespace;

  // 系统地址
  @Column
  @NotNull
  private String domainUrl;
  // 请求方法
  @Column
  @NotNull
  private String requestMethod;

  // 请求形式
  @Column
  @NotNull
  private String requestFormat;
  // 请求报文格式
  @Column
  private String requestContenttype;

  // 请求接收类型
  @Column
  private String requestAccept;
  // 是否需要验证
  @Column
  @NotNull
  private String authFlag;
  // 校验用户名
  @Column
  private String authUsername;
  // 校验密码
  @Column
  private String authPassword;
  // 是否有效
  @Column
  @NotNull
  private String enableFlag;

  // 包装类
  @Column
  private String mapperClass;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Transient
  private String lang;

  //多语言
  @Column
  //@MultiLanguageField
  private String name;
  @Column
  //@MultiLanguageField
  private String description;

  @Column
  private String systemType;

  // 一行

  @Transient
  private String lineId;

  @Transient
  private String lineCode;

  @Transient
  private String iftUrl;



  @Children
  @Transient
  private List<HapInterfaceLine> lineList;


  public String getHeaderId() {
    return headerId;
  }

  public void setHeaderId(String headerId) {
    this.headerId = headerId;
  }

  public List<HapInterfaceLine> getLineList() {
    return lineList;
  }

  public void setLineList(List<HapInterfaceLine> lineList) {
    this.lineList = lineList;
  }

  public String getInterfaceCode() {
    return interfaceCode;
  }

  public void setInterfaceCode(String interfaceCode) {
    this.interfaceCode = interfaceCode;
  }

  public String getInterfaceType() {
    return interfaceType;
  }

  public void setInterfaceType(String interfaceType) {
    this.interfaceType = interfaceType;
  }

  public String getBodyHeader() {
    return bodyHeader;
  }

  public void setBodyHeader(String bodyHeader) {
    this.bodyHeader = bodyHeader;
  }

  public String getBodyTail() {
    return bodyTail;
  }

  public void setBodyTail(String bodyTail) {
    this.bodyTail = bodyTail;
  }

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getDomainUrl() {
    return domainUrl;
  }

  public void setDomainUrl(String domainUrl) {
    this.domainUrl = domainUrl;
  }

  public String getRequestMethod() {
    return requestMethod;
  }

  public void setRequestMethod(String requestMethod) {
    this.requestMethod = requestMethod;
  }

  public String getRequestFormat() {
    return requestFormat;
  }

  public void setRequestFormat(String requestFormat) {
    this.requestFormat = requestFormat;
  }

  public String getRequestContenttype() {
    return requestContenttype;
  }

  public void setRequestContenttype(String requestContenttype) {
    this.requestContenttype = requestContenttype;
  }

  public String getRequestAccept() {
    return requestAccept;
  }

  public void setRequestAccept(String requestAccept) {
    this.requestAccept = requestAccept;
  }

  public String getAuthFlag() {
    return authFlag;
  }

  public void setAuthFlag(String authFlag) {
    this.authFlag = authFlag;
  }

  public String getAuthUsername() {
    return authUsername;
  }

  public void setAuthUsername(String authUsername) {
    this.authUsername = authUsername;
  }

  public String getAuthPassword() {
    return authPassword;
  }

  public void setAuthPassword(String authPassword) {
    this.authPassword = authPassword;
  }

  public String getEnableFlag() {
    return enableFlag;
  }

  public void setEnableFlag(String enableFlag) {
    this.enableFlag = enableFlag;
  }

  public String getMapperClass() {
    return mapperClass;
  }

  public void setMapperClass(String mapperClass) {
    this.mapperClass = mapperClass;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSystemType() {
    return systemType;
  }

  public void setSystemType(String systemType) {
    this.systemType = systemType;
  }

  public String getLineId() {
    return lineId;
  }

  public void setLineId(String lineId) {
    this.lineId = lineId;
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

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

}
