package nsfw.info.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import nsfw.info.entity.Info;
import nsfw.info.service.InfoService;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import core.action.BaseAction;
import core.page.PageResult;
import core.util.CodeUtil;
import core.util.QueryHelper;

public class InfoAction extends BaseAction {
	@Resource
	private InfoService infoService;
	private Info info;
	private String strTitle;
	
	
	//列表页面
	public String listUI() throws Exception{
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		QueryHelper queryHelper = new QueryHelper(Info.class, "i");
		/*
		 * 搜索
		 */
		List<Object> parameters = new ArrayList<Object>();
		if(info != null){
			if(StringUtils.isNotBlank(info.getTitle())){
				
				//给搜索参数解码
				info.setTitle(CodeUtil.dealDecode(info.getTitle()));
				queryHelper.addCondition(" i.title like ?", "%" + info.getTitle() + "%");
			}
		}
//		queryHelper.addCondition("i.state=?", "1");
		queryHelper.addOrderByProperty("i.createTime", queryHelper.ORDER_BY_DESC);
		
//		infoList = infoService.findObjects(queryHelper);
		pageResult = infoService.getPageResult(queryHelper, getPageNo(), getPageSize());
		return "listUI";
	}
	
	//跳转到新增页面
	public String addUI(){
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		strTitle = info.getTitle();
		info = new Info();
		info.setCreateTime(new Timestamp(new Date().getTime()));
		return "addUI";
	}
	//保存新增
	public String add(){
		try {
			if(info != null){
				infoService.save(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "list";
	}

	//跳转到编辑页面
	public String editUI(){
		ActionContext.getContext().getContextMap().put("infoTypeMap", Info.INFO_TYPE_MAP);
		if(info != null && info.getInfoId() != null){
			//防止编辑的时候，搜索的内容被覆盖
			strTitle = info.getTitle();
			info = infoService.findObjectBy_id(info.getInfoId());
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		try {
			if(info != null){
				infoService.update(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}

	//删除
	public String delete(){
		if(info != null && info.getInfoId() != null){
			strTitle = info.getTitle();
			infoService.delete(info.getInfoId());
		}
		return "list";
	}
	
	//异步状态改变
	public void publicInfo() {
		try {
			if(info != null){
				/*前端只传了状态过来
				 * 必须通过id查找到info的所有属性的值
				 * 再更新，不然除了状态，其他值都被更新为空
				 */
				Info tem = infoService.findObjectBy_id(info.getInfoId());
				tem.setState(info.getState());
				infoService.update(tem);
				
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write("更新信息状态成功".getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public String getStrTitle() {
		return strTitle;
	}

	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	
}
