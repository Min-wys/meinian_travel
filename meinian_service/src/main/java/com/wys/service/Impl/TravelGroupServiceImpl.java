package com.wys.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wys.dao.TravelGroupDao;
import com.wys.entity.PageResult;
import com.wys.entity.QueryPageBean;
import com.wys.pojo.TravelGroup;
import com.wys.service.TravelGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service.Impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/3 16:04
 * @Version: 1.0
 */
@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    private TravelGroupDao travelgroupDao;

    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
        //添加跟团游
        travelgroupDao.add(travelGroup);
        //添加跟团游以及自由行中间表的数据
        setTravelGroupAndTravelItem(travelGroup.getId(), travelItemIds);

    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<TravelGroup> page = travelgroupDao.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delete(Integer id) {
        //查询中间表是否有数据
        Long count = travelgroupDao.findTravelGroupId(id);
        if (count > 0) {
            //先删中间表数据
            deleteTravelGroupAndTravelItem(id);
        }

        travelgroupDao.delete(id);
    }

    @Override
    public TravelGroup findById(Integer id) {
        TravelGroup travelGroup = travelgroupDao.findById(id);

        return travelGroup;
    }

    @Override
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        List<Integer> travelItemIds = travelgroupDao.findTravelItemIdByTravelgroupId(id);

        return travelItemIds;
    }

    @Override
    public void edit(TravelGroup travelGroup, Integer[] travelItemIds) {
        travelgroupDao.edit(travelGroup);

        //先删除中间表数据再添加
        deleteTravelGroupAndTravelItem(travelGroup.getId());
        setTravelGroupAndTravelItem(travelGroup.getId(), travelItemIds);
    }

    @Override
    public List<TravelGroup> findAll() {
        List<TravelGroup> list = travelgroupDao.findAll();
        return list;
    }

    public void deleteTravelGroupAndTravelItem(Integer id) {
        travelgroupDao.deleteByTravelGroupId(id);
    }

    public void setTravelGroupAndTravelItem(Integer travelGroupId, Integer[] travelItemIds) {
        if (travelItemIds != null && travelItemIds.length > 0) {
            for (Integer travelItemId : travelItemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("travelItemId", travelItemId);
                map.put("travelGroupId", travelGroupId);
                travelgroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
}
