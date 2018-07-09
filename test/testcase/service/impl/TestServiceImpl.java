package testcase.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import testcase.dao.TestDao;
import testcase.entity.Person;
import testcase.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {
	@Resource
	TestDao testDao;
	public void say() {
		System.out.println("service saying hi.");
	}
	public void save(Person person) {
		testDao.save(person);
		int i = 1/0;
	}
	public Person findPerson(Serializable id) {
		save(new Person("test"));//测试只读
		return testDao.findPerson(id);
	}
}
