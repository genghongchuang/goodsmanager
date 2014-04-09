package com.geng.goodsmanage.goods.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.geng.goodsmanage.utils.base.model.BaseModel;
/**
 * 
* @ClassName: GoodsOrder 
* @Description: TODO(中间表) 
* @author genghongchuang
* @date 2013-12-20 下午5:06:14 
*
 */
@Table(name="t_goods_order")
@Entity
public class GoodsOrder extends BaseModel{
	private static final long serialVersionUID = -3183636377563324630L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name="orderid")
	private Order order;
	@ManyToOne
	@JoinColumn(name="goodsid")
	private Goods goods;
	private int count;
	private double realPrice;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Order getOrder() {
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
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(double realPrice) {
		this.realPrice = realPrice;
	}
	
	

}
