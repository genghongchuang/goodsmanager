package com.geng.goodsmanage.system.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;



import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import com.geng.goodsmanage.utils.annotation.DataSourceAnnotation;
import com.geng.goodsmanage.utils.base.model.BaseModel;
@Entity
@Table(name="t_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseModel{
	private static final long serialVersionUID = -4868794851113232502L;
	@DataSourceAnnotation(dataSourceDesc="编号")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;
	@DataSourceAnnotation(dataSourceDesc="登陆次数")
	@Column(name = "loginnum")
	private int loginNum;
	@DataSourceAnnotation(dataSourceDesc="账号")
	@Column(name = "user_name")
	private String userName;
	@DataSourceAnnotation(dataSourceDesc="密码")
	@Column(name = "password")
	private String password;
	@DataSourceAnnotation(dataSourceDesc="姓名")
	@Column(name = "user_fullname")
	private String userFullName;
	@DataSourceAnnotation(dataSourceDesc="创建人")
	@Column(name = "create_user")
	private int createUser;
	@DataSourceAnnotation(dataSourceDesc="状态")
	@Column(name = "state")
	private String state;
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="t_user_role",joinColumns={@JoinColumn(name="user_id")},
	inverseJoinColumns={@JoinColumn(name="role_id")})
	private Set<Role> roles=new HashSet<Role>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLoginNum() {
		return loginNum;
	}
	public void setLoginNum(int loginNum) {
		this.loginNum = loginNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	public int getCreateUser() {
		return createUser;
	}
	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

}
