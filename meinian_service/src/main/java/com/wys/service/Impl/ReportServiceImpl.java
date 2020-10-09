package com.wys.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wys.dao.MemberDao;
import com.wys.dao.OrderDao;
import com.wys.service.ReportService;
import com.wys.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service.Impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/8 10:24
 * @Version: 1.0
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {

        HashMap<String, Object> map = new HashMap<>();
        // 日期工具类
        // 1：当前时间
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        // 2：本周（周一）
        String weekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        // 3：本周（周日）
        String weekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
        // 4：本月（1号）
        String monthFirst = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        // 5：本月（31号）
        String monthLast = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());

        //今日新增会员数
        int todayNewMember = memberDao.getTodayNewMember(today);
        //总会员数
        int totalMember = memberDao.getTotalMember();
        //本周新增会员数
        int thisWeekNewMember = memberDao.getThisWeekNewMember(weekMonday);
        //本月新增会员数
        int thisMonthNewMember = memberDao.getThisMonthNewMember(monthFirst);

        //今日预约数
        int todayOrderNumber = orderDao.getTodayOrderNumber(today);
        //今日出游数
        int todayVisitsNumber = orderDao.getTodayVisitsNumber(today);
        //本周预约数
        HashMap<String, Object> orderOfThisWeek = new HashMap<>();
        map.put("weekMonday", weekMonday);
        map.put("weekSunday", weekSunday);
        int thisWeekOrderNumber = orderDao.getThisWeekOrderNumber(orderOfThisWeek);
        //本周出游数
        HashMap<String, Object> visitOfThisWeek = new HashMap<>();
        map.put("weekMonday", weekMonday);
        map.put("weekSunday", weekSunday);
        int thisWeekVisitsNumber = orderDao.getThisWeekVisitsNumber(weekMonday);
        //本月预约数
        HashMap<String, Object> orderOfThisMonth = new HashMap<>();
        map.put("monthFirst", monthFirst);
        map.put("monthLast", monthLast);
        int thisMonthOrderNumber = orderDao.getThisMonthOrderNumber(orderOfThisMonth);
        //本月出游数
        HashMap<String, Object> visitOfThisMonth = new HashMap<>();
        map.put("monthFirst", monthFirst);
        map.put("monthLast", monthLast);
        int thisMonthVisitsNumber = orderDao.getThisMonthVisitsNumber(monthFirst);

        //热门套餐
        List<Map<String, Object>> hotSetmeal = orderDao.getHotSetmeal();

        map.put("reportDate", today);
        map.put("todayNewMember", todayNewMember);
        map.put("totalMember", totalMember);
        map.put("thisWeekNewMember", thisWeekNewMember);
        map.put("thisMonthNewMember", thisMonthNewMember);
        map.put("todayOrderNumber", todayOrderNumber);
        map.put("todayVisitsNumber", todayVisitsNumber);
        map.put("thisWeekOrderNumber", thisWeekOrderNumber);
        map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        map.put("thisMonthOrderNumber", thisMonthOrderNumber);
        map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        map.put("hotSetmeal", hotSetmeal);

        return map;
    }
}
