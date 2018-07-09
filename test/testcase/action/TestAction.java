package testcase.action;

import javax.annotation.Resource;

import testcase.service.TestService;

import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport {
	@Resource
	TestService testService;
	
	public String execute() {
		testService.say();
		return SUCCESS;
	}
}
