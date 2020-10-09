package com.wys.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.service
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/8 10:32
 * @Version: 1.0
 */
public interface MemberService {
    List<Integer> CountMemberByMonth(ArrayList<String> list);
}
