package com.geng.goodsmanage.system.service;

import java.util.List;

import com.geng.goodsmanage.system.model.ResourceNode;



public interface ResourceService {

	public List<ResourceNode> getRoot() throws Exception;
	
	public List<ResourceNode> getChildren(Integer id) throws Exception;
}
