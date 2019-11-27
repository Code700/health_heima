package com.heima.health.service;

import com.heima.health.pojo.Member;

import java.util.Map;

/**
 * @author 王立腾
 * @created 2019/11/25 15:17.
 * @description
 */
public interface MemberService {
    Member handleMember(Map<String, String> map);
}
