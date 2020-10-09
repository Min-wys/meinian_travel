package com.wys.service;

import com.github.pagehelper.Page;
import com.wys.entity.PageResult;
import com.wys.entity.QueryPageBean;
import com.wys.pojo.Setmeal;

import java.util.List;
import java.util.Map;

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


    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    List<Map<String, Object>> findSetmealCount();
}
