package com.tssa.common.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.TimeZone;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateWarpUtils
{
  public static final String YYYYMMDD = "yyyy-MM-dd";
  public static final String YYYYMMDDHH = "yyyy-MM-dd HH";
  public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
  public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
  public static final String YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";
  public static final String[] PARSEPATTERNS = { "yyyy-MM-dd", "yyyy-MM-dd HH", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS" };

  public static java.util.Date date()
  {
    return new java.util.Date();
  }

  public static String format(java.util.Date date)
  {
    if (date != null) {
      return DateFormatUtils.format(date, "yyyy-MM-dd");
    }
    throw new NullPointerException();
  }

  public static String formatFull(java.util.Date date)
  {
    if (date != null) {
      return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }
    throw new NullPointerException();
  }

  public static String format(java.util.Date date, String pattern)
  {
    if ((date != null) && (pattern != null)) {
      return DateFormatUtils.format(date, pattern);
    }
    throw new NullPointerException();
  }

  public static String format(java.util.Date date, java.util.Date defaultDate)
  {
    if (date != null)
      return DateFormatUtils.format(date, "yyyy-MM-dd");
    if ((date == null) && (defaultDate != null)) {
      return DateFormatUtils.format(defaultDate, "yyyy-MM-dd");
    }
    throw new NullPointerException();
  }

  public static String formatFull(java.util.Date date, java.util.Date defaultDate)
  {
    if (date != null)
      return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    if ((date == null) && (defaultDate != null)) {
      return DateFormatUtils.format(defaultDate, "yyyy-MM-dd HH:mm:ss");
    }
    throw new NullPointerException();
  }

  public static String format(java.util.Date date, java.util.Date defaultDate, String pattern)
  {
    if ((date != null) && (pattern != null))
      return DateFormatUtils.format(date, pattern);
    if ((defaultDate != null) && (pattern != null)) {
      return DateFormatUtils.format(defaultDate, pattern);
    }
    throw new NullPointerException();
  }

  public static java.util.Date parseDate(String date)
  {
    if (date != null) {
      try {
        return DateUtils.parseDate(date, PARSEPATTERNS);
      } catch (ParseException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    }
    throw new NullPointerException();
  }

  public static java.util.Date parseDate(String date, String[] pattern)
  {
    if (date != null) {
      try {
        return DateUtils.parseDate(date, pattern);
      } catch (ParseException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    }
    throw new NullPointerException();
  }

  public static java.sql.Date parseSqlDate(String date)
  {
    return new java.sql.Date(parseDate(date).getTime());
  }

  public static java.sql.Date parseSqlDate(java.util.Date date)
  {
    if (date != null) {
      return new java.sql.Date(date.getTime());
    }
    throw new NullPointerException();
  }

  public static Calendar toCalendar(java.util.Date date)
  {
    return DateUtils.toCalendar(date);
  }

  public static java.util.Date truncateSecond(Object date)
  {
    return DateUtils.truncate(date, 13);
  }

  public static java.util.Date truncateMinute(Object date)
  {
    return DateUtils.truncate(date, 12);
  }

  public static java.util.Date truncateHour(Object date)
  {
    return DateUtils.truncate(date, 10);
  }

  public static java.util.Date truncateDay(Object date)
  {
    return DateUtils.truncate(date, 5);
  }

  public static java.util.Date truncateMonth(Object date)
  {
    return DateUtils.truncate(date, 2);
  }

  public static java.util.Date truncateYear(Object date)
  {
    return DateUtils.truncate(date, 1);
  }

  public static java.util.Date ceilingSecond(Object date)
  {
    return DateUtils.ceiling(date, 13);
  }

  public static java.util.Date ceilingMinute(Object date)
  {
    return DateUtils.ceiling(date, 12);
  }

  public static java.util.Date ceilingHour(Object date)
  {
    return DateUtils.ceiling(date, 10);
  }

  public static java.util.Date ceilingDate(Object date)
  {
    return DateUtils.ceiling(date, 5);
  }

  public static java.util.Date ceilingMonth(Object date)
  {
    return DateUtils.ceiling(date, 2);
  }

  public static java.util.Date ceilingYear(Object date)
  {
    return DateUtils.ceiling(date, 1);
  }

  public static java.util.Date addSeconds(java.util.Date date, int amount)
  {
    return DateUtils.addSeconds(date, amount);
  }

  public static java.util.Date addMinutes(java.util.Date date, int amount)
  {
    return DateUtils.addMinutes(date, amount);
  }

  public static java.util.Date addHours(java.util.Date date, int amount)
  {
    return DateUtils.addHours(date, amount);
  }

  public static java.util.Date addDays(java.util.Date date, int amount)
  {
    return DateUtils.addDays(date, amount);
  }

  public static java.util.Date addWeeks(java.util.Date date, int amount)
  {
    return DateUtils.addWeeks(date, amount);
  }

  public static java.util.Date addMonths(java.util.Date date, int amount)
  {
    return DateUtils.addMonths(date, amount);
  }

  public static java.util.Date addYears(java.util.Date date, int amount)
  {
    return DateUtils.addYears(date, amount);
  }

  public static DateFormat getDateFormat() {
    return null;
  }

  public static class ThreadSafeDateFormat extends DateFormat
  {
    private static final long serialVersionUID = 4219655779054666128L;

    public StringBuffer format(java.util.Date date, StringBuffer toAppendTo, FieldPosition fieldPosition)
    {
      return toAppendTo.append(DateWarpUtils.formatFull(date));
    }

    public java.util.Date parse(String source, ParsePosition pos)
    {
      return DateWarpUtils.parseDate(source);
    }

    public java.util.Date parse(String source) throws ParseException
    {
      return DateWarpUtils.parseDate(source);
    }

    public Object parseObject(String source) throws ParseException
    {
      return DateWarpUtils.parseDate(source);
    }

    public Object parseObject(String source, ParsePosition pos)
    {
      return DateWarpUtils.parseDate(source);
    }

    public TimeZone getTimeZone()
    {
      return TimeZone.getDefault();
    }

    public Object clone()
    {
      return this;
    }
  }
}