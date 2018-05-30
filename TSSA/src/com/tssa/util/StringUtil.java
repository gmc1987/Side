/**
 * 
 */
package com.tssa.util;

/**
 * @author gmc870223
 * @see 字符串处理类
 */
public class StringUtil {

	public static String number2String(int number) {
		String str = null;
		if(number > 10) {
			str = String.valueOf(number);
			if(str.length() < 2) {
				str = "0"+str;
			}
		} else {
			str = String.valueOf(number);
		}
		return str;
	}
}
