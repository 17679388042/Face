package com.pchen.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pchen.entity.Pic;

/** 
* @author privatechen 
* 创建时间：2018年1月25日 下午2:03:25 
* 类说明 
*/
public interface PicMapper {
	public int addPic(Pic pic);
	public List<Pic> getPicsByUser(String username);
	public Pic getPic(String picName);
	public Pic getLatestPic(String username);
	public int deletePic(Map<String, String> username_picpath);
}
