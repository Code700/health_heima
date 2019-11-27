package com.heima.health.mapper;

import com.heima.health.pojo.Member;

/**
 * @author 王立腾
 * @created 2019/11/25 15:28.
 * @description
 */
public interface MemberMapper {
    /**
     * 根据手机号码查询会员信息
     *
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 注册会员
     *
     * @param member
     */
    void add(Member member);

    void update(Member member);
}
