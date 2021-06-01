package com.ian.tools.other;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static int INTERCONVERSION = 1;
	public static int NOT_INTERCONVERSION = 2;

	/**
	 * 取得民國年
	 * 
	 * @return
	 */
	public static String getCurrentTWYear() {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR) - 1911);
		return year;
	}

	/**
	 * 取得目前民國年月日(YYYMMDD)
	 * 
	 * @return
	 */
	public static String getTWDate(String sign) {
		// if(StrUtil.isEmpty(sign)){sign="";}
		Calendar cal = Calendar.getInstance();
		String theDate = null;
		String month = String.valueOf((cal.get(Calendar.MONTH) + 1) / 10) + ""
				+ String.valueOf((cal.get(Calendar.MONTH) + 1) % 10);
		String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH) / 10) + ""
				+ String.valueOf(cal.get(Calendar.DAY_OF_MONTH) % 10);
		theDate = getCurrentTWYear() + sign + month + sign + day;
		return theDate;
	}

	public static String getCurentDateTime(String pattern) {
		// pattern = StrUtil.isNotEmpty(pattern)?pattern:"yyyyMMddHHmmss";
		SimpleDateFormat sdFormat = new SimpleDateFormat(pattern);
		Date current = new Date();
		String d = sdFormat.format(current);
		return d;
	}

	/**
	 * 去得目前時間(HHMMSS)
	 * 
	 * @param sign
	 * @return
	 */
	public static String getTheTime(String sign) {
		// if(StrUtil.isEmpty(sign)){sign="";}
		Calendar cal = Calendar.getInstance();
		String theTime = null;
		String theHours = String.valueOf(cal.get(Calendar.HOUR_OF_DAY) / 10) + ""
				+ String.valueOf(cal.get(Calendar.HOUR_OF_DAY) % 10);
		String theMinutes = String.valueOf(cal.get(Calendar.MINUTE) / 10) + ""
				+ String.valueOf(cal.get(Calendar.MINUTE) % 10);
		String theSeconds = String.valueOf(cal.get(Calendar.SECOND) / 10) + ""
				+ String.valueOf(cal.get(Calendar.SECOND) % 10);
		theTime = theHours + sign + theMinutes + sign + theSeconds;
		return theTime;
	}

	public static String getDate(String sign) {
		// if(StrUtil.isEmpty(sign)){sign="";}
		Calendar cal = Calendar.getInstance();
		String theDate = null;
		String month = String.valueOf((cal.get(Calendar.MONTH) + 1) / 10) + ""
				+ String.valueOf((cal.get(Calendar.MONTH) + 1) % 10);
		String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH) / 10) + ""
				+ String.valueOf(cal.get(Calendar.DAY_OF_MONTH) % 10);
		theDate = cal.get(Calendar.YEAR) + sign + month + sign + day;
		// System.out.println("theDate>>"+theDate);
		return theDate;
	}

	/**
	 * 取得下個日期
	 * 
	 * @param retPattern 輸入回傳的格式 ex yyyy/MM/dd =2016/07/16
	 * @return
	 */
	public static String getNextDate(String retPattern) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = null;
		Date date = null;
		try {
			date = format.parse(getDate(""));
			calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			format.applyPattern(retPattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("next date>>" + format.format(calendar.getTime()));
		return format.format(calendar.getTime());
	}

	/**
	 * 取得下N天日期
	 * 
	 * @param retPattern
	 * @param num
	 * @return
	 */
	public static String getNext_N_Date(String retPattern, Integer num) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = null;
		Date date = null;
		try {
			date = format.parse(getDate(""));
			calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, num);
			format.applyPattern(retPattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("next date>>" + format.format(calendar.getTime()));
		return format.format(calendar.getTime());
	}

	/**
	 * 取得2個日期間的天數
	 * 
	 * @param firstdate
	 * @param seconddate
	 * @return
	 */
	public static int getDiffDate(String firstdate, String seconddate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		long d1 = 0;
		long d2 = 0;
		BigDecimal diffdays = new BigDecimal(0);
		try {
			// System.out.println("firstdate>>"+firstdate+"seconddate>>"+seconddate);
			calendar.setTime(sdf.parse(firstdate));
			calendar2.setTime(sdf.parse(seconddate));
			d1 = calendar.getTimeInMillis();
			d2 = calendar2.getTimeInMillis();
			diffdays = new BigDecimal((d1 - d2) / (1000 * 60 * 60 * 24)).abs();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diffdays.intValue() + 1;

	}

	/**
	 * 取得2個日期間的天數
	 * 
	 * @param firstdate
	 * @param seconddate
	 * @return
	 */
	public static int getDiffTimeStamp(String firstdate, String seconddate) {
		// System.out.println("firstdate>>"+firstdate);
		// System.out.println("seconddate>>"+seconddate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		long d1 = 0;
		long d2 = 0;
		BigDecimal diffdays = new BigDecimal(0);
		try {
			// System.out.println("firstdate>>"+firstdate+"seconddate>>"+seconddate);
			calendar.setTime(sdf.parse(firstdate));
			calendar2.setTime(sdf.parse(seconddate));
			d1 = calendar.getTimeInMillis();
			d2 = calendar2.getTimeInMillis();
			// diffdays = new BigDecimal((d1-d2)/(1000*60*60*24)).abs();
			diffdays = new BigDecimal((d1 - d2) / (1000)).abs();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diffdays.intValue() + 1;

	}

	/**
	 * 西元年 民國年互轉
	 * 
	 * @param type(1(INTERCONVERSION)=西元民國互轉;2(NOT_INTERCONVERSION)=不轉)
	 * @param AD
	 * @param beforeFormat(轉換前的格式)
	 * @param afterFormat(轉換後的格式)                                       範例
	 *                                                                  convertDate("2013-05-08
	 *                                                                  21:10:10
	 *                                                                  ","yyyy-MM-dd
	 *                                                                  HH:mm:ss","yyyyMMdd
	 *                                                                  HH:mm:ss")
	 *                                                                  convertDate("0102-05-08
	 *                                                                  21:20:10","yyyy-MM-dd
	 *                                                                  HH:mm:ss","yyyyMMdd
	 *                                                                  HH:mm:ss"
	 * @return
	 */
	public static String convertDate(int type, String AD, String beforeFormat, String afterFormat) {// 轉年月格式
		// if (StrUtil.isEmpty(AD)) return "";
		SimpleDateFormat df4 = new SimpleDateFormat(beforeFormat);
		SimpleDateFormat df2 = new SimpleDateFormat(afterFormat);
		Calendar cal = Calendar.getInstance();
		String tmp = "";
		try {
			cal.setTime(df4.parse(AD));
			if (type == INTERCONVERSION) {
				if (cal.get(Calendar.YEAR) > 1492) {
					if (cal.get(Calendar.YEAR) - 1911 < 100) {
						tmp = "0";
					}
					cal.add(Calendar.YEAR, -1911);
				} else {
					cal.add(Calendar.YEAR, +1911);
				}
			}
			return tmp + df2.format(cal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 此方法會因為作業系統語系不同而產生相對應的字串 輸入日期回傳星期幾
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static String date2DayofWeek(String dateString, String pattern) throws ParseException {
		// pattern = StrUtil.isEmpty(pattern)?"yyyy-MM-dd":pattern;
		// SimpleDateFormat dateStringFormat = new SimpleDateFormat( "yyyy-MM-dd" );
		SimpleDateFormat dateStringFormat = new SimpleDateFormat(pattern);
		Date date = dateStringFormat.parse(dateString);
		// SimpleDateFormat date2DayFormat = new SimpleDateFormat( "u" );
		SimpleDateFormat date2DayFormat = new SimpleDateFormat("E");
		// System.out.println(""+date2DayFormat.format( date ));
		return date2DayFormat.format(date);
	}

	public static enum Week {
		Mon, Tue, Wed, Thu, Fri, Sat, Sun
	}

}
