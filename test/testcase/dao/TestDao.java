package testcase.dao;

import java.io.Serializable;

import testcase.entity.Person;

public interface TestDao {
	public void save(Person person);
	public Person findPerson(Serializable id);
}
