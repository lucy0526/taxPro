package nsfw.role.dao;

import nsfw.role.entity.Role;
import core.dao.BaseDao;

public interface RoleDao extends BaseDao<Role> {

	public void deleteRoleprivilegeByRoleId(String roleId);
	
}
