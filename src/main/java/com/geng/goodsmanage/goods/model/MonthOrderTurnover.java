package com.geng.goodsmanage.goods.model;

public class MonthOrderTurnover {
	private double inPriceSum;
	private double realPriceSum;
	private String createTime;
	private int userId;
	private String userFullName;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	public double getInPriceSum() {
		return inPriceSum;
	}
	public void setInPriceSum(double inPriceSum) {
		this.inPriceSum = inPriceSum;
	}
	public double getRealPriceSum() {
		return realPriceSum;
	}
	public void setRealPriceSum(double realPriceSum) {
		this.realPriceSum = realPriceSum;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public MonthOrderTurnover(double inPriceSum, double realPriceSum,
			String createTime) {
		super();
		this.inPriceSum = inPriceSum;
		this.realPriceSum = realPriceSum;
		this.createTime = createTime;
	}
	public MonthOrderTurnover(double inPriceSum, double realPriceSum,
			String createTime, int userId, String userFullName) {
		super();
		this.inPriceSum = inPriceSum;
		this.realPriceSum = realPriceSum;
		this.createTime = createTime;
		this.userId = userId;
		this.userFullName = userFullName;
	}
	
	

}
