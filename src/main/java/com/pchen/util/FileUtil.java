package com.pchen.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/** 
* @author privatechen 
* 创建时间：2018年1月17日 上午8:55:24 
* 类说明 
*/
public class FileUtil {
	
	public static String readFileAsString(String filepath) throws IOException {
		File file = new File(filepath);
		
		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		
		if(file.length() > 10 * 1024 * 1024) {
			throw new IOException("文件太大了，最大10M");
			
		}
		
		StringBuilder sb = new StringBuilder((int) filepath.length());
		
		FileInputStream fis = new FileInputStream(file);
		
		byte[] bytes = new byte[1024];
		
		int hasRead = 0;
		
		while((hasRead = fis.read(bytes)) != 0) {
			sb.append(new String(bytes, 0, hasRead));
		}
		
		fis.close();
		
		return sb.toString();
		
	}
	
	
	
	public static byte[] readFileByBytes(String filepath) throws IOException {
		File file = new File(filepath);
		
		if(!file.exists()) {
			throw new FileNotFoundException();
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream((int)file.length());
			BufferedInputStream in = null;
			
			try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }
                
                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
		}
	}
	
}
