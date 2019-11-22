package com.heima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.heima.health.common.MessageConst;
import com.heima.health.entity.PageResult;
import com.heima.health.entity.QueryPageBean;
import com.heima.health.entity.Result;
import com.heima.health.pojo.CheckGroup;
import com.heima.health.pojo.CheckItem;
import com.heima.health.service.CheckgroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王立腾
 * @created 2019/11/19 16:00.
 * @description
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckgroupController {

    @Reference
    CheckgroupService checkgroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitems) {
        try {
            Integer num = checkgroupService.add(checkGroup, checkitems);

            if (num > 0) {
                return new Result(true, MessageConst.ADD_CHECKGROUP_SUCCESS);
            } else {
                return new Result(false, MessageConst.ADD_CHECKGROUP_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.ADD_CHECKGROUP_FAIL);
        }
    }


    @RequestMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitems) {
        try {
            Integer num = checkgroupService.update(checkGroup, checkitems);

            if (num > 0) {
                return new Result(true, MessageConst.EDIT_CHECKGROUP_SUCCESS);
            } else {
                return new Result(false, MessageConst.EDIT_CHECKGROUP_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "数据异常，请稍后再试！");
        }
    }

    @RequestMapping("/findByPage")
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkgroupService.findByPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/getCheckgroupItem")
    public Result getCheckgroupItem(Integer id) {
        try {
            CheckGroup checkGroup = checkgroupService.getCheckgroupItem(id);
            return new Result(true, MessageConst.ADD_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.QUERY_CHECKGROUP_FAIL);
        }
    }


    @RequestMapping("/deleteCheckgroup")
    public Result deleteCheckgroup(Integer id) {
        try {
            Integer integer = checkgroupService.deleteCheckgroup(id);
            if (integer == 1) {
                return new Result(true, MessageConst.DELETE_CHECKGROUP_SUCCESS);
            } else if (integer == -1) {
                return new Result(true, "该检查组已在套餐中，不能删除该组！");
            } else {
                return new Result(false, MessageConst.DELETE_CHECKGROUP_FAIL);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConst.DELETE_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/getCheckgroupItemCheckId")
    public Result getCheckgroupItemCheckId(Integer id) {
        try {
            List<Integer> list = checkgroupService.getCheckgroupItemCheckId(id);
            return new Result(true, MessageConst.QUERY_CHECKITEM_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConst.QUERY_CHECKITEM_FAIL);
        }
    }


    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckGroup> checkGroups = checkgroupService.findAll();

            if (null != checkGroups) {
                return new Result(true, MessageConst.ACTION_SUCCESS, checkGroups);
            }
            return new Result(false, MessageConst.QUERY_CHECKGROUP_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "数据异常，请稍后再试！");
        }
    }
}
