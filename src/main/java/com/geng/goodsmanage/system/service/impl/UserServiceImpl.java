package com.geng.goodsmanage.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.geng.goodsmanage.system.dao.UserDao;
import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.system.service.UserService;
import com.geng.goodsmanage.utils.base.service.impl.BaseServiceImpl;
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService {
	@Autowired
	UserDao userDao;

	@Override
	public User find(User u) {
		List<User> users=userDao.queryUser(u.getUserName(), u.getPassword());
		User user=null;
		if(users.size()>0){
			user=users.get(0);
		}
		return user;
	}

}
