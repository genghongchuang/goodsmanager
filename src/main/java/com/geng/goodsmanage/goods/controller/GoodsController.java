package com.geng.goodsmanage.goods.controller;

import java.util.Date;
import java.util.HashMap;
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
import com.geng.goodsmanage.goods.service.GoodsClassService;
import com.geng.goodsmanage.goods.service.GoodsColorService;
import com.geng.goodsmanage.goods.service.GoodsService;
import com.geng.goodsmanage.goods.service.GoodsSizeService;
import com.geng.goodsmanage.utils.pagination.Page;
@RequestMapping("goods/goods")
@Controller
public class GoodsController {
	protected Logger log = LoggerFactory.getLogger(GoodsController.class); // 日志记录
	@Autowired
	GoodsService goodsService;
	@Autowired
	GoodsClassService goodsClassService;
	@Autowired
	GoodsColorService goodsColorService;
	@Autowired
	GoodsSizeService goodsSizeService;
    /**
     * 
    * @Title: goodsClassList 
    * @Description: TODO(跳转到货物分类管理) 
    * @return     
    * @throws
     */
	@RequestMapping("/goodsList")
	public ModelAndView goodsList(){
		ModelAndView mav=new ModelAndView("goods/goodsList");
		return mav;
	}
	/**
	 * 
	* @Title: addGoodsClass 
	* @Description: TODO(添加货物分类) 
	* @param goodsClass
	* @return     
	* @throws
	 */
	@RequestMapping(value="/addGoods",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addGoods(Goods goods,Integer classId,Integer colorId,Integer sizeId,String nameId){
		boolean result=false;
		try {
			GoodsClass goodsClass=goodsClassService.load(GoodsClass.class, classId);
			GoodsColor goodsColor=goodsColorService.load(GoodsColor.class, colorId);
			GoodsSize goodsSize=goodsSizeService.load(GoodsSize.class, sizeId);
			goods.setGoodsClass(goodsClass);
			goods.setGoodsColor(goodsColor);
			goods.setGoodsSize(goodsSize);
			int pk=goodsService.save(goods);
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
			map.put("nameId", nameId);
		}else{
			map.put("msg", "添加失败，请稍后重试");
		}
		
		return map;
		
	}
	/**
	 * 
	* @Title: updateGoodsClass 
	* @Description: TODO(更新货物分类) 
	* @param goodsClass
	* @return     
	* @throws
	 */
	@RequestMapping("/updateGoods")
	@ResponseBody
	public Map<String, Object> updateGoods(Goods goods){
		boolean result=false;
		try {
			//Goods oldGoods=goodsService.load(Goods.class, goods.getId());
			//oldGoods.setClassName(goodsClass.getClassName());;
			//oldClass.setInUse(goodsClass.getInUse());
			result=goodsService.update(goods);
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
	* @Title: betchDelGoodsClass 
	* @Description: TODO(批量删除货物分类) 
	* @param ids
	* @return     
	* @throws
	 */
	@RequestMapping("/betchDelGoods/{ids}")
	@ResponseBody
	public Map<String,Object> betchDelGoods(@PathVariable("ids")String ids){
		boolean result=false;
		boolean betchResult=true;
		try {
			String[] rIds=ids.split(",");
			String hql="delete Goods as g where g.id=?";
			for(String rId:rIds){
				if(betchResult){
					int rid=Integer.valueOf(rId);
					result=goodsService.delete(hql,rid);
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
			map.put("msg", "批量取消失败");
			return map;
		}else{
			map.put("success", true);
			map.put("msg", "批量取消成功");
			return map;
		}
		
		
	}
	/**
	 * 
	* @Title: getGoodsClass 
	* @Description: TODO(货物分类分页显示) 
	* @param goodsClass
	* @param start
	* @param limit
	* @param page
	* @return     
	* @throws
	 */
	@RequestMapping("/getGoods")
	@ResponseBody
	public  Page<Goods> getGoods(Goods goods,String classId,int start,int limit,int page){
		String hql="from Goods g where 1=1 and g.goodsName like ? and g.goodsNum like ? and g.brandName like ? ";
		Page<Goods> page1=null;
		try {
			String goodsName=StringUtils.defaultIfEmpty(goods.getGoodsName(),"");
			String goodsNum=StringUtils.defaultIfEmpty(goods.getGoodsNum(), "");
			String brandName=StringUtils.defaultIfEmpty(goods.getBrandName(), "");
			classId=StringUtils.defaultIfEmpty(classId, "");
			if(!"".equals(classId)){
				hql+=" and g.goodsClass.id="+classId;
			}
			page1=goodsService.getPage(hql, page, limit,"%"+goodsName+"%","%"+goodsNum+"%","%"+brandName+"%");
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
	}
	/**
	 * 
	* @Title: getGoodsForSell 
	* @Description: TODO(销售显示库存数量) 
	* @param goods
	* @param classId
	* @param start
	* @param limit
	* @param page
	* @return     
	* @throws
	 */
	@RequestMapping("/getGoodsForSell")
	@ResponseBody
	public  Page<Goods> getGoodsForSell(Goods goods,String classId,int start,int limit,int page){
		String hql="from Goods g where 1=1 and g.goodsName like ? and g.goodsNum like ? and g.brandName like ? and g.count>0";
		Page<Goods> page1=null;
		try {
			String goodsName=StringUtils.defaultIfEmpty(goods.getGoodsName(),"");
			String goodsNum=StringUtils.defaultIfEmpty(goods.getGoodsNum(), "");
			String brandName=StringUtils.defaultIfEmpty(goods.getBrandName(), "");
			classId=StringUtils.defaultIfEmpty(classId, "");
			if(!"".equals(classId)){
				hql+=" and g.goodsClass.id="+classId;
			}
			page1=goodsService.getPage(hql, page, limit,"%"+goodsName+"%","%"+goodsNum+"%","%"+brandName+"%");
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
	}

}
