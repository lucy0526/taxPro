package login.action;

import java.util.List;

import javax.annotation.Resource;

import nsfw.user.entity.User;
import nsfw.user.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import core.constant.Constant;

public class LoginAction extends ActionSupport {
	@Resource
	private UserService userService;
	private User user;
	private String loginResult;
	//跳转到登陆页面
	public String toLoginUI() {
		return "loginUI";
	}
	//登陆
	public String login() {
		if(user != null){
			if(StringUtils.isNoneBlank(user.getAccount()) && StringUtils.isNoneBlank(user.getPassword())){
				//查询用户
				List<User> list = userService.findUserBy_AccountAndPass(user.getAccount(), user.getPassword());
				if(list != null && list.size() > 0){
					User user = list.get(0);
					//查询角色
					user.setUserRoles(userService.getUserRolesBy_userId(user.getId()));
					
					//保存到session
					ServletActionContext.getRequest().getSession().setAttribute(Constant.USER, user);
					Log log = LogFactory.getLog(getClass());
					log.info("用户名称为："+ user.getName()+" 的用户登陆了系统");
					return "home";
				}else{
					loginResult = "账号或密码不正确！";
				}
			}else{
				loginResult = "账号或密码不能为空！";
			}
		}else{
			loginResult = "请输入账号和密码！";
		}
		return toLoginUI();
	}
	public String logout() {
		ServletActionContext.getRequest().getSession().removeAttribute(Constant.USER);
		return toLoginUI();
	}
	
	//没有权限提示页面
	public String toNoPermissionUI() {
		return "noPermissionUI";
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	
}
