package com.geng.goodsmanage.system.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.geng.goodsmanage.utils.annotation.DataSourceAnnotation;
import com.geng.goodsmanage.utils.base.model.BaseModel;

@Entity
@Table(name="t_treenode")
public class TreeNode extends BaseModel {
	private static final long serialVersionUID = 6027616172143509695L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@DataSourceAnnotation(dataSourceDesc="节点编号")
	private Integer id;
	@DataSourceAnnotation(dataSourceDesc="父节点编号")
    private Integer pid;
	@DataSourceAnnotation(dataSourceDesc="节点内容")
	private String text;
	@DataSourceAnnotation(dataSourceDesc="节点样式")
	private String iconCls;
	@DataSourceAnnotation(dataSourceDesc="叶子节点")
	private String leaf;
	@DataSourceAnnotation(dataSourceDesc="节点url")
	private String url;
	@DataSourceAnnotation(dataSourceDesc="节点类型")
	private String type;
	@OneToMany(mappedBy="pid")
	@DataSourceAnnotation(dataSourceDesc="子节点")
	private Set<TreeNode> children;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
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
	public String getLeaf() {
		return leaf;
	}
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Set<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(Set<TreeNode> children) {
		this.children = children;
	}
	
	

}
