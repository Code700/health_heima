package com.heima.health.service;

import com.heima.health.entity.Result;
import com.heima.health.pojo.Order;

import java.text.ParseException;
import java.util.Map;

/**
 * @author 王立腾
 * @created 2019/11/24 20:56.
 * @description
 */
public interface OrderService {

    Result submit(Map<String, String> map) throws ParseException;
}
