package com.geng.goodsmanage.vip.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.geng.goodsmanage.system.controller.UserController;
import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.utils.pagination.Page;
import com.geng.goodsmanage.vip.model.Vip;
import com.geng.goodsmanage.vip.service.VipService;
/**
 * 
 * 
* @ClassName: VipController 
* @Description: TODO(会员管理) 
* @author genghongchuang
* @date 2013年11月28日 下午12:55:14 
*
 */
@Controller
@RequestMapping("/vip")
public class VipController {
	protected Logger log = LoggerFactory.getLogger(VipController.class); // 日志记录
	@Autowired
	VipService vipService;
	/**
	 * 
	* @Title: vipList 
	* @Description: TODO(会员管理页面) 
	* @return     
	* @throws
	 */
	@RequestMapping("/vipList")
	public ModelAndView vipList(){
		ModelAndView mav=new ModelAndView("vip/vipList");
		return mav;
	}
	/**
	 * 
	* @Title: addVip 
	* @Description: TODO(添加会员) 
	* @param vip
	* @return     
	* @throws
	 */
	@RequestMapping("/addVip")
	@ResponseBody
	public Map<String, Object> addVip(Vip vip){
		boolean result=false;
		try {
			vip.setJoinTime(new Date());
			int pk=vipService.save(vip);
			if(pk>0){
				result=true;
			}
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
			
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("success", result);
		if(result){
			map.put("msg", "添加成功");
		}else{
			map.put("msg", "添加失败，请稍后重试");
		}
		
		return map;
		
	}
	/**
	 * 
	* @Title: updateVip 
	* @Description: TODO(更新会员信息) 
	* @param vip
	* @return     
	* @throws
	 */
	@RequestMapping("/updateVip")
	@ResponseBody
	public Map<String, Object> updateVip(Vip vip){
		boolean result=false;
		try {
			Vip oldVip=vipService.load(Vip.class, vip.getId());
			oldVip.setUsername(vip.getUsername());
			oldVip.setPhoneNum(vip.getPhoneNum());
			result=vipService.update(oldVip);
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("success", result);
		if(result){
			map.put("msg", "修改成功");
		}else{
			map.put("msg", "修改失败，请稍后重试");
		}
		
		return map;
	}
	/**
	 * 
	* @Title: betchDelVip 
	* @Description: TODO(批量删除) 
	* @param ids
	* @return     
	* @throws
	 */
	@RequestMapping("/betchDelVip/{ids}")
	@ResponseBody
	public Map<String,Object> betchDelVip(@PathVariable("ids")String ids){
		boolean result=false;
		boolean betchResult=true;
		try {
			String[] rIds=ids.split(",");
			String hql="delete Vip as v where v.id=?";
			for(String rId:rIds){
				if(betchResult){
					int rid=Integer.valueOf(rId);
					result=vipService.delete(hql,rid);
				}
				
			}
			if(!result){
				betchResult=false;
			}
		} catch (Exception e) {
			betchResult=false;
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		Map<String, Object> map=new HashMap<String, Object>();
		if(!betchResult){
			map.put("success", false);
			map.put("msg", "批量删除失败");
			return map;
		}else{
			map.put("success", true);
			map.put("msg", "批量删除成功");
			return map;
		}
		
		
	}
	@RequestMapping("/getVip")
	@ResponseBody
	public Vip getVip(String accountNum){
		String hql="from Vip v where v.accountNum = ? ";
		accountNum=StringUtils.defaultIfEmpty(accountNum, "");
		Vip vip=null;
		try {
		List<Vip> vips=	vipService.getList(hql, accountNum);
		if(null!=vips&&vips.size()>0){
			vip=vips.get(0);
		}
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return vip;
		
	}
	@RequestMapping("/getVips")
	@ResponseBody
	public  Page<Vip> getVips(Vip vip,int start,int limit,int page,String startTime,String endTime){
		
		//String hql="from Vip v where u.userName like ? and u.userFullName like ?";
		String hql="from Vip v where v.username like ? and v.accountNum like ? ";
		Page<Vip> page1=null;
		try {
			if(null!=startTime&&!"".equals(startTime)){
				hql+=" and v.joinTime>='"+startTime+" 00:00:00'";
			}
			if(null!=endTime&&!"".equals(endTime)){
				hql+=" and v.joinTime<='"+endTime+" 23:59:59'";
			}
			if(StringUtils.isEmpty(vip.getAccountNum())){
				vip.setAccountNum("");
			}
			if(StringUtils.isEmpty(vip.getUsername())){
				vip.setUsername("");
			}

			//page1=vipService.getPage(hql, page, limit,"%"+user.getUserName()+"%","%"+user.getUserFullName()+"%");
			page1=vipService.getPage(hql, page, limit,"%"+vip.getUsername()+"%","%"+vip.getAccountNum()+"%");
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
	}

}
