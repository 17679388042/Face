package com.pchen.constant;
/** 
* @author privatechen 
* 创建时间：2018年1月31日 上午9:30:22 
* 类说明 
*/
public enum Face {
    
    // 这里替换成你自己的东西。。
	APP_ID("18285806"),
    API_KEY("ZmdTyhOvZXhHD0iz0YQzG7YD"),
    SECRET_KEY("iNQcA8iPoi4Fjykr3ZLsp7aZzkthD9Dv");
	
	private String value;
	
	private Face(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
