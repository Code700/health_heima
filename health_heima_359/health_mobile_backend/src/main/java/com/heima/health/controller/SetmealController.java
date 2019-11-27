package com.heima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.common.MessageConst;
import com.heima.health.entity.Result;
import com.heima.health.pojo.Setmeal;
import com.heima.health.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王立腾
 * @created 2019/11/24 15:21.
 * @description
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    SetmealService setmealService;

    @RequestMapping("/getSetmeal")
    public Result getSetmeal() {
        try {
            List<Setmeal> setmealList = setmealService.findAll();
            return new Result(true, MessageConst.QUERY_SETMEALLIST_SUCCESS, setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_SETMEALLIST_FAIL);
        }

    }

    //findById
    @RequestMapping("findById")
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConst.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_SETMEAL_FAIL);
        }
    }


}

