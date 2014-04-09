package com.geng.goodsmanage.utils.base.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.geng.goodsmanage.utils.Assert;
import com.geng.goodsmanage.utils.base.dao.BaseDao;
@Repository("baseDao")
public class BaseDaoImpl<T extends java.io.Serializable, PK extends java.io.Serializable>
		implements BaseDao<T, PK> {
	//private Class<T> entityClass;
	//private final String HQL_LIST_ALL;
	private String pkName = null;//主键
	@Autowired
	@Qualifier("sessionFactory")
	SessionFactory sessionFactory;
/*	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		 this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	        Field[] fields = this.entityClass.getDeclaredFields();
	        for(Field f : fields) {
	            if(f.isAnnotationPresent(Id.class)) {
	                this.pkName = f.getName();
	            }
	        }
	        
	        Assert.notNull(pkName);
	        HQL_LIST_ALL = "from " + this.entityClass.getSimpleName() + " order by " + pkName + " desc";
		
	}*/

	public Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PK save(T model) {

		return (PK) getSession().save(model);
	}

	/**
	 * 
	 * 此方法描述的是：
	 * 
	 * @author: genghc
	 * @version: 2013年10月21日 下午1:02:37
	 */
	protected  List<T> list(final String sql, final Object... paramlist) throws Exception{
		return list(sql, -1, -1, paramlist);
	}

	/**
	 * 
	 * 此方法描述的是：分页查询
	 * 
	 * @author: genghc
	 * @version: 2013年10月21日 上午10:51:29
	 */
	@SuppressWarnings("unchecked")
	public List<T> list(final String hql, final int pn,
			final int pageSize, final Object... paramlist) throws Exception{
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		
		if (pn > -1 && pageSize > -1) {
			query.setMaxResults(pageSize);
			int start = com.geng.goodsmanage.utils.pagination.PageUtil
					.getPageStart(pn, pageSize);
			if (start != 0) {
				System.out.println(start);
				query.setFirstResult(start);
			}
		}
		if (pn < 0) {
			query.setFirstResult(0);
		}
		List<T> results = query.list();
		return results;
	}

	protected void setParameters(Query query, Object[] paramlist) {
		if (paramlist != null) {
			for (int i = 0; i < paramlist.length; i++) {
				if (paramlist[i] instanceof Date) {
					// TODO Date类型的
					query.setTimestamp(i, (Date) paramlist[i]);
				} else {
					query.setParameter(i, paramlist[i]);
				}
			}
		}
	}
	/**
	 * 
	 * @Title: buildHQLByClass
	 * @Description: TODO(拼接hql)
	 * @param @param clazz
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String buildHQLByClass(Class<?> clazz) {
		return "from " + clazz.getSimpleName() + " t";
	}
	/**
	 * 
	 * @Title: getEntityClass
	 * @Description: TODO(获取实体类的class对象)
	 * @param @return    设定文件
	 * @return Class<T>    返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected String getEntityName() {
		return getEntityClass().getSimpleName();
	}

	@Override
	public boolean update(T model) throws Exception {
		boolean result=true;
		try {
			this.getSession().update(model);
		} catch (Exception e) {
			result=false;
			throw e;
			
		}
		return result;
	}

	@Override
	public T get(Class<?> clazz, PK pk) throws Exception {
		T model=(T) this.getSession().get( clazz, pk);
		return model;
	}

	@Override
	public boolean delete(T model) throws Exception {
		boolean result=true;
		try {
			this.getSession().delete(model);
		
		} catch (Exception e) {
			result=false;
			throw e;
		}
		return result;
	}

	@Override
	public long countAll(String hql, Object... paramlist) throws Exception {
		hql="select count(*) " +hql;
		long count = 0;
		try {
			Query query=getSession().createQuery(hql);
			setParameters(query, paramlist);
			count = (Long) query.uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return count;
	}

	@Override
	public boolean delete(final String hql,final Object... paramList) throws Exception {
		boolean result=true;
		try {
			Query query=getSession().createQuery(hql);
			setParameters(query, paramList);
			int pk=query.executeUpdate();
			if(pk<=0){
				result=false;
			}
		} catch (Exception e) {
			result=false;
			throw e;
		}
		return result;
	}

	@Override
	public List<T> getAllList(String hql) throws Exception {
		List<T> list=new ArrayList<T>(); 
		try {
			Query query=getSession().createQuery(hql);
			list=query.list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<T> getAllList(String hql, Object... paramlist) throws Exception {
		List<T> list=new ArrayList<T>(); 
		try {
			Query query=getSession().createQuery(hql);
			setParameters(query, paramlist);
			list=query.list();
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public T load(Class<?> clazz, PK pk) throws Exception {
		T model=(T) this.getSession().load( clazz, pk);
		return model;
	}

	@Override
	public T getSimpleResult(String hql, Object... paramlist) throws Exception {
		T result;
		try {
			Query query=getSession().createQuery(hql);
			setParameters(query, paramlist);
			result = (T) query.uniqueResult();
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

}
