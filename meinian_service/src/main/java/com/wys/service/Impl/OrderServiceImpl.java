package com.wys.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wys.constant.MessageConstant;
import com.wys.dao.MemberDao;
import com.wys.dao.OrderDao;
import com.wys.dao.OrderSettingDao;
import com.wys.entity.Result;
import com.wys.pojo.Member;
import com.wys.pojo.Order;
import com.wys.pojo.OrderSetting;
import com.wys.service.OrderService;
import com.wys.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service.Impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/7 15:02
 * @Version: 1.0
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderSettingDao orderSettingDao;


    @Override
    public Result order(Map map) throws Exception {

        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        //查询该日期是否已经提前进行预约设置
        OrderSetting orderSetting = orderSettingDao.findOrderSetting(date);

        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        } else {
            //查询是否预约已满
            int reservations = orderSetting.getReservations();
            int number = orderSetting.getNumber();

            if (reservations >= number) {
                return new Result(false, MessageConstant.ORDER_FULL);
            }
        }

        //使用手机号查询用户是否是会员,不是会员就注册会员
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTel(telephone);

        if (member == null) {
            Member member1 = new Member();
            member1.setRegTime(new Date());
            member1.setIdCard((String) map.get("idCard"));
            member1.setPhoneNumber(telephone);
            member1.setSex((String) map.get("sex"));
            member1.setName((String) map.get("name"));
            memberDao.add(member1);
        } else {
            //是会员的话检查订单是否重复
            Integer memberId = member.getId();
            Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order = new Order(memberId, date, null, null, setmealId);
            List<Order> orders = orderDao.findOrder(order);
            if (orders != null && orders.size() > 0) {
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }

        orderSetting.setNumber(orderSetting.getReservations() + 1);
        orderSettingDao.editNumberByDate(orderSetting);

        Order order = new Order();
        order.setOrderDate(date);
        order.setOrderType((String) map.get("orderType"));
        order.setMemberId(member.getId());
        order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderDao.order(order);

        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map findById(Integer id) {

        Map map = orderDao.findById(id);

        if (map != null) {
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",orderDate);
        }

        return map;
    }
}
