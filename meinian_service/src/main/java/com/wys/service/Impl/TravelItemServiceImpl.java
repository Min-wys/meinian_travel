package com.wys.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wys.dao.TravelItemDao;
import com.wys.entity.PageResult;
import com.wys.entity.QueryPageBean;
import com.wys.pojo.TravelItem;
import com.wys.service.TravelItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service
 * @Author: WangYongShuai
 * @Description: 自由行服务实现类
 * @Date: 2020/10/2 16:20
 * @Version: 1.0
 */
@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService {

    @Autowired
    private TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<TravelItem> page = travelItemDao.findPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        //删除之前先查询中间表是否有值
        long count = travelItemDao.findTravelItemId(id);

        if (count > 0) {
            deleteTFromTravelgroupAndTravelitem(id);
        }

        travelItemDao.deleteById(id);
    }

    @Override
    public TravelItem findById(Integer id) {
        return travelItemDao.findById(id);
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    @Override
    public List<TravelItem> findAll() {
        return travelItemDao.findAll();
    }

    //删除中间表中自由行的id
    public void deleteTFromTravelgroupAndTravelitem(Integer id) {
        travelItemDao.deleteTravelItemId(id);
    }
}
