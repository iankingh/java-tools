package com.ian.tools.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil
{
	public String DateFormatChange(Date date)
	{
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String newFormatDate = df.format(date);
		return newFormatDate;
	}

}
