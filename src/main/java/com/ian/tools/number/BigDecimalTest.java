package com.ian.tools.number;

import java.math.BigDecimal;

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
	}
}
