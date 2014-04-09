package com.geng.goodsmanage.system.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.TikaBridge;

import com.geng.goodsmanage.utils.base.model.BaseModel;
/**
 * 
* @ClassName: SignIn 
* @Description: TODO(用户签到) 
* @author genghongchuang
* @date 2013年11月29日 上午10:19:07 
*
 */
@Entity
@Table(name="t_signin")
public class SignIn extends BaseModel{
	private static final long serialVersionUID = -5541636380826676722L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name = "intime")
	private Date inTime;
	
	@Column(name = "outtime")
	private Date outTime;
	@OneToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE})
	private User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getInTime() {
		return inTime;
	}
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	

}
