package com.wys.service;

import com.wys.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/5 15:45
 * @Version: 1.0
 */
public interface OrderSettingService {
    void add(List<OrderSetting> orderSettings);

    List<Map<String,Object>> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
