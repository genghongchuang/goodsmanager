package com.geng.goodsmanage.system.service.impl;

import org.springframework.stereotype.Service;

import com.geng.goodsmanage.system.model.SignIn;
import com.geng.goodsmanage.system.service.SignInService;
import com.geng.goodsmanage.utils.base.service.impl.BaseServiceImpl;
@Service("signService")
public class SignInServiceImpl extends BaseServiceImpl<SignIn, Integer> implements SignInService{

}
