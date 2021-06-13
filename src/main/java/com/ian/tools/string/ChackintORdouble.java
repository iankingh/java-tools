package com.ian.tools.string;

import java.util.Scanner;

public class ChackintORdouble
{

	public static void main(String[] args)
	{

		// -----------AA
		Scanner sc = new Scanner(System.in);

		String number = sc.nextLine();

		if (number.contains("."))
		{
			// 说明是一个小数
			double d = Double.valueOf(number);
			System.out.println("double");
		}
		else
		{
			int i = Integer.valueOf(number);
			System.out.println("int");
		}
		// -----------AA

		// -----------BB
		// s是获得到的String值
		int index = number.indexOf(".");
		// indexOf()返回小数点的下标，找不到的话返回-1
		boolean flag = false;
		if (index != -1)
		{
			flag = true; // true表示有小数点，就是说是double数据
		}

		System.out.println(" BBBBB" + flag);

		// -----------BB

	}

}
