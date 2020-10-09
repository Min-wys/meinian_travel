package com.wys.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wys.constant.RedisConstant;
import com.wys.dao.SetmealDao;
import com.wys.entity.PageResult;
import com.wys.entity.QueryPageBean;
import com.wys.pojo.Setmeal;
import com.wys.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
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
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Setmeal> getSetmeal() {
        List<Setmeal> list = setmealDao.getSetmeal();
        return list;
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal = setmealDao.findById(id);
        return setmeal;
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {

        List<Map<String, Object>> list = setmealDao.findSetmealCount();

        return list;
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
