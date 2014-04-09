package com.geng.goodsmanage.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.geng.goodsmanage.system.model.TreeNode;
import com.geng.goodsmanage.system.service.TreeNodeService;
/**
 * 
	 * 此类描述的是：菜单树的c
	 * @author: genghc
	 * @version: 2013年10月31日 下午1:05:14
 */
@Controller
@RequestMapping("/treeNode")
public class TreeNodeController {
	protected Logger log = LoggerFactory.getLogger(TreeNodeController.class); // 日志记录
	@Autowired
	TreeNodeService treeNodeService;
	/**
	 * 
	 * @Title: addNode
	 * @Description: TODO(添加节点)
	 * @param @param pid
	 * @param @return    设定文件
	 * @return ModelAndView    返回类型
	 * @throws
	 */
	@RequestMapping("/addNode")
	@ResponseBody
	public Map<String, Object> addNode(TreeNode treeNode){
		Map<String, Object> map = new HashMap<String, Object>();
		int pk=-1;
	        treeNode.setLeaf("1");
			try {
				TreeNode pNode=treeNodeService.get(TreeNode.class, treeNode.getPid());
				if("1".equals(pNode.getLeaf())){
					pNode.setLeaf("0");
					treeNodeService.update(pNode);//更新父节点为非叶子节点
				}
				pk=treeNodeService.save(treeNode);
			} catch (Exception e) {
				log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
			}
			if(pk>0){
				map.put("success", true);
				map.put("msg", "添加成功!");
			}else{
				map.put("success", false);
				map.put("msg", "添加失败!");
			}
		
		return map;
		
	}
	/**
	 * 
	 * @param treeNode
	 * @return
	 */
	@RequestMapping("/editNode")
	@ResponseBody
	public Map<String, Object> editNode(TreeNode treeNode){
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result=false;
			try {
				TreeNode node=treeNodeService.get(TreeNode.class, treeNode.getId());
				if(null!=node){
					node.setText(treeNode.getText());
					node.setIconCls(treeNode.getIconCls());
					node.setUrl(treeNode.getUrl());
					node.setUpdateTime(new Date());
					result=treeNodeService.update(node);
				}
				
			} catch (Exception e) {
				log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
			}
			if(result==true){
				map.put("success", true);
				map.put("msg", "编辑成功!");
			}else{
				map.put("success", false);
				map.put("msg", "编辑失败!");
			}
		
		return map;
		
	}
	@RequestMapping("/deleteNode")
	@ResponseBody
	public Map<String, Object> deleteNode(TreeNode treeNode){
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result=false;
			try {
				result=treeNodeService.deleteNode(treeNode);
				
			} catch (Exception e) {
				log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
				map.put("success", false);
				map.put("msg", e);
			}
			if(result==true){
				map.put("success", true);
				map.put("msg", "删除成功!");
			}else{
				map.put("success", false);
				map.put("msg", "删除失败!");
			}
		
		return map;
		
	}
	/**
	 * 
	 * @Title: root
	 * @Description: TODO(获取树节点)
	 * @param @return    设定文件
	 * @return JSON    返回类型
	 * @throws
	 */
	@RequestMapping("/root")
	@ResponseBody
	public List<TreeNode> root(){
		
		List<TreeNode> nodes=null;
		try {
			 nodes=treeNodeService.getRoot();
		} catch (Exception e) {
			log.error("className:"+e.getStackTrace()[0].getClassName()+"; methodName:"+e.getStackTrace()[0].getMethodName(),e);
		}
		return nodes;
		
	}

}
