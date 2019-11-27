package com.heima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.heima.health.common.MessageConst;
import com.heima.health.common.RedisConst;
import com.heima.health.entity.Result;
import com.heima.health.pojo.Order;
import com.heima.health.service.OrderService;
import com.heima.health.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.text.ParseException;
import java.util.Map;

/**
 * @author 王立腾
 * @created 2019/11/24 18:15.
 * @description
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    OrderService orderService;
    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String, String> map) {

        //从jedis中拿取验证码对比
        String validateCodeInRedis = jedisPool.getResource().get(RedisConst.SENDTYPE_ORDER + "-" + map.get("telephone"));

        if (!(validateCodeInRedis != null && validateCodeInRedis.equals(map.get("validateCode")))) {
            return new Result(false, MessageConst.VALIDATECODE_ERROR);
        }

        //2. 设置预约类型
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        //3. 添加预约信息到数据
        Result result = null;

        try {
            result = orderService.submit(map);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (result.isFlag()) {
            //4. 发送通知短信给用户(省略了)
//            System.out.println("发送通知短信给客户");
            SMSUtils.sendShortMessage(map.get("telephone"), "123456");
        }
        return result;

    }
}
