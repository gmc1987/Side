/**
 * 
 */
package com.tssa.menus.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import com.tssa.role.pojo.Role;

/**
 * @author gmc
 * @see 菜单实体
 */

@Entity
@Table(name="SYSTEM_MENU")
public class Menu implements Serializable {

	private static final long serialVersionUID = 7321901488085449612L;
	
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String mid;
	
	@Column(length=50, nullable=false)
	private String menuName;
	
	@Column(length=200, nullable=true)
	private String menuUrl;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private Menu parentIds;

	/**
	 * 子级产品类型ID集
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "parentIds", cascade = CascadeType.REMOVE, fetch=FetchType.LAZY)
	private Set<Menu> subMenuId = new HashSet<Menu>(0);
	
	@Column(length=4, nullable=true)
	private Integer menuOrder;
	
	@Column(length=4, nullable=true)
	private Integer menuOrderDisplay;
	
	@ManyToMany(mappedBy="mid", fetch=FetchType.LAZY)
//	@JoinTable(name="MANAGER_ROLE_MENU_OPTION",
//	joinColumns={@JoinColumn(name="mid",referencedColumnName="mid")},
//	inverseJoinColumns={@JoinColumn(name="rid",referencedColumnName="roleId")})
	private Set<Role> rid;
	
	@Column(length=32, nullable=true)
	private String createUser;
	
	@Column
	private Date createDate;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Menu getParentIds() {
		return parentIds;
	}

	public void setParentIds(Menu parentIds) {
		this.parentIds = parentIds;
	}

	public Set<Menu> getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(Set<Menu> subMenuId) {
		this.subMenuId = subMenuId;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public Integer getMenuOrderDisplay() {
		return menuOrderDisplay;
	}

	public void setMenuOrderDisplay(Integer menuOrderDisplay) {
		this.menuOrderDisplay = menuOrderDisplay;
	}

	public Set<Role> getRid() {
		return rid;
	}

	public void setRid(Set<Role> rid) {
		this.rid = rid;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
