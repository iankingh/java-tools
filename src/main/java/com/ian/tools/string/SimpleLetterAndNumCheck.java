package com.ian.tools.string;

public class SimpleLetterAndNumCheck {

	/**
	 * 
	 * 
	 * 
	 * 
	 * /** JAVA 判断是否连续字母或者数字
	 * 
	 * JAVA实现连续字母或者数字： 实现思路：统一转成ASCII进行计数判断，纯数字、纯字母 //纯数字(数字0 -- 数字9,对应ASCII为48
	 * -- 57) //大写纯字母(大写字母A -- 大写字母Z,对应ASCII为65 -- 90) //小写纯字母(小写字母a --
	 * 小写字母z，对应ASCII为97 -- 122)
	 */

	/**
	 * @ClassName: SimpleLetterAndNumCheck.java
	 * @Description: JAVA实现连续字母或者数字：<br/>
	 *               实现思路：统一转成ASCII进行计数判断，纯数字、纯字母<br/>
	 *               //纯数字(数字0 -- 数字9,对应ASCII为48 -- 57)<br/>
	 *               //大写纯字母(大写字母A -- 大写字母Z,对应ASCII为65 -- 90)<br/>
	 *               //小写纯字母(小写字母a -- 小写字母z，对应ASCII为97 -- 122)<br/>
	 * 
	 * 
	 *               檢查是否為相同的英數字、連續英文字或連號數字
	 * 
	 * @param value
	 * @param length
	 * @return 是 true ,不是 false
	 */

	public boolean simpleLetterAndNumCheck(String value, int length) {
		// 是否不合法
		boolean isValidate = false;
		//
		int i = 0;
		// 計數器
		int counter = 1;
		//
		for (; i < value.length() - 1;) {
			// 當前Ascii值
			int currentAscii = Integer.valueOf(value.charAt(i));
			// 下一個ascii值
			int nextAscii = Integer.valueOf(value.charAt(i + 1));
			// 滿足區間進行判斷
			if ((this.rangeInDefined(currentAscii, 48, 57) || this.rangeInDefined(currentAscii, 65, 90)
					|| this.rangeInDefined(currentAscii, 97, 122))
					&& (this.rangeInDefined(nextAscii, 48, 57) || this.rangeInDefined(nextAscii, 65, 90)
							|| this.rangeInDefined(nextAscii, 97, 122))) {
				// 計算兩數之間差一位關於連續
				if (Math.abs((nextAscii - currentAscii)) == 1) {
					// 計數器++
					counter++;
				} else {
					// 否計算器重新計數
					counter = 1;
				}
			}
			// 滿足連續數字或者字母
			if (counter >= length)
				return !isValidate;
			//
			i++;
		}

		//
		return isValidate;
	}

	/**
	 * 判斷一個數字是否在某個區間
	 *
	 * @param current
	 *            當前比對值
	 * @param min
	 *            最小範圍值
	 * @param max
	 *            最大範圍值
	 * @return
	 */
	public boolean rangeInDefined(int current, int min, int max) {
		//
		return Math.max(min, current) == Math.min(current, max);
	}

	public boolean letterAndNumCheck(String value, int length) {
		// 是否不合法
		boolean isValidate = false;
		//
		int i = 0;
		// 計數器
		int counter = 1;
		//
		for (; i < value.length() - 1;) {
			// 當前Ascii值
			int currentAscii = Integer.valueOf(value.charAt(i));
			// 下一個ascii值
			int nextAscii = Integer.valueOf(value.charAt(i + 1));
			// 滿足區間進行判斷
			// 計算兩數之間差一位關於連續
			if (currentAscii == nextAscii) {
				// 計數器++
				counter++;
			} else {
				// 否計算器重新計數
				counter = 1;
			}
			// 滿足連續數字或者字母
			if (counter >= length)
				return !isValidate;
			//
			i++;
		}

		//
		return isValidate;
	}

	public static void main(String[] args) {
		//
		// String str = "1234677A!@#B0abcdeg123456DDzZ09";
		// 连续不合法区间值校验
		// String str = ":;<=>?@A";
		//
		String str = "sAAAAAA";
		SimpleLetterAndNumCheck s = new SimpleLetterAndNumCheck();
		// boolean validate = s.simpleLetterAndNumCheck(str, 6);
		boolean validate = s.letterAndNumCheck(str, 6);

		if (validate) {
			System.out.println("连续字母或者数字");
		} else {
			System.out.println("合法的校验");
		}
	}

}
