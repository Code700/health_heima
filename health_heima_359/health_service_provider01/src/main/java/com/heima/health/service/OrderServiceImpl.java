package com.heima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.heima.health.common.MessageConst;
import com.heima.health.entity.Result;
import com.heima.health.mapper.MemberMapper;
import com.heima.health.mapper.OrderMapper;
import com.heima.health.mapper.OrderSettingMapper;
import com.heima.health.pojo.Member;
import com.heima.health.pojo.Order;
import com.heima.health.pojo.OrderSetting;
import com.heima.health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author 王立腾
 * @created 2019/11/24 20:57.
 * @description
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderSettingMapper orderSettingMapper;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    OrderMapper orderMapper;

    /**
     * 1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
     * 2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
     * 3、检查用户是否是会员
     * ​	如果不是会员，自动注册为会员
     * ​	如果是会员
     * ​			更新会员信息（原来的信息可能已过时）
     * ​			检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
     * 4、以上判断都没有问题，开始预约，添加预约信息到数据库
     * 5、预约成功，更新当日的已预约人数
     *
     * @param map
     * @return
     */
    @Override
    public Result submit(Map<String, String> map) throws ParseException {
        String orderDate = map.get("orderDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date order_Date = simpleDateFormat.parse(orderDate);
        //获取手机号码
        String telephone = map.get("telephone");
        //获取套餐id
        Integer setmealId = Integer.parseInt(map.get("setmealId"));

        //1.查询预约的日期是否可以预约
        OrderSetting orderSetting = orderSettingMapper.findByDate(order_Date);
        if (null == orderSetting) {
            return new Result(false, MessageConst.SELECTED_DATE_CANNOT_ORDER);
        }
        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            return new Result(false, MessageConst.ORDER_FULL);
        }
        //3. 判断是否是会员
        Member member = memberMapper.findByTelephone(telephone);

        if (member != null) {
            //是会员,跟新数据，
            member.setName(map.get("name"));
            member.setSex(map.get("sex"));
            member.setIdCard(map.get("idCard"));
            //更新会员信息
            memberMapper.update(member);
            //3.2.2 判断是否预约过此套餐（该会员是否预约了该日期的该套餐）
            //条件： 会员（member）  日期(orderDate)   套餐(参数中获取)
            //创建条件对象
            Order condition = new Order();
            condition.setMemberId(member.getId());
            condition.setOrderDate(order_Date);
            condition.setSetmealId(setmealId);
            //根据条件查询预约信息
            long count = orderMapper.findByOrder(condition);
            if (count > 0) {
                return new Result(false, MessageConst.HAS_ORDERED);
            }

        } else {
            //不是会员
            member = new Member();
            //给会员对象赋值
            //档案编号
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //档案编号： 日期 +  4位随机数
            String fileNumber = format.format(new Date()) + ValidateCodeUtils.generateValidateCode(4);
            member.setFileNumber(fileNumber);
            //姓名
            member.setName(map.get("name"));
            //性别
            member.setSex(map.get("sex"));
            //身份证号码
            member.setIdCard(map.get("idCard"));
            //手机号码
            member.setPhoneNumber(telephone);
            //注册时间
            member.setRegTime(new Date());
            //自动注册为会员(主键回显)
            memberMapper.add(member);

        }


        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(order_Date);
        order.setOrderType(map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(setmealId);
        //添加预约信息
        orderMapper.addOrder(order);
        //5. 更新预约设置信息： 已预约人数加一
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingMapper.edit(orderSetting);

        return new Result(true,MessageConst.ORDER_SUCCESS, order);

    }
}
