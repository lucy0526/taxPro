package nsfw.user.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

import core.service.BaseService;

import nsfw.role.entity.UserRole;
import nsfw.user.entity.User;

public interface UserService extends BaseService<User> {
	
	//导出用户列表
	public void exportExcel(List<User> userList,
			ServletOutputStream outputStream);
	public void importExcel(File userExcel, String userExcelFileName);
	//根据账号和id查询用户
	public List<User> findUserBy_AccountAndId(String id, String account);
	public void saveUserAndRole(User user, String... roleIds);
	public void updateUserAndRole(User user, String... roleIds);
	public List<UserRole> getUserRolesBy_userId(String id);
	public List<User> findUserBy_AccountAndPass(String account, String password);
}
