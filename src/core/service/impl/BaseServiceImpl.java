package core.service.impl;

import java.io.Serializable;
import java.util.List;

import nsfw.info.dao.InfoDao;
import nsfw.info.entity.Info;
import core.dao.BaseDao;
import core.page.PageResult;
import core.service.BaseService;
import core.util.QueryHelper;

public class BaseServiceImpl<T> implements BaseService<T> {
	private BaseDao<T> baseDao;
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}
	public void delete(Serializable id) {
		baseDao.delete(id);
	}

	public T findObjectBy_id(Serializable id) {
		return baseDao.findObjectBy_id(id);
	}

	public List<T> findObjects() {
		return baseDao.findObjects();
	}

	public void save(T entity) {
		baseDao.save(entity);
	}

	public void update(T entity) {
		baseDao.update(entity);
	}
	public List<T> findObjects(String hql, List<Object> parameters) {
		return baseDao.findObjects(hql, parameters);
	}
	public List<T> findObjects(QueryHelper queryHelper) {
		return baseDao.findObjects(queryHelper);
	}
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo,
			int pageSize) {
		return baseDao.getPageResult(queryHelper, pageNo, pageSize);
	}
}
