package com.geng.goodsmanage.system.model;

import com.geng.goodsmanage.utils.annotation.DataSourceAnnotation;
import com.geng.goodsmanage.utils.base.model.BaseModel;

public class ResourceNode extends BaseModel {
	private static final long serialVersionUID = -5892651921383084437L;
	@DataSourceAnnotation(dataSourceDesc="节点编号")
	private Integer id;
	@DataSourceAnnotation(dataSourceDesc="父节点编号")
	private String text;
	@DataSourceAnnotation(dataSourceDesc="节点样式")
	private String iconCls;
	@DataSourceAnnotation(dataSourceDesc="节点类型")
	private String type;
	@DataSourceAnnotation(dataSourceDesc="编号")
	private String component;
	@DataSourceAnnotation(dataSourceDesc="编号")
	private boolean leaf;
	@DataSourceAnnotation(dataSourceDesc="编号")
	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}
	
	
}
