/**
 * 
 */
package com.tssa.adminUsers.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.tssa.role.pojo.Role;




/**
 * @author gmc
 * @see 用户信息对象
 */

@Entity
@Table(name="MANAGER_USER")
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Users implements Serializable {
	
	public Users(){
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5865119500568875911L;
	
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String uid;
	
	@Column(length=32, nullable=false)
	private String userCode;
	
	@Column(length=50)
	private String userName;
	
	@Column(length=32, nullable=false)
	private String password;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="rid")
	private Role rid;
	
	@Column(nullable=false)
	private Date createDate;
	
	@Column(length=32)
	private String createUser;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRid() {
		return rid;
	}

	public void setRid(Role rid) {
		this.rid = rid;
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


}
