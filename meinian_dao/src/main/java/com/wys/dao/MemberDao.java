package com.wys.dao;

import com.wys.pojo.Member;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.dao
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/7 16:03
 * @Version: 1.0
 */
public interface MemberDao {
    Member findByTel(String telephone);

    void add(Member member);

    Integer CountMemberByMonth(String lastDayOfMonth);

    int getTodayNewMember(String today);

    int getTotalMember();

    int getThisWeekNewMember(String weekMonday);

    int getThisMonthNewMember(String monthFirst);
}
