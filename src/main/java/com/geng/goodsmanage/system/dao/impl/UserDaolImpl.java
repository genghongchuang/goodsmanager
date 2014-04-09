package com.geng.goodsmanage.system.dao.impl;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.geng.goodsmanage.system.dao.UserDao;
import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.utils.base.dao.impl.BaseDaoImpl;
@Repository("userDao")
public class UserDaolImpl extends BaseDaoImpl<User, Integer> implements UserDao {
	 private static final String HQL_LIST = "from User ";
	@SuppressWarnings("unchecked")
	@Override
	public List<User> queryUser(String userName, String password) {
		String HQL_queryUser=HQL_LIST+"where userName=? and password=?";
		
		List<User> users = null;
		try {
			users = list(HQL_queryUser, userName,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null==users?Collections.EMPTY_LIST:users;
	}

}
