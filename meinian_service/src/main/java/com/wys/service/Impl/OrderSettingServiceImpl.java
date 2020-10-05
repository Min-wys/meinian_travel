package com.wys.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wys.dao.OrderSettingDao;
import com.wys.pojo.OrderSetting;
import com.wys.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service.Impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/5 15:45
 * @Version: 1.0
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
            //先查询当前日期是否已经设置了预约
            long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            //已经设置预约就更新，没有就添加
            if (count >0 ) {
                editNumberByDate(orderSetting);
            }

            orderSettingDao.add(orderSetting);
        }

    }

    @Override
    public List<Map<String, Object>> getOrderSettingByMonth(String date) {

        String begin = date + "-1";
        String end = date + "-31";

        Map<String, Object> map = new HashMap<>();

        map.put("begin", begin);
        map.put("end", end);

        List<OrderSetting> orderSettings = orderSettingDao.getOrderSettingByMonth(map);

        List<Map<String, Object>> list = new ArrayList<>();

        for (OrderSetting orderSetting : orderSettings) {
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("date",orderSetting.getOrderDate().getDate());
            map1.put("number",orderSetting.getNumber());
            map1.put("reservations",orderSetting.getReservations());
            list.add(map1);
        }

        return list;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        orderSettingDao.editNumberByDate(orderSetting);
    }
}
