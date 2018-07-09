package testcase.entity;

/**
 * TLeader entity. @author MyEclipse Persistence Tools
 */

public class TLeader implements java.io.Serializable {

	// Fields

	private String empId;
	private TEmploy TEmploy;
	private String deptId;
	private String name;
	private Integer position;

	// Constructors

	/** default constructor */
	public TLeader() {
	}

	/** minimal constructor */
	public TLeader(TEmploy TEmploy) {
		this.TEmploy = TEmploy;
	}

	/** full constructor */
	public TLeader(TEmploy TEmploy, String deptId, String name, Integer position) {
		this.TEmploy = TEmploy;
		this.deptId = deptId;
		this.name = name;
		this.position = position;
	}

	// Property accessors

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public TEmploy getTEmploy() {
		return this.TEmploy;
	}

	public void setTEmploy(TEmploy TEmploy) {
		this.TEmploy = TEmploy;
	}

	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

}