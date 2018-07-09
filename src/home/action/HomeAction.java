package home.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import nsfw.complain.entity.Complain;
import nsfw.complain.service.ComplainService;
import nsfw.info.entity.Info;
import nsfw.info.service.InfoService;
import nsfw.user.entity.User;
import nsfw.user.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import core.constant.Constant;
import core.util.QueryHelper;

public class HomeAction extends ActionSupport {
	//struts插件
	@Resource
	private UserService userService;
	@Resource
	private ComplainService complainService;
	@Resource
	private InfoService infoService;
	
	private List<User> userList;
	private Map<String, Object> return_map;
	private Complain comp;
	private Info info;
	
	public String execute() {
		//加载信息列表
		//加载分类列表
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		QueryHelper queryHelper1 = new QueryHelper(Info.class, "i");
		queryHelper1.addOrderByProperty("i.createTime", QueryHelper.ORDER_BY_DESC);
		List<Info> infoList = infoService.getPageResult(queryHelper1, 1, 5).getItems();
		ActionContext.getContext().getContextMap().put("infoList", infoList);
		
		User user = (User)ServletActionContext.getRequest().getSession().getAttribute(Constant.USER);
		//加载我要投诉列表
		//加载状态集合
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		QueryHelper queryHelper2 = new QueryHelper(Complain.class, "c");
		queryHelper2.addCondition("c.compName=?", user.getName());
//			排序
		queryHelper2.addOrderByProperty("c.compTime", QueryHelper.ORDER_BY_ASC);
		queryHelper2.addOrderByProperty("c.state", QueryHelper.ORDER_BY_DESC);
		
		List<Complain> complainList = complainService.getPageResult(queryHelper2, 1, 6).getItems();
		ActionContext.getContext().getContextMap().put("complainList", complainList);
		
		return "home";
	}
	//跳转到我要投诉
	public String complainAddUI() {
		
		return "complainAddUI";
	}
	/**方式一：
	 * 用输出json格式字符串方法
	 */
	public void getUserJson() {
		try {
			//获取部门
			String dept = ServletActionContext.getRequest().getParameter("dept");
			if(StringUtils.isNotBlank(dept)){
				QueryHelper queryHelper = new QueryHelper(User.class, "u");
				queryHelper.addCondition("u.dept like ?", "%"+dept);
				//查询用户列表
				List<User> userList = userService.findObjects(queryHelper);
				//以Json方式输入用户列表
				JSONObject jso = new JSONObject();
				jso.put("msg", "success");
				jso.accumulate("userList", userList);
//					输出
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(jso.toString().getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**方式二：
	 * 用truts插件框架
	 * @return
	 */
	public String getUserJson2() {
		try {
			//获取部门
			String dept = ServletActionContext.getRequest().getParameter("dept");
			if(StringUtils.isNotBlank(dept)){
				QueryHelper queryHelper = new QueryHelper(User.class, "u");
				queryHelper.addCondition("u.dept like ?", dept);
				//查询用户列表
				return_map = new HashMap<String, Object>();
				return_map.put("msg", "success");
				return_map.put("userList", userService.findObjects(queryHelper));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//保存投诉
	public void complainAdd() {
		try {
			if(comp != null){
				//设置投诉状态为待受理
				comp.setState(Complain.COMPLAIN_STATE_UNDONE);
				comp.setCompTime(new Timestamp(new Date().getTime()));
				complainService.save(comp);
				
				//输出投诉结果
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write("success".toString().getBytes("utf-8"));
				outputStream.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String infoViewUI() {
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		if(info != null){
			info = infoService.findObjectBy_id(info.getInfoId());
		}
		return "infoViewUI";
	}
	public String complainViewUI() {
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		if(comp != null){
			comp = complainService.findObjectBy_id(comp.getCompId());
		}
		return "complainViewUI";
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public Map<String, Object> getReturn_map() {
		return return_map;
	}
	public void setReturn_map(Map<String, Object> returnMap) {
		return_map = returnMap;
	}
	public Complain getComp() {
		return comp;
	}
	public void setComp(Complain comp) {
		this.comp = comp;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
}
