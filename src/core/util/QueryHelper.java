package core.util;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {
	//from，一定有
	private String fromClause = "";
	//where
	private String whereClause = "";
	//order by
	private String orderByClause = "";
	
	private List<Object> parameters;
	
	public static String ORDER_BY_DESC = "DESC";
	public static String ORDER_BY_ASC = "ASC";
	
	/*
	 * 构造from语句
	 * alias：别名
	 */
	public QueryHelper(Class clazz, String alias) {
		fromClause = "from "+ clazz.getSimpleName() + " " + alias;
	}
	/*
	 * 构造where语句
	 */
	public void addCondition(String condition, Object... params){
		if (whereClause.length() > 1) {//非第一次添加查询条件
			whereClause += " and " + condition;
		}else{
			whereClause += " where " + condition;
		}
		
		//设置条件
		if(parameters == null){
			parameters = new ArrayList<Object>();
		}
		if(params != null){
			for(Object param : params){
				parameters.add(param);
			}
		}
	}
	
	/*
	 * 构造order by语句
	 */
	public void addOrderByProperty(String property, String order) {
		if (orderByClause.length() > 1) {//非第一次添加查询条件
			orderByClause += ", " + property + " " + order;
		}else{
			whereClause += " order by " + property + " " + order;
		}
	}
	
	
	//hql语句
	public String getQueryListHql(){
		return fromClause+whereClause+orderByClause;
	}
	//查询总数语句
	public String getQueryCountHql(){
		return "select count(*) " + fromClause + whereClause;
	}
	
	//参数
	public List<Object> getParameters(){
		return parameters;
	}
}
