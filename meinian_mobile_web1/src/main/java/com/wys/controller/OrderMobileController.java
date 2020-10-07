package com.wys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wys.constant.MessageConstant;
import com.wys.entity.Result;
import com.wys.pojo.Order;
import com.wys.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/7 15:01
 * @Version: 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderMobileController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * @param map:
     * @Description: 下订单
     * @Author: WangYongShuai
     * @Date: 2020/10/7 15:46
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) throws Exception {
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        //获取redis中的验证码
        String validateCodeOnRedis = jedisPool.getResource().get(telephone);

        if (validateCodeOnRedis == null && !validateCodeOnRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        Result result = orderService.order(map);


        return result;
    }

    /**
     * @Description: 通过id查询订单信息
     * @Author: WangYongShuai
     * @Date: 2020/10/7 19:29
     * @param id:
     * @return: com.wys.pojo.Order
     **/
    @RequestMapping("/findById")
    public Map findById(Integer id) {

        Map map = orderService.findById(id);

        return map ;
    }
}
