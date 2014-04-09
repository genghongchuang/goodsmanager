package com.geng.goodsmanage.system.model;

import com.geng.goodsmanage.utils.base.model.BaseModel;

public class RadioGroup extends BaseModel{
	private static final long serialVersionUID = 1861993834728273337L;
	private String boxLabel;
	private String name;
	private String inputValue;
	public String getBoxLabel() {
		return boxLabel;
	}
	public void setBoxLabel(String boxLabel) {
		this.boxLabel = boxLabel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInputValue() {
		return inputValue;
	}
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	

}
