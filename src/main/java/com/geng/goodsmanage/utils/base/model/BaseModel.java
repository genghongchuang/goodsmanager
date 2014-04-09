package com.geng.goodsmanage.utils.base.model;

import java.util.Date;

import javax.persistence.Column;

public abstract class BaseModel implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Column(name = "create_time")
	protected Date createTime;
	@Column(name = "update_time")
	protected Date updateTime;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	

}
