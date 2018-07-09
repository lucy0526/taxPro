package nsfw.complain.dao;

import java.util.List;

import nsfw.complain.entity.Complain;
import core.dao.BaseDao;

public interface ComplainDao extends BaseDao<Complain> {

	public List<Object[]> getAnnualStatisticData_byYear(int year);

}
