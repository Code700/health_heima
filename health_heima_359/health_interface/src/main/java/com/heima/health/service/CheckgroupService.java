package com.heima.health.service;

import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.pojo.CheckGroup;

import java.util.List;

/**
 * @author 王立腾
 * @created 2019/11/19 16:13.
 * @description
 */
public interface CheckgroupService {
    Integer add(CheckGroup checkGroup, Integer[] checkitems);

    PageResult findByPage(QueryPageBean queryPageBean);

    CheckGroup getCheckgroupItem(Integer id);

    List<Integer> getCheckgroupItemCheckId(Integer id);

    Integer update(CheckGroup checkGroup, Integer[] checkitems);

    Integer deleteCheckgroup(Integer id);
}
