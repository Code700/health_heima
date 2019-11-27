package com.heima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.heima.health.mapper.MemberMapper;
import com.heima.health.pojo.Member;
import com.heima.health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author 王立腾
 * @created 2019/11/25 15:20.
 * @description
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberMapper memberMapper;

    @Override
    public Member handleMember(Map<String, String> map) {
        String telephone = map.get("telephone");
        //根据手机号码查询会员信息
        Member member = memberMapper.findByTelephone(telephone);
        //如果没有注册，自动注册
        if (member == null) {
            //创建会员对象
            member = new Member();
            //设置档案编号
            member.setFileNumber(getFileNumber());
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            //注册会员
            memberMapper.add(member);
        }
        return member;
    }

    private String getFileNumber(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
        Integer rand = ValidateCodeUtils.generateValidateCode(6);

        return date + rand;
    }
}
