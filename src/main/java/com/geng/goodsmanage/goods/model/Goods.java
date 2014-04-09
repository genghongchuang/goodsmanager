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
import com.geng.goodsmanage.vip.model.Vip;
@Table(name="t_goods")
@Entity
public class Goods extends BaseModel{
	private static final long serialVersionUID = -8274395552338702841L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String brandName;
	private String goodsName;
	private String goodsNum;
	private int count;
	private double inPrice;
	private double orderPrice;
	private double discount;
	private double vipDiscount;
	@ManyToOne
	@JoinColumn(name="vipid")
	private Vip vip;
	private double outPrice;
	private Date addTime;
	private Date sellTime;
	@ManyToOne
	@JoinColumn(name="classid")
	private GoodsClass goodsClass;
	@ManyToOne
	@JoinColumn(name="sizeid")
	private GoodsSize goodsSize;
	@ManyToOne
	@JoinColumn(name="colorid")
	private GoodsColor goodsColor;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public String getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getInPrice() {
		return inPrice;
	}
	public void setInPrice(double inPrice) {
		this.inPrice = inPrice;
	}
	public double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getVipDiscount() {
		return vipDiscount;
	}
	public void setVipDiscount(double vipDiscount) {
		this.vipDiscount = vipDiscount;
	}
	public double getOutPrice() {
		return outPrice;
	}
	public void setOutPrice(double outPrice) {
		this.outPrice = outPrice;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getSellTime() {
		return sellTime;
	}
	public void setSellTime(Date sellTime) {
		this.sellTime = sellTime;
	}
	public Vip getVip() {
		return vip;
	}
	public void setVip(Vip vip) {
		this.vip = vip;
	}
	public GoodsClass getGoodsClass() {
		return goodsClass;
	}
	public void setGoodsClass(GoodsClass goodsClass) {
		this.goodsClass = goodsClass;
	}
	public GoodsSize getGoodsSize() {
		return goodsSize;
	}
	public void setGoodsSize(GoodsSize goodsSize) {
		this.goodsSize = goodsSize;
	}
	public GoodsColor getGoodsColor() {
		return goodsColor;
	}
	public void setGoodsColor(GoodsColor goodsColor) {
		this.goodsColor = goodsColor;
	}
	
	
	

}
