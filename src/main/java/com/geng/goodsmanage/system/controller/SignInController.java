package com.geng.goodsmanage.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geng.goodsmanage.system.model.Role;
import com.geng.goodsmanage.system.model.SignIn;
import com.geng.goodsmanage.system.model.TreeNode;
import com.geng.goodsmanage.system.model.User;
import com.geng.goodsmanage.system.service.SignInService;
import com.geng.goodsmanage.system.service.UserService;

/**
 * 
* @ClassName: SignInController 
* @Description: TODO(用户签到) 
* @author genghongchuang
* @date 2013年11月29日 上午10:30:31 
*
 */
@Controller
@RequestMapping("/system/signIn")
public class SignInController {
	protected Logger log = LoggerFactory.getLogger(SignInController.class); // 日志记录
	@Autowired
	UserService userService;
	@Autowired
	SignInService signInService;
	@RequestMapping("/toSignIn")
	@ResponseBody
	public Map<String, Object> signIn(User user){
		Map<String, Object> map = new HashMap<String, Object>();
		User u = userService.find(user);	
		try {
			if (null != u) {
				map.put("success", true);
				SignIn s=new SignIn();
				s.setUser(u);
				s.setInTime(new Date());
				String hql="from SignIn s where s.user.id=? and TO_DAYS(s.inTime)=TO_DAYS(NOW()) and s.outTime = null";
				List<SignIn> signIns=signInService.getList(hql, u.getId());
				if(null!=signIns&&signIns.size()>0){
					map.put("msg", u.getUserFullName()+",您已签到，当天不可重复签到!");
				}else{
					signInService.save(s);
					map.put("msg", u.getUserFullName()+",您已签到成功,祝一天上班好心情!");
				}
				
			} else {
				map.put("success", false);
				map.put("msg", "密码错误!");
			}
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
			map.put("success", false);
			map.put("msg", "出错了,请稍后重试!");
		}
		return map;
		
	}
	@RequestMapping("/toSignOut")
	@ResponseBody
	public Map<String, Object> signOut(User user){
		Map<String, Object> map = new HashMap<String, Object>();
		User u = userService.find(user);	
		try {
			if (null != u) {
				map.put("success", true);
				
				String hql="from SignIn s where s.user.id=? and TO_DAYS(s.inTime)=TO_DAYS(NOW()) and s.outTime = null";
				List<SignIn> signIns=signInService.getList(hql, u.getId());
				if(null!=signIns&&signIns.size()>0){
					SignIn s=signIns.get(0);
					s.setOutTime(new Date());
					boolean result=signInService.update(s);
					if(result==true){
						map.put("msg", u.getUserFullName()+",辛苦一天了,下班愉快!");
					}else{
						map.put("msg", u.getUserFullName()+",退出出错了，请与管理员联系");
					}
					
				}else{
					map.put("msg", u.getUserFullName()+",尚未签到!");
				}
				
			} else {
				map.put("success", false);
				map.put("msg", "密码错误!");
			}
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
			map.put("success", false);
			map.put("msg", "出错了,请稍后重试!");
		}
		return map;
		
	}
	@RequestMapping("/getSignIns")
	@ResponseBody
	public List<TreeNode> getSignIns(){
		List<TreeNode> treeNodes=new ArrayList<TreeNode>();
		String hql="from SignIn s where  TO_DAYS(s.inTime)=TO_DAYS(NOW()) and s.outTime = null";
		try {
			List<SignIn> signIns=signInService.getList(hql);
			
			if(null!=signIns){
				for(SignIn signIn:signIns){
					TreeNode treeNode=new TreeNode();
					treeNode.setId(signIn.getId());
					treeNode.setText(signIn.getUser().getUserFullName());
					treeNode.setIconCls("icon-status-online");
					treeNode.setPid(signIn.getUser().getId());
			        treeNodes.add(treeNode);
					
				}
			}
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return treeNodes;
	}
	@RequestMapping("/getAllUser")
	@ResponseBody
	public List<TreeNode> getAllUser(){
		List<TreeNode> treeNodes=new ArrayList<TreeNode>();
		String hql="from User";
		try {
			List<User> users=userService.getList(hql);
			
			if(null!=users){
				for(User user:users){
					TreeNode treeNode=new TreeNode();
					treeNode.setId(user.getId());
					treeNode.setText(user.getUserFullName());
					treeNode.setIconCls("icon-status-online");
					treeNode.setPid(user.getId());
			        treeNodes.add(treeNode);
					
				}
			}
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return treeNodes;
	}

}
