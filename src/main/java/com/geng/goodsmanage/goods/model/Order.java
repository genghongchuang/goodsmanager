package com.geng.goodsmanage.goods.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.utils.base.model.BaseModel;
import com.geng.goodsmanage.vip.model.Vip;
/**
 * 
* @ClassName: Order 
* @Description: TODO(订单) 
* @author genghongchuang
* @date 2013-12-20 下午5:02:16 
*
 */
@Table(name="t_goodsorder")
@Entity
public class Order extends BaseModel{
	private static final long serialVersionUID = 9074106635741360402L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private Double realPrice;
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;
	@ManyToOne
	@JoinColumn(name="vipid")
	private Vip vip;
	private Date createTime;
	private String status;//0,未完成 1完成

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Vip getVip() {
		return vip;
	}
	public void setVip(Vip vip) {
		this.vip = vip;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

}
