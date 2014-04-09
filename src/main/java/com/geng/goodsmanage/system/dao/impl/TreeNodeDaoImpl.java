package com.geng.goodsmanage.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.geng.goodsmanage.system.dao.TreeNodeDao;
import com.geng.goodsmanage.system.model.TreeNode;
import com.geng.goodsmanage.utils.base.dao.impl.BaseDaoImpl;
@Repository("treeNodeDao")
public class TreeNodeDaoImpl  extends BaseDaoImpl<TreeNode, Integer> implements TreeNodeDao{

	@Override
	public List<TreeNode> getRoot() throws Exception {
		String hql="from TreeNode where pid=0";
		return list(hql);
	}
	

}
