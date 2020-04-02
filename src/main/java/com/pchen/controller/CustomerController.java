package com.pchen.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.pchen.util.ControllerUtil;

/**
 * @author privatechen 创建时间：2018年1月30日 下午2:47:33 类说明
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController extends BaseController {

     

    

    @RequestMapping(value = "/getUserName")
    public void getUserName(HttpServletRequest req, HttpServletResponse response) {
        JSONObject returnObject = JSONObject.parseObject("{}");


        String username = (String) req.getSession().getAttribute("userInfo");
       

         
        returnObject.put("username", username);

        ControllerUtil.printToPage(response, returnObject);
    }

           
}
