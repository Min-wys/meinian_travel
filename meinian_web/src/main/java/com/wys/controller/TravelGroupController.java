package com.wys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wys.constant.MessageConstant;
import com.wys.entity.PageResult;
import com.wys.entity.QueryPageBean;
import com.wys.entity.Result;
import com.wys.pojo.TravelGroup;
import com.wys.service.TravelGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.controller
 * @Author: WangYongShuai
 * @Description: 跟团游
 * @Date: 2020/10/3 16:02
 * @Version: 1.0
 */
@RestController
@RequestMapping("/travelgroup")
public class TravelGroupController {
    @Reference
    private TravelGroupService travelgroupService;

    /**
     * @Description: 查找所有跟团游数据
     * @Author: WangYongShuai
     * @Date: 2020/10/4 15:45
     * @return: java.util.List<com.wys.pojo.TravelGroup>
     **/
    @RequestMapping("/findAll")
    public List<TravelGroup> findAll() {
        List<TravelGroup> list = travelgroupService.findAll();
        return list;
    }

    /**
     * @param travelGroup:
     * @param travelItemIds:
     * @Description: 更新跟团游
     * @Author: WangYongShuai
     * @Date: 2020/10/4 15:43
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/edit")
    public Result edit(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds) {
        travelgroupService.edit(travelGroup, travelItemIds);

        return new Result(true, MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }

    /**
     * @param id:
     * @Description: 通过跟团游Id查找对象的自由行Id
     * @Author: WangYongShuai
     * @Date: 2020/10/3 23:12
     * @return: java.util.List<java.lang.Integer>
     **/
    @RequestMapping("/findTravelItemIdByTravelgroupId")
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        List<Integer> travelItemIds = travelgroupService.findTravelItemIdByTravelgroupId(id);

        return travelItemIds;
    }

    /**
     * @param id:
     * @Description: 通过跟团游Id查找跟团游信息
     * @Author: WangYongShuai
     * @Date: 2020/10/3 22:53
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            TravelGroup travelGroup = travelgroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS, travelGroup);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }

    }

    /**
     * @param id:
     * @Description: 通过Id删除对应跟团游信息
     * @Author: WangYongShuai
     * @Date: 2020/10/3 22:45
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/delete")
    public Result delete(Integer id) {

        try {
            travelgroupService.delete(id);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_TRAVELGROUP_FAIL);
        }

        return new Result(true, MessageConstant.DELETE_TRAVELGROUP_SUCCESS);
    }

    /**
     * @param queryPageBean:
     * @Description: 跟团游分页
     * @Author: WangYongShuai
     * @Date: 2020/10/3 21:23
     * @return: com.wys.entity.PageResult
     **/
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return travelgroupService.findPage(queryPageBean);
    }

    /**
     * @param travelGroup:
     * @param travelItemIds:
     * @Description: 新增跟团游
     * @Author: WangYongShuai
     * @Date: 2020/10/3 21:00
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/add")
    public Result add(@RequestBody TravelGroup travelGroup, Integer[] travelItemIds) {

        travelgroupService.add(travelGroup, travelItemIds);

        return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
    }
}
