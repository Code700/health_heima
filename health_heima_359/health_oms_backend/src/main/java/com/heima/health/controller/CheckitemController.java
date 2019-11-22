package com.heima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.common.MessageConst;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.entity.Result;
import com.heima.health.pojo.CheckItem;
import com.heima.health.service.CheckitemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王立腾
 * @created 2019/11/18 14:34.
 * @description
 */
@RestController
@RequestMapping("/checkitem")
public class CheckitemController {

    @Reference
    CheckitemService checkitemService;

    @RequestMapping("/add")
    public Result addCheckitem(@RequestBody CheckItem checkItem) {
        System.out.println(checkItem);

        try {
            Integer integer = checkitemService.addCheckitem(checkItem);

            if (integer > 0) {
                return new Result(true, MessageConst.ADD_CHECKGROUP_SUCCESS);
            } else {
                return new Result(false, MessageConst.ADD_CHECKITEM_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = checkitemService.findByPage(queryPageBean);

            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/deleteCheckItemById")
    public Result deleteCheckItemById(String id) {

        try {
            Integer num = checkitemService.deleteCheckItemById(id);
            if (num == -1) {
                return new Result(false, MessageConst.DELETE_CHECKITEM_FAIL + "，组中已存在！");
            } else if (num > 0) {
                return new Result(true, MessageConst.DELETE_CHECKITEM_FAIL + "，组中已存在！");
            } else {
                return new Result(false, MessageConst.DELETE_CHECKITEM_FAIL);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "服务器异常，请稍后再试！");
        }

    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {

        CheckItem checkItem = checkitemService.findById(id);

        if (null != checkItem) {
            return new Result(true, MessageConst.ACTION_SUCCESS, checkItem);
        }
        return new Result(false, MessageConst.QUERY_CHECKITEM_FAIL);
    }



    @RequestMapping("/update")
    public Result update(@RequestBody CheckItem checkItem) {

        try {
            Integer integer = checkitemService.update(checkItem);
            if (integer > 0) {
                return new Result(true, MessageConst.ACTION_SUCCESS);
            } else {
                return new Result(false, MessageConst.ACTION_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "服务器异常！");
        }

    }

    @RequestMapping("findChekitems")
    public Result findChekitem() {
        List<CheckItem> list = checkitemService.findChekitems();
        return new Result(true, MessageConst.QUERY_CHECKITEM_SUCCESS, list);
    }

}
