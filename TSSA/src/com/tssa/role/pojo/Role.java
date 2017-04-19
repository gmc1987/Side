/**
 * 
 */
package com.tssa.role.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tssa.menus.pojo.Menu;
import com.tssa.options.pojo.Option;

/**
 * @author gmc
 *
 */
@Entity
@Table(name="MANAGER_ROLE")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6135795106222321048L;
	
	public Role(){
		
	}
	
	public Role(String roleCode, String roleName, int roleValue, String roleValueName, String createUser){
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.roleValue = roleValue;
		this.createUser = createUser;
		this.roleValueName = roleValueName;
		this.createDate = new Date();
	}
	
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String roleId;
	
	@Column(length=32, nullable=false)
	private String roleCode;
	
	@Column(length=50, nullable=false)
	private String roleName;
	
	@Column
	private Integer roleValue;
	
	@Column(length=32, nullable=false)
	private String roleValueName;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.MERGE})
	@JoinTable(name="MANAGER_ROLE_MENU_OPTION",
	joinColumns={@JoinColumn(name="rid",referencedColumnName="roleId")},
	inverseJoinColumns={@JoinColumn(name="pid",referencedColumnName="pid")})
	private Set<Option> pid;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.MERGE})
	@JoinTable(name="MANAGER_ROLE_MENU_OPTION",
	joinColumns={@JoinColumn(name="rid",referencedColumnName="roleId")},
	inverseJoinColumns={@JoinColumn(name="mid",referencedColumnName="mid")})
	private Set<Menu> mid;
	
	@Column
	private Date createDate;
	
	@Column(length=32)
	private String createUser;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<Option> getPid() {
		return pid;
	}

	public void setPid(Set<Option> pid) {
		this.pid = pid;
	}

	public Set<Menu> getMid() {
		return mid;
	}

	public void setMid(Set<Menu> mid) {
		this.mid = mid;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Integer getRoleValue() {
		return roleValue;
	}

	public void setRoleValue(Integer roleValue) {
		this.roleValue = roleValue;
	}

	public String getRoleValueName() {
		return roleValueName;
	}

	public void setRoleValueName(String roleValueName) {
		this.roleValueName = roleValueName;
	}
	
	

}
