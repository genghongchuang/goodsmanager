package com.geng.goodsmanage.utils.base.service;

import java.util.List;

import com.geng.goodsmanage.utils.pagination.Page;

public interface BaseService<T ,PK extends java.io.Serializable> {
	/**
	 * 
	* @Title: save 
	* @Description: TODO(保存) 
	* @param model
	* @return 主键
	* @throws Exception     
	* @throws
	 */
	public PK save(T model) throws Exception;
	/**
	 * 
	 * @Title: update
	 * @Description: TODO(更新一个bean)
	 * @param @param model
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean update(T model) throws Exception;
	/**
	 * 删除
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public boolean delete(T model) throws Exception;
	/**
	 * 
	* @Title: delete 
	* @Description: TODO(删除) 
	* @param hql
	* @param paramlist
	* @return
	* @throws Exception     
	* @throws
	 */
	public boolean delete(String hql,Object... paramlist) throws Exception;
	/**
	 * 
	 * @Title: get
	 * @Description: TODO(通过主键获取一个bean)
	 * @param @param model
	 * @param @param pk
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return T    返回类型
	 * @throws
	 */
	public T get(Class<?> clazz,PK pk) throws Exception;
	/**
	 * 
	* @Title: load 
	* @Description: TODO(在缓存里查找) 
	* @param clazz
	* @param pk
	* @return
	* @throws Exception     
	* @throws
	 */
	public T load(Class<?> clazz,PK pk) throws Exception;
	public List<T> list(final String hql, final int pn,
			final int pageSize, final Object... paramlist) throws Exception;
	/**
	 * 
	* @Title: getPage 
	* @Description: TODO(分页) 
	* @param @param hql
	* @param @param pn
	* @param @param pageSize
	* @param @param paramlist
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Page<T>    返回类型 
	* @throws
	 */
	public Page<T> getPage(final String hql,final int pn,
			final int pageSize, final Object... paramlist) throws Exception;
	/**
	 * 
	* @Title: getList 
	* @Description: TODO(根据hql查询所有的) 
	* @param hql
	* @return
	* @throws Exception     
	* @throws
	 */
    public List<T> getList(String hql) throws Exception;
    /**
     * 
    * @Title: getList 
    * @Description: TODO(根据查询条件获取所有) 
    * @param hql
    * @param paramlist
    * @return
    * @throws Exception     
    * @throws
     */
    public List<T> getList(String hql,Object... paramlist) throws Exception;
    /**
     * 
    * @Title: getSimpleResult 
    * @Description: TODO(根据查询条件获取一个结果) 
    * @param hql
    * @param paramlist
    * @return
    * @throws Exception     
    * @throws
     */
    public T getSimpleResult(String hql,Object... paramlist) throws Exception;
}
