package nsfw.role.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;

import nsfw.role.dao.RoleDao;
import nsfw.role.entity.Role;
import core.dao.impl.BaseDaoImpl;

public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

	public void deleteRoleprivilegeByRoleId(String roleId) {
		Query query = getSession().createQuery("delete from RolePrivilege where id.role.roleId=?");
		query.setParameter(0, roleId);
		query.executeUpdate();
	}

}
