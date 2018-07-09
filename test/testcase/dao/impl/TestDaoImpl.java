package testcase.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import testcase.dao.TestDao;
import testcase.entity.Person;

public class TestDaoImpl extends HibernateDaoSupport implements TestDao {
	public Person findPerson(Serializable id) {
		return getHibernateTemplate().get(Person.class, id);
	}

	public void save(Person person) {
		getHibernateTemplate().save(person);
	}
}
