package com.ian.tools.other;

import java.text.DecimalFormat;

public class TestNumber
{

	public static void main(String[] args)
	{
		DecimalFormat df = new DecimalFormat(",##0.00");
		String bal = "50000";
		bal = df.format(Double.parseDouble(bal.trim().replaceAll("^0", "").replaceAll(",", "")));
		System.out.println(bal);

		String bal2 = "50".trim().replaceAll("^0", ""); // 去空白
		bal2 = bal2.substring(0, bal2.length() - 2); // 出後兩碼
		System.out.println(bal2);

	}

}
