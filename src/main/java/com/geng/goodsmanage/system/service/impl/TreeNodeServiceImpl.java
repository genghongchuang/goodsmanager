package com.geng.goodsmanage.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geng.goodsmanage.system.dao.TreeNodeDao;
import com.geng.goodsmanage.system.model.TreeNode;
import com.geng.goodsmanage.system.service.TreeNodeService;
import com.geng.goodsmanage.utils.base.service.impl.BaseServiceImpl;
@Service("treeNodeService")
public class TreeNodeServiceImpl extends BaseServiceImpl<TreeNode, Integer> implements TreeNodeService{
	@Autowired
	TreeNodeDao treeNodeDao;
	@Override
	public List<TreeNode> getRoot() throws Exception {
		
		return treeNodeDao.getRoot();
	}
	@Override
	public boolean deleteNode(TreeNode treeNode) throws Exception {
		   boolean result=true;
	
			TreeNode node=this.get(TreeNode.class, treeNode.getId());
			if(null!=node){
				int pid=node.getPid();
				TreeNode pNode=this.get(TreeNode.class, pid);
				if(null!=pNode&&pNode.getChildren().size()==1){
					pNode.setLeaf("1");
					result=this.update(pNode);
				}
				if(result){
					result=this.delete(node);
					
				}
		
	        }
			return result;

}
}
