package com.geng.goodsmanage.goods.service.impl;

import org.springframework.stereotype.Service;

import com.geng.goodsmanage.goods.model.Goods;
import com.geng.goodsmanage.goods.model.Order;
import com.geng.goodsmanage.goods.service.GoodsService;
import com.geng.goodsmanage.goods.service.OrderService;
import com.geng.goodsmanage.utils.base.service.impl.BaseServiceImpl;
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<Order, Integer> implements OrderService{

}
