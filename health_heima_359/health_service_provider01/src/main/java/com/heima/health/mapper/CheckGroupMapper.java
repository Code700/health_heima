package com.heima.health.mapper;

import com.github.pagehelper.Page;
import com.heima.health.pojo.CheckGroup;
import com.heima.health.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 王立腾
 * @created 2019/11/19 16:26.
 * @description
 */
public interface CheckGroupMapper {

    /**
     * 添加组
     *
     * @param checkGroup
     * @return
     */
    Integer add(CheckGroup checkGroup);

    /**
     * 添加组与项目的中间表关系
     *
     * @param id
     * @param checkitemId
     */
    void addRelation(@Param("checkgroupId") Integer id, @Param("checkitemId") Integer checkitemId);

    /**
     * 分页查询
     *
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByPage(String queryString);

    /**
     * 根据id获取检查组相关信息
     *
     * @param id
     * @return
     */
    CheckGroup getCheckgroupItem(Integer id);

    /**
     * 查询检查组对应的检查项id
     *
     * @param id
     * @return
     */
    List<Integer> getCheckgroupItemCheckId(Integer id);

    /**
     * 跟新检查组
     *
     * @param checkGroup
     * @return
     */
    Integer update(CheckGroup checkGroup);

    /**
     * 删除检查组与检查项的关系
     *
     * @param id
     */
    void delRelation(Integer id);

    /**
     * 查询检查组与套餐是否存在关系
     *
     * @param id
     * @return
     */
    Integer queryRelation(Integer id);

    /**
     * 根据id删除指定检查组
     *
     * @param id
     * @return
     */
    Integer delCheckgroup(Integer id);

    /**
     * 查询所有
     *
     * @return
     */
    List<CheckGroup> findAll();
}
