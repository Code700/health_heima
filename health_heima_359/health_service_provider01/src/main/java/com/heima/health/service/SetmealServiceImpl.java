package com.heima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.heima.health.common.RedisConst;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.mapper.SetmealMapper;
import com.heima.health.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author 王立腾
 * @created 2019/11/21 21:39.
 * @description
 */
@Service
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    JedisPool jedisPool;

    @Override
    public Integer add(Setmeal setmeal, Integer[] checkgroupIds) {

        Integer integer = setmealMapper.add(setmeal);

        Integer setmealId = setmeal.getId();

        if (null != setmealId && null != checkgroupIds && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealMapper.addRelationCheckgroup(setmealId, checkgroupId);
            }
        }

        jedisPool.getResource().sadd(RedisConst.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());

        return integer;
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> pages = setmealMapper.findByPage(queryPageBean.getQueryString());

        return new PageResult(pages.getTotal(), pages);
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealMapper.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealMapper.findById(id);
    }
}
