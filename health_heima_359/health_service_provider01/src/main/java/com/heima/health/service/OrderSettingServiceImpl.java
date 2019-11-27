package com.heima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.heima.health.mapper.OrderSettingMapper;
import com.heima.health.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王立腾
 * @created 2019/11/23 14:59.
 * @description
 */
@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService{

    @Autowired
    OrderSettingMapper orderSettingMapper;

    @Override
    public void addOrderSettings(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0){
            // 1. 循环
            for (OrderSetting orderSetting : orderSettingList) {
                //2. 判断该日期是否进行了预约设置
                OrderSetting orderSettingDb =  orderSettingMapper.findByDate(orderSetting.getOrderDate());
                //2.1 如果已经设置，执行修改操作
                if(orderSettingDb != null){
                    //注意：修改时需要判断已预约人数（数据库）是否大于可预约人数(参数中)
                    if(orderSettingDb.getReservations() > orderSetting.getNumber()){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        throw new RuntimeException("您设置的[ "+sdf.format(orderSetting.getOrderDate())+" ]已预约人数大于可预约，不能设置！！");
                    }else{
                        orderSettingMapper.edit(orderSetting);
                    }
                }
                //2.2 如果没有设置，执行添加操作
                else{
                    orderSettingMapper.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<OrderSetting> findByMonth(String month) {
        String startDate = month + "-01";
        String endDate = month + "-31";
        Map<String,Object> map = new HashMap<>();
        map.put("startDate",startDate );
        map.put("endDate", endDate);

        return orderSettingMapper.findByMonth(map);
    }
}
