package com.wys.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wys.dao.MemberDao;
import com.wys.service.MemberService;
import com.wys.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service.Impl
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/8 10:33
 * @Version: 1.0
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;


    @Override
    public List<Integer> CountMemberByMonth(ArrayList<String> list) {

        ArrayList<Integer> memberCount = new ArrayList<>();

        if (list != null && list.size() > 0) {
            for (String month : list) {
                //获取月份的最后一天
                String lastDayOfMonth = DateUtils.getLastDayOfMonth(month);
                Integer count = memberDao.CountMemberByMonth(lastDayOfMonth);
                memberCount.add(count);
            }
        }

        return memberCount;
    }
}
