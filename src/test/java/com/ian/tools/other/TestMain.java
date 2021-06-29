package com.ian.tools.other;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;




public class TestMain {
	public static String camelCase(String input, boolean firstCharacterUppercase) {
		StringBuilder sb = new StringBuilder();
		boolean nextUpperCase = false;
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			switch (c) {
				case '_':
				case '-':
				case '@':
				case '$':
				case '#':
				case ' ':
					if (sb.length() > 0) {
						nextUpperCase = true;
					}
					break;
				default:
					if (nextUpperCase) {
						sb.append(Character.toUpperCase(c));
						nextUpperCase = false;
					} else {
						sb.append(Character.toLowerCase(c));
					}
					break;
			}
		}
		if (firstCharacterUppercase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}
		return sb.toString();
	}
	@Test
	public void testGsonConvert() {
		Gson gson = new Gson();
		String gradeIds = "[fc3938f6-e0b0-4fa9-b638-185475934336','bd528189-f1f9-48fc-9014-0580782c2dcb']";
		List<String> ids = gson.fromJson(gradeIds, new TypeToken<List<String>>() {
		}.getType());
		System.out.println(ids);
	}


	@Test
	public void test1() throws UnsupportedEncodingException {
		String unicode = "中文";
		System.out.println("UTF-16: " + unicode);
		char y[] = unicode.toCharArray();
		for (int i = 0; i < y.length; i++) {
			System.out.printf("%x ", (int) y[i]);
		}
		System.out.println();

		// unicode 轉成 Big5 編碼
		byte[] big5 = unicode.getBytes("Big5");
		// Big5 編碼 轉回 unicode
		unicode = new String(big5, "Big5");
		System.out.println("Big5: " + unicode);
		byte x[] = big5;
		for (int i = 0; i < x.length; i++) {
			System.out.printf("%x ", x[i]);
		}
		System.out.println();
		byte[] utf8 = null;
		// unicode 轉成 UTF-8 編碼
		// utf8 = unicode.getBytes("UTF-8");

		// Big5 編碼 轉回 unicode 再轉成 UTF-8 編碼
		utf8 = new String(big5, "Big5").getBytes("UTF-8");

		System.out.println("UTF-8: " + unicode);
		byte z[] = utf8;
		for (int i = 0; i < z.length; i++) {
			System.out.printf("%x ", z[i]);
		}

	}

}
