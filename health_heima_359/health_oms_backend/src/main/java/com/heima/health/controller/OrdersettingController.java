package com.heima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.common.MessageConst;
import com.heima.health.entity.Result;
import com.heima.health.pojo.OrderSetting;
import com.heima.health.service.OrderSettingService;
import com.heima.health.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 王立腾
 * @created 2019/11/23 13:45.
 * @description
 */
@RestController
@RequestMapping("ordersetting")
public class OrdersettingController {

    @Reference
    OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        try {
            List<String[]> stringsList = POIUtils.readExcel(excelFile);
            //把string数组转换为orderSetting对象
            List<OrderSetting> orderSettingList = new ArrayList<>();
            //循环
            for (String[] strs : stringsList) {
                //一个数组对应一个OrderSetting
                OrderSetting orderSetting = new OrderSetting();
                //把字符串日期转换为日期类型
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Date date = sdf.parse(strs[0]);
                //预约日期
                orderSetting.setOrderDate(date);
                //可预约人数
                orderSetting.setNumber(Integer.parseInt(strs[1]));

                //添加到集合中
                orderSettingList.add(orderSetting);
            }
            //发出请求，把orderSettingList写入到数据库
            orderSettingService.addOrderSettings(orderSettingList);
            return new Result(true, MessageConst.IMPORT_ORDERSETTING_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/findByMonth")
    public Result findByMonth(String date) {
        try {
            List<OrderSetting> orderSettingList = orderSettingService.findByMonth(date);
            //转换结构
            List<Map<String, Object>> mapList = new ArrayList<>();
            //循环查询到的数据
            for (OrderSetting orderSetting : orderSettingList) {
                //一个ordersetting 对应一个map、集合
                Map<String, Object> map = new HashMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                String date1 = sdf.format(orderSetting.getOrderDate());

                map.put("date", date1);
                map.put("number", orderSetting.getNumber());
                map.put("reservations", orderSetting.getReservations());
                //添加到集合中
                mapList.add(map);
            }
            return new Result(true, MessageConst.GET_ORDERSETTING_SUCCESS, mapList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.GET_ORDERSETTING_FAIL);
        }

    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody OrderSetting orderSetting) {
        try {
            List<OrderSetting> orderSettingList = new ArrayList<>();
            orderSettingList.add(orderSetting);
            orderSettingService.addOrderSettings(orderSettingList);
            return new Result(true, MessageConst.ORDERSETTING_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.IMPORT_ORDERSETTING_FAIL);
        }
    }
}