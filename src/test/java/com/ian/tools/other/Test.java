package com.ian.tools.other;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class Test
{
	public static String camelCase(String input, boolean firstCharacterUppercase)
	{
		StringBuilder sb = new StringBuilder();
		boolean nextUpperCase = false;
		for (int i = 0; i < input.length(); i++)
		{
			char c = input.charAt(i);
			switch (c)
			{
			case '_':
			case '-':
			case '@':
			case '$':
			case '#':
			case ' ':
				if (sb.length() > 0)
				{
					nextUpperCase = true;
				}
				break;
			default:
				if (nextUpperCase)
				{
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				}
				else
				{
					sb.append(Character.toLowerCase(c));
				}
				break;
			}
		}
		if (firstCharacterUppercase)
		{
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}
		return sb.toString();
	}

	public void testGsonConvert()
	{
		Gson gson = new Gson();
		String gradeIds = "[fc3938f6-e0b0-4fa9-b638-185475934336','bd528189-f1f9-48fc-9014-0580782c2dcb']";
		List<String> ids = gson.fromJson(gradeIds, new TypeToken<List<String>>()
		{
		}.getType());
		System.out.println(ids);
	}

	public static void main(String[] args)
	{

		Test t = new Test();
		t.testGsonConvert();

		System.out.println(t.camelCase("easy_book", true));

		Integer id = new Integer("1000000000");
		BigDecimal bigDecimal = new BigDecimal("8000000080000000000000000000000000000000000000000000000");
		System.out.println("iD " + id);
		System.out.println("big " + bigDecimal);

		boolean isInt = false;

		boolean isDouble = false;

		Scanner scanner = new Scanner(System.in);

		String input = scanner.next();

		char c1[] = new char[input.length()];

		for (int i = 0; i < input.length(); i++)
		{
			c1[i] = input.charAt(i);
			if (Character.isDigit(c1[i]) == false)
			{
				isDouble = true;
			}
		}
		if (isDouble == false)
		{
			isInt = true;
		}
		if (isInt == true)
		{
			System.out.println("int");
		}
		else if (isDouble == true)
		{
			System.out.println("double");
		}
	}
}
