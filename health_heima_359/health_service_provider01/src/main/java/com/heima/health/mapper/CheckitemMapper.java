package com.heima.health.mapper;

import com.github.pagehelper.Page;
import com.heima.health.pojo.CheckItem;

/**
 * @author 王立腾
 * @created 2019/11/18 15:02.
 * @description
 */
public interface CheckitemMapper {

    /**
     * 添加检测项
     *
     * @param checkItem
     * @return
     */
    Integer addCheckitem(CheckItem checkItem);

    /**
     * 分页查询添加的选项
     *
     * @param queryString 查询时条件
     * @return
     */
    Page<CheckItem> findByPage(String queryString);

    /**
     * 查询该检测项是否于组关联
     *
     * @param id
     * @return
     */
    Integer findById(String id);

    /**
     * 查询是否与组关联
     *
     * @param id
     * @return
     */
    Integer deleteCheckItemById(String id);

    /**
     * 根据id查询检查项
     *
     * @param id
     * @return
     */
    CheckItem findCheckItemById(Integer id);

    /**
     * 跟新检查项目
     *
     * @param checkItem
     * @return
     */
    Integer update(CheckItem checkItem);
}
