/**
 * 地区数据对象-洲/大陆
 */
package com.tssa.remote.locations.vo;

import java.util.Map;

/**
 * @author gmc870223
 *
 */
@SuppressWarnings("serial")
public class ContinentVO extends LocationVO {

		//声明该字段为主键
		private Integer id;
		
		/** 名称 */
		private String name;
		
		/**英文名字*/
		private String enName;
		
		private String path;
		
		/* 父级节点 */
//		private String pareLocation;

//		/** 代码 */
//		private String lcode;
		
		/** 类别 */
		private String ltype;
		
		/** 序号 */
//		private Integer orderid;
		
		/** 属性说明 :是否启用 1:启用，0：不启用*/
//		private Integer status;
		
		/** 全拼音 **/
		private String fullPinyin;
		
		/** 汉字首拼音 **/
//		private String pinyin;
		
//		private Integer hasUsedChild;
		
//		private Integer showNode;
		
		/** 属性说明 树集合转收缩树的临时父ID*/
//		private String tempPareId;
		
		/** 属性说明： 类型描述*/
//		@SuppressWarnings("unused")
//		private Integer typeDesc;
		
//		public void setPareLocation(String pareLocation) {
//			this.pareLocation = pareLocation;
//		}

//		public String getTypeDesc(){
//			return TYPE_INFORMATION.get(ltype);
//		}
		
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

//		public Integer getOrderid() {
//			return orderid;
//		}
//
//		public void setOrderid(Integer orderid) {
//			this.orderid = orderid;
//		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getFullPinyin() {
			return fullPinyin;
		}

		public void setFullPinyin(String fullPinyin) {
			this.fullPinyin = fullPinyin;
		}

//		public String getPinyin() {
//			return pinyin;
//		}
//
//		public void setPinyin(String pinyin) {
//			this.pinyin = pinyin;
//		}

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

//		public Integer getStatus() {
//			return status;
//		}
//
//		public void setStatus(Integer status) {
//			this.status = status;
//		}

//		public Integer getShowNode() {
//			return showNode;
//		}
//
//		public void setShowNode(Integer showNode) {
//			this.showNode = showNode;
//		}
//
//		public Integer getHasUsedChild() {
//			return hasUsedChild;
//		}
//
//		public void setHasUsedChild(Integer hasUsedChild) {
//			this.hasUsedChild = hasUsedChild;
//		}

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

//		public void setTypeDesc(Integer typeDesc) {
//			this.typeDesc = typeDesc;
//		}
//
//		public String getTempPareId() {
//			return tempPareId;
//		}
//
//		public void setTempPareId(String tempPareId) {
//			this.tempPareId = tempPareId;
//		}
//
//		public String getLcode() {
//			return lcode;
//		}
//
//		public void setLcode(String lcode) {
//			this.lcode = lcode;
//		}
//
//		public String getPareLocation() {
//			return pareLocation;
//		}

		public String getLtype() {
			return ltype;
		}

		public void setLtype(String ltype) {
			this.ltype = ltype;
		}
	
}
