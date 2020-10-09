package com.wys.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wys.constant.MessageConstant;
import com.wys.entity.Result;
import com.wys.service.MemberService;
import com.wys.service.ReportService;
import com.wys.service.SetmealService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.controller
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/8 10:23
 * @Version: 1.0
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    /**
     * @Description: 按月份统计会员数量
     * @Author: WangYongShuai
     * @Date: 2020/10/8 10:28
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        //获取日历对象
        Calendar calendar = Calendar.getInstance();
        //根据当前时间获取前12个月的日历
        calendar.add(calendar.MONTH, -12);

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            calendar.add(calendar.MONTH, 1);
            list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("months", list);

        List<Integer> members = memberService.CountMemberByMonth(list);
        map.put("memberCount", members);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }

    /**
     * @Description: 统计套餐人数比例
     * @Author: WangYongShuai
     * @Date: 2020/10/8 14:33
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {

        List<Map<String, Object>> list = setmealService.findSetmealCount();

        HashMap<String, Object> map = new HashMap<>();
        map.put("setmealCount", list);

        List<String> setmealNames = new ArrayList<>();

        for (Map<String, Object> map02 : list) {
            String name = (String) map02.get("name");
            setmealNames.add(name);
        }

        map.put("setmealNames", setmealNames);

        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
    }

    /**
     * @Description: 获取运营统计数据
     * @Author: WangYongShuai
     * @Date: 2020/10/8 14:39
     * @return: com.wys.entity.Result
     **/
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() throws Exception {
        Map<String, Object> map = reportService.getBusinessReportData();

        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
    }

    /**
     * @Description: 运营数据统计报表导出
     * @Author: WangYongShuai
     * @Date: 2020/10/8 16:15
     * @return: void
     **/
    @RequestMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> result = reportService.getBusinessReportData();

        //取出返回结果数据，准备将报表数据写入到Excel文件中
        String reportDate = (String) result.get("reportDate");
        Integer todayNewMember = (Integer) result.get("todayNewMember");
        Integer totalMember = (Integer) result.get("totalMember");
        Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
        Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
        Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
        Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
        Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
        Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
        Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
        Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
        List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

        //获取Excel模板文件的绝对路径
        String temlateRealPath = request.getSession().getServletContext().getRealPath("template") +
                File.separator + "report_template.xlsx";

        //读取模板文件创建Excel表格对象
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(temlateRealPath)));

        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = sheet.getRow(2);
        row.getCell(5).setCellValue(reportDate);//日期

        row = sheet.getRow(4);
        row.getCell(5).setCellValue(todayNewMember);//新增会员
        row.getCell(7).setCellValue(totalMember);//总会员数

        row = sheet.getRow(5);
        row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员
        row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员

        row = sheet.getRow(7);
        row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
        row.getCell(7).setCellValue(todayVisitsNumber);//今日出游数

        row = sheet.getRow(8);
        row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
        row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周出游数

        row = sheet.getRow(9);
        row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
        row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月出游数

        int rowNum = 12;
        for (Map map : hotSetmeal) {
            String name = (String) map.get("name");
            Long setmeal_count = (Long) map.get("setmeal_count");
            BigDecimal proportion = (BigDecimal) map.get("proportion");
            row = sheet.getRow(rowNum++);
            row.getCell(4).setCellValue(name);//套餐名称
            row.getCell(5).setCellValue(setmeal_count);//预约数量
            row.getCell(6).setCellValue(proportion.doubleValue());//占比
        }

        //通过输出输出流进行文件下载
        ServletOutputStream out = response.getOutputStream();
        //下载的数据类型（excel)
        response.setContentType("application/vnd.ms-excel");
        // 设置下载形式(通过附件的形式下载)
        response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
        workbook.write(out);

        out.flush();
        out.close();
        workbook.close();

    }

}
