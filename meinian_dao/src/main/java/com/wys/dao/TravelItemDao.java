package com.wys.dao;

import com.github.pagehelper.Page;
import com.wys.entity.PageResult;
import com.wys.pojo.TravelItem;

import java.util.List;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.dao
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/2 16:23
 * @Version: 1.0
 */
public interface TravelItemDao {
    void add(TravelItem travelItem);

    Page<TravelItem> findPage(String queryString);

    void deleteById(Integer id);

    long findTravelItemId(Integer id);

    void deleteTravelItemId(Integer id);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();
}
