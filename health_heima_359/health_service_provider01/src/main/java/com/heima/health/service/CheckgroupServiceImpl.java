package com.heima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.mapper.CheckGroupMapper;
import com.heima.health.pojo.CheckGroup;
import com.heima.health.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王立腾
 * @created 2019/11/19 16:15.
 * @description
 */
@Service
@Transactional
public class CheckgroupServiceImpl implements CheckgroupService {

    @Autowired
    CheckGroupMapper checkGroupMapper;

    @Override
    public Integer add(CheckGroup checkGroup, Integer[] checkitems) {

        Integer num = checkGroupMapper.add(checkGroup);
        Integer id = checkGroup.getId();
        if (null != id && null != checkitems && checkitems.length > 0) {
            for (Integer checkitemId : checkitems) {
                checkGroupMapper.addRelation(id, checkitemId);
            }
        }
        return num;
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> pages = checkGroupMapper.findByPage(queryPageBean.getQueryString());
        return new PageResult(pages.getTotal(), pages);
    }

    @Override
    public CheckGroup getCheckgroupItem(Integer id) {
        return checkGroupMapper.getCheckgroupItem(id);
    }

    @Override
    public List<Integer> getCheckgroupItemCheckId(Integer id) {
        return checkGroupMapper.getCheckgroupItemCheckId(id);
    }

    @Override
    public Integer update(CheckGroup checkGroup, Integer[] checkitems) {
        Integer num = checkGroupMapper.update(checkGroup);

        Integer id = checkGroup.getId();

        checkGroupMapper.delRelation(id);

        if (null != id && null != checkitems && checkitems.length > 0) {
            for (Integer checkitemId : checkitems) {
                checkGroupMapper.addRelation(id, checkitemId);
            }
        }
        return num;
    }

    @Override
    public Integer deleteCheckgroup(Integer id) {

        //是否与套餐有关系
        //如果没有关系就删除该组对应的检查项
        //然后再删除组信息
        //如果跟套餐有关系
        //告诉前台不能删除

        Integer num = checkGroupMapper.queryRelation(id);

        if (num > 0) {
            return -1;
        } else {
            checkGroupMapper.delRelation(id);
            Integer integer = checkGroupMapper.delCheckgroup(id);
            return integer;
        }
    }
}
