package com.wys.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wys.constant.RedisConstant;
import com.wys.dao.SetmealDao;
import com.wys.pojo.Setmeal;
import com.wys.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service.Impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/4 15:22
 * @Version: 1.0
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(Setmeal setmeal, Integer[] travelgroupIds) {
        setmealDao.add(setmeal);

        if (travelgroupIds != null && travelgroupIds.length > 0) {
            setSetmealAndTravelGroup(setmeal.getId(), travelgroupIds);
        }
        //将图片保存到redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }

    public void setSetmealAndTravelGroup(Integer setmealId, Integer[] travelgroupIds) {

        for (Integer travelgroupId : travelgroupIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("setmealId", setmealId);
            map.put("travelgroupId", travelgroupId);
            setmealDao.setSetmealAndTravelGroup(map);
        }

    }
}
