package com.wys.dao;

import com.wys.pojo.Permission;

import java.util.Set;

/**
 * @ProjectName: meinian_travel
 * @Package: com.wys.dao
 * @Author: WangYongShuai
 * @Description:
 * @Date: 2020/10/7 21:59
 * @Version: 1.0
 */
public interface PermissionDao {
    Set<Permission> findPermissionsByRoleId(Integer roleId);
}
