package com.wys.dao;

import com.wys.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.dao
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/7 15:04
 * @Version: 1.0
 */
public interface OrderDao {
    List<Order> findOrder(Order order);

    void order(Order order);

    Map findById(Integer id);
}
