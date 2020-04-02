package com.pchen.filter;
/** 
* @author privatechen 
* 创建时间：2017年11月15日 下午2:10:15 
* 类说明 
*/

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断用户是否登录的过滤器
 * @author Administrator
 *
 */
public class IsLogin implements Filter {

	private String excludedPages;

	private String[] excludedPageArray;

	@Override
	public void destroy() {
		/**
		 * @author privatechen 创建时间：2017年11月15日 下午3:07:19 类说明
		 */

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/**
		 * @author privatechen 创建时间：2017年11月15日 下午3:07:19 类说明
		 */
		HttpServletRequest httpServletResuest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		String userInfo = (String) httpServletResuest.getSession().getAttribute("userInfo");
		boolean isExcludedPage = false;
		System.out.println("----------"+httpServletResuest.getServletPath());
		for (String page : excludedPageArray) {// 判断是否在过滤url之外
			/*
			 * System.out.println("网路地址：" + ((HttpServletRequest)
			 * request).getServletPath());
			 */
			
			if (httpServletResuest.getServletPath().equals(page) || httpServletResuest.getServletPath().contains(".css")
					|| httpServletResuest.getServletPath().contains(".js")) {
				isExcludedPage = true;
				break;
			}

		}
		/*System.out.println("地址：" + ((HttpServletRequest) request).getServletPath() + "   是否是排除页面：" + isExcludedPage);*/
		
		if (isExcludedPage) {// 在过滤url之外
			
			chain.doFilter(request, httpServletResponse);
		} else {// 不在过滤url之外，判断session是否存在
			
			if (userInfo != null && userInfo.length() > 0) {
				chain.doFilter(request, httpServletResponse);
			} else {
				String rootPath = httpServletResuest.getContextPath();
				httpServletResponse.sendRedirect(rootPath + "/welcome.html");
			}

		}

	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		/**
		 * @author privatechen 创建时间：2017年11月15日 下午3:07:19 类说明
		 */

		excludedPages = config.getInitParameter("excludePages");
		if (excludedPages.length() > 0) {
			excludedPageArray = excludedPages.split(",");
		}
		return;

	}

}
