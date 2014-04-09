package com.geng.goodsmanage.system.dao;

import java.util.List;

import com.geng.goodsmanage.system.model.TreeNode;
import com.geng.goodsmanage.utils.base.dao.BaseDao;

public interface TreeNodeDao extends BaseDao<TreeNode, Integer> {
	public List<TreeNode> getRoot() throws Exception;

}
