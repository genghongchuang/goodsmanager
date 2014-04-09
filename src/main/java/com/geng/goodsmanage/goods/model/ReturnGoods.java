package com.geng.goodsmanage.goods.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.geng.goodsmanage.utils.base.model.BaseModel;

@Entity
@Table(name="t_returngoods")
public class ReturnGoods extends BaseModel{
	private static final long serialVersionUID = 3317955710452141289L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private int count;
	@ManyToOne
	@JoinColumn(name="goodsid")
	private Goods goods;
	//退货状态 0，未成功 1成功
	private String state;
	private Date returnTime;
	
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

}
