package com.wys.service;

import com.wys.pojo.User;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/7 21:32
 * @Version: 1.0
 */
public interface UserService {
    User findUserByUserName(String username);
}
