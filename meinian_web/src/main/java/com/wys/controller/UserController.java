package com.wys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wys.constant.MessageConstant;
import com.wys.entity.Result;
import com.wys.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/7 23:42
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    /**
     * @Description: 获取用户名
     * @Author: WangYongShuai
     * @Date: 2020/10/7 23:44
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/getUsername")
    public Result getUsername() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, user.getUsername());
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}

