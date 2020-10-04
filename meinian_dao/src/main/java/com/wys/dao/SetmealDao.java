package com.wys.dao;

import com.wys.pojo.Setmeal;

import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.dao
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/4 15:23
 * @Version: 1.0
 */
public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealAndTravelGroup(Map<String, Integer> map);
}
