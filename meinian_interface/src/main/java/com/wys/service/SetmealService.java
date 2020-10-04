package com.wys.service;

import com.wys.pojo.Setmeal;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/4 15:22
 * @Version: 1.0
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] travelgroupIds);
}
