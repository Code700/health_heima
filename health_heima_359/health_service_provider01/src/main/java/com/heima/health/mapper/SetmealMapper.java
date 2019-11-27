package com.heima.health.mapper;

import com.github.pagehelper.Page;
import com.heima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 王立腾
 * @created 2019/11/21 21:44.
 * @description
 */
public interface SetmealMapper {
    /**
     * 添加套餐
     *
     * @param setmeal
     * @return
     */
    Integer add(Setmeal setmeal);

    /**
     * 添加套餐和绑定的检查项的关系
     *
     * @param setmealId
     * @param checkgroupId
     */
    void addRelationCheckgroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    /**
     * 带条件分页查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findByPage(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
