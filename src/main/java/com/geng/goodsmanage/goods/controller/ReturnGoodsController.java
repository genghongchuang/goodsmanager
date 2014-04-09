package com.geng.goodsmanage.goods.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.geng.goodsmanage.goods.model.Goods;
import com.geng.goodsmanage.goods.model.GoodsClass;
import com.geng.goodsmanage.goods.model.GoodsColor;
import com.geng.goodsmanage.goods.model.GoodsSize;
import com.geng.goodsmanage.goods.model.ReturnGoods;
import com.geng.goodsmanage.goods.service.GoodsService;
import com.geng.goodsmanage.goods.service.ReturnGoodsService;
import com.geng.goodsmanage.utils.pagination.Page;
@RequestMapping("goods/returnGoods")
@Controller
public class ReturnGoodsController {
	protected Logger log = LoggerFactory.getLogger(ReturnGoodsController.class); // 日志记录
	@Autowired
	ReturnGoodsService returnGoodsService;
	@Autowired
	GoodsService goodsService;
	/**
	 * 
	* @Title: returnGoodsList 
	* @Description: TODO(显示退货状态列表) 
	* @return     
	* @throws
	 */
	@RequestMapping("/returnGoodsList")
	public ModelAndView returnGoodsList(){
		ModelAndView mav=new ModelAndView("goods/returnGoodsList");
		return mav;
	}
	/**
	 * 
	* @Title: addReturnGoods 
	* @Description: TODO(退货) 
	* @param returnGoods
	* @return     
	* @throws
	 */
	@RequestMapping(value="/addReturnGoods",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addReturnGoods(ReturnGoods returnGoods){
		boolean result=false;
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			
			//returnGoodsService.load(re, pk)
			Goods goods=goodsService.load(Goods.class, returnGoods.getGoods().getId());
			if(goods.getCount()<returnGoods.getCount()){
				map.put("success", result);
			}else{
				goods.setCount(goods.getCount()-returnGoods.getCount());
				goodsService.update(goods);
			}
			String hql="from ReturnGoods r where r.goods.id=? and r.state='0'";
			List<ReturnGoods> goodses=returnGoodsService.getList(hql, returnGoods.getGoods().getId());
			int pk=-1;
			if(null!=goodses&&goodses.size()>0){
				ReturnGoods returnGoodsExist=goodses.get(0);
				pk=returnGoodsExist.getId();
				returnGoodsExist.setCount(returnGoodsExist.getCount()+returnGoods.getCount());
				returnGoodsService.update(returnGoodsExist);
			}else{
				returnGoods.setState("0");
				returnGoods.setReturnTime(new Date());
				pk=returnGoodsService.save(returnGoods);
			}
			
			if(pk>0){
				result=true;
			}
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
			
		}
	
		map.put("success", result);
		if(result){
			map.put("msg", "退货提交成功");
			//map.put("nameId", nameId);
		}else{
			map.put("msg", "退货提交失败，请稍后重试");
		}
		
		return map;
		
	}
	/**
	 * 
	* @Title: getReturnGoods 
	* @Description: TODO(查询退货) 
	* @param goods
	* @param state
	* @param start
	* @param limit
	* @param page
	* @param startTime
	* @param endTime
	* @return     
	* @throws
	 */
	@RequestMapping("/getReturnGoods")
	@ResponseBody
	public  Page<ReturnGoods> getReturnGoods(Goods goods,String state,int start,int limit,int page,String startTime,String endTime){
		String hql="from ReturnGoods r where r.goods.goodsName like ? and r.goods.goodsNum like ? and r.goods.brandName like ?";
		Page<ReturnGoods> page1=null;
		try {
			String goodsName=StringUtils.defaultIfEmpty(goods.getGoodsName(),"");
			String goodsNum=StringUtils.defaultIfEmpty(goods.getGoodsNum(), "");
			String brandName=StringUtils.defaultIfEmpty(goods.getBrandName(), "");
			
			int classId=-1;
			if(null!=goods.getGoodsClass()){
				classId=goods.getGoodsClass().getId();
			}
			
			if(classId>0){
				hql+=" and r.goods.goodsClass.id="+classId;
			}
			if(null!=startTime&&!"".equals(startTime)){
				hql+=" and r.returnTime>='"+startTime+" 00:00:00'";
			}
			if(null!=endTime&&!"".equals(endTime)){
				hql+=" and r.returnTime<='"+endTime+" 23:59:59'";
			}
			if("0".equals(state)||"1".equals(state)){
				hql+=" and r.state ="+state;
			}
			page1=returnGoodsService.getPage(hql, page, limit,"%"+goodsName+"%","%"+goodsNum+"%","%"+brandName+"%");
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
	}
	/**
	 * 
	* @Title: updateReturnGoods 
	* @Description: TODO(更新退货信息) 
	* @param returnGoods
	* @param goodsId
	* @return     
	* @throws
	 */
	@RequestMapping("/updateReturnGoods")
	@ResponseBody
	public Map<String, Object> updateReturnGoods(ReturnGoods returnGoods,int goodsId){
		boolean result=false;
		try {
			ReturnGoods oldReturnGoods=returnGoodsService.load(ReturnGoods.class, returnGoods.getId());
			if("1".equals(returnGoods.getState())){//标记为已成功
				oldReturnGoods.setState("1");
				result=returnGoodsService.update(oldReturnGoods);
			}else{
				int due=oldReturnGoods.getCount()-returnGoods.getCount();
				
				Goods goods=goodsService.load(Goods.class, goodsId);
				if(due>goods.getCount()){
					result=false;
				}else{
					if(due!=0){//退货量变化
						goods.setCount(goods.getCount()+due);
						goodsService.update(goods);
					}
					oldReturnGoods.setCount(returnGoods.getCount());
					oldReturnGoods.setState("0");
					result=returnGoodsService.update(oldReturnGoods);
				}
				
			}
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("success", result);
		if(result){
			map.put("msg", "修改成功");
		}else{
			map.put("msg", "修改失败，请检查库存总数，稍后重试");
		}
		
		return map;
	}
	/**
	 * 
	* @Title: betchDelGoods 
	* @Description: TODO(批量删除退货信息) 
	* @param ids
	* @return     
	* @throws
	 */
	@RequestMapping("/betchDelReturnGoods/{ids}")
	@ResponseBody
	public Map<String,Object> betchDelReturnGoods(@PathVariable("ids")String ids){
		boolean result=false;
		boolean betchResult=true;
		try {
			
			String[] rIds=ids.split(",");
			String hql="delete ReturnGoods as g where g.id=?";
			for(String rId:rIds){
				if(betchResult){
					int rid=Integer.valueOf(rId);
					ReturnGoods returnGoods=returnGoodsService.load(ReturnGoods.class, rid);
					if(!"1".equals(returnGoods.getState())){
						Goods goods=returnGoods.getGoods();
						goods.setCount(goods.getCount()+returnGoods.getCount());
						goodsService.update(goods);
						result=goodsService.delete(hql,rid);
					}
					
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

}
