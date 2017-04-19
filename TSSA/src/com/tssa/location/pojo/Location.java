package com.tssa.location.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * <p> 类说明 :国家区域</p>
 * @author 高铭潮
 * Date: 2015-02-09
 * @version 1.0.0
 *
 */
@Entity
@Table(name = "SYSTEM_LOCATION")
public class Location{
	public static final Integer STATUS_YES = 1;  //启用
	public static final Integer STATUS_NO = 0;  //不启用
	
	public static final Map<String,String> TYPE_INFORMATION = new LinkedHashMap<String,String>();
	public static final Map<String,Integer> TYPEINDEX_INDORMATION = new HashMap<String,Integer>();  //区域类型顺序
	
	public static final String COUNTRY = "country"; //国家
	public static final String BIGAREA = "bigArea"; //大区
	public static final String SPECIALCITY = "specialCity"; //直辖市
	public static final String SPECIALAREA = "specialArea"; //特区
	public static final String PROVINCE = "province"; //省份
	public static final String CITY = "city"; //城市
	public static final String AREA = "area"; //区域
	private static final Integer NUMBER_3 = 3;
	private static final Integer NUMBER_4 = 4;
	private static final Integer NUMBER_5 = 5;
	
	static{
		TYPE_INFORMATION.put(COUNTRY, "国家");
		TYPE_INFORMATION.put(BIGAREA, "大区");
		TYPE_INFORMATION.put(SPECIALCITY, "直辖市");
		TYPE_INFORMATION.put(SPECIALAREA, "特区");
		TYPE_INFORMATION.put(PROVINCE, "省份");
		TYPE_INFORMATION.put(CITY, "城市");
		TYPE_INFORMATION.put(AREA, "区域");
	}
	
	static{
		TYPEINDEX_INDORMATION.put(COUNTRY, 1);
		TYPEINDEX_INDORMATION.put(BIGAREA, 2);
		TYPEINDEX_INDORMATION.put(SPECIALCITY, NUMBER_3);
		TYPEINDEX_INDORMATION.put(SPECIALAREA, NUMBER_3);
		TYPEINDEX_INDORMATION.put(PROVINCE, NUMBER_3);
		TYPEINDEX_INDORMATION.put(CITY, NUMBER_4);
		TYPEINDEX_INDORMATION.put(AREA, NUMBER_5);
	}
	
	/** 属性说明 */
	private static final long serialVersionUID = -1446344894616053945L;

	private static final int COLUMN_LENG_50 = 50;
	
	private static final int COLUMN_LENG_32 = 32;

	@Id  //声明该字段为主键
	@Column(length = 11) 
	@GeneratedValue(generator = "paymentableGenerator") 
	@GenericGenerator(name = "paymentableGenerator", strategy = "identity")
	private Integer id;
	
	/** 名称 */
	@Column(length = COLUMN_LENG_32) //字段长度
	private String name;
	
	/**英文名字*/
	@Column(length = COLUMN_LENG_32)
	private String enName;
	
	@Column(length = COLUMN_LENG_50)
	private String path;
	
	@ManyToOne
	@JoinColumn(name = "pareId")
	private Location pareLocation;

	@OneToMany(mappedBy = "pareLocation", cascade = CascadeType.REMOVE)
	private Set<Location> subLocations = new HashSet<Location>(0);
	
	/** 代码 */
	@Column(length = COLUMN_LENG_32)
	private String lcode;
	
	/** 类别 */
	@Column(length = COLUMN_LENG_32)
	private String ltype;
	
	/** 序号 */
	@Column
	private Integer orderid;
	
	/** 属性说明 :是否启用 1:启用，0：不启用*/
	@Column
	private Integer status;
	
	
	/** 是否是热门城市 1为是 0为不是 */
	@Column
	private Integer isHotCity=0;
	
	/** 全拼音 **/
	@Column(length = COLUMN_LENG_50)
	private String fullPinyin;
	
	/** 汉字首拼音 **/
	@Column(length = COLUMN_LENG_32)
	private String pinyin;
	
	@Transient
	private Integer hasUsedChild;
	
	@Transient
	private Integer showNode;
	
	/** 属性说明 树集合转收缩树的临时父ID*/
	@Transient
	private String tempPareId;
	
	/** 属性说明： 类型描述*/
	@Transient
	private Integer typeDesc;
	
	public String getTypeDesc(){
		return TYPE_INFORMATION.get(ltype);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Location getPareLocation() {
		return pareLocation;
	}

	public void setPareLocation(Location pareLocation) {
		this.pareLocation = pareLocation;
	}

	public Set<Location> getSubLocations() {
		return subLocations;
	}

	public void setSubLocations(Set<Location> subCitys) {
		this.subLocations = subCitys;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getIsHotCity() {
		return isHotCity;
	}

	public void setIsHotCity(Integer isHotCity) {
		this.isHotCity = isHotCity;
	}

	public String getFullPinyin() {
		return fullPinyin;
	}

	public void setFullPinyin(String fullPinyin) {
		this.fullPinyin = fullPinyin;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public static int getColumnLeng32() {
		return COLUMN_LENG_32;
	}
	
	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getShowNode() {
		return showNode;
	}

	public void setShowNode(Integer showNode) {
		this.showNode = showNode;
	}

	public Integer getHasUsedChild() {
		return hasUsedChild;
	}

	public void setHasUsedChild(Integer hasUsedChild) {
		this.hasUsedChild = hasUsedChild;
	}

	public static Integer getStatusYes() {
		return STATUS_YES;
	}

	public static Integer getStatusNo() {
		return STATUS_NO;
	}

	public static Map<String, String> getTypeInformation() {
		return TYPE_INFORMATION;
	}

	public static String getCountry() {
		return COUNTRY;
	}

	public static String getBigarea() {
		return BIGAREA;
	}

	public static String getSpecialcity() {
		return SPECIALCITY;
	}

	public static String getSpecialarea() {
		return SPECIALAREA;
	}

	public static String getProvince() {
		return PROVINCE;
	}

	public static String getCity() {
		return CITY;
	}

	public static String getArea() {
		return AREA;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static int getColumnLeng50() {
		return COLUMN_LENG_50;
	}

	public void setTypeDesc(Integer typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getTempPareId() {
		return tempPareId;
	}

	public void setTempPareId(String tempPareId) {
		this.tempPareId = tempPareId;
	}

	public String getLcode() {
		return lcode;
	}

	public void setLcode(String lcode) {
		this.lcode = lcode;
	}

	public String getLtype() {
		return ltype;
	}

	public void setLtype(String ltype) {
		this.ltype = ltype;
	}
	
}
