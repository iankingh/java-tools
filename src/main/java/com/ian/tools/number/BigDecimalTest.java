package com.ian.tools.number;

import java.math.BigDecimal;

/**
 * 參考 Jax 的工作紀錄: [轉載] [Java] BigDecimal的四則運算
 * https://jax-work-archive.blogspot.com/2015/02/java-bigdecimal.html
 * 
 */
public class BigDecimalTest {
	public static void main(String[] args) {
		System.out.println(0.0000000000000000000000000000000005 + 0.01);
		System.out.println(1.0 - 0.42);
		System.out.println(4.015 * 100);
		System.out.println(123.3 / 100);
		BigDecimal a = new BigDecimal("0.0000000000000000000000000000000005");
		BigDecimal b = new BigDecimal("0.01");
		System.out.println(a.intValue());
		System.out.println(a.add(b));
		System.out.println(b);
		String repStr = "12,000.00".replaceAll(",", "");
		BigDecimal c = new BigDecimal(repStr);
		System.out.println(c);

		// 宣告第一個需要運算的數值
		BigDecimal bigNumber = new BigDecimal("89.1234567890123456789"); // 宣告第二個需要運算的數值
		BigDecimal bigRate = new BigDecimal(1000);

		// 宣告運算後的答案為 bigResult
		BigDecimal bigResult = new BigDecimal(0);

		// bigResult 為 bigNumBer * bigRate
		bigResult = bigNumber.multiply(bigRate);

		// 印出 bigResult
		System.out.println(bigResult.toString());

		// 將 bigNumber 四捨五入變為 double 型態
		double dData = bigNumber.doubleValue();

		// 印出 dDate
		System.out.println(dData);

		// 宣告 data2 為 bigNumber/bigRate 並四捨五入至小數點第二位
		double data2 = bigNumber.divide(bigRate, 2, BigDecimal.ROUND_HALF_UP).doubleValue();

		// 印出 data2
		System.out.println(data2);

	}
}
