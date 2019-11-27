package com.heima.health.service;

import com.heima.health.pojo.OrderSetting;

import java.util.List;

/**
 * @author 王立腾
 * @created 2019/11/23 15:05.
 * @description
 */
public interface OrderSettingService {
    void addOrderSettings(List<OrderSetting> orderSettingList);

    List<OrderSetting> findByMonth(String month);
}
