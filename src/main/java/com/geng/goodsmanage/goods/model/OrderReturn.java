package com.geng.goodsmanage.goods.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.utils.base.model.BaseModel;

@Table(name="t_order_return")
@Entity
public class OrderReturn extends BaseModel{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	//@ManyToOne
	//@JoinColumn(name="orderid")
	//private Order order;//需退换货订单
	@ManyToOne
	@JoinColumn(name="goid")
	private GoodsOrder goodsOrder;//需退货订单
	//@OneToOne
	//@JoinColumn(name="goodsid")
	//private Goods goods;//需退还的物品
	private int goodsCount;
	private int status;//0 退 1换 2删除
	@ManyToOne
	@JoinColumn(name="neworderid")
	private Order newOrder;//换货的新订单
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;//操作人
	private Date addTime;//添加时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/*public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}*/
	public int getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Order getNewOrder() {
		return newOrder;
	}
	public void setNewOrder(Order newOrder) {
		this.newOrder = newOrder;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public GoodsOrder getGoodsOrder() {
		return goodsOrder;
	}
	public void setGoodsOrder(GoodsOrder goodsOrder) {
		this.goodsOrder = goodsOrder;
	}
	

}
