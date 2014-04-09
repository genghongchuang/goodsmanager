package com.geng.goodsmanage.utils.base.dao;

import java.util.List;
/**
 * 
	 * 此类描述的是：dao的基类
	 * @author: genghc
	 * @version: 2013年10月31日 下午1:16:28
 */
public interface BaseDao<T extends java.io.Serializable,PK extends java.io.Serializable> {
	
	/**
	 * 
		 * 此方法描述的是：添加返回主键
		 * @author: genghc
		 * @version: 2013年10月19日 下午9:26:10
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
	* @param paramList
	* @return
	* @throws Exception     
	* @throws
	 */
	public boolean delete(String hql,Object... paramList) throws Exception;
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
	/**
	 * 
	* @Title: getAllList 
	* @Description: TODO(根据条件获取所有的) 
	* @param hql
	* @return
	* @throws Exception     
	* @throws
	 */
	public List<T> getAllList(String hql) throws Exception;
	/**
	 * 
	* @Title: getAllList 
	* @Description: TODO(根据条件获取所有的) 
	* @param hql
	* @param paramlist
	* @return
	* @throws Exception     
	* @throws
	 */
	public List<T> getAllList(String hql,Object...paramlist ) throws Exception;
	/**
	 * 
	 * @Title: list
	 * @Description: TODO(分页查询)
	 * @param @param hql
	 * @param @param pn
	 * @param @param pageSize
	 * @param @param paramlist
	 * @param @return
	 * @param @throws Exception    设定文件
	 * @return List<T>    返回类型
	 * @throws
	 */
	public List<T> list(final String hql, final int pn,
			final int pageSize, final Object... paramlist) throws Exception;
	/**
	 * 
	* @Title: countAll 
	* @Description: TODO(获取分页获取查询总数) 
	* @param @param hql
	* @param @param paramlist
	* @param @return
	* @param @throws Exception    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public long countAll(final String hql, final Object... paramlist) throws Exception;
	/**
	 * 
	* @Title: getSimpleResult 
	* @Description: TODO(根据条件获取一个查询结果) 
	* @param hql
	* @param paramlist
	* @return
	* @throws Exception     
	* @throws
	 */
	public T getSimpleResult(String hql,Object... paramlist) throws Exception;
	

}
