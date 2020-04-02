package com.pchen.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pchen.entity.User;
import com.pchen.util.Params;

/** 
* @author privatechen 
* 创建时间：2018年1月25日 下午2:03:25 
* 类说明 
*/
public interface UserMapper {
	int addUser(User user);
	String isExist(String username);
	User getUser(Params params);
}
