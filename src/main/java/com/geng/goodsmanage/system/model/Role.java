package com.geng.goodsmanage.system.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.geng.goodsmanage.utils.annotation.DataSourceAnnotation;
import com.geng.goodsmanage.utils.base.model.BaseModel;

@Entity
@Table(name="t_role")
public class Role extends BaseModel{	
	private static final long serialVersionUID = 3492569282344120782L;
	@DataSourceAnnotation(dataSourceDesc="编号")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;
	@DataSourceAnnotation(dataSourceDesc="角色名")
	@Column(name = "role_name")
	private String roleName;
	@DataSourceAnnotation(dataSourceDesc="角色类型")
	@Column(name = "role_type")
	private String roleType;
	@DataSourceAnnotation(dataSourceDesc="角色级别")
	@Column(name="role_level")
	private int roleLevel;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public int getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}
	
	
	

}
