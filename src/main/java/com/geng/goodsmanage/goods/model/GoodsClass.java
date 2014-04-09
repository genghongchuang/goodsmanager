package com.geng.goodsmanage.goods.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.geng.goodsmanage.utils.base.model.BaseModel;
@Table(name="t_goodsclass")
@Entity
public class GoodsClass extends BaseModel{
	private static final long serialVersionUID = 1011923066509066807L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String className;
	private String inUse;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getInUse() {
		return inUse;
	}
	public void setInUse(String inUse) {
		this.inUse = inUse;
	}
	

}
