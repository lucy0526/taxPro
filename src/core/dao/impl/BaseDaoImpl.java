package core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import core.dao.BaseDao;
import core.page.PageResult;
import core.util.QueryHelper;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements
		BaseDao<T> {
	//反射获得具体类
	Class<T> clazz;
	public BaseDaoImpl(){
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();//获取BaseDaoImpl<User>
		clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}
	public void delete(Serializable id) {
		getHibernateTemplate().delete(findObjectBy_id(id));
	}
	
	/**
	 * 查找
	 */
	public T findObjectBy_id(Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}
	public List<T> findObjects() {
		Query query = getSession().createQuery("from "+clazz.getSimpleName());
		return query.list();
	}
	public List<T> findObjects(String hql, List<Object> parameters) {
		Query query = getSession().createQuery(hql);
		if(parameters != null){
			for(int i=0; i<parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	public List<T> findObjects(QueryHelper queryHelper){
		Query query = getSession().createQuery(queryHelper.getQueryListHql());
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i=0; i<parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize){
		//指定数据
		Query query = getSession().createQuery(queryHelper.getQueryListHql());
		/*
		 * 设置查询的参数！！
		 */
		List<Object> parameters = queryHelper.getParameters();
		if(parameters != null){
			for(int i=0; i<parameters.size(); i++){
				query.setParameter(i, parameters.get(i));
			}
		}
		
		if(pageNo < 1) pageNo = 1;
		query.setFirstResult((pageNo-1)*pageSize);//起始索引号
		query.setMaxResults(pageSize);
		
		List<Object> items = query.list();
		
		//总记录数
		Query queryCount = getSession().createQuery(queryHelper.getQueryCountHql());
		if(parameters != null){
			for(int i=0; i<parameters.size(); i++){
				queryCount.setParameter(i, parameters.get(i));
			}
		}
		long totalCount = (Long)queryCount.uniqueResult();//The Long class wraps a value of the primitive type long in an object不能是long，不然报错 
		
		
		return new PageResult(totalCount, pageNo, pageSize, items);
	}

	
	
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	public void update(T entity) {
//		getHibernateTemplate().update(entity);
		getHibernateTemplate().merge(entity);
	}

}
