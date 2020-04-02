package com.pchen.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/** 
* @author privatechen 
* 创建时间：2018年1月30日 上午9:05:42 
* 类说明 
*/
public class ClickNumListener implements ServletRequestListener {
	
	public ClickNumListener () {
		
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		/** 
		* @author privatechen 
		* 创建时间：2018年1月30日 上午9:05:42 
		* 类说明 
		*/

	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		/** 
		* @author privatechen 
		* 创建时间：2018年1月30日 上午9:05:42 
		* 类说明 
		*/
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		HttpSession session = request.getSession(true);
		session.setAttribute("ip", request.getRemoteAddr());
		String uri = request.getRequestURI();
		
		String[] suffix = {".html", ".do"};
		
		for(int i = 0; i < suffix.length; i++) {
			if(uri.endsWith(suffix[i])) {
				break;
			} 
			if(i ==suffix.length -1) {
				continue;
			}
		}
		
		Integer activeTime = (Integer) session.getAttribute("activeTime");
		if(activeTime == null) {
			activeTime = 0;
		}
		session.setAttribute("activeTime", activeTime);
		
		
	
	}

}
