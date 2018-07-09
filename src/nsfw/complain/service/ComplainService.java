package nsfw.complain.service;

import java.util.List;
import java.util.Map;

import nsfw.complain.entity.Complain;
import core.service.BaseService;

public interface ComplainService extends BaseService<Complain> {
	//自动受理投诉
	public void autoDeal();
	
	/**
	 * 根据年份查询投诉数
	 * @param year
	 * @return
[
{
   "label": "1 月",
   "value": "42"
},
{
   "label": "2 月",
   "value": "81"
}
]

	 */
	public List<Map> getAnnualStatisticData_byYear(int year);
}
