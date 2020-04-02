package com.pchen.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pchen.util.Params;

/** 
* @author privatechen 
* 创建时间：2018年1月25日 下午5:17:42 
* 类说明 
*/
public class BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 得到modelAndView
	 * @author privatechen 
	 * 创建时间：2018年1月25日 下午5:19:21   
	 * @return
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	
	/**
	 * 得到request
	 * @author privatechen 
	 * 创建时间：2018年1月25日 下午5:19:42   
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	/**
	 * 
	 * @author privatechen 
	 * 创建时间：2018年1月25日 下午5:19:54   
	 * @param logger 传入的logger
	 * @param interfaceName  当前位置
	 */
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	/**
	 * 
	 * @author privatechen 
	 * 创建时间：2018年1月25日 下午5:20:33   
	 * @param logger 传入当前Logger
	 */
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
	/**
	 * 用于传参数
	 * @author privatechen 
	 * 创建时间：2018年1月26日 上午10:50:03   
	 * @return
	 */
	public Params getParams() {
		return new Params(this.getRequest());
	}
}
