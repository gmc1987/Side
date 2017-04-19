/**
 * 
 */
package com.tssa.customer.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author gmc
 *
 */

@Entity
@Table(name="CUST_USERINFO")
public class Customer implements Serializable {

	private static final long serialVersionUID = 5673641793180341632L;
	/*未生成二维码*/
	public static final String UN_QRCODE ="0";
	/*已生成二维码*/
	public static final String HAS_QRCODE ="1";
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/*客户编码*/
	@Column(length=16, nullable=false)
	private String uid;
	
	/*客户名*/
	@Column(length=50, nullable=false)
	private String username;
	
	/*密码*/
	@Column(length=32, nullable=false)
	private String password;
	
	/*创建时间*/
	@Column(nullable=false)
	private Date createDate;
	
	/*性别*/
	@Column(length=1)
	private Integer sex;
	
	/*生日*/
	@Column
	private Date birthday;
	
	/*年龄*/
	@Column
	private Integer age;
	
	/*手机*/
	@Column(length=20)
	private String phone;
	
	/*邮箱*/
	@Column(length=50)
	private String email;
	
	
	@Column(length=20)
	private String qq;
	
	/*头像图片路径*/
	@Column(length=200)
	private String headPicture;
	
	/*昵称*/
	@Column(length=50)
	private String nickname;
	
	/*支付密码*/
 	@Column(length=32)
	private String payPassword;
	
	/*是否生成二维码*/
 	@Column(length=255)
 	private String qrCode;
 	
 	/*账户等级*/
 	@Column
 	private Integer customerLever;
 	
 	/*客户关联账户*/
 	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
 	@JoinColumn(name="auid", insertable=true, nullable=true)
 	private Account custAccount;
 	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getHeadPicture() {
		return headPicture;
	}
	public void setHeadPicture(String headPicture) {
		this.headPicture = headPicture;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public Integer getCustomerLever() {
		return customerLever;
	}
	public void setCustomerLever(Integer customerLever) {
		this.customerLever = customerLever;
	}
	public Account getCustAccount() {
		return custAccount;
	}
	public void setCustAccount(Account custAccount) {
		this.custAccount = custAccount;
	}

}
