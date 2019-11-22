package com.heima.health.job;

import com.heima.health.common.RedisConst;
import com.heima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Set;

/**
 * @author 王立腾
 * @created 2019/11/22 14:51.
 * @description
 */
public class TestJob {
    @Autowired
    JedisPool jedisPool;

    public void run() {

        System.out.println("定时任务类执行……" + new Date());
        //1. 获取垃圾图片的名称
        Set<String> imgNames = jedisPool.getResource().sdiff(RedisConst.SETMEAL_PIC_RESOURCES, RedisConst.SETMEAL_PIC_DB_RESOURCES);
        //2. 遍历名称数组
        for (String imgName : imgNames) {
            //删除七牛云上的垃圾图片
            QiniuUtils.deleteFileFromQiniu(imgName);
            // 删除redis中的垃圾图片名称
            jedisPool.getResource().srem(RedisConst.SETMEAL_PIC_RESOURCES, imgName);

            System.out.println("定时任务删除了:" + imgName);
        }

    }

}
