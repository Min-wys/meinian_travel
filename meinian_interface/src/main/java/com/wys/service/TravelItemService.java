package com.wys.service;

import com.wys.entity.PageResult;
import com.wys.entity.QueryPageBean;
import com.wys.pojo.TravelItem;

import java.util.List;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/2 16:22
 * @Version: 1.0
 */
public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();
}
