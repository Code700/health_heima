package com.heima.health.service;

import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.pojo.Setmeal;

import java.util.List;

/**
 * @author 王立腾
 * @created 2019/11/21 21:37.
 * @description
 */
public interface SetmealService {

    Integer add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findByPage(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
