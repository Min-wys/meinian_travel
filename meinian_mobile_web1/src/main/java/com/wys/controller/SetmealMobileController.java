package com.wys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wys.constant.MessageConstant;
import com.wys.entity.Result;
import com.wys.pojo.Setmeal;
import com.wys.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.controller
 * @Author: WangYongShuai
 * @Description: 移动端预约
 * @Date: 2020/10/6 15:38
 * @Version: 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    private SetmealService setmealService;

    /**
     * @Description: 获取所有套餐信息
     * @Author: WangYongShuai
     * @Date: 2020/10/6 15:41
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/getSetmeal")
    public Result getSetmeal() {
        List<Setmeal> list = setmealService.getSetmeal();

        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, list);

    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {

        Setmeal setmeal = setmealService.findById(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }
}
