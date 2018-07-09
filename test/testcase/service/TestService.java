package testcase.service;

import java.io.Serializable;

import testcase.entity.Person;

public interface TestService {
	public void say();
	public void save(Person person);
	public Person findPerson(Serializable id);
}
