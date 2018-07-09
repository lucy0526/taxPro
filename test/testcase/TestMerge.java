package testcase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import testcase.entity.Person;
import testcase.service.TestService;

public class TestMerge{
	ClassPathXmlApplicationContext ctx;
	
//	测试spring配置框架
	@Before
	public void loadCtx(){
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");//加载spring
	}
	
	@Test
	public void testSpring(){
		TestService ts = (TestService)ctx.getBean("testService");
		ts.say();
	}
	
//	测试Hibernate配置框架
	@Test
	public void testHibernate(){
		SessionFactory sf = (SessionFactory)ctx.getBean("sessionFactory");
		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(new Person("人员1"));
		transaction.commit();
		session.close();
	}
	
//	测试业务框架
	@Test
	public void testServiceAndDao(){
		TestService ts = (TestService)ctx.getBean("testService");
//		ts.save(new Person("人员2"));
		System.out.println(ts.findPerson("4028b881643b56eb01643b56f2ae0001").getName());
	}
	
	@Test
	public void testTransationReadOnly(){
		TestService ts = (TestService)ctx.getBean("testService");
		System.out.println(ts.findPerson("4028b881643b56eb01643b56f2ae0001").getName());
	}
	@Test
	public void testTransationRollback(){
		TestService ts = (TestService)ctx.getBean("testService");
		ts.save(new Person("人员3"));
	}
	
}
