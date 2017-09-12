
package com.huan.HTed.account.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.huan.HTed.mybatis.annotation.Condition;
import com.huan.HTed.system.dto.BaseDTO;

/**
 * 用户对象.
 */
@Table(name = "sys_user")
public class User extends BaseDTO {

    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_USER_NAME = "userName";
    
    public static final String PASSWORD_EXPIRE_VERIFY = "password_expire_verify";

    public static final String STATUS_ACTV = "ACTV";
    public static final String STATUS_EXPR = "EXPR";
    public static final String STATUS_LOCK = "LOCK";

    private static final long serialVersionUID = -1938818306103232890L;

    @Id
    @Column
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long userId;

    // 用户名
    @NotEmpty
    @Column
    private String userName;

    @Column(updatable = false)
    @Condition(exclude = true)
    @JsonIgnore
    private String passwordEncrypted;

    @Transient
    private String password;

    private String userType;

    // 邮箱
    @JsonInclude(Include.NON_NULL)
    @NotEmpty
    private String email;
    // 手机
    @JsonInclude(Include.NON_NULL)
    @NotEmpty
    private String phone;

    @Column
    @Condition(operator = ">=")
    private Date startActiveDate;

    @Column
    @Condition(operator = "<=")
    private Date endActiveDate;

    // 状态
    @JsonInclude(Include.NON_NULL)
    @Column
    private String status;
    
    //最后一次登录时间
    @Column
    private Date lastLoginDate;
    
    //最后一次修改密码时间
    @Column
    private Date lastPasswordUpdateDate;

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordEncrypted() {
        return passwordEncrypted;
    }

    public void setPasswordEncrypted(String passwordEncrypted) {
        this.passwordEncrypted = passwordEncrypted;
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastPasswordUpdateDate() {
        return lastPasswordUpdateDate;
    }

    public void setLastPasswordUpdateDate(Date lastPasswordUpdateDate) {
        this.lastPasswordUpdateDate = lastPasswordUpdateDate;
    }
    
    
}