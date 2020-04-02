package com.pchen.service;

import java.util.List;

import com.pchen.entity.Pic;

/** 
* @author privatechen 
* 创建时间：2018年1月25日 下午1:58:07 
* 类说明 
*/

public interface PicService {
	public int addPic(Pic pic);
	public List<Pic> getPicsByUser(String username);
	public Pic getPicByPicName(String picname);
	public String addFace(String username, String imgString);
}
