package com.pchen.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/** 
* @author privatechen 
* 创建时间：2018年1月25日 下午4:43:28 
* 类说明 
*/
public class MyData extends HashMap<Object, Object> implements Map<Object, Object>{
	private static final long serialVersionUID = 1L;
	
	Map<Object, Object> map = null;
	
	HttpServletRequest request = null;
	
	public MyData(HttpServletRequest request) {
		this.request = request;
		
		Map<String, String[]> properties = request.getParameterMap();
		
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator entries = properties.entrySet().iterator();
		
		String entry;
		while(entries.hasNext()) {
			entry = (String) entries.next();
		}
		
		
	}
}
