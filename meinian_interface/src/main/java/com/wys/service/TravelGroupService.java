package com.wys.service;

import com.wys.entity.PageResult;
import com.wys.entity.QueryPageBean;
import com.wys.pojo.TravelGroup;

import java.util.List;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/3 16:05
 * @Version: 1.0
 */
public interface TravelGroupService {
    void add(TravelGroup travelGroup, Integer[] travelItemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void edit(TravelGroup travelGroup, Integer[] travelItemIds);
}
