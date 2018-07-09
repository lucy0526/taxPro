package nsfw.complain.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import nsfw.complain.dao.ComplainDao;
import nsfw.complain.entity.Complain;
import nsfw.complain.service.ComplainService;

import org.springframework.stereotype.Service;

import core.service.impl.BaseServiceImpl;
import core.util.QueryHelper;
@Service("complainService")
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements
		ComplainService {
	private ComplainDao complainDao;
	
	/**
	 * 在接口中实现时确定dao的值
	 * 注入dao
	 * @param complainDao
	 */
	@Resource
	public void setComplainDao(ComplainDao complainDao) {
		super.setBaseDao(complainDao);
		this.complainDao = complainDao;
	}

	public void autoDeal() {
		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.MONTH, 1);//下一个月
//		calendar.add(Calendar.MONTH, -2);//前两个月
		//当前月
		calendar.set(Calendar.DAY_OF_MONTH, 9);//9号
		calendar.set(Calendar.HOUR_OF_DAY, 0);//0点
		calendar.set(Calendar.MINUTE, 0);//0分
		calendar.set(Calendar.SECOND, 0);//0秒
		//查
		QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
		queryHelper.addCondition("c.state=?", Complain.COMPLAIN_STATE_UNDONE);
		queryHelper.addCondition("c.compTime<?", calendar.getTime());
		
		List<Complain> list = findObjects(queryHelper);
		if(list!=null && list.size()>0){
			//改状态
			for(Complain comp:list){
				comp.setState(Complain.COMPLAIN_STATE_INVALID);
				update(comp);
			}
		}
	}

	public List<Map> getAnnualStatisticData_byYear(int year) {
		List<Map> retList = new ArrayList<Map>();
		//获取数据
		List<Object[]> list = complainDao.getAnnualStatisticData_byYear(year);
		if(list != null && list.size() > 0){
			Calendar calendar = Calendar.getInstance();
			boolean isCurYear = (calendar.get(Calendar.YEAR) == year);
			int curMonth = calendar.get(Calendar.MONTH)+1;
			//格式化结果
			int temMonth = 0;
			Map<String, Object> map = null;
			
			for(Object[] obj:list){
				
				map = new HashMap<String, Object>();
				map.put("label", temMonth + " 月");
				temMonth = Integer.valueOf(obj[0]+"");//字符串->数字
				if(isCurYear){
					if(curMonth < temMonth){
						//未到月份
						map.put("value", "");
					}else{
						map.put("value", obj[1]==null?"0":obj[1]);
					}
				}else{
					//非当前年份
					map.put("value", obj[1]==null?"0":obj[1]);
				}
				retList.add(map);
			}
		}
		return retList;
	}
}
