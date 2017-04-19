package com.tssa.remote.object;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>返回手机端实体 </p>
 * @author lch
 * Date: 2013-11-20
 * @version 1.01
 */
public class CustomerVo extends BaseVo{
	
	/*主键*/
	private String cid;
	
	/*客户编码*/
	private String uid;
	
	/*客户名*/
	private String username;
	
	/*密码*/
	private String password;
	
	/*创建时间*/
	private Date createDate;
	
	/*性别*/
	private Integer sex;
	
	/*生日*/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	
	/*年龄*/
	private Integer age;
	
	/*手机*/
	private String phone;
	
	/*邮箱*/
	private String email;
	
	private String qq;
	
	/*头像图片路径*/
	private String headPicture;
	
	/*昵称*/
	private String nickname;
	
	/*支付密码*/
	private String payPassword;
	
	/*是否生成二维码*/
 	private String qrCode;
 	
 	/*账户等级*/
 	private Integer customerLever;
	
 	
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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
	
}
