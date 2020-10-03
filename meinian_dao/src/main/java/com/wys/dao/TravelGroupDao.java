package com.wys.dao;

import com.github.pagehelper.Page;
import com.wys.pojo.TravelGroup;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.dao
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/3 16:06
 * @Version: 1.0
 */
public interface TravelGroupDao {
    void add(TravelGroup travelGroup);

    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    Page<TravelGroup> findPage(String queryString);

    Long findTravelGroupId(Integer id);

    void delete(Integer id);

    void deleteByTravelGroupId(Integer id);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void edit(TravelGroup travelGroup);
}
