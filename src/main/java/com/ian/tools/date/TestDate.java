package com.ian.tools.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestDate {
	public static void main(String[] args) {

		// 取得今天日期
		Date date = new Date();
		DateFormatMethod dateFormatchange = new DateFormatMethod();
		String newFormateDate = dateFormatchange.DateFormatChange(date);
		System.out.println("NewFormateDate : " + newFormateDate);
		// 取得今天日期

		// 取現在時間
		Timestamp newTimestamp = new java.sql.Timestamp(new java.util.Date().getTime());
		System.out.println("newTimestamp : " + newTimestamp);

		// 設定時間格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 取現在時間(String)
		String NEWLAST_ACCESS_TIME = new java.sql.Timestamp(new java.util.Date().getTime()).toString();
		System.out.println("NEWLAST_ACCESS_TIME : " + NEWLAST_ACCESS_TIME);
		// 更新時間格式
		String NEWLAST_ACCESS_TIMEchange = df.format(newTimestamp);

		System.out.println("NEWLAST_ACCESS_TIMEchange : " + NEWLAST_ACCESS_TIMEchange);

		System.out.println("當前日期和時間的 java.time.LocalDateTime = "
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		System.out.println("當前日期和時間的 java.time.LocalDate     = "
				+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		System.out.println("當前日期和時間的 java.time.LocalTime     = "
				+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

		LocalDate today = LocalDate.now();
		LocalDate christmas = LocalDate.parse("2017-12-25");
		LocalDateTime LocalDateTime = LocalDate.now().atTime(0, 0, 0, 0);

		System.out.println("today " + today);
		System.out.println("christmas " + christmas);
		System.out.println("LocalDateTime " + LocalDateTime);
		;

		//// DateTime joda = new DateTime("2017-11-18T16:08:13.001");
		// System.out.println("joda " + joda);
		// LocalTime today22 = LocalTime.now();
		//
		// System.out.println("today22 " + today22);
		// Date date2 = new Date();
		// System.out.println("date " + date2);
	}

}
