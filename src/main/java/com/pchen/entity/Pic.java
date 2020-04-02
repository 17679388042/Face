package com.pchen.entity;

import java.sql.Timestamp;

/** 
* @author privatechen 
* 创建时间：2018年1月30日 下午1:49:11 
* 类说明 
*/
public class Pic {
	private String picName;
	private String picPath;
	private Timestamp uploadtime;
	private String username;
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public Timestamp getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(Timestamp uploadtime) {
		this.uploadtime = uploadtime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
