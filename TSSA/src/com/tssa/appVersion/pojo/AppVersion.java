/**
 * 
 */
package com.tssa.appVersion.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author gmc
 * @see 应用版本实体
 */
@SuppressWarnings("serial")
@Entity
@Table(name="SYS_APPVERSION")
public class AppVersion implements Serializable {

	/**
	 * id 主键
	 */
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/**
	 * 应用名称
	 */
	@Column(length=100, nullable=false)
	private String appName;
	
	/**
	 * 版本
	 */
	@Column(length=32)
	private String version;
	
	/**
	 * 构建号
	 */
	@Column(length=4)
	private Integer build;
	
	/**
	 * 应用标识
	 */
	@Column(length=32)
	private String identifier;
	
	/**
	 * 	平台
	 */
	@Column(length=20)
	private String platform;
	
	/**
	 * 发布人
	 */
	@Column(length=32)
	private String publisher;
	
	/**
	 * 发布日期
	 */
	@Column
	private Date publishDate;
	
	/**
	 * 是否已发布
	 * 0-待发布，1-已发布，2-下架
	 */
	@Column(length=4)
	private Integer	isPublished;
	
	/**
	 * 安装包下载路径
	 */
	@Column(length=200)
	private String downLoadPath;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getBuild() {
		return build;
	}

	public void setBuild(Integer build) {
		this.build = build;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Integer isPublished) {
		this.isPublished = isPublished;
	}

	public String getDownLoadPath() {
		return downLoadPath;
	}

	public void setDownLoadPath(String downLoadPath) {
		this.downLoadPath = downLoadPath;
	}
	
}
