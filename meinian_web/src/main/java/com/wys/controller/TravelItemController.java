package com.wys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wys.constant.MessageConstant;
import com.wys.entity.PageResult;
import com.wys.entity.QueryPageBean;
import com.wys.entity.Result;
import com.wys.pojo.TravelItem;
import com.wys.service.TravelItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.controller
 * @Author: WangYongShuai
 * @Description: 自由行前端控制类
 * @Date: 2020/10/2 16:19
 * @Version: 1.0
 */
@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference
    private TravelItemService travelItemService;

    /**
     * @Description: 查询所有自由行的信息
     * @Author: WangYongShuai
     * @Date: 2020/10/3 16:52
     * @return: java.util.List<com.wys.pojo.TravelItem>
     **/
    @RequestMapping("/findAll")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")//权限校验
    public List<TravelItem> findAll() {
        return travelItemService.findAll();
    }

    /**
     * @param travelItem:
     * @Description: 更新自由行
     * @Author: WangYongShuai
     * @Date: 2020/10/3 15:15
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")//权限校验
    public Result edit(@RequestBody TravelItem travelItem) {
        travelItemService.edit(travelItem);
        return new Result(true, MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }

    /**
     * @param id:
     * @Description: 通过Id查询对应的自由行
     * @Author: WangYongShuai
     * @Date: 2020/10/3 14:44
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/findById")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")//权限校验
    public Result findById(Integer id) {
        TravelItem travelItem = travelItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, travelItem);
    }

    /**
     * @param id:
     * @Description: 通过Id删除对应的自由行
     * @Author: WangYongShuai
     * @Date: 2020/10/3 14:42
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE123')")//权限校验，使用TRAVELITEM_DELETE123测试
    public Result deleteById(Integer id) {
        travelItemService.deleteById(id);

        return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
    }

    /**
     * @param queryPageBean:
     * @Description: 分页查询
     * @Author: WangYongShuai
     * @Date: 2020/10/2 20:45
     * @return: com.wys.entity.PageResult
     **/
    @RequestMapping("/findPage")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")//权限校验
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = travelItemService.findPage(queryPageBean);
        return pageResult;
    }

    /**
     * @Author: WangYongShuai
     * @param travelItem:
     * @Description: 新增自由行
     * @Date: 2020/10/2 20:03
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")//权限校验
    public Result add(@RequestBody TravelItem travelItem) {
        try {
            travelItemService.add(travelItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }
}
