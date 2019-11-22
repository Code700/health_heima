package com.heima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.common.MessageConst;
import com.heima.health.common.RedisConst;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.entity.Result;
import com.heima.health.pojo.Setmeal;
import com.heima.health.service.SetmealService;
import com.heima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @author 王立腾
 * @created 2019/11/21 20:34.
 * @description
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    SetmealService setmealService;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //得到文件的格式
        String originalFilename = imgFile.getOriginalFilename();
        //要上传的文件名（防止重复所以使用uuid）
        String uuid = UUID.randomUUID().toString().replace("-", "");//上传文件
        //截取后缀( .jpg  .png )
        String extendName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //服务器上的文件名
        String serverName = uuid + extendName;

        //上传文件
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), serverName);

            //执行的这说明图片上传成功,把数据放到redis中
            jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_RESOURCES, serverName);

            return new Result(true, MessageConst.PIC_UPLOAD_SUCCESS, serverName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConst.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(Integer[] checkgroupIds, @RequestBody Setmeal setmeal) {
        try {
            Integer num = setmealService.add(setmeal, checkgroupIds);
            if (num > 0) {
                return new Result(true, MessageConst.ADD_SETMEAL_SUCCESS);
            } else {
                return new Result(false, MessageConst.ADD_SETMEAL_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.EXCEPTION);
        }
    }


    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = setmealService.findByPage(queryPageBean);

            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
