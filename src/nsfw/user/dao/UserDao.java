package nsfw.user.dao;

import java.io.Serializable;
import java.util.List;

import nsfw.role.entity.UserRole;
import nsfw.user.entity.User;
import core.dao.BaseDao;

public interface UserDao extends BaseDao<User> {

	public List<User> findUserBy_AccountAndId(String id, String account);

	public void saveUserRole(UserRole userRole);

	public void deleteUserRoleByUserId(Serializable id);

	public List<UserRole> getUserRolesBy_userId(String id);

	public List<User> findUserBy_AccountAndPass(String account, String password);

}
