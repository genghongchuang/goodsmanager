package com.geng.goodsmanage.goods.controller;


import java.util.ArrayList;
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

import com.geng.goodsmanage.goods.model.GoodsColor;
import com.geng.goodsmanage.goods.model.GoodsNumRule;
import com.geng.goodsmanage.goods.model.GoodsSize;
import com.geng.goodsmanage.goods.service.GoodsColorService;
import com.geng.goodsmanage.goods.service.GoodsNumRuleService;
import com.geng.goodsmanage.goods.service.GoodsSizeService;
import com.geng.goodsmanage.system.model.RadioGroup;
import com.geng.goodsmanage.utils.base.service.BaseService;
import com.geng.goodsmanage.utils.pagination.Page;
@RequestMapping("goods/goodsNumRule")
@Controller
public class GoodsNumRuleController {
	protected Logger log = LoggerFactory.getLogger(GoodsNumRuleController.class); // 日志记录
	@Autowired
	GoodsNumRuleService goodsNumRuleService;
	@Autowired
	BaseService<RadioGroup, Integer> baseService;
	/**
	 * 
	* @Title: goodsNumRuleList 
	* @Description: TODO(货号生成规则管理) 
	* @return     
	* @throws
	 */
	@RequestMapping("/goodsNumRuleList")
	public ModelAndView goodsNumRuleList(){
		ModelAndView mav=new ModelAndView("goods/goodsNumRuleList");
		return mav;
	}
	/**
	 * 
	* @Title: addGoodsNumRule 
	* @Description: TODO(添加货号生成规) 
	* @param goodsNumRule
	* @return     
	* @throws
	 */
	@RequestMapping("/addGoodsNumRule")
	@ResponseBody
	public Map<String, Object> addGoodsNumRule(GoodsNumRule goodsNumRule){
		boolean result=false;
		try {
			
			int pk=goodsNumRuleService.save(goodsNumRule);
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
	* @Title: updateGoodsNumRule 
	* @Description: TODO(更新货号生成规) 
	* @param goodsNumRule
	* @return     
	* @throws
	 */
	@RequestMapping("/updateGoodsNumRule")
	@ResponseBody
	public Map<String, Object> updateGoodsNumRule(GoodsNumRule goodsNumRule){
		boolean result=false;
		try {
			GoodsNumRule oldRule=goodsNumRuleService.load(GoodsNumRule.class, goodsNumRule.getId());
			oldRule.setAutoCreate(goodsNumRule.getAutoCreate());
			oldRule.setInUse(goodsNumRule.getInUse());
			oldRule.setPrefix(goodsNumRule.getPrefix());
			result=goodsNumRuleService.update(oldRule);
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
	* @Title: betchDelGoodsNumRule 
	* @Description: TODO(批量删除货号生成规) 
	* @param ids
	* @return     
	* @throws
	 */
	@RequestMapping("/betchDelGoodsNumRule/{ids}")
	@ResponseBody
	public Map<String,Object> betchDelGoodsNumRule(@PathVariable("ids")String ids){
		boolean result=false;
		boolean betchResult=true;
		try {
			String[] rIds=ids.split(",");
			String hql="delete GoodsNumRule as g where g.id=?";
			for(String rId:rIds){
				if(betchResult){
					int rid=Integer.valueOf(rId);
					result=goodsNumRuleService.delete(hql,rid);
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
	/**
	 * 
	* @Title: getGoodsNumRule 
	* @Description: TODO(货号生成规分页显示) 
	* @param goodsNumRule
	* @param start
	* @param limit
	* @param page
	* @return     
	* @throws
	 */
	@RequestMapping("/getGoodsNumRule")
	@ResponseBody
	public  Page<GoodsNumRule> getGoodsNumRule(GoodsNumRule goodsNumRule,int start,int limit,int page){
		
		//String hql="from Vip v where u.userName like ? and u.userFullName like ?";
		String hql="from GoodsNumRule g where g.prefix like ?  ";
		Page<GoodsNumRule> page1=null;
		try {
			String prefix=StringUtils.defaultIfEmpty(goodsNumRule.getPrefix(),"");
			String autoCreate=StringUtils.defaultIfEmpty(goodsNumRule.getAutoCreate(),"");
			if("1".equals(goodsNumRule.getInUse())){
				hql+=" and g.inUse=1";
			}
			if("0".equals(goodsNumRule.getInUse())){
				hql+=" and g.inUse=0";
			}
			if("1".equals(autoCreate)){
				hql+=" and g.autoCreate=1";
			}
			if("0".equals(autoCreate)){
				hql+=" and g.autoCreate=0";
			}
			
			
			//page1=vipService.getPage(hql, page, limit,"%"+user.getUserName()+"%","%"+user.getUserFullName()+"%");
			page1=goodsNumRuleService.getPage(hql, page, limit,"%"+prefix+"%");
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
	}
	/**
	 * 
	* @Title: getRadios 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return     
	* @throws
	 */
	@RequestMapping("/getRadios")
	@ResponseBody
	public List<RadioGroup> getRadios(){
		List<RadioGroup> radios=new ArrayList<RadioGroup>();
		List<GoodsNumRule> rules=new ArrayList<GoodsNumRule>();
		try {
			rules=goodsNumRuleService.getList("from GoodsNumRule g where g.inUse='1' ");
			if(null!=rules){
				for(GoodsNumRule rule:rules){
					RadioGroup radio=new RadioGroup();
					radio.setName("rg");
					if("1".equals(rule.getAutoCreate())){
						radio.setBoxLabel("自动编号");
					}else{
						radio.setBoxLabel(rule.getPrefix());
					}
					
					radio.setInputValue(rule.getPrefix());
					radios.add(radio);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return radios;
	}

}
