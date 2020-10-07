package com.wys.dao;

import com.wys.pojo.User;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.dao
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/7 21:53
 * @Version: 1.0
 */
public interface UserDao {
    User findUserByUserName(String username);
}
