package com.ian.tools.number;

public class NumberUtils {

    	// 抽成 外部檔
	public static void checkNum() {
		String num = "151651651";

		// java 驗證密碼的正則表達式,要求同時有數字和字母，長度最小6，最大16
		String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
		// 規則 第一位[1-9], 第二位以後[0-9] 出現次數{4,14}
		String regex = "[1-9][0-9]{4,14}";
		boolean flag = num.matches(regex);

		if (flag) {
			System.out.println("OK");
		} else {
			System.out.println("No");
		}
	}

    
}
