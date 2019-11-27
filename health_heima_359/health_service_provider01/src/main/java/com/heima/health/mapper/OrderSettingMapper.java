package com.heima.health.mapper;

import com.heima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 王立腾
 * @created 2019/11/23 15:34.
 * @description
 */
public interface OrderSettingMapper {
    /**
     * 根据日期查询当日基本信息9
     * @param orderDate
     * @return
     */
    OrderSetting findByDate(Date orderDate);

    void edit(OrderSetting orderSetting);
    void uplade(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> findByMonth(Map<String, Object> map);
}
