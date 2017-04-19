package com.tssa.common.util;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class Parameter
{
  public static String getParameter(HttpServletRequest request, String name)
  {
    if (StringUtils.hasText(name)) {
      String value = request.getParameter(name);
      if (value != null) {
        return value;
      }
      return null;
    }
    throw new NullPointerException();
  }

  public static String getParameter(HttpServletRequest request, String name, String defaultString)
  {
    String value = getParameter(request, name);
    if (value != null) {
      return value;
    }
    return defaultString;
  }

  public static Date getParameter2Date(HttpServletRequest request, String name, Date defaultDate)
  {
    String value = getParameter(request, name);
    if (StringUtils.hasText(value)) {
      return DateWarpUtils.parseDate(value);
    }
    return defaultDate;
  }

  public static int getParameter2Int(HttpServletRequest request, String name, int defaultInt)
  {
    String value = getParameter(request, name);
    if (StringUtils.hasText(value)) {
      return Integer.parseInt(value);
    }
    return defaultInt;
  }

  public static long getParameter2Long(HttpServletRequest request, String name, long defaultLong)
  {
    String value = getParameter(request, name);
    if (StringUtils.hasText(value)) {
      return Long.parseLong(value);
    }
    return defaultLong;
  }

  public static boolean getParameter2Boolean(HttpServletRequest request, String name, boolean defaultBoolean)
  {
    String value = getParameter(request, name);
    if (StringUtils.hasText(value)) {
      return (name.equalsIgnoreCase("t")) || (name.equalsIgnoreCase("true")) || (name.equals("1"));
    }
    return defaultBoolean;
  }

  public static String[] getParameterValues(HttpServletRequest request, String name)
  {
    String[] values = request.getParameterValues(name);
    return values != null ? values : new String[0];
  }
}