package testcase.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * TPrivilege entity. @author MyEclipse Persistence Tools
 */

public class TPrivilege implements java.io.Serializable {

	// Fields

	private String privilegeId;
	private String name;
	private Set rolePris = new HashSet(0);

	// Constructors

	/** default constructor */
	public TPrivilege() {
	}

	/** full constructor */
	public TPrivilege(String name, Set rolePris) {
		this.name = name;
		this.rolePris = rolePris;
	}

	// Property accessors

	public String getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getRolePris() {
		return this.rolePris;
	}

	public void setRolePris(Set rolePris) {
		this.rolePris = rolePris;
	}

}