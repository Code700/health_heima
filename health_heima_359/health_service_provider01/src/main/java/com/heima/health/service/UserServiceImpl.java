package com.heima.health.service;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author 王立腾
 * @created 2019/11/16 17:24.
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean login(String username, String password) {
        System.out.println("service_provide...u:" + username + " p:" + password);
        if ("admin".equals(username) && "123".equals(password)) {
            return true;
        }
        return false;
    }
}
