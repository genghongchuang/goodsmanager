package com.geng.goodsmanage.goods.controller;


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

import com.geng.goodsmanage.goods.model.GoodsClass;
import com.geng.goodsmanage.goods.model.GoodsColor;
import com.geng.goodsmanage.goods.service.GoodsColorService;
import com.geng.goodsmanage.utils.pagination.Page;
@RequestMapping("goods/goodsColor")
@Controller
public class GoodsColorController {
	protected Logger log = LoggerFactory.getLogger(GoodsColorController.class); // 日志记录
	@Autowired
	GoodsColorService goodsColorService;
   /**
    * 
   * @Title: goodsColorList 
   * @Description: TODO(货物颜色管理) 
   * @return     
   * @throws
    */
	@RequestMapping("/goodsColorList")
	public ModelAndView goodsColorList(){
		ModelAndView mav=new ModelAndView("goods/goodsColorList");
		return mav;
	}
	/**
	 * 
	* @Title: addGoodsColor 
	* @Description: TODO(添加货物颜色) 
	* @param goodsColor
	* @return     
	* @throws
	 */
	@RequestMapping("/addGoodsColor")
	@ResponseBody
	public Map<String, Object> addGoodsColor(GoodsColor goodsColor){
		boolean result=false;
		try {
			
			int pk=goodsColorService.save(goodsColor);
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
	* @Title: updateGoodsColor 
	* @Description: TODO(更新货物颜色) 
	* @param goodsColor
	* @return     
	* @throws
	 */
	@RequestMapping("/updateGoodsColor")
	@ResponseBody
	public Map<String, Object> updateGoodsColor(GoodsColor goodsColor){
		boolean result=false;
		try {
			GoodsColor oldColor=goodsColorService.load(GoodsColor.class, goodsColor.getId());
			oldColor.setColorName(goodsColor.getColorName());
			oldColor.setInUse(goodsColor.getInUse());
			result=goodsColorService.update(oldColor);
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
	* @Title: betchDelGoodsColor 
	* @Description: TODO(批量删除货物颜色) 
	* @param ids
	* @return     
	* @throws
	 */
	@RequestMapping("/betchDelGoodsColor/{ids}")
	@ResponseBody
	public Map<String,Object> betchDelGoodsColor(@PathVariable("ids")String ids){
		boolean result=false;
		boolean betchResult=true;
		try {
			String[] rIds=ids.split(",");
			String hql="delete GoodsColor as g where g.id=?";
			for(String rId:rIds){
				if(betchResult){
					int rid=Integer.valueOf(rId);
					result=goodsColorService.delete(hql,rid);
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
	* @Title: getGoodsColor 
	* @Description: TODO(货物颜色分页显示) 
	* @param goodsColor
	* @param start
	* @param limit
	* @param page
	* @return     
	* @throws
	 */
	@RequestMapping("/getGoodsColor")
	@ResponseBody
	public  Page<GoodsColor> getGoodsColor(GoodsColor goodsColor,int start,int limit,int page){
		
		//String hql="from Vip v where u.userName like ? and u.userFullName like ?";
		String hql="from GoodsColor g where g.colorName like ?  ";
		Page<GoodsColor> page1=null;
		try {
			String colorName=StringUtils.defaultIfEmpty(goodsColor.getColorName(),"");
			if("1".equals(goodsColor.getInUse())){
				hql+=" and g.inUse=1";
			}
			if("0".equals(goodsColor.getInUse())){
				hql+=" and g.inUse=0";
			}
			String inUse=StringUtils.defaultIfEmpty(goodsColor.getInUse(), "1");
			
			//page1=vipService.getPage(hql, page, limit,"%"+user.getUserName()+"%","%"+user.getUserFullName()+"%");
			page1=goodsColorService.getPage(hql, page, limit,"%"+colorName+"%");
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
	}
	/**
	 * 
	* @Title: getGoodsClassInuse 
	* @Description: TODO(获取可用的颜色) 
	* @return     
	* @throws
	 */
	@RequestMapping("/getGoodsColorInuse")
	@ResponseBody
	public  List<GoodsColor> getGoodsClassInuse(){
		String hql="from GoodsColor g where g.inUse=1  ";
		List<GoodsColor> goodsColors=null;
		try {
			goodsColors=goodsColorService.getList(hql);
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return goodsColors;
	}

}
