package com.geng.goodsmanage.system.service;

import java.util.List;

import com.geng.goodsmanage.system.model.TreeNode;
import com.geng.goodsmanage.utils.base.service.BaseService;

public interface TreeNodeService extends BaseService<TreeNode, Integer> {
	public List<TreeNode> getRoot() throws Exception;
	public boolean deleteNode(TreeNode model) throws Exception;

}
