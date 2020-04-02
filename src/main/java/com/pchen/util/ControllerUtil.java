package com.pchen.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/** 
* @author privatechen 
* 创建时间：2018年1月29日 下午2:34:53 
* 类说明 
*/
public class ControllerUtil {
	
	public static void printToPage(HttpServletResponse response, Object returnInfos) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.print(returnInfos);
		} catch (IOException e) {
			System.out.println("错误啦。。");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			printWriter.flush();
			printWriter.close();
		}
	}
}
