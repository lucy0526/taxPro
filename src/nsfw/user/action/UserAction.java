package nsfw.user.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import nsfw.info.entity.Info;
import nsfw.role.entity.UserRole;
import nsfw.role.service.RoleService;
import nsfw.user.entity.User;
import nsfw.user.service.UserService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import core.action.BaseAction;
import core.util.CodeUtil;
import core.util.QueryHelper;

public class UserAction extends BaseAction {
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	private User user;
	private File headImage;
	private String headImageContentType;
	private String headImageFileName;
	private File userExcel;
	private String userExcelContentType;
	private String userExcelFileName;
	private String[] userRoleIds;
	private String strName;
	
	//列表页面
	public String listUI() throws Exception{
		QueryHelper queryHelper = new QueryHelper(User.class, "u");
		/*
		 * 搜索
		 */
		List<Object> parameters = new ArrayList<Object>();
		if(user != null){
			if(StringUtils.isNotBlank(user.getName())){
				
				//给搜索参数解码
				user.setName(CodeUtil.dealDecode(user.getName()));
				queryHelper.addCondition(" u.name like ?", "%" + user.getName() + "%");
			}
		}
		pageResult = userService.getPageResult(queryHelper, getPageNo(), getPageSize());
		return "listUI";
	}
	
	//跳转到新增页面
	public String addUI(){
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		return "addUI";
	}
	//保存新增
	public String add(){
		
		try {
			if(user != null){
				//处理头像
				if(headImage != null){
//				1. 保存到upload/user
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString().replaceAll("-", "")+headImageFileName.substring(headImageFileName.lastIndexOf("."));
//					复制文件
					FileUtils.copyFile(headImage, new File(filePath, fileName));
//				2. 设置用户头像路径到数据库
					user.setHeadImage("user/"+fileName);
				}
				userService.saveUserAndRole(user, userRoleIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "list";
	}

	//跳转到编辑页面
	public String editUI(){
		ActionContext.getContext().getContextMap().put("roleList", roleService.findObjects());
		if(user != null && user.getId() != null){
			strName = user.getName();
			user = userService.findObjectBy_id(user.getId());
			//角色回显
			List<UserRole> list = userService.getUserRolesBy_userId(user.getId());
			if(list != null && list.size() >= 0){
				userRoleIds = new String[list.size()];
				for(int i=0; i<list.size(); i++){
					userRoleIds[i] = list.get(i).getId().getRole().getRoleId();
					
				}
			}
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		try {
			if(user != null){
				if(headImage != null){
					String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
					String fileName = UUID.randomUUID().toString().replaceAll("-", "")+headImageFileName.substring(headImageFileName.lastIndexOf("."));
					FileUtils.copyFile(headImage, new File(filePath, fileName));
					user.setHeadImage("user/"+fileName);
				}
				userService.updateUserAndRole(user, userRoleIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}

	//删除
	public String delete(){
		if(user != null && user.getId() != null){
			userService.delete(user.getId());
		}
		return "list";
	}
	//批量删除
	public String deleteSeleted(){
		if(selectedRow != null){
			for(String id : selectedRow){
				userService.delete(id);
			}
		}
		return "list";
	}

	/**
	 *导出列表 
	 */
	public void exportExcel(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-excel");
			response.setHeader("Content-Disposition", "attachment;filename="+new String("用户列表.xls".getBytes(), "ISO-8859-1"));
			ServletOutputStream outputStream = response.getOutputStream();
			
//			2、导出
			userService.exportExcel(userService.findObjects(), outputStream);
			
			if(outputStream != null){
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public String importExcel() {
		if(userExcel != null){
			if(userExcelFileName.matches("^.+\\.(?i)((xls|xlsx))$")){
//				导入
				
				userService.importExcel(userExcel, userExcelFileName);
			}
		}
		return "list";
	}
	
	public void verifyAccount() {
		
try {
			//		1、获取帐号
			if(user != null && StringUtils.isNotBlank(user.getAccount())){
//		2、校验
				List<User> list = userService.findUserBy_AccountAndId(user.getId(), user.getAccount());
				String strResult = "true";
				if(list != null && list.size() > 0){
					strResult = "false";
				}
				
//		3、输出
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(strResult.getBytes());
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File getHeadImage() {
		return headImage;
	}

	public void setHeadImage(File headImage) {
		this.headImage = headImage;
	}

	public String getHeadImageContentType() {
		return headImageContentType;
	}

	public void setHeadImageContentType(String headImageContentType) {
		this.headImageContentType = headImageContentType;
	}

	public String getHeadImageFileName() {
		return headImageFileName;
	}

	public void setHeadImageFileName(String headImageFileName) {
		this.headImageFileName = headImageFileName;
	}

	public File getUserExcel() {
		return userExcel;
	}

	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}

	public String getUserExcelContentType() {
		return userExcelContentType;
	}

	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}

	public String getUserExcelFileName() {
		return userExcelFileName;
	}

	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}

	public String[] getUserRoleIds() {
		return userRoleIds;
	}

	public void setUserRoleIds(String[] userRoleIds) {
		this.userRoleIds = userRoleIds;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}
	
}
