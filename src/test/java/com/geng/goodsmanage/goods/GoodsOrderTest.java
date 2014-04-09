package com.geng.goodsmanage.goods;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.geng.goodsmanage.utils.TimeUtil;
import com.geng.goodsmanage.utils.base.service.BaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="../../../../applicationContext.xml")
public class GoodsOrderTest {
	@Autowired
	BaseService<Object, Integer> baseService;
	@Test
	public void testMonthTurnover(){
		try {
			Date date=new Date();
			String year=TimeUtil.formatDateToYearStr(date);
			String hql="select go.order.user.id,go.order.user.userFullName,sum(go.goods.inPrice) as inPriceSum,sum(go.realPrice) as realPriceSum,concat(year(go.order.createTime),'-',month(go.order.createTime)) as createTime from GoodsOrder go where concat(year(go.order.createTime)) ="+year+" group by  concat(year(go.order.createTime),'-',month(go.order.createTime)),go.order.user.id";
			List<Object> objects=baseService.getList(hql);
			System.out.println(objects);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void testYearTurnover(){
		try {
			Date date=new Date();
			String year=TimeUtil.formatDateToYearStr(date);
			String hql="select sum(go.goods.inPrice * go.count) as inPriceSum,sum(go.realPrice * go.count) as realPriceSum,year(go.order.createTime) as createTime from GoodsOrder go  group by  year(go.order.createTime) ";
			List<Object> objects=baseService.getList(hql);
			System.out.println(objects);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void testOrderPriceSum(){
		try {
			String hql="select sum(go.realPrice*go.count) from GoodsOrder go where go.order.id=?";
			List<Object> objects=baseService.getList(hql, 24);
			System.out.println(objects.get(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
