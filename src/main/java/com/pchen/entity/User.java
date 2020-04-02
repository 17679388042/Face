package com.pchen.entity;

import java.sql.Timestamp;

/** 
* @author privatechen 
* 创建时间：2018年1月25日 下午1:55:41 
* 类说明 
*/
public class User {
    private String username;
    private String password;
    private int age;
    private String info;
    private String sex;
    private Timestamp createtime;
    private Timestamp modifytime;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Timestamp getCreatetime() {
        return createtime;
    }
    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
    public Timestamp getModifytime() {
        return modifytime;
    }
    public void setModifytime(Timestamp modifytime) {
        this.modifytime = modifytime;
    }
    
    
}
