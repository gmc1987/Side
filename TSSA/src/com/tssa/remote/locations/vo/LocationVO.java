/**
 * 
 */
package com.tssa.remote.locations.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author gmc870223
 *
 */
public class LocationVO implements Serializable {

	public static final Integer STATUS_YES = 1;  //启用
	public static final Integer STATUS_NO = 0;  //不启用
	
	public static final Map<String,String> TYPE_INFORMATION = new LinkedHashMap<String,String>();
	public static final Map<String,Integer> TYPEINDEX_INDORMATION = new HashMap<String,Integer>();//区域类型顺序
	
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
	
	public static int getColumnLeng50() {
		return COLUMN_LENG_50;
	}
	
	public static int getColumnLeng32() {
		return COLUMN_LENG_32;
	}
}
