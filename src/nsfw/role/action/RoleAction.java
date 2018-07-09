package nsfw.role.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import nsfw.role.entity.Role;
import nsfw.role.entity.RolePrivilege;
import nsfw.role.entity.RolePrivilegeId;
import nsfw.role.service.RoleService;
import nsfw.user.entity.User;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import core.action.BaseAction;
import core.constant.Constant;
import core.util.CodeUtil;
import core.util.QueryHelper;

public class RoleAction extends BaseAction {
	@Resource
	private RoleService roleService;
	
	private Role role;
	private String[] privilegeIds;
	private String strName;
	
	//列表页面
	public String listUI() throws Exception{
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		QueryHelper queryHelper = new QueryHelper(Role.class, "r");
		/*
		 * 搜索
		 */
		List<Object> parameters = new ArrayList<Object>();
		if(role != null){
			if(StringUtils.isNotBlank(role.getName())){
				
				//给搜索参数解码
				role.setName(CodeUtil.dealDecode(role.getName()));
				queryHelper.addCondition(" r.name like ?", "%" + role.getName() + "%");
			}
		}
		pageResult = roleService.getPageResult(queryHelper, getPageNo(), getPageSize());
		return "listUI";
	}
	
	//跳转到新增页面
	public String addUI(){
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		return "addUI";
	}
	//保存新增
	public String add(){
		try {
			if(role != null){
//				权限处理、保存
				if(privilegeIds != null){
					HashSet<RolePrivilege> set = new HashSet<RolePrivilege>();
					for(int i=0; i<privilegeIds.length; i++){
						set.add(new RolePrivilege(new RolePrivilegeId(role, privilegeIds[i])));
					}
					role.setRolePrivileges(set);
				}
				roleService.save(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "list";
	}

	//跳转到编辑页面
	public String editUI(){
		ActionContext.getContext().getContextMap().put("privilegeMap", Constant.PRIVILEGE_MAP);
		if(role != null && role.getRoleId() != null){
			strName = role.getName();
			role = roleService.findObjectBy_id(role.getRoleId());
//			权限回显
			if(role.getRolePrivileges() != null){
				privilegeIds = new String[role.getRolePrivileges().size()];
				int i = 0;
				for(RolePrivilege rp : role.getRolePrivileges()){
					privilegeIds[i++] = rp.getId().getCode();
				}
			}
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		try {
			if(role != null){
//				权限处理、保存
				if(privilegeIds != null){
					HashSet<RolePrivilege> set = new HashSet<RolePrivilege>();
					for(int i=0; i<privilegeIds.length; i++){
						set.add(new RolePrivilege(new RolePrivilegeId(role, privilegeIds[i])));
					}
					role.setRolePrivileges(set);
				}
				roleService.update(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}

	//删除
	public String delete(){
		if(role != null && role.getRoleId() != null){
			roleService.delete(role.getRoleId());
		}
		return "list";
	}

	public Role getrole() {
		return role;
	}

	public void setrole(Role role) {
		this.role = role;
	}

	public String[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(String[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}
	
}
