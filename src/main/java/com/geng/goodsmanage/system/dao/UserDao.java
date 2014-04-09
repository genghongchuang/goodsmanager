package com.geng.goodsmanage.system.dao;

import java.util.List;

import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.utils.base.dao.BaseDao;

public interface UserDao extends BaseDao<User,Integer> {
	public List<User> queryUser(String userName,String password);
	

}
