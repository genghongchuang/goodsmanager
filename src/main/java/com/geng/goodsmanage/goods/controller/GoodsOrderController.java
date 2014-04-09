package com.geng.goodsmanage.goods.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.geng.goodsmanage.goods.model.Goods;
import com.geng.goodsmanage.goods.model.GoodsOrder;
import com.geng.goodsmanage.goods.model.MonthOrderTurnover;
import com.geng.goodsmanage.goods.model.Order;
import com.geng.goodsmanage.goods.model.OrderReturn;
import com.geng.goodsmanage.goods.model.ReturnGoods;
import com.geng.goodsmanage.goods.service.GoodsOrderService;
import com.geng.goodsmanage.goods.service.GoodsService;
import com.geng.goodsmanage.goods.service.OrderReturnService;
import com.geng.goodsmanage.goods.service.OrderService;
import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.system.service.UserService;
import com.geng.goodsmanage.utils.DoubleUtil;
import com.geng.goodsmanage.utils.TimeUtil;
import com.geng.goodsmanage.utils.base.service.BaseService;
import com.geng.goodsmanage.utils.pagination.Page;
import com.geng.goodsmanage.vip.model.Vip;
import com.geng.goodsmanage.vip.service.VipService;
/**
 * 
* @ClassName: GoodsOrderController 
* @Description: TODO(销售) 
* @author genghongchuang
* @date 2013-12-25 下午12:53:12 
*
 */
@RequestMapping("goods/goodsOrder")
@Controller
public class GoodsOrderController {
	protected Logger log = LoggerFactory.getLogger(GoodsOrderController.class); // 日志记录
	@Autowired
	GoodsOrderService goodsOrderService;
	@Autowired
	OrderService orderService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	BaseService<Object, Integer> baseService;
	@Autowired
	VipService vipService;
	@Autowired
	UserService userService;
	@Autowired
	OrderReturnService orderReturnService;
	
	/**
	 * 
	* @Title: goodsOrderList 
	* @Description: TODO(显示销售页面) 
	* @return     
	* @throws
	 */
	@RequestMapping("/goodsOrderList")
	public ModelAndView goodsOrderList(){
		ModelAndView mav=new ModelAndView("goods/goodsOrderList");
		return mav;		
	}
	/**
	 * 
	* @Title: goodsTurnoverList 
	* @Description: TODO(显示营业额页面) 
	* @return     
	* @throws
	 */
	@RequestMapping("/goodsTurnoverList")
	public ModelAndView goodsTurnoverList(){
		ModelAndView mav=new ModelAndView("goods/goodsTurnoverList");
		return mav;
	}
	/**
	 * 
	* @Title: goodsReturnOrChangeList 
	* @Description: TODO(退换货) 
	* @return     
	* @throws
	 */
	@RequestMapping("/goodsReturnOrChangeList")
	public ModelAndView goodsReturnOrChangeList(){
		ModelAndView mav=new ModelAndView("goods/goodsReturnOrChangeList");
		return mav;
	}
	/**
	 * 
	* @Title: goodsReturnList 
	* @Description: TODO(获取退货列表) 
	* @return     
	* @throws
	 */
	@RequestMapping("/goodsReturnList")
	public ModelAndView goodsReturnList(){
		ModelAndView mav=new ModelAndView("goods/goodsReturnList");
		return mav;
	}
	@RequestMapping("/goodsChangeList")
	public ModelAndView goodsChangeList(){
		ModelAndView mav=new ModelAndView("goods/goodsSellChangeList");
		return mav;
	}
	/**
	 * 
	* @Title: getOrderId 
	* @Description: TODO(获取订单编号) 
	* @return     
	* @throws
	 */
	@RequestMapping("/getOrderId")
	@ResponseBody
	public int getOrderId(){
		int orderId = 0;
		try {
			String hql="from Order o where o.status='0'";
			List<Order> orders=orderService.getList(hql);
			Order order;
			if(null!=orders && orders.size()>0){
				order=orders.get(0);
				orderId=order.getId();
			}else{
				order=new Order();
				order.setStatus("0");
				orderId=orderService.save(order);
				
			}
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return orderId;
		
	}
	/**
	 * 
	* @Title: addShopcar 
	* @Description: TODO(添加购物车) 
	* @param goodsId
	* @param count
	* @return     
	* @throws
	 */
	@RequestMapping("/addShopcar")
	@ResponseBody
	public Map<String, Object> addShopcar(int goodsId,int count){
		boolean result=false;
		try {
			int orderId=getOrderId();
			GoodsOrder goodsOrder=new GoodsOrder();
			Order order=orderService.load(Order.class, orderId);
			Goods goods=goodsService.load(Goods.class, goodsId);
			goodsOrder.setCount(count);
			goodsOrder.setCreateTime(new Date());
			goodsOrder.setGoods(goods);
			goodsOrder.setOrder(order);
			//goodsOrder.setRealPrice(order.getRealPrice());
			int pk=goodsOrderService.save(goodsOrder);
			if(pk>0){
				result=true;
			}
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
			
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("success", result);
		if(result){
			map.put("msg", "加入成功");
		}else{
			map.put("msg", "加入失败，请稍后重试");
		}
		
		return map;		
	}
	@RequestMapping("/updatOrderPrice")
	@ResponseBody
	public Map<String, Object> updatOrderPrice(int orderId,int price){
		boolean result=false;
		try {
		
			GoodsOrder goodsOrder=goodsOrderService.load(GoodsOrder.class, orderId);
			goodsOrder.setRealPrice(price);			
			result=goodsOrderService.update(goodsOrder);
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
			
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("success", result);
		if(result){
			map.put("msg", "修改成功");
		}else{
			map.put("msg", "修改失败，请稍后重试");
		}
		
		return map;		
	}
	/**
	 * 
	* @Title: getGoodsOrder 
	* @Description: TODO(获取购物车里的货物) 
	* @param orderId
	* @param page
	* @param limit
	* @return     
	* @throws
	 */
	@RequestMapping("/getGoodsOrder")
	@ResponseBody
	public Page<GoodsOrder> getGoodsOrder(int orderId,int page,int limit){
		String hql="from GoodsOrder go where go.order.id=?";
		Page<GoodsOrder> page1=null;
		try {

			page1=goodsOrderService.getPage(hql, page, limit,orderId);
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
		
	}
	/**
	 * 
	* @Title: getMonthTurnover 
	* @Description: TODO(获取月销售统计数据) 
	* @return     
	* @throws
	 */
    @RequestMapping("/getMonthTurnover")
    @ResponseBody
	public List<MonthOrderTurnover> getMonthTurnover(){
		List<MonthOrderTurnover> monthOrderTurnovers=new ArrayList<MonthOrderTurnover>();
		try {
			Date date=new Date();
			String year=TimeUtil.formatDateToYearStr(date);
			String hql="select sum(go.goods.inPrice * go.count) as inPriceSum,sum(go.realPrice * go.count) as realPriceSum,concat(year(go.order.createTime),'-',month(go.order.createTime)) as createTime from GoodsOrder go where go.status !=1 and concat(year(go.order.createTime)) ="+year+" group by  concat(year(go.order.createTime),'-',month(go.order.createTime)) ";
			List<Object> objects=baseService.getList(hql);
			 java.text.DecimalFormat df=new java.text.DecimalFormat("#.##");
			  
			for(Object object:objects){
				Object[] array=(Object[]) object;
				String inPriceSum=df.format((Double)array[0]);
				String realPriceSum=df.format((Double)array[1]);
				MonthOrderTurnover monthOrderTurnover=new MonthOrderTurnover(Double.valueOf(inPriceSum), Double.valueOf(realPriceSum), (String)array[2]);
				monthOrderTurnovers.add(monthOrderTurnover);
			}
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return monthOrderTurnovers;
	}
    /**
	 * 
	* @Title: getMonthTurnover 
	* @Description: TODO(获取月销售统计数据) 
	* @return     
	* @throws
	 */
    @RequestMapping("/getYearTurnover")
    @ResponseBody
	public List<MonthOrderTurnover> getYearTurnover(){
		List<MonthOrderTurnover> monthOrderTurnovers=new ArrayList<MonthOrderTurnover>();
		try {
			
			String hql="select sum(go.goods.inPrice * go.count) as inPriceSum,sum(go.realPrice * go.count) as realPriceSum,year(go.order.createTime) as createTime from GoodsOrder go where go.status !=1   group by  year(go.order.createTime) ";
			List<Object> objects=baseService.getList(hql);
			 java.text.DecimalFormat df=new java.text.DecimalFormat("#.##");
			  
			for(Object object:objects){
				Object[] array=(Object[]) object;
				String inPriceSum=df.format((Double)array[0]);
				String realPriceSum=df.format((Double)array[1]);
				MonthOrderTurnover monthOrderTurnover=new MonthOrderTurnover(Double.valueOf(inPriceSum), Double.valueOf(realPriceSum), String.valueOf(array[2]));
				monthOrderTurnovers.add(monthOrderTurnover);
			}
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return monthOrderTurnovers;
	}
    /**
     * 
    * @Title: getUserYearTurnover 
    * @Description: TODO(获取销售人员盈利数据) 
    * @return     
    * @throws
     */
    @RequestMapping("/getUserYearTurnover")
    @ResponseBody
	public List<MonthOrderTurnover> getUserYearTurnover(){
		List<MonthOrderTurnover> monthOrderTurnovers=new ArrayList<MonthOrderTurnover>();
		try {
			
			String hql="select sum(go.goods.inPrice * go.count) as inPriceSum,sum(go.realPrice * go.count) as realPriceSum,year(go.order.createTime) as createTime ,go.order.user.id as userId,go.order.user.userFullName as userFullName from  GoodsOrder go where go.status !=1  group by  year(go.order.createTime),go.order.user.id ";
			List<Object> objects=baseService.getList(hql);
			 java.text.DecimalFormat df=new java.text.DecimalFormat("#.##");
			  
			for(Object object:objects){
				Object[] array=(Object[]) object;
				String inPriceSum=df.format((Double)array[0]);
				String realPriceSum=df.format((Double)array[1]);
				int id=(Integer)array[3];
				String userFullName=(String)array[4];
				MonthOrderTurnover monthOrderTurnover=new MonthOrderTurnover(Double.valueOf(inPriceSum), Double.valueOf(realPriceSum), String.valueOf(array[2]),id,userFullName);
				monthOrderTurnovers.add(monthOrderTurnover);
			}
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return monthOrderTurnovers;
	}
	/**
	 * 
	* @Title: getGoodsOrderTurnover 
	* @Description: TODO(查询营业额) 
	* @param orderId
	* @param userId
	* @param page
	* @param limit
	* @return     
	* @throws
	 */
	@RequestMapping("/getOrderTurnover")
	@ResponseBody
	public Page<GoodsOrder> getOrderTurnover(Integer orderId,Integer userId,String startTime,String endTime,int page,int limit){
		String hql="from GoodsOrder so where 1=1 and so.status !=1 ";
		StringBuffer sb=new StringBuffer(hql);
	//	List<Object> params=new ArrayList<Object>();
		if(null!=orderId&&orderId>0){
			sb.append(" and so.order.id= ").append(orderId);
			//params.add(orderId);

		}
		if(null!=userId&&userId>0){
			sb.append(" and so.order.user.id= ").append(userId);
			//params.add(userId);
		}
		if(StringUtils.isNotBlank(startTime)){
			sb.append(" and so.order.createTime>= ");
			sb.append("'").append(startTime).append(" 00:00:00'");
			
		}
		if(StringUtils.isNotBlank(endTime)){
			sb.append(" and so.order.createTime<=");
			sb.append("'").append(endTime).append(" 23:59:59'");
		}
		sb.append(" order by so.id desc");
		Page<GoodsOrder> page1=null;
		try {

			page1=goodsOrderService.getPage(sb.toString(), page, limit,null);
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
		
	}
	/**
	 * 
	* @Title: getTotalPrice 
	* @Description: TODO(获取订单总价) 
	* @param orderId
	* @param accountNum
	* @return     
	* @throws
	 */
	@RequestMapping("/getTotalPrice")
	@ResponseBody
	public Map<String, Object> getTotalPrice(int orderId,String accountNum){
		String hql="select sum(Case  When go.realPrice=0 Then (go.count*go.goods.orderPrice*discount*0.1) else (go.realPrice*go.count) End )from GoodsOrder go where go.order.id=?";
		//String hql="select sum(Case  When go.realPrice=0 Then go.goods.orderPrice else go.realPrice End )from GoodsOrder go where go.order.id=?";
		if(StringUtils.isNotBlank(accountNum)){
			 hql="select sum(Case  When go.realPrice=0 Then (go.count*go.goods.orderPrice*vipDiscount*0.1) else (go.realPrice*go.count) End )from GoodsOrder go where go.order.id=?";
		}
		Map<String, Object> map=new HashMap<String, Object>();
		
		try {
			Double count=(Double) baseService.getSimpleResult(hql, orderId);
			map.put("success", true);
			map.put("count", count);
		} catch (Exception e) {
			map.put("success", true);
			map.put("count", 0);
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return map;
		
	}
	/**
	 * 
	* @Title: betchDelGoodsOrder 
	* @Description: TODO(批量删除订单) 
	* @param ids
	* @return     
	* @throws
	 */
	@RequestMapping("/betchDelGoodsOrder/{ids}")
	@ResponseBody
	public Map<String,Object> betchDelGoodsOrder(@PathVariable("ids")String ids){
		boolean result=false;
		boolean betchResult=true;
		try {
			String[] rIds=ids.split(",");
			//String hql="delete GoodsOrder as g where g.id=?";
			for(String rId:rIds){
				if(betchResult){
					int rid=Integer.valueOf(rId);
					GoodsOrder goodsOrder=goodsOrderService.load(GoodsOrder.class, rid);
					goodsOrder.setStatus("1");
					result=goodsOrderService.update(goodsOrder);
				}
				
			}
			if(!result){
				betchResult=false;
			}
		} catch (Exception e) {
			betchResult=false;
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		Map<String, Object> map=new HashMap<String, Object>();
		if(!betchResult){
			map.put("success", false);
			map.put("msg", "批量删除失败");
			return map;
		}else{
			map.put("success", true);
			map.put("msg", "批量删除成功");
			return map;
		}
		
		
	}
	/**
	 * 
	* @Title: sell 
	* @Description: TODO(出售货物) 
	* @return     
	* @throws
	 */
	@RequestMapping("/sell")
	@ResponseBody
	public Map<String, Object> sell(int userId, int orderId,String accountNum){
		Map<String, Object> map=new HashMap<String, Object>();
		String hql="from GoodsOrder go where go.order.id=?";
		String viphql="from Vip v where v.accountNum = ? ";
		accountNum=StringUtils.defaultIfEmpty(accountNum, "");
		Vip vip=null;
		boolean result=true;
		try {
			List<Vip> vips=	vipService.getList(viphql, accountNum);
			if(vips.size()==1){
				vip=vips.get(0);
			}
			List<GoodsOrder> goodsOrders=goodsOrderService.getList(hql, orderId);
			for(GoodsOrder goodsOrder:goodsOrders){
				if(goodsOrder.getRealPrice()<=0){
					if(vip!=null){
						goodsOrder.setRealPrice(goodsOrder.getGoods().getOrderPrice()*goodsOrder.getGoods().getVipDiscount()*0.1);
					}else{
						goodsOrder.setRealPrice(goodsOrder.getGoods().getOrderPrice()*goodsOrder.getGoods().getDiscount()*0.1);
					}
					
					result=goodsOrderService.update(goodsOrder);
				}
				Goods goods=goodsOrder.getGoods();
				
				int count=goodsOrder.getCount();
				if(goods.getCount()<count){
					throw new Exception("购买数量大于库存数量");
				}else{
					goods.setCount(goods.getCount()-count);
					goodsService.update(goods);
				}
			}
			Order order=orderService.load(Order.class, orderId);
		    User user=userService.load(User.class, userId);
			order.setVip(vip);
			order.setUser(user);
			order.setStatus("1");//订单完成
			order.setCreateTime(new Date());
			if(result){
				result=orderService.update(order);
				
			}
			if(result){
			  map.put("msg", "订单提交完成！");
			}else{
				map.put("msg", "订单提交失败！");
			}
			map.put("success", result);
			
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
			map.put("msg", "订单提交失败！");
			map.put("success", false);
		}
		return map;
		
	}
	/**
	 * 
	* @Title: returnGoods 
	* @Description: TODO(退货) 
	* @param orderId
	* @param goodsId
	* @param count
	* @param operateUser
	* @return     
	* @throws
	 */
	@RequestMapping("/returnGoods")
	@ResponseBody
	public Map<String, Object> returnGoods(int id,int count,int operateUser){
		boolean result=false;
		try {
			GoodsOrder goodsOrder=goodsOrderService.load(GoodsOrder.class, id);
			int nowCount=goodsOrder.getCount();
			User user=userService.load(User.class, operateUser);
			OrderReturn orderReturn=new OrderReturn();
			orderReturn.setAddTime(new Date());
			orderReturn.setGoodsOrder(goodsOrder);
			
			orderReturn.setGoodsCount(count);
			orderReturn.setUser(user);
			int id1=orderReturnService.save(orderReturn);
			//List<GoodsOrder> goodsOrders=goodsOrderService.getList("from GoodsOrder go where go.order.id=? and go.goods.id=?", orderId,goodsId);
			if(id1>0&&null!=goodsOrder){
				//GoodsOrder goodsOrder=goodsOrders.get(0);
				if(count>=nowCount){
					goodsOrder.setStatus("1");
					goodsOrderService.update(goodsOrder);
				}else{
					count=nowCount-count;
					goodsOrder.setCount(count);
					goodsOrderService.save(goodsOrder);
				}
				result=true;
			}
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("success", result);
		if(result){
			map.put("msg", "操作成功");
		}else{
			map.put("msg", "操作失败，请稍后重试");
		}
		
		return map;
	}
	/**
	 * 
	* @Title: changeGoods 
	* @Description: TODO(换货) 
	* @param id
	* @param newOrderId
	* @param count
	* @param operateUser
	* @return     
	* @throws
	 */
	@RequestMapping("/changeGoods")
	@ResponseBody
	public Map<String, Object> changeGoods(int id,int newOrderId,int count,int operateUser){
		boolean result=false;
		try {
			GoodsOrder goodsOrder=goodsOrderService.load(GoodsOrder.class, id);
			Order newOrder=orderService.load(Order.class, newOrderId);
			int nowCount=goodsOrder.getCount();
			User user=userService.load(User.class, operateUser);
			OrderReturn orderReturn=new OrderReturn();
			orderReturn.setAddTime(new Date());
			orderReturn.setGoodsOrder(goodsOrder);
			orderReturn.setNewOrder(newOrder);
			orderReturn.setStatus(2);
			orderReturn.setGoodsCount(count);
			orderReturn.setUser(user);
			int id1=orderReturnService.save(orderReturn);
			//List<GoodsOrder> goodsOrders=goodsOrderService.getList("from GoodsOrder go where go.order.id=? and go.goods.id=?", orderId,goodsId);
			if(id1>0&&null!=goodsOrder){
				//GoodsOrder goodsOrder=goodsOrders.get(0);
				if(count>=nowCount){
					goodsOrder.setStatus("1");
					goodsOrderService.update(goodsOrder);
				}else{
					count=nowCount-count;
					goodsOrder.setCount(count);
					goodsOrderService.save(goodsOrder);
				}
				result=true;
			}
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("success", result);
		if(result){
			map.put("msg", "操作成功");
		}else{
			map.put("msg", "操作失败，请稍后重试");
		}
		
		return map;
	}
	/**
	 * 
	* @Title: getOrderPriceSum 
	* @Description: TODO(返回订单价格总计) 
	* @param orderId
	* @return     
	* @throws
	 */
	@RequestMapping("/getOrderPriceSum")
	@ResponseBody
	public double getOrderPriceSum(int orderId){
		double sum=0;
		try {
			String hql="select sum(go.realPrice*go.count) from GoodsOrder go where go.status!=1 and  go.order.id=?";
			List<Object> objects=baseService.getList(hql, orderId);
			if(null!=objects&&objects.size()==1){
				sum=(Double) objects.get(0);
				sum=DoubleUtil.tayste(sum);
			}
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return sum;
	}
	/**
	 * 
	* @Title: getOrderReturn 
	* @Description: TODO(获取退货列表) 
	* @param orderId
	* @param operateUserId
	* @param sellUserId
	* @param startTime
	* @param endTime
	* @param page
	* @param limit
	* @return     
	* @throws
	 */
	@RequestMapping("/getOrderReturn")
	@ResponseBody
	public Page<OrderReturn> getOrderReturn(Integer orderId,Integer operateUserId,Integer sellUserId,String startTime,String endTime,int page,int limit){
		Page<OrderReturn> pages=null;
		String hql="from OrderReturn re where 1=1  and re.status=0";
		StringBuffer sb=new StringBuffer(hql);
	//	List<Object> params=new ArrayList<Object>();
		if(null!=orderId&&orderId>0){
			sb.append(" and re.goodsOrder.order.id= ").append(orderId);
			//params.add(orderId);

		}
		if(null!=sellUserId&&sellUserId>0){
			sb.append(" and re.goodsOrder.order.user.id= ").append(sellUserId);
			//params.add(userId);
		}
		if(null!=operateUserId&&operateUserId>0){
			sb.append(" and re.user.id= ").append(operateUserId);
			//params.add(userId);
		}
		if(StringUtils.isNotBlank(startTime)){
			sb.append(" and re.addTime>= ");
			sb.append("'").append(startTime).append(" 00:00:00'");
			
		}
		if(StringUtils.isNotBlank(endTime)){
			sb.append(" and re.addTime<=");
			sb.append("'").append(endTime).append(" 23:59:59'");
		}
		sb.append(" order by re.id desc");
		//Page<GoodsOrder> page1=null;
		//List<OrderReturn> orderReturns=new ArrayList<OrderReturn>(); 
		hql=sb.toString();
		try {			
			pages=orderReturnService.getPage(hql, page, limit, null);
			//orderReturns=orderReturnService.getList(hql);
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return pages;
	}
	/**
	 * 
	* @Title: getOrderChange 
	* @Description: TODO(获取换货列表) 
	* @param orderId
	* @param operateUserId
	* @param sellUserId
	* @param startTime
	* @param endTime
	* @param page
	* @param limit
	* @return     
	* @throws
	 */
	@RequestMapping("/getOrderChange")
	@ResponseBody
	public Page<OrderReturn> getOrderChange(Integer orderId,Integer operateUserId,Integer sellUserId,String startTime,String endTime,int page,int limit){
		Page<OrderReturn> pages=null;
		String hql="from OrderReturn re where 1=1  and re.status =2";
		StringBuffer sb=new StringBuffer(hql);
	//	List<Object> params=new ArrayList<Object>();
		if(null!=orderId&&orderId>0){
			sb.append(" and re.goodsOrder.order.id= ").append(orderId);
			//params.add(orderId);

		}
		if(null!=sellUserId&&sellUserId>0){
			sb.append(" and re.goodsOrder.order.user.id= ").append(sellUserId);
			//params.add(userId);
		}
		if(null!=operateUserId&&operateUserId>0){
			sb.append(" and re.user.id= ").append(operateUserId);
			//params.add(userId);
		}
		if(StringUtils.isNotBlank(startTime)){
			sb.append(" and re.addTime>= ");
			sb.append("'").append(startTime).append(" 00:00:00'");
			
		}
		if(StringUtils.isNotBlank(endTime)){
			sb.append(" and re.addTime<=");
			sb.append("'").append(endTime).append(" 23:59:59'");
		}
		sb.append(" order by re.id desc");
		//Page<GoodsOrder> page1=null;
		//List<OrderReturn> orderReturns=new ArrayList<OrderReturn>(); 
		hql=sb.toString();
		try {			
			pages=orderReturnService.getPage(hql, page, limit, null);
			//orderReturns=orderReturnService.getList(hql);
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return pages;
	}


}
