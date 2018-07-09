package nsfw.complain.action;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import nsfw.complain.entity.Complain;
import nsfw.complain.entity.ComplainReply;
import nsfw.complain.service.ComplainService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import core.action.BaseAction;
import core.util.CodeUtil;
import core.util.QueryHelper;


public class ComplainAction extends BaseAction {
	@Resource
	private ComplainService complainService;
	private Complain complain;
	private String startTime;
	private String endTime;
	private ComplainReply reply;
	private String strTitle;
	private String strState;
	private Map<String, Object> statisticMap;
	
	
	public String listUI() throws Exception{
		//加载状态列表
		ActionContext.getContext().getContextMap().put("complainStateMap", complain.COMPLAIN_STATE_MAP);
		QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
		/*
		 * 先查询时间，排除大部分信息
		 * 性能高
		 */
		if(StringUtils.isNotBlank(startTime)){
			startTime = CodeUtil.dealDecode(startTime);
			queryHelper.addCondition("c.compTime>=?", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm"));
		}
		if(StringUtils.isNotBlank(endTime)){
			endTime = CodeUtil.dealDecode(endTime);
			queryHelper.addCondition("c.compTime<=?", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm"));
		}
		/*
		 * 标题、状态
		 */
		if(complain != null){
			if(StringUtils.isNotBlank(complain.getCompTitle())){
				complain.setCompTitle(CodeUtil.dealDecode(complain.getCompTitle()));
				queryHelper.addCondition("c.compTitle like ?", "%" + complain.getCompTitle() + "%");
			}
			if(StringUtils.isNotBlank(complain.getState())){
				queryHelper.addCondition("c.state=?", complain.getState());
			}
		}
		/**
		 * 按照投诉时间升序排序
		 * 		状态升序排序
		 */
		queryHelper.addOrderByProperty("c.compTime", QueryHelper.ORDER_BY_ASC);
		queryHelper.addOrderByProperty("c.state", QueryHelper.ORDER_BY_ASC);
		
		pageResult = complainService.getPageResult(queryHelper, getPageNo(), getPageSize());
		return "listUI";
	}
	
	/**
	 * 跳转到受理页面
	 * @return
	 */
	public String dealUI() {
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		if(complain != null){
			//回显
			strTitle = complain.getCompTitle();
			strState = complain.getState();
			
			complain = complainService.findObjectBy_id(complain.getCompId());
		}
		return "dealUI";
	}
	public String deal() {
		//更新状态为已经受理
		if(complain!=null){
			Complain tem = complainService.findObjectBy_id(complain.getCompId());
			if(!Complain.COMPLAIN_STATE_DONE.equals(tem.getState())){
				tem.setState(Complain.COMPLAIN_STATE_DONE);
			}
			
			//保存回复信息
			if(reply != null){
				reply.setComplain(tem);
				reply.setReplyTime(new Timestamp(new Date().getTime()));
				/*
				 * 在hibernate中设置级联
				 * 修改complain，自动修改reply
				 */
				tem.getComplainReplies().add(reply);
			}
			//更新投诉信息状态的同时保存回复
			complainService.update(tem);
			
		}
		return "list";
	}
	public String getAnnualStatisticData() {
		//获取年份
		HttpServletRequest request = ServletActionContext.getRequest();
		int year = 0;
		if(request.getParameter("year") != null){
			year = Integer.valueOf(request.getParameter("year"));
		}else{
			year = Calendar.getInstance().get(Calendar.YEAR);//得到当前年份
		}
		//获取投诉数
		statisticMap = new HashMap<String, Object>();
		statisticMap.put("msg", "success");
		statisticMap.put("chartData", complainService.getAnnualStatisticData_byYear(year));
		
		
		return "annualStatisticData";
	}
	//跳转到统计页面
	public String annualStatisticChartUI() {
		return "annualStatisticChartUI";
	}
	public Complain getComplain() {
		return complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ComplainReply getReply() {
		return reply;
	}

	public void setReply(ComplainReply reply) {
		this.reply = reply;
	}

	public String getStrTitle() {
		return strTitle;
	}

	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = strState;
	}
	public Map<String, Object> getStatisticMap() {
		return statisticMap;
	}
}
