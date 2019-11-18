package com.heima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.common.MessageConst;
import com.heima.health.entity.Result;
import com.heima.health.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王立腾
 * @created 2019/11/16 17:26.
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Reference
    UserService userService;

    @RequestMapping("/login")
    public Result login(String username,String password){
        System.out.println("oms backend,user:"+username+" ,password:"+password);
        if(userService.login(username,password)){
            System.out.println("login ok!!!");
            return new Result(true, MessageConst.ACTION_SUCCESS);
        }else{
            System.out.println("login fail");
            return new Result(false,MessageConst.ACTION_FAIL);
        }
    }

}
