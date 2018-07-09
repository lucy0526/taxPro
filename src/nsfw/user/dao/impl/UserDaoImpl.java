package nsfw.user.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import nsfw.role.entity.UserRole;
import nsfw.user.dao.UserDao;
import nsfw.user.entity.User;
import core.dao.impl.BaseDaoImpl;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public List<User> findUserBy_AccountAndId(String id, String account) {
		//这里的user是指User类
		String sql = "from User where account=?";
		if(StringUtils.isNoneBlank(id)){
			sql += " and id!=?";
		}
		Query query = getSession().createQuery(sql);
		query.setParameter(0, account);
		if(StringUtils.isNoneBlank(id)){
			query.setParameter(1, id);
		}
		return query.list();
	}

	public void deleteUserRoleByUserId(Serializable id) {
		Query query = getSession().createQuery("delete from UserRole where id.userId=?");
		query.setParameter(0, id);
		query.executeUpdate();
	}

	public void saveUserRole(UserRole userRole) {
		getHibernateTemplate().save(userRole);
	}

	public List<UserRole> getUserRolesBy_userId(String id) {
		Query query = getSession().createQuery("from UserRole where id.userId=?");
		query.setParameter(0, id);
		return query.list();
	}

	public List<User> findUserBy_AccountAndPass(String account, String password) {
		Query query = getSession().createQuery("from User where account=? and password=?");
		query.setParameter(0, account);
		query.setParameter(1, password);
		return query.list();
	}
	
}
