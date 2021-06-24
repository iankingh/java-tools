package com.ian.tools.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDate {
	public static void main(String[] args) {

		// 取得今天日期
		Date date = new Date();
		DateFormatUtil dateFormatchange = new DateFormatUtil();
		String newFormateDate = dateFormatchange.DateFormatChange(date);
		log.debug("NewFormateDate : " + newFormateDate);

		// 取現在時間
		Timestamp newTimestamp = new java.sql.Timestamp(new java.util.Date().getTime());
		log.debug("newTimestamp : " + newTimestamp);

		// 設定時間格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 取現在時間(String)
		String NEWLAST_ACCESS_TIME = new java.sql.Timestamp(new java.util.Date().getTime()).toString();
		log.debug("NEWLAST_ACCESS_TIME : " + NEWLAST_ACCESS_TIME);
		// 更新時間格式
		String NEWLAST_ACCESS_TIMEchange = df.format(newTimestamp);

		log.debug("NEWLAST_ACCESS_TIMEchange : " + NEWLAST_ACCESS_TIMEchange);

		log.debug("當前日期和時間的 java.time.LocalDateTime = "
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		log.debug("當前日期和時間的 java.time.LocalDate     = "
				+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		log.debug("當前日期和時間的 java.time.LocalTime     = "
				+ LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

		LocalDate today = LocalDate.now();
		LocalDate christmas = LocalDate.parse("2017-12-25");
		LocalDateTime LocalDateTime = LocalDate.now().atTime(0, 0, 0, 0);

		log.debug("today " + today);
		log.debug("christmas " + christmas);
		log.debug("LocalDateTime " + LocalDateTime);
		;

		//// DateTime joda = new DateTime("2017-11-18T16:08:13.001");
		// log.debug("joda " + joda);
		// LocalTime today22 = LocalTime.now();
		//
		// log.debug("today22 " + today22);
		// Date date2 = new Date();
		// System.out.println("date " + date2);
	}

}
