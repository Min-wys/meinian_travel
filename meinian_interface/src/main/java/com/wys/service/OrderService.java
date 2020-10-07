package com.wys.service;

import com.wys.entity.Result;
import com.wys.pojo.Order;

import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/7 15:02
 * @Version: 1.0
 */
public interface OrderService {
    Result order(Map map) throws Exception;

    Map findById(Integer id);
}
