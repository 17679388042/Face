package com.pchen.service;

import com.alibaba.fastjson.JSONObject;
import com.pchen.entity.User;
import com.pchen.util.Params;

/** 
* @author privatechen 
* 创建时间：2018年1月25日 下午1:58:07 
* 类说明 
*/

public interface UserService {
	public int addUser(User user);
	public User getUser(Params params);
	public boolean isExist(String username);
	public JSONObject loginByFace(byte[] picByte);
}
