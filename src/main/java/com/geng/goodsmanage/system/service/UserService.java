package com.geng.goodsmanage.system.service;

import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.utils.base.service.BaseService;

public interface UserService extends BaseService<User, Integer>{
	public User find(User u);

}
