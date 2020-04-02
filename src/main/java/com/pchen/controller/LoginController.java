package com.pchen.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.util.Util;
import com.pchen.entity.User;
import com.pchen.service.PicService;
import com.pchen.service.UserService;
import com.pchen.util.Base64Util;
import com.pchen.util.ControllerUtil;
import com.pchen.util.FileUtil;
import com.pchen.util.GsonUtils;
import com.pchen.util.HttpUtil;
import com.pchen.util.MD5;
import com.pchen.util.Params;
import sun.misc.BASE64Decoder;

/**
 * @author privatechen 创建时间：2018年1月25日 下午2:19:22 类说明
 */
@Controller
@RequestMapping(value = "/user")
public class LoginController extends BaseController {

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "picService")
	private PicService picService;

	@RequestMapping(value = "/doLogin")
	public void doLogin(HttpServletRequest req, HttpServletResponse response) {

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String confirmLogin = req.getParameter("confirmLogin");

		password = MD5.md5(password);

		JSONObject returnObject = JSONObject.parseObject("{}");

		Params params = new Params();
		params.put("username", username);
		params.put("password", password);

		User user = new User();
		user = userService.getUser(params);
		if (user == null) {
			returnObject.put("user", "notExist");
		} else {

			returnObject.put("user", user);
			// 登录成功，把信息存放到这里
			req.getSession().setAttribute("userInfo", username);
		}
		ControllerUtil.printToPage(response, returnObject);
	}

	/// 注销
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest req, HttpServletResponse response) {
		JSONObject returnObject = JSONObject.parseObject("{}");
		returnObject.put("logout", "yes");
		if (req.getSession().getAttribute("userInfo") != null) {
			req.getSession().removeAttribute("userInfo");
		}

		ControllerUtil.printToPage(response, returnObject);
	}

	// 跳转到注册界面
	@RequestMapping(value = "/register")
	public ModelAndView register() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/user/register");
		return mv;
	}

	// 跳转到人脸注册界面
	@RequestMapping(value = "/faceRegister")
	public ModelAndView faceRegister() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/user/faceRegister");
		return mv;
	}
	
	@RequestMapping(value = "/faceRegister2")
	public ModelAndView faceRegister2() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/user/faceRegister2");
		return mv;
	}

	private void generateImage(String base64Str, String filePath) {

		@SuppressWarnings("restriction")
		BASE64Decoder decoder = new BASE64Decoder();
		// 生成jpeg图片
		FileOutputStream out = null;
		try {
			// Base64解码
			@SuppressWarnings("restriction")
			byte[] b = decoder.decodeBuffer(base64Str);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}

			out = new FileOutputStream(filePath);
			out.write(b);
			out.flush();
		} catch (Exception e) {

		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 人脸注册
	@RequestMapping(value = "/doFaceRegister")
	public void doFaceRegister(HttpServletRequest req, HttpServletResponse response) {
		JSONObject returnObject = JSONObject.parseObject("{}");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String photoData = req.getParameter("photoData");
		if (photoData.length() > 0) {
			photoData = photoData.split(",")[1];
		}

		System.out.println("doFaceRegister.........");
		System.out.println(username + ":" + password + ":" + photoData);

		String facesPath = req.getSession().getServletContext().getRealPath("") + "/faces/";

		boolean isExist = false;// userService.isExist(username);

		if (isExist) {
			returnObject.put("registerSuccess", "no");
			ControllerUtil.printToPage(response, returnObject);
			return;
		}

		generateImage(photoData, facesPath + username + ".png");
		String addResult = addFace(password,username, facesPath + username + ".png");
		JSONObject addFaceResult = JSONObject.parseObject(addResult);
		System.out.println("addFaceResult:" + addFaceResult);
		// 如果结果不包含log_id，那么说明添加脸失败
		if (addFaceResult.containsKey("error_code")) {
			returnObject.put("registerSuccess", "no");
			return;
		}

		User newUser = new User();
		if (username.length() > 0 && password.length() > 0) {
			newUser.setUsername(username);
			newUser.setPassword(MD5.md5(password));
			Timestamp createtime = new Timestamp(System.currentTimeMillis());// 获取系统当前时间
			newUser.setCreatetime(createtime);
		}

		int isAddSuccess = userService.addUser(newUser);
		/// 如果加入成功了，那么受影响的行为1，即isAddSuccess = 1

		/// 已经注册成功了，那么将用户信息存放到session里
		if (isAddSuccess != 0) {
			req.getSession().setAttribute("userInfo", username);
			returnObject.put("registerSuccess", "yes");
		}

		ControllerUtil.printToPage(response, returnObject);
	}

	public static String addFace(String password,String username, String filepath) {
		// 请求url
		String usernames = String.valueOf("用户名:"+username+","+"密码:"+password);
		String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
		try {
			// 本地文件路径
			// byte[] imgData = FileUtil.readFileByBytes(filepath);
			// Base64Util. encode(Util.
			// readFileByBytes("G:\\Temp\\ReadImage.jpg"));
			String imgStr = Base64Util.encode(FileUtil.readFileByBytes(filepath));
			// String imgParam = URLEncoder.encode(imgStr, "UTF-8");

			// 注意这里的图片格式！
			// System.out.println("imgParam:" + imgParam);
			int id = (int) (Math.random() * 100000000);
			String user_id = "_" + id;
			Map<String, Object> map = new HashMap<>();
			map.put("image", imgStr);
			map.put("group_id", "myuser");
			map.put("user_id", user_id);
			map.put("user_info", usernames);
			// map.put("liveness_control", "NORMAL");
			map.put("image_type", "BASE64");
			// map.put("quality_control", "LOW");

			String param = GsonUtils.toJson(map);

			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间，
			// 客户端可自行缓存，过期后重新获取。
			String accessToken = "24.15db3e151b15292e0131910467c9513e.2592000.1585290403.282335-18285806";

			String result = HttpUtil.post(url, accessToken, "application/json", param);
			// uId有要求，文档上有写，不能是纯数字
			// group_id可以写成你自己的组名字，我这里定义的是myuser
			// images格式要注意
			/*
			 * String param = "user_id=" + user_id + "&user_info=" + username +
			 * "&group_id=myuser&images=" + imgParam; //
			 * 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间，
			 * 客户端可自行缓存，过期后重新获取。 String accessToken =
			 * "24.d009d9b6130d62d8eb469361495c5056.2592000.1581761591.282335-18285806";
			 * 
			 * String result = HttpUtil.post(url, accessToken, param);
			 */
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/customer")
	public ModelAndView customer(HttpServletRequest req, HttpServletResponse response) {
		String username = req.getParameter("username");
		ModelAndView mv = this.getModelAndView();
		mv.addObject("username", username);
		mv.setViewName("/user/customer");
		return mv;
	}

	@RequestMapping(value = "/isLogin")
	public void isLogin(HttpServletRequest req, HttpServletResponse response) {
		JSONObject returnObject = JSONObject.parseObject("{}");
		// 将session中的值去掉\
		String userInfo = (String) req.getSession().getAttribute("userInfo");
		if (userInfo != null && userInfo.length() > 0) {
			returnObject.put("userInfo", userInfo);
		} else {
			returnObject.put("userInfo", "");
		}
		ControllerUtil.printToPage(response, returnObject);
	}

	@RequestMapping(value = "/doRegister")
	public void doRegister(HttpServletRequest req, HttpServletResponse response) {
		// 如果能进到注册里来，说明没有该用户，但是为了保险还是要检测一下。。
		JSONObject returnObject = JSONObject.parseObject("{}");

		User newUser = new User();

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String age = req.getParameter("age");
		String info = req.getParameter("info");
		String sex = req.getParameter("sex");
		Timestamp createtime = new Timestamp(System.currentTimeMillis());// 获取系统当前时间

		boolean isExist = false;
		isExist = userService.isExist(username);
		if (isExist) {
			returnObject.put("registerSuccess", "no");
			ControllerUtil.printToPage(response, returnObject);
			return;
		}

		if (username.length() > 0 && password.length() > 0) {
			newUser.setUsername(username);
			newUser.setPassword(MD5.md5(password));
			if (age != null && age.length() > 0) {
				newUser.setAge(Integer.parseInt(age.trim()));
			} else {
				newUser.setAge(0);
			}
			if (info != null && info.length() > 0) {
				newUser.setInfo(info);
			} else {
				newUser.setInfo("没有相关信息");
			}

			if (sex != null && sex.length() > 0) {
				newUser.setSex(sex);
			} else {
				newUser.setSex("O");
			}

			newUser.setCreatetime(createtime);
		}

		int isAddSuccess = userService.addUser(newUser);
		/// 如果加入成功了，那么受影响的行为1，即isAddSuccess = 1

		/// 已经注册成功了，那么将用户信息存放到session里
		if (isAddSuccess != 0) {
			req.getSession().setAttribute("userInfo", username);
			returnObject.put("registerSuccess", "yes");
		}

		ControllerUtil.printToPage(response, returnObject);
		System.out.println(isAddSuccess);

	}

	@RequestMapping(value = "/loginByFace")
	public String loginByFace(HttpServletRequest req, HttpServletResponse response) {
		String faceImage = req.getParameter("faceImage");
		float checkScores = 0L;
		String checkUser = "";
		faceImage = faceImage.split(",")[1];

		byte[] factByte = Base64.decodeBase64(faceImage);
		//JSONObject returnObject = JSONObject.parseObject("{}");
		//进入对比  获得比对值
		ModelAndView mv = this.getModelAndView();
		Double score = reess(factByte);
		if(score==null){
			System.out.println("未拍摄到脸");
			mv.setViewName("/user/error");
			return "/user/error";
		}
		System.out.println(score);
		System.out.println("login by face...");
		//System.out.println("faceImage" + faceImage);
		//Double wsy = Double.parseDouble(score);
		/// 包含错误信息，直接是用户不存在
		/*if (null == returnObject.getJSONArray("result")) {
			System.out.println("失败");
			returnObject.put("user", "notExist");
			ControllerUtil.printToPage(response, returnObject);
			return;
		}
		JSONObject resultObject = (JSONObject) returnObject.getJSONArray("result").get(0);

		if (resultObject.getString("result") != null) {
			System.out.println("来了");
			checkScores = Float.parseFloat(resultObject.getJSONArray("scores").getString(0).trim());
			checkUser = resultObject.getString("user_info").trim();
		}
		System.out.println(checkUser + ":" + checkScores);*/

		// 进行判断，如果分数小于80，则认为不存在这个用户
		if (score < 80) {
			System.out.println("用户不存在");
			mv.setViewName("/user/error");
			return "/user/error";
		} else {
			System.out.println("登录成功");
			// 登录成功，把信息存放到这里
			req.getSession().setAttribute("userInfo", checkUser);
			mv.setViewName("/user/success");
			return "/user/success";
			
		}
		// 进行判断

	}
	
	
	public Double reess(byte[] picByte){
		System.out.println("前台图像："+picByte);
		//人脸识别
		String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
		//人脸搜索
		String urls = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
        	//String imgStr = Base64Util.encode(FileUtil.readFileByBytes(filess));
            //byte[] bytes1 = FileUtil.readFileByBytes("D:\\Users\\image\\ting.png");
            //byte[] bytes2 = FileUtil.readFileByBytes("D:\\Users\\image\\12313.png");
            //byte[] bytes2 = FileUtil.readFileByBytes("D:\\Users\\image\\ting.png");
        	//转成BASE64格式
            String image1 = Base64Util.encode(picByte);
            //String image2 = Base64Util.encode(bytes2);

            List<Map<String, Object>> images = new ArrayList<>();

            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            //查找后返回的用户数量。返回相似度最高的几个用户，默认为1，最多返回50个。
            map1.put("max_user_num", 1);
            //从指定的group中进行查找 用逗号分隔，上限10个
            map1.put("group_id_list", "myuser");
            //活体检测控制
            map1.put("liveness_control", "NORMAL");
            /*Map<String, Object> map2 = new HashMap<>();
            map2.put("image", image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NORMAL");
            images.add(map1);*/
            //images.add(map2);
            String param = GsonUtils.toJson(map1);
            System.out.println("尼亚"+param);
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.15db3e151b15292e0131910467c9513e.2592000.1585290403.282335-18285806";
            //JSONObject returnObject = null;
            //返回一串字符串 即是循环嵌套的json数据
            String result = HttpUtil.post(urls, accessToken, "application/json", param);
            System.out.println("搜索返回的结果集："+result);
            //截取json数据
            //String score=result.split(",")[5].split(":")[2];
            //将String的字符串转成JSON类型
            JSONObject returnObject = JSONObject.parseObject(result);
            //获取嵌套JSON数据中的“result”的JSON数据
            JSONObject obj = returnObject.getJSONObject("result");
            System.out.print("数据【result】："+returnObject.getJSONObject("result"));
            //截取最后一串json数据即"user_list"
            JSONArray jsonArray = obj.getJSONArray("user_list");
            System.out.println("比对值"+jsonArray.getJSONObject(0).getString("score"));
            //获取用户信息
            System.out.println("用户信息{"+jsonArray.getJSONObject(0).getString("user_info")+"}");
            
            /*String user = jsonArray.getJSONObject(0).getString("user_info");
            JSONObject jsonObject = JSONObject.parseObject(user);*/
            //System.out.println("用户JSON数据："+jsonObject);
            //获取user_list中的比对值即score
            String scores = jsonArray.getJSONObject(0).getString("score");
            //String类型转成Double类型
            Double scoress = Double.valueOf(scores);
            return scoress;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
