package com.geng.goodsmanage.goods.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.geng.goodsmanage.utils.base.model.BaseModel;
@Table(name="t_goodsnum_rule")
@Entity
public class GoodsNumRule extends BaseModel{
	private static final long serialVersionUID = 1011923066509066807L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String autoCreate;//是否自动生成  0,否 1,是
	private String prefix;
	private String inUse;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAutoCreate() {
		return autoCreate;
	}
	public void setAutoCreate(String autoCreate) {
		this.autoCreate = autoCreate;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getInUse() {
		return inUse;
	}
	public void setInUse(String inUse) {
		this.inUse = inUse;
	}
	

}
