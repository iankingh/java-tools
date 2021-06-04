package com.ian.tools.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 	// ---------------------
	// 作者：自古红蓝出CP
	// 来源：CSDN
	// 原文：https://blog.csdn.net/Blue_Red_1314/article/details/72897078
	// 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class CheckTime {


	public void isBelongTest() {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");// 设置日期格式
		Date now = null;
		Date beginTime = null;
		Date endTime = null;
		try {
			now = df.parse(df.format(new Date()));
			beginTime = df.parse("06:00");
			endTime = df.parse("22:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Boolean flag = belongCalendar(now, beginTime, endTime);
		System.out.println(flag);
	}

	/**
	 * 判断时间是否在时间段内
	 * 
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}


}
