package core.service;

import java.io.Serializable;
import java.util.List;

import nsfw.info.entity.Info;
import core.page.PageResult;
import core.util.QueryHelper;

public interface BaseService<T> {
	//增
	public void save(T entity);
	//更新
	public void update(T entity);
	//根据id删除
	public void delete(Serializable id);
	//根据id查找
	public T findObjectBy_id(Serializable id);
	//查找列表
	public List<T> findObjects();
	@Deprecated
	public List<T> findObjects(String hql, List<Object> parameters);
	public List<T> findObjects(QueryHelper queryHelper);
	public PageResult getPageResult(QueryHelper queryHelper, int pageNo, int pageSize);
}
