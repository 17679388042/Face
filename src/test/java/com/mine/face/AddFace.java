package com.mine.face;

 

import java.net.URLEncoder;

import com.pchen.util.Base64Util;
import com.pchen.util.FileUtil;
import com.pchen.util.HttpUtil;

/**
* 人脸注册
*/
public class AddFace {

    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
    public static String add(String filepath) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
        try {
            // 本地文件路径
            byte[] imgData = FileUtil.readFileByBytes(filepath);
            
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            System.out.println("imgParam:" + imgParam);
            
            //String param = "uid=" + "_2123123123" + "&user_info=" + "hejiong" + "&group_id=" + "myuser" + "&images=" + imgParam ;
            String param = "uid=" + "_12312313122222312313" + "&user_info=" + "zjl" + "&group_id=myuser&images="
                + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            System.out.println(">>>>>>>>>>>>>>>>:" + param);
            String accessToken = "24.59aa49473321b41ebe6ad8901779cd17.2592000.1533104170.282335-10698857";

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        AddFace.add("C:\\Users\\privatechen\\Pictures\\zjl.png");
    }
}