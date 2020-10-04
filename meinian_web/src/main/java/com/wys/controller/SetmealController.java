package com.wys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.wys.constant.MessageConstant;
import com.wys.constant.RedisConstant;
import com.wys.entity.PageResult;
import com.wys.entity.QueryPageBean;
import com.wys.entity.Result;
import com.wys.pojo.Setmeal;
import com.wys.service.SetmealService;
import com.wys.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.controller
 * @Author: WangYongShuai
 * @Description: 套餐游
 * @Date: 2020/10/4 15:21
 * @Version: 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private SetmealService setmealService;

    /**
     * @param imgFile:
     * @Description: 文件上传
     * @Author: WangYongShuai
     * @Date: 2020/10/4 18:17
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        try {
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            //找到.最后出现的位置
            int lastIndex = originalFilename.lastIndexOf(".");
            //获取文件后缀 jsp
            String substring = originalFilename.substring(lastIndex);

            //生成唯一的文件名
            String fileName = UUID.randomUUID().toString() + substring;
            //上传到七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            //缓存在redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);

            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * @Description: 新增套餐游
     * @Author: WangYongShuai
     * @Date: 2020/10/4 20:47
     * @param setmeal:
     * @param travelgroupIds:
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] travelgroupIds) {
        try {
            setmealService.add(setmeal, travelgroupIds);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.findPage(queryPageBean);

        return pageResult;
    }


}
