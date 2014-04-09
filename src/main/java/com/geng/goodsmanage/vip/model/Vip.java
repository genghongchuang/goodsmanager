package com.geng.goodsmanage.vip.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.utils.base.model.BaseModel;

/**
 * 
* @ClassName: Vip 
* @Description: TODO(会员) 
* @author genghongchuang
* @date 2013年11月27日 下午3:19:36 
*
 */
@Entity
@Table(name="t_vip")
public class Vip extends BaseModel{
	private static final long serialVersionUID = 9190477825538703614L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String accountNum;
	private String username;
	private String phoneNum;
	private int score;
	private Date joinTime;
	@ManyToOne
	@JoinColumn(name="createuser")
	private User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
