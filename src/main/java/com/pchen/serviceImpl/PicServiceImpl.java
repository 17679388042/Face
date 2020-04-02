package com.pchen.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pchen.entity.Pic;
import com.pchen.mapper.PicMapper;
import com.pchen.service.PicService;
import com.pchen.util.HttpUtil;

/** 
* @author privatechen 
* 创建时间：2018年1月25日 下午2:34:34 
* 类说明 
*/
@Service("picService")
public class PicServiceImpl implements PicService{
	

		
	@Resource
	private PicMapper picMapper;


	@Override
	public int addPic(Pic pic) {
		/** 
		* @author privatechen 
		* 创建时间：2018年1月30日 下午5:01:35 
		* 类说明 
		*/ 
		 
		picMapper.addPic(pic);
		return 0;
	}


	@Override
	public List<Pic> getPicsByUser(String username) {
		/** 
		* @author privatechen 
		* 创建时间：2018年1月30日 下午5:01:35 
		* 类说明 
		*/ 
		List<Pic> pics = picMapper.getPicsByUser(username);
		
		return pics;
	}


	@Override
	public Pic getPicByPicName(String picName) {
		/** 
		* @author privatechen 
		* 创建时间：2018年1月30日 下午5:01:35 
		* 类说明 
		*/ 
		Pic pic = picMapper.getPic(picName);
		
		return pic;
	}


	 
	
 // 把脸添加到远程人脸库
    @Override
    public String addFace(String username, String imgParam) {
        
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
        try {
            // 本地文件路径
         
            // uId有要求，文档上有写
            // group_id可以写成你自己的组名字，我这里定义的是myuser
            // images格式要注意
            int id = (int) (Math.random() * 100000000);
            String uId = "_" + id;
            String param = "uid=" + uId + "&user_info=" + username + "&group_id=myuser&images="
                    + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间，
            // 客户端可自行缓存，过期后重新获取。
            
            
            //***********************************************************
            //***********************************************************
            //  这里要换成你自己的accessToken....
            String accessToken = "24.d009d9b6130d62d8eb469361495c5056.2592000.1581761591.282335-18285806";
            //***********************************************************
            //***********************************************************
            String result = HttpUtil.post(url, accessToken, param);
            System.out.println("result:" + result);
            return result;
            //return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
        
    }
    
     

}
