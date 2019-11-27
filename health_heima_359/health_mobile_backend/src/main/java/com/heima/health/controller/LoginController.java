package com.heima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.common.MessageConst;
import com.heima.health.common.RedisConst;
import com.heima.health.entity.Result;
import com.heima.health.pojo.Member;
import com.heima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author 王立腾
 * @created 2019/11/25 15:12.
 * @description
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    JedisPool jedisPool;

    @Reference
    MemberService memberService;

    @RequestMapping("/check")
    public Result check(@RequestBody Map<String, String> map) {

        try {
            String telephone = map.get("telephone");

            String validateCodeInRedis = jedisPool.getResource().get(RedisConst.SENDTYPE_LOGIN + "-" + telephone);
            String validateCodeInParam = map.get("validateCode");


            if (!(validateCodeInRedis != null && validateCodeInRedis.equals(validateCodeInParam))) {
                return new Result(false, MessageConst.VALIDATECODE_ERROR);
            }
            //2. 自动注册为会员
            Member member = memberService.handleMember(map);
            return new Result(true, MessageConst.LOGIN_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.VALIDATECODE_ERROR);
        }
    }

}
