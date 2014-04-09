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

import com.geng.goodsmanage.goods.model.GoodsColor;
import com.geng.goodsmanage.goods.model.GoodsSize;
import com.geng.goodsmanage.goods.service.GoodsColorService;
import com.geng.goodsmanage.goods.service.GoodsSizeService;
import com.geng.goodsmanage.utils.pagination.Page;
@RequestMapping("goods/goodsSize")
@Controller
public class GoodsSizeController {
	protected Logger log = LoggerFactory.getLogger(GoodsSizeController.class); // 日志记录
	@Autowired
	GoodsSizeService goodsSizeService;
    /**
     * 
    * @Title: goodsSizeList 
    * @Description: TODO(货物尺码管理) 
    * @return     
    * @throws
     */
	@RequestMapping("/goodsSizeList")
	public ModelAndView goodsSizeList(){
		ModelAndView mav=new ModelAndView("goods/goodsSizeList");
		return mav;
	}
	/**
	 * 
	* @Title: addGoodsSize 
	* @Description: TODO(添加货物尺码) 
	* @param goodsSize
	* @return     
	* @throws
	 */
	@RequestMapping("/addGoodsSize")
	@ResponseBody
	public Map<String, Object> addGoodsSize(GoodsSize goodsSize){
		boolean result=false;
		try {
			
			int pk=goodsSizeService.save(goodsSize);
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
	* @Title: updateGoodsSize 
	* @Description: TODO(更新货物尺码) 
	* @param goodsSize
	* @return     
	* @throws
	 */
	@RequestMapping("/updateGoodsSize")
	@ResponseBody
	public Map<String, Object> updateGoodsSize(GoodsSize goodsSize){
		boolean result=false;
		try {
			GoodsSize oldSize=goodsSizeService.load(GoodsSize.class, goodsSize.getId());
			oldSize.setSize(goodsSize.getSize());
			oldSize.setInUse(goodsSize.getInUse());
			result=goodsSizeService.update(oldSize);
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
	* @Title: betchDelGoodsSize 
	* @Description: TODO(批量删除货物尺码) 
	* @param ids
	* @return     
	* @throws
	 */
	@RequestMapping("/betchDelGoodsSize/{ids}")
	@ResponseBody
	public Map<String,Object> betchDelGoodsSize(@PathVariable("ids")String ids){
		boolean result=false;
		boolean betchResult=true;
		try {
			String[] rIds=ids.split(",");
			String hql="delete GoodsSize as g where g.id=?";
			for(String rId:rIds){
				if(betchResult){
					int rid=Integer.valueOf(rId);
					result=goodsSizeService.delete(hql,rid);
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
	* @Title: getGoodsSize 
	* @Description: TODO(货物尺码分页显示) 
	* @param goodsSize
	* @param start
	* @param limit
	* @param page
	* @return     
	* @throws
	 */
	@RequestMapping("/getGoodsSize")
	@ResponseBody
	public  Page<GoodsSize> getGoodsSize(GoodsSize goodsSize,int start,int limit,int page){
		
		//String hql="from Vip v where u.userName like ? and u.userFullName like ?";
		String hql="from GoodsSize g where g.size like ?  ";
		Page<GoodsSize> page1=null;
		try {
			String size=StringUtils.defaultIfEmpty(goodsSize.getSize(),"");
			if("1".equals(goodsSize.getInUse())){
				hql+=" and g.inUse=1";
			}
			if("0".equals(goodsSize.getInUse())){
				hql+=" and g.inUse=0";
			}
			
			
			//page1=vipService.getPage(hql, page, limit,"%"+user.getUserName()+"%","%"+user.getUserFullName()+"%");
			page1=goodsSizeService.getPage(hql, page, limit,"%"+size+"%");
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
	}
	/**
	 * 
	* @Title: getGoodsSizeInuse 
	* @Description: TODO(获取可用的尺码) 
	* @return     
	* @throws
	 */
	@RequestMapping("/getGoodsSizeInuse")
	@ResponseBody
	public  List<GoodsSize> getGoodsSizeInuse(){
		String hql="from GoodsSize g where g.inUse=1  ";
		List<GoodsSize> goodsSizes=null;
		try {
			goodsSizes=goodsSizeService.getList(hql);
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return goodsSizes;
	}


}
