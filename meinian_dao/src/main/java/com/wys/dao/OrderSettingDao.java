package com.wys.dao;

import com.wys.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.dao
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/5 15:46
 * @Version: 1.0
 */
public interface OrderSettingDao {
    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map<String, Object> map);

    void editNumberByDate(OrderSetting orderSetting);

    Long findCountByOrderDate(Date orderDate);
}
