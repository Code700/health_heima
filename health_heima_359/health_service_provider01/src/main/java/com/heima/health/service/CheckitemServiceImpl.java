package com.heima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.mapper.CheckitemMapper;
import com.heima.health.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 王立腾
 * @created 2019/11/18 15:00.
 * @description
 */
@Service
public class CheckitemServiceImpl implements CheckitemService {

    @Autowired
    CheckitemMapper checkitemMapper;

    @Override
    public Integer addCheckitem(CheckItem checkItem) {
        return checkitemMapper.addCheckitem(checkItem);
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> pages = checkitemMapper.findByPage(queryPageBean.getQueryString());

        return new PageResult(pages.getTotal(), pages);
    }

    @Override
    public Integer deleteCheckItemById(String id) {

        Integer num;
        num = checkitemMapper.findById(id);

        if (num > 0)
            return -1;

        num = checkitemMapper.deleteCheckItemById(id);

        return num;
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkitemMapper.findCheckItemById(id);
    }

    @Override
    public Integer update(CheckItem checkItem) {
        return checkitemMapper.update(checkItem);
    }
}
