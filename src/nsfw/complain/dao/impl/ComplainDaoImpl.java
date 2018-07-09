package nsfw.complain.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SQLQuery;

import nsfw.complain.dao.ComplainDao;
import nsfw.complain.entity.Complain;
import core.dao.impl.BaseDaoImpl;
import core.page.PageResult;
import core.util.QueryHelper;

public class ComplainDaoImpl extends BaseDaoImpl<Complain> implements
		ComplainDao {

	public List<Object[]> getAnnualStatisticData_byYear(int year) {
		StringBuffer sb = new StringBuffer();
		sb.append("select imonth, count(comp_id)")
		.append(" from tmonth left join complain on imonth=month(comp_time)")
		.append(" and year(comp_time)=?")
		.append(" group by imonth")
		.append(" order by imonth");
		SQLQuery sqlQuery = getSession().createSQLQuery(sb.toString());
		sqlQuery.setParameter(0, year);
		
		return sqlQuery.list();
	}

}
