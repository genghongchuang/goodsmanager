package com.geng.goodsmanage.utils.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geng.goodsmanage.utils.base.dao.BaseDao;
import com.geng.goodsmanage.utils.base.service.BaseService;
import com.geng.goodsmanage.utils.pagination.Page;
@Repository("baseService")
public class BaseServiceImpl<T extends java.io.Serializable, PK extends java.io.Serializable> implements BaseService<T, PK> {
	@Autowired
	BaseDao<T, PK> baseDao;
	@Override
	public PK save(T model) throws Exception {
		
	   return baseDao.save(model);
		
		//return model;
	}
	@Override
	public List<T> list(String hql, int pn, int pageSize, Object... paramlist)
			throws Exception {
		
		return baseDao.list(hql, pn, pageSize, paramlist);
	}
	@Override
	public boolean update(T model) throws Exception {
		return baseDao.update(model);
	}
	@Override
	public T get(Class<?> clazz, PK pk) throws Exception {
		
		return baseDao.get(clazz, pk);
	}
	@Override
	public boolean delete(T model) throws Exception {
		//throw new RuntimeException();
		return baseDao.delete(model);
	}
	@Override
	public Page<T> getPage(String hql, int pn, int pageSize,
			Object... paramlist) throws Exception {
		List<T> items;
		long count;
		try {
			items = baseDao.list(hql, pn, pageSize, paramlist);
			count = baseDao.countAll(hql, paramlist);
		} catch (Exception e) {
			throw e;
		}
		Page<T> page= new Page<T>();
		page.setItems(items);
		page.setCount(count);
		return page;
	}
	@Override
	public boolean delete(String hql, Object... paramlist) throws Exception {
		
		return baseDao.delete(hql, paramlist);
	}
	@Override
	public List<T> getList(String hql) throws Exception {
		
		return baseDao.getAllList(hql);
	}
	@Override
	public List<T> getList(String hql, Object... paramlist) throws Exception {
		
		return baseDao.getAllList(hql, paramlist);
	}
	@Override
	public T load(Class<?> clazz, PK pk) throws Exception {

		return baseDao.load(clazz, pk);
	}
	@Override
	public T getSimpleResult(String hql, Object... paramlist) throws Exception {
		
		return baseDao.getSimpleResult(hql, paramlist);
	}
	

}
