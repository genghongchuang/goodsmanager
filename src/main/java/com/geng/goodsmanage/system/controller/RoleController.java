package com.geng.goodsmanage.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

















import com.geng.goodsmanage.system.model.Role;
import com.geng.goodsmanage.system.service.RoleService;
import com.geng.goodsmanage.utils.pagination.Page;
/**
 * 角色管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/role")
public class RoleController {
	protected Logger log = LoggerFactory.getLogger(RoleController.class); // 日志记录
	@Autowired
	RoleService roleService;
	/**
	 * 
	* @Title: roleList 
	* @Description: TODO(角色列表) 
	* @param     设定文件 
	* @return ModelAndView    返回类型 
	* @throws
	 */
	@RequestMapping("/roleList")
	public ModelAndView roleList(){
		ModelAndView mav=new ModelAndView("system/roleList");
		return mav;
	}
	/**
	 * 
	* @Title: getRoles 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param role
	* @param @return    设定文件 
	* @return List<Role>    返回类型 
	* @throws
	 */
	@RequestMapping("/getRoles")
	@ResponseBody
	public  Page<Role> getRoles(Role role,int start,int limit,int page){
		
		String hql="from Role r where r.roleName like ? ";
		Page<Role> page1=null;
		try {
			if(StringUtils.isEmpty(role.getRoleName())){
				role.setRoleName("");
			}
			page1=roleService.getPage(hql, page, limit,"%"+role.getRoleName()+"%");
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
	}
	/**
	 * 
	* @Title: getAllRoles 
	* @Description: TODO(获取所有的角色) 
	* @return     
	* @throws
	 */
	@RequestMapping("/getAllRoles")
	@ResponseBody
	public List<Role> getAllRoles(){
		String hql="from Role ";
		List<Role> roles = null;
		try {
			 roles=roleService.getList(hql);
	
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return roles;
	}
	/**
	 * 
	* @Title: addRole 
	* @Description: TODO(添加一个角色) 
	* @param @param role
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping("/addRole")
	@ResponseBody
	public Map<String, Object> addRole(Role role){
		boolean result=false;
		try {
			int pk=roleService.save(role);
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
	* @Title: updateRole 
	* @Description: TODO(更新角色) 
	* @param @param role
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping("/updateRole")
	@ResponseBody
	public Map<String, Object> updateRole(Role role){
		boolean result=false;
		try {
			result=roleService.update(role);
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
	* @Title: betchDelRole 
	* @Description: TODO(批量删除) 
	* @param @param ids
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping("/betchDelRole/{ids}")
	@ResponseBody
	public Map<String,Object> betchDelRole(@PathVariable("ids")String ids){
		boolean result=false;
		boolean betchResult=true;
		try {
			String[] rIds=ids.split(",");
			System.out.println(ids);
			String hql="delete Role as r where r.id=?";
			for(String rId:rIds){
				if(betchResult){
					int rid=Integer.valueOf(rId);
					result=roleService.delete(hql,rid);
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
