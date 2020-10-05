package com.wys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wys.constant.MessageConstant;
import com.wys.entity.Result;
import com.wys.pojo.OrderSetting;
import com.wys.service.OrderSettingService;
import com.wys.utils.POIUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.controller
 * @Author: WangYongShuai
 * @Description: 预约出游
 * @Date: 2020/10/5 15:43
 * @Version: 1.0
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * @Description: 更新预约
     * @Author: WangYongShuai
     * @Date: 2020/10/5 20:50
     * @param orderSetting:
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {

        try {
            orderSettingService.editNumberByDate(orderSetting);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }

        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }

    /**
     * @Description: 通过日期获取 预约信息
     * @Author: WangYongShuai
     * @Date: 2020/10/5 20:34
     * @param date:
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @RequestMapping("/getOrderSettingByMonth")
    public List<Map<String,Object>> getOrderSettingByMonth(String date) {
        List<Map<String,Object>> list = orderSettingService.getOrderSettingByMonth(date);
        return list;
    }

    /**
     * @Description: 上传预约信息文档
     * @Author: WangYongShuai
     * @Date: 2020/10/5 19:10
     * @param excelFile:
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {

        try {
            //解析文件
            List<String[]> list = null;  list = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettings = new ArrayList<>();

            for (String[] str : list) {
                OrderSetting orderSetting = new OrderSetting(new Date(str[0]), Integer.parseInt(str[1]));
                orderSettings.add(orderSetting);

            }
            orderSettingService.add(orderSettings);

            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }


    }
}
