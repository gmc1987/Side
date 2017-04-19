package com.tssa.options.pojo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tssa.role.pojo.Role;


@Entity
@Table(name="MANAGER_OPTION")
public class Option implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6091595515473522634L;
	
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String pid;
	
	@Column(length=50, nullable=false)
	private String optionName;
	
	@Column(length=4, nullable=false)
	private Integer optionValue;
	
	@ManyToMany(mappedBy="pid", fetch=FetchType.LAZY)
//	@JoinTable(name="MANAGER_ROLE_MENU_OPTION",
//	joinColumns={@JoinColumn(name="pid",referencedColumnName="pid")},
//	inverseJoinColumns={@JoinColumn(name="rid",referencedColumnName="roleId")})
	private Set<Role> rid;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public Integer getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(Integer optionValue) {
		this.optionValue = optionValue;
	}
	public Set<Role> getRid() {
		return rid;
	}
	public void setRid(Set<Role> rid) {
		this.rid = rid;
	}
	
	
}
