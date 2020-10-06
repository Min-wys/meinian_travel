package com.wys.controller;

import com.wys.constant.MessageConstant;
import com.wys.constant.RedisConstant;
import com.wys.entity.Result;
import com.wys.utils.SMSUtils;
import com.wys.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.controller
 * @Author: WangYongShuai
 * @Description: 短信验证
 * @Date: 2020/10/6 16:29
 * @Version: 1.0
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * @param telephone:
     * @Description: 发送短信验证码
     * @Author: WangYongShuai
     * @Date: 2020/10/6 16:33
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) throws Exception {

        //生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        SMSUtils.sendShortMessage(telephone, String.valueOf(validateCode));

        jedisPool.getResource().setex(telephone, 5 * 60, String.valueOf(validateCode));


        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
