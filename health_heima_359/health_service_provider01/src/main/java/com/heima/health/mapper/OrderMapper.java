package com.heima.health.mapper;

import com.heima.health.pojo.Order;

/**
 * @author 王立腾
 * @created 2019/11/25 20:08.
 * @description
 */
public interface OrderMapper {

    long findByOrder(Order condition);

    void addOrder(Order order);
}
