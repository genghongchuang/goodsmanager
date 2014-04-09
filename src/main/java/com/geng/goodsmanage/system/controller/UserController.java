package com.geng.goodsmanage.system.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.geng.goodsmanage.system.model.Role;
import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.system.service.RoleService;
import com.geng.goodsmanage.system.service.UserService;
import com.geng.goodsmanage.utils.pagination.Page;

/**
 * 
	 * 此类描述的是：用户管理的控制类
	 * @author: genghc
	 * @version: 2013年10月3日 下午7:47:55
 */
@Controller
@RequestMapping("/system/user")
public class UserController {
	protected Logger log = LoggerFactory.getLogger(UserController.class); // 日志记录
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	/**
	 * 
	* @Title: userList 
	* @Description: TODO(跳转用户管理) 
	* @param user
	* @return     
	* @throws
	 */
	@RequestMapping("/userList")
	public ModelAndView userList(User user){
		ModelAndView mav=new ModelAndView("system/userList");
		return mav;
	}
	/**
	 * 
	* @Title: getUsers 
	* @Description: TODO(分页显示用户列表) 
	* @param user
	* @param start
	* @param limit
	* @param page
	* @return     
	* @throws
	 */
	@RequestMapping("/getUsers")
	@ResponseBody
	public  Page<User> getUsers(User user,int start,int limit,int page){
		
		String hql="from User u where u.userName like ? and u.userFullName like ?";
		Page<User> page1=null;
		try {
			if(StringUtils.isEmpty(user.getUserName())){
				user.setUserName("");
			}
			if(StringUtils.isEmpty(user.getUserFullName())){
				user.setUserFullName("");
			}

			page1=userService.getPage(hql, page, limit,"%"+user.getUserName()+"%","%"+user.getUserFullName()+"%");
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return page1;
	}
	/**
	 * 
	* @Title: getUserRoles 
	* @Description: TODO(获取用户的角色属性) 
	* @param id
	* @return     
	* @throws
	 */
	@RequestMapping("/getUserRoles/{id}")
	@ResponseBody
	public Set<Role> getUserRoles(@PathVariable("id") int id){
		Set<Role> roles=new HashSet<Role>();
		try {
			User u=userService.get(User.class, id);
			roles=u.getRoles();
			
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return roles;
	}
	/***
	 * 
	* @Title: addUser 
	* @Description: TODO(添加用户) 
	* @param user
	* @return     
	* @throws
	 */
	@RequestMapping("/addUser")
	@ResponseBody
	public Map<String, Object> addUser(User user){
		boolean result=false;
		try {
			int pk=userService.save(user);
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
	* @Description: TODO(更新一个用户) 
	* @param user
	* @return     
	* @throws
	 */
	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String, Object> updateRole(User user){
		boolean result=false;
		try {
			result=userService.update(user);
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
	* @Title: updateUserRoles 
	* @Description: TODO(更新用户角色) 
	* @param uid
	* @param rids
	* @return     
	* @throws
	 */
	@RequestMapping("/updateUserRoles/{id}")
	@ResponseBody
	public Map<String,Object> updateUserRoles(@PathVariable("id")int uid,String rids){
		boolean result=false;
		try {
			String[] rIds=rids.split(",");
			User u=userService.get(User.class, uid);
			Set<Role> roles=new HashSet<Role>();
			for(String rId:rIds){
				int rid=Integer.valueOf(rId);
				Role role=roleService.get(Role.class, rid);
				roles.add(role);				
			}
			if(roles.size()>0){
				u.setRoles(roles);
			}
			result=userService.update(u);
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("success", result);
		if(result){
			map.put("msg", "设置成功");
		}else{
			map.put("msg", "设置失败，请稍后重试");
		}
		return map;
	}
	/**
	 * 
	* @Title: betchDelRole 
	* @Description: TODO(批量删除用户) 
	* @param ids
	* @return     
	* @throws
	 */
	@RequestMapping("/betchDelUser/{ids}")
	@ResponseBody
	public Map<String,Object> betchDelRole(@PathVariable("ids")String ids){
		boolean result=false;
		boolean betchResult=true;
		try {
			String[] rIds=ids.split(",");
			//System.out.println(ids);
			String hql="delete User as u where u.id=?";
			for(String rId:rIds){
				if(betchResult){
					int rid=Integer.valueOf(rId);
					result=userService.delete(hql,rid);
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
