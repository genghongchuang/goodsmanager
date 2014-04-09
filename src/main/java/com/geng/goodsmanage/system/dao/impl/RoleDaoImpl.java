package com.geng.goodsmanage.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.geng.goodsmanage.system.dao.RoleDao;
import com.geng.goodsmanage.system.model.Role;
import com.geng.goodsmanage.utils.base.dao.impl.BaseDaoImpl;
@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role, Integer> implements RoleDao{

}
