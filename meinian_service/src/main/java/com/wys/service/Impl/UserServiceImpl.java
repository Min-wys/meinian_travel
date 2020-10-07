package com.wys.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wys.dao.UserDao;
import com.wys.pojo.User;
import com.wys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service.Impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/7 21:33
 * @Version: 1.0
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByUserName(String username) {

        User user = userDao.findUserByUserName(username);

        return user;
    }
}
