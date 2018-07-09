package testcase.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * TEmploy entity. @author MyEclipse Persistence Tools
 */

public class TEmploy implements java.io.Serializable {

	// Fields

	private String empId;
	private TDept TDept;
	private String name;
	private Set TLeaders = new HashSet(0);
	private Set empRoles = new HashSet(0);

	// Constructors

	/** default constructor */
	public TEmploy() {
	}

	/** full constructor */
	public TEmploy(TDept TDept, String name, Set TLeaders, Set empRoles) {
		this.TDept = TDept;
		this.name = name;
		this.TLeaders = TLeaders;
		this.empRoles = empRoles;
	}

	// Property accessors

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public TDept getTDept() {
		return this.TDept;
	}

	public void setTDept(TDept TDept) {
		this.TDept = TDept;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getTLeaders() {
		return this.TLeaders;
	}

	public void setTLeaders(Set TLeaders) {
		this.TLeaders = TLeaders;
	}

	public Set getEmpRoles() {
		return this.empRoles;
	}

	public void setEmpRoles(Set empRoles) {
		this.empRoles = empRoles;
	}

}