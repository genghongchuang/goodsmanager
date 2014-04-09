package com.geng.goodsmanage.goods.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.geng.goodsmanage.utils.base.model.BaseModel;
@Table(name="t_goodscolor")
@Entity
public class GoodsColor extends BaseModel{
	private static final long serialVersionUID = 1011923066509066807L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String colorName;
	private String inUse;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getInUse() {
		return inUse;
	}
	public void setInUse(String inUse) {
		this.inUse = inUse;
	}
	

}
