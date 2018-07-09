package nsfw.user.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import nsfw.role.entity.Role;
import nsfw.role.entity.UserRole;
import nsfw.role.entity.UserRoleId;
import nsfw.user.dao.UserDao;
import nsfw.user.entity.User;
import nsfw.user.service.UserService;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import core.service.impl.BaseServiceImpl;
import core.util.ExcelUtil;

@Service("userService")//action使用
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	
	private UserDao userDao;
	@Resource
	public void setUserDao(UserDao userDao) {
		super.setBaseDao(userDao);
		this.userDao = userDao;
	}
	
	public void delete(Serializable id) {
		userDao.delete(id);
		//删除用户对应的所有权限，不然删了用户后 找不到对应的权限表，权限表已经就无法删除对应用户的数据，并永久保存了
		userDao.deleteUserRoleByUserId(id);
	}
	public void exportExcel(List<User> userList,
			ServletOutputStream outputStream) {
		ExcelUtil.exportUserExcel(userList, outputStream);
	}

	public void importExcel(File userExcel, String userExcelFileName) {
		ExcelUtil.importUserExcel(userExcel, userExcelFileName);
	}

	public List<User> findUserBy_AccountAndId(String id, String account) {
		return userDao.findUserBy_AccountAndId(id, account);
	}

	public void saveUserAndRole(User user, String... roleIds) {
		//1. 保存用户
		save(user);
		//2. 保存用户的角色
		if(roleIds != null){
			for(String roleId : roleIds){
				userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId), user.getId())));//经过保存，hibernate有回填属性
			}
		}
	}

	public void updateUserAndRole(User user, String... roleIds) {
//		1. 根据用户删除历史记录
		userDao.deleteUserRoleByUserId(user.getId());
//		2.更新用户
		update(user);
		if(roleIds != null){
			for(String roleId : roleIds){
				userDao.saveUserRole(new UserRole(new UserRoleId(new Role(roleId), user.getId())));//经过保存，hibernate有回填属性
			}
		}
	}

	public List<UserRole> getUserRolesBy_userId(String id) {
		return userDao.getUserRolesBy_userId(id);
	}

	public List<User> findUserBy_AccountAndPass(String account, String password) {
		return userDao.findUserBy_AccountAndPass(account, password);
	}

	
	
}
