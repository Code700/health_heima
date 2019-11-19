package com.heima.health.service;

import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.pojo.CheckItem;

/**
 * @author 王立腾
 * @created 2019/11/18 14:59.
 * @description
 */
public interface CheckitemService {
    Integer addCheckitem(CheckItem checkItem);

    PageResult findByPage(QueryPageBean queryPageBean);

    Integer deleteCheckItemById(String id);

    CheckItem findById(Integer id);

    Integer update(CheckItem checkItem);
}
