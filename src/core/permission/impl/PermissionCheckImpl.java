package core.permission.impl;

import java.util.List;

import javax.annotation.Resource;

import nsfw.role.entity.Role;
import nsfw.role.entity.RolePrivilege;
import nsfw.role.entity.UserRole;
import nsfw.user.entity.User;
import nsfw.user.service.UserService;
import core.permission.PermissionCheck;

public class PermissionCheckImpl implements PermissionCheck {
	@Resource
	private UserService userService;
	
	public boolean isAccessible(User user, String code) {
		List<UserRole> list = user.getUserRoles();
		if(list == null){
			list = userService.getUserRolesBy_userId(user.getId());
		}
		if(list != null && list.size() > 0){
			for(UserRole ur : list){
				Role role = ur.getId().getRole();
				for(RolePrivilege rp : role.getRolePrivileges()){
					//每个角色的权限
					if(code.equals(rp.getId().getCode())){
						return true;
					}
				}
			}
		}
		return false;
	}
}
