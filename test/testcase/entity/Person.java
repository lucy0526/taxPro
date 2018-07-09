package testcase.entity;

import java.io.Serializable;

//实现serializable可以直接写入硬盘、直接传递对象、可以作为组件
public class Person implements Serializable {
	private String id;//使用uuid32bit
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Person(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public Person() {
	}
	public Person(String name) {
		this.name = name;
	}
}
