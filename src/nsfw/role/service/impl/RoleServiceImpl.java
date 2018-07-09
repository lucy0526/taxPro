package nsfw.role.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import nsfw.role.dao.RoleDao;
import nsfw.role.entity.Role;
import nsfw.role.service.RoleService;

import org.springframework.stereotype.Service;

import core.service.impl.BaseServiceImpl;
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	private RoleDao roleDao;
	@Resource
	public void setRoleDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
		this.roleDao = roleDao;
	}
	public void update(Role role) {
//		删除记录，才能完全更新，否则保存原来记录，增加以前没有的
		roleDao.deleteRoleprivilegeByRoleId(role.getRoleId());
		roleDao.update(role);
	}

}
