package com.tssa.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;


/**
 * 日期操作类
 * <p>
 * 功能： 1、日期格式化 2、日期转换 3、截断日期 4、时期计算
 * 
 * 
 * @author whatlly
 * 
 */

public class DateWarpUtils {

	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final String YYYYMMDDHH = "yyyy-MM-dd HH";
	public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String[] PARSEPATTERNS = { YYYYMMDD, YYYYMMDDHH, YYYYMMDDHHMM, YYYYMMDDHHMMSS,
			YYYYMMDDHHMMSSSSS };

	public static Date date() {
		return new Date();
	}

	/***
	 * @return：yyyy-MM-dd
	 */
	public static String format(Date date) {
		if (date != null) {
			return DateFormatUtils.format(date, YYYYMMDD);
		}
		throw new NullPointerException();
	}

	/***
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String formatFull(Date date) {
		if (date != null) {
			return DateFormatUtils.format(date, YYYYMMDDHHMMSS);
		}
		throw new NullPointerException();
	}

	/***
	 * 返回指定格式
	 */
	public static String format(Date date, String pattern) {
		if (date != null && pattern != null) {
			return DateFormatUtils.format(date, pattern);
		}
		throw new NullPointerException();
	}

	/**
	 * 
	 * @param date
	 *            转换的日期
	 * @param defaultDate
	 *            默认日期
	 * @return yyyy-MM-dd
	 */
	public static String format(Date date, Date defaultDate) {
		if (date != null) {
			return DateFormatUtils.format(date, YYYYMMDD);
		} else if (date == null && defaultDate != null) {
			return DateFormatUtils.format(defaultDate, YYYYMMDD);
		}
		throw new NullPointerException();
	}

	/**
	 * 
	 * @param date
	 *            转换的日期
	 * @param defaultDate
	 *            默认日期
	 * @return
	 */
	public static String formatFull(Date date, Date defaultDate) {
		if (date != null) {
			return DateFormatUtils.format(date, YYYYMMDDHHMMSS);
		} else if (date == null && defaultDate != null) {
			return DateFormatUtils.format(defaultDate, YYYYMMDDHHMMSS);
		}
		throw new NullPointerException();
	}

	/**
	 * 
	 * @param date
	 *            转换的日期
	 * @param defaultDate
	 *            默认日期
	 * @param pattern
	 * @return 指定格式
	 */
	public static String format(Date date, Date defaultDate, String pattern) {
		if (date != null && pattern != null) {
			return DateFormatUtils.format(date, pattern);
		} else if (defaultDate != null && pattern != null) {
			return DateFormatUtils.format(defaultDate, pattern);
		}
		throw new NullPointerException();
	}

	/***
	 * String 转成 java.util.Date
	 */
	public static Date parseDate(String date) {
		if (date != null) {
			try {
				return DateUtils.parseDate(date, PARSEPATTERNS);
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		throw new NullPointerException();
	}

	/***
	 * String 转成 java.util.Date
	 */
	public static Date parseDate(String date, String[] pattern) {
		if (date != null) {
			try {
				return DateUtils.parseDate(date, pattern);
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		throw new NullPointerException();
	}

	/**
	 * String 转成 java.sql.Date
	 */
	public static java.sql.Date parseSqlDate(String date) {
		return new java.sql.Date(parseDate(date).getTime());
	}

	/***
	 * java.util.Date 转成 java.sql.Date
	 */
	public static java.sql.Date parseSqlDate(java.util.Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		}
		throw new NullPointerException();
	}

	/**
	 * Date转Calendar
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar toCalendar(Date date) {
		return DateUtils.toCalendar(date);
	}

	/**
	 * 截断时间，单位：秒
	 * 
	 * @param date
	 *            时间
	 * @return 截断后的时间
	 */
	public static Date truncateSecond(Object date) {
		return DateUtils.truncate(date, Calendar.SECOND);
	}

	/**
	 * 截断时间，单位：分
	 * <p>
	 * 2013-08-09 12:52:17输出2013-08-09 12:52:00
	 * 
	 * @param date
	 *            时间
	 * @return 截断后的时间
	 */
	public static Date truncateMinute(Object date) {
		return DateUtils.truncate(date, Calendar.MINUTE);
	}

	/**
	 * 截断时间，单位：时
	 * <p>
	 * 2013-08-09 12:52:17输出2013-08-09 12:00:00
	 * 
	 * @param date
	 *            时间
	 * @return 截断后的时间
	 */
	public static Date truncateHour(Object date) {
		return DateUtils.truncate(date, Calendar.HOUR);
	}

	/**
	 * 截断时间，单位：天
	 * <p>
	 * 2013-08-09 12:52:17输出2013-08-09 00:00:00
	 * 
	 * @param date
	 *            时间
	 * @return 截断后的时间
	 */
	public static Date truncateDay(Object date) {
		return DateUtils.truncate(date, Calendar.DATE);
	}

	/**
	 * 截断时间，单位：月
	 * 
	 * <p>
	 * 2013-08-09 12:52:17输出2013-08-01 00:00:00
	 * 
	 * @param date
	 *            时间
	 * @return 截断后的时间
	 */
	public static Date truncateMonth(Object date) {
		return DateUtils.truncate(date, Calendar.MONTH);
	}

	/**
	 * 截断时间，单位：年
	 * <p>
	 * 2013-08-09 12:52:17输出2013-01-01 00:00:00
	 * 
	 * @param date
	 *            时间
	 * @return 截断后的时间
	 */
	public static Date truncateYear(Object date) {
		return DateUtils.truncate(date, Calendar.YEAR);
	}

	/**
	 * 上限时间，单位：秒
	 * 
	 * @param date
	 *            时间
	 * @return 上限时间
	 */
	public static Date ceilingSecond(Object date) {
		return DateUtils.ceiling(date, Calendar.SECOND);
	}

	/**
	 * 上限时间，单位：分
	 * <p>
	 * 2013-08-09 12:19:36 输出2013-08-09 12:20:00,超过19分，下个时间20分
	 * 
	 * @param date
	 *            时间
	 * @return 上限时间
	 */
	public static Date ceilingMinute(Object date) {
		return DateUtils.ceiling(date, Calendar.MINUTE);
	}

	/**
	 * 上限时间，单位：时
	 * <p>
	 * 2013-08-09 12:19:36 输出2013-08-09 13:00:00,超过12时，下个时间13时
	 * 
	 * @param date
	 *            时间
	 * @return 上限时间
	 */
	public static Date ceilingHour(Object date) {
		return DateUtils.ceiling(date, Calendar.HOUR);
	}

	/**
	 * 上限时间，单位：天
	 * <p>
	 * 2013-08-09 12:19:36 输出2013-08-10 00:00:00,超过9日，下个时间10日
	 * 
	 * @param date
	 *            时间
	 * @return 上限时间
	 */
	public static Date ceilingDate(Object date) {
		return DateUtils.ceiling(date, Calendar.DATE);
	}

	/**
	 * 上限时间，单位：月
	 * <p>
	 * 2013-08-09 12:19:36 输出2013-09-01 00:00:00,超过8月，下个时间9月
	 * 
	 * @param date
	 *            时间
	 * @return 上限时间
	 */
	public static Date ceilingMonth(Object date) {
		return DateUtils.ceiling(date, Calendar.MONTH);
	}

	/**
	 * 上限时间，单位：年
	 * <p>
	 * 2013-08-09 12:19:36 输出2014-01-01 00:00:00,超过2013年，下个时间2014年
	 * 
	 * @param date
	 *            时间
	 * @return 上限时间
	 */
	public static Date ceilingYear(Object date) {
		return DateUtils.ceiling(date, Calendar.YEAR);
	}

	/**
	 * 时间加法，单位，秒
	 * 
	 * @param date
	 *            输入时间
	 * @param amount
	 *            数量，小于0即为减
	 * @return 加法后的时间
	 */
	public static Date addSeconds(Date date, int amount) {
		return DateUtils.addSeconds(date, amount);
	}

	/**
	 * 时间加法，单位，分
	 * 
	 * @param date
	 *            输入时间
	 * @param amount
	 *            数量，小于0即为减
	 * @return 加法后的时间
	 */
	public static Date addMinutes(Date date, int amount) {
		return DateUtils.addMinutes(date, amount);
	}

	/**
	 * 时间加法，单位，小时
	 * 
	 * @param date
	 *            输入时间
	 * @param amount
	 *            数量，小于0即为减
	 * @return 加法后的时间
	 */
	public static Date addHours(Date date, int amount) {
		return DateUtils.addHours(date, amount);
	}

	/**
	 * 时间加法，单位，天
	 * 
	 * @param date
	 *            输入时间
	 * @param amount
	 *            数量，小于0即为减
	 * @return 加法后的时间
	 */
	public static Date addDays(Date date, int amount) {
		return DateUtils.addDays(date, amount);
	}

	/**
	 * 时间加法，单位，星期
	 * 
	 * @param date
	 *            输入时间
	 * @param amount
	 *            数量，小于0即为减
	 * @return 加法后的时间
	 */
	public static Date addWeeks(Date date, int amount) {
		return DateUtils.addWeeks(date, amount);
	}

	/**
	 * 时间加法，单位，月
	 * 
	 * @param date
	 *            输入时间
	 * @param amount
	 *            数量，小于0即为减
	 * @return 加法后的时间
	 */
	public static Date addMonths(Date date, int amount) {
		return DateUtils.addMonths(date, amount);
	}

	/**
	 * 时间加法，单位，年
	 * 
	 * @param date
	 *            输入时间
	 * @param amount
	 *            数量，小于0即为减
	 * @return 加法后的时间
	 */
	public static Date addYears(Date date, int amount) {
		return DateUtils.addYears(date, amount);
	}

	public static DateFormat getDateFormat() {
		return null;
	}

	/**
	 * 线程安全DateFormat
	 * 
	 */
	public static class ThreadSafeDateFormat extends DateFormat {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4219655779054666128L;

		/**
		 * 覆盖
		 * 
		 */
		@Override
		public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
			return toAppendTo.append(DateWarpUtils.formatFull(date));
		}

		@Override
		public Date parse(String source, ParsePosition pos) {
			return parseDate(source);
		}

		@Override
		public Date parse(String source) throws ParseException {
			return DateWarpUtils.parseDate(source);
		}

		@Override
		public Object parseObject(String source) throws ParseException {
			return parseDate(source);
		}

		@Override
		public Object parseObject(String source, ParsePosition pos) {
			return parseDate(source);
		}

		@Override
		public TimeZone getTimeZone() {
			return TimeZone.getDefault();
		}

		@Override
		public Object clone() {
			return this;
		}
	}

}
