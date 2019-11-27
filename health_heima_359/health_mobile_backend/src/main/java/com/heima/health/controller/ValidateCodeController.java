package com.heima.health.controller;

import com.heima.health.common.MessageConst;
import com.heima.health.common.RedisConst;
import com.heima.health.entity.Result;
import com.heima.health.utils.SMSUtils;
import com.heima.health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author 王立腾
 * @created 2019/11/25 14:45.
 * @description
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    JedisPool jedisPool;


    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        try { //1. 生成验证码
            Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
            //2. 发送给用户
            //SMSUtils.sendShortMessage(telephone, String.valueOf(validateCode));
            //3. 存储验证码到redis 001-13000000000  002-13000000000
            jedisPool.getResource().setex(RedisConst.SENDTYPE_ORDER + "-" + telephone, 5 * 60, String.valueOf(validateCode));
            return new Result(true, MessageConst.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {

        try {
            //1. 生成验证码
            Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
            //2. 发送给用户
            //SMSUtils.sendShortMessage(telephone, String.valueOf(validateCode));
            //3. 存储验证码到redis 001-13000000000  002-13000000000
            jedisPool.getResource().setex(RedisConst.SENDTYPE_LOGIN + "-" + telephone, 5 * 60, String.valueOf(validateCode));
            return new Result(true, MessageConst.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.SEND_VALIDATECODE_FAIL);
        }

    }
}
