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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.geng.goodsmanage.goods.model.GoodsClass;
import com.geng.goodsmanage.goods.service.GoodsClassService;
import com.geng.goodsmanage.utils.pagination.Page;
@RequestMapping("goods/goodsClass")
@Controller
public class GoodsClassController {
	protected Logger log = LoggerFactory.getLogger(GoodsClassController.class); // 日志记录
	@Autowired
	GoodsClassService goodsClassService;
    /**
     * 
    * @Title: goodsClassList 
    * @Description: TODO(跳转到货物分类管理) 
    * @return     
    * @throws
     */
	@RequestMapping("/goodsClassList")
	public ModelAndView goodsClassList(){
		ModelAndView mav=new ModelAndView("goods/goodsClassList");
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
	@RequestMapping("/addGoodsClass")
	@ResponseBody
	public Map<String, Object> addGoodsClass(GoodsClass goodsClass){
		boolean result=false;
		try {
			
			int pk=goodsClassService.save(goodsClass);
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
	* @Title: updateGoodsClass 
	* @Description: TODO(更新货物分类) 
	* @param goodsClass
	* @return     
	* @throws
	 */
	@RequestMapping("/updateGoodsClass")
	@ResponseBody
	public Map<String, Object> updateGoodsClass(GoodsClass goodsClass){
		boolean result=false;
		try {
			GoodsClass oldClass=goodsClassService.load(GoodsClass.class, goodsClass.getId());
			oldClass.setClassName(goodsClass.getClassName());;
			oldClass.setInUse(goodsClass.getInUse());
			result=goodsClassService.update(oldClass);
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
	@RequestMapping("/betchDelGoodsClass/{ids}")
	@ResponseBody
	public Map<String,Object> betchDelGoodsClass(@PathVariable("ids")String ids){
		boolean result=false;
		boolean betchResult=true;
		try {
			String[] rIds=ids.split(",");
			String hql="delete GoodsClass as g where g.id=?";
			for(String rId:rIds){
				if(betchResult){
					int rid=Integer.valueOf(rId);
					result=goodsClassService.delete(hql,rid);
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
	* @Title: getGoodsClass 
	* @Description: TODO(货物分类分页显示) 
	* @param goodsClass
	* @param start
	* @param limit
	* @param page
	* @return     
	* @throws
	 */
	@RequestMapping("/getGoodsClass")
	@ResponseBody
	public  Page<GoodsClass> getGoodsClass(GoodsClass goodsClass,int start,int limit,int page){
		
		//String hql="from Vip v where u.userName like ? and u.userFullName like ?";
		String hql="from GoodsClass g where g.className like ?  ";
		Page<GoodsClass> page1=null;
		try {
			String className=StringUtils.defaultIfEmpty(goodsClass.getClassName(),"");
			if("1".equals(goodsClass.getInUse())){
				hql+=" and g.inUse=1";
			}
			if("0".equals(goodsClass.getInUse())){
				hql+=" and g.inUse=0";
			}
			String inUse=StringUtils.defaultIfEmpty(goodsClass.getInUse(), "1");
			
			//page1=vipService.getPage(hql, page, limit,"%"+user.getUserName()+"%","%"+user.getUserFullName()+"%");
			page1=goodsClassService.getPage(hql, page, limit,"%"+className+"%");
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
	}
	/**
	 * 
	* @Title: getGoodsClassInuse 
	* @Description: TODO(返回可用的分类) 
	* @return     
	* @throws
	 */
	@RequestMapping("/getGoodsClassInuse/{inUse}")
	@ResponseBody
	public  List<GoodsClass> getGoodsClassInuse(@PathVariable(value="inUse")String inUser){
		inUser=StringUtils.defaultIfEmpty(inUser, "1");
		String hql;
		if("1".equals(inUser)){
			 hql="from GoodsClass g where g.inUse="+inUser;
		}else{
			 hql="from GoodsClass g ";
		}
		
		List<GoodsClass> goodsClasses=null;
		try {
			goodsClasses=goodsClassService.getList(hql);
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return goodsClasses;
	}

}
