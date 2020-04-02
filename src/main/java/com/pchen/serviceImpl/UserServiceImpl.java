package com.pchen.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.face.AipFace;
import com.pchen.constant.Face;
import com.pchen.entity.User;
import com.pchen.mapper.UserMapper;
import com.pchen.service.UserService;
import com.pchen.util.Base64Util;
import com.pchen.util.FileUtil;
import com.pchen.util.GsonUtils;
import com.pchen.util.HttpUtil;
import com.pchen.util.Params;


/**
 * @author privatechen 创建时间：2018年1月25日 下午2:34:34 类说明
 */
@Service("userService")
public class UserServiceImpl implements UserService {


	@Resource
	private UserMapper userMapper;

	@Override
	public int addUser(User user) {
	    int isAddSuccess = 0;
	    isAddSuccess = userMapper.addUser(user);
		/**
		 * @author privatechen 创建时间：2018年1月25日 下午2:34:47 类说明
		 */
		return isAddSuccess;
	}

	@Override
	public User getUser(Params params) {
		User user = new User();

		user = userMapper.getUser(params);

		return user;
	}

	/**
	 * @param username
	 *            传入的用户名
	 */
	@Override
	public boolean isExist(String username) {
		boolean isExist = false;
		String isExistUsername = userMapper.isExist(username);
		if (isExistUsername != null && isExistUsername.length() > 0) {
			isExist = true;
		}

		return isExist;
	}
   
    @Override
    public JSONObject loginByFace(byte[] picByte) {
        /*JSONObject returnObject = null;
        System.out.println("进service");
        HashMap<String, String> options = new HashMap<String, String>();
        AipFace client = new AipFace(Face.APP_ID.getValue(), Face.API_KEY.getValue(), Face.SECRET_KEY.getValue());
        // 得到分数最高的那个。。identifyUser("myuser", picByte, options).toString();
        options.put("user_top_num", "1");
        String result = client.identifyUser("myuser", picByte, options).toString();
        returnObject = JSONObject.parseObject(result);
        return returnObject;*/
        
        /*String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "[调用鉴权接口获取的token]";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;*/
    	String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {

            byte[] bytes1 = FileUtil.readFileByBytes("C:\\Users\\Administrator\\Desktop\\liu8.png");
            /*byte[] bytes2 = FileUtil.readFileByBytes("C:\\Users\\Administrator\\Desktop\\liu6.png");*/
            String image1 = Base64Util.encode(bytes1);
            /*String image2 = Base64Util.encode(bytes2);*/

            List<Map<String, Object>> images = new ArrayList<>();

            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "NORMAL");

/*            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NORMAL");*/

            images.add(map1);

            String param = GsonUtils.toJson(images);
            String accessToken = "24.d009d9b6130d62d8eb469361495c5056.2592000.1581761591.282335-18285806";
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            String score=result.split(",")[5].split(":")[2];
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    }

