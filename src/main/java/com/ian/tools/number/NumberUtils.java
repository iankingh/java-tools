package com.ian.tools.number;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberUtils {

	/**
	 * 
	 * @param num
	 */
	public static boolean checkNum(String num) {

		// java 驗證密碼的正則表達式,要求同時有數字和字母，長度最小6，最大16
		String reg = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
		// 規則 第一位[1-9], 第二位以後[0-9] 出現次數{4,14}
		String regex = "[1-9][0-9]{4,14}";
		boolean flag = num.matches(regex);
		if (flag) {
			log.debug("OK");

		} else {
			log.debug("NO");
		}
		return flag;
	}


	// 最近因為需要計算報表中的房物出租率，會用到BigDecimal的運算，目前只知道int, double, float等基本型態的我，連BigDecimal是三小都不太清楚，後來查了許多資料才知道原來BigDecimal可以用在精確的數學計算上面，許多商業用報表都需要用到BigDecimal型別。

// BigDecimal有兩個較常用到的方法：
// 1.	BigDecimal(double val)
// Translates a double into a BigDecimal.
// 2.	BigDecimal(String val)
// Translates the String repre sentation of a BigDecimal into a BigDecimal.

// API將上述方法解釋的非常清楚，而我們如果要精確計算，一定得把數值轉型成String型別，否則得到的結果會是有問題的。簡單來說，若要使用BigDecimal做加法計算必須有以下步驟：
// 1.	需要先將兩個浮點數(double)轉為String，並分別宣告為BigDecimal
// 2.	在其中一個浮點數使用add方法，傳入另一個浮點數作為參數
// 3.	作為運算結果的參數（答案）也須宣告為BigDecimal
// 4.	最後把運算的結果（BigDecimal）再轉換為浮點數

// 在BigDecimal的運算中，加為add, 減為sub，乘為multiply，除法為divide。在做運算之前需針對每一個變數new BigDecimal物件，舉例如下:


	 /**
	  * 
	  * 精確的加法運算，d1 為加數，d2 為被加數，return d1 + d2  
	  * @param d1
	  * @param d2
	  * @return
	  */
	public static double add(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.add(b2).doubleValue();
	}

	/**
	 *  d 為需要四捨五入的數字，scale 為小數點後面要保留幾位數，return 四捨五入後的結果  
	 * @param d
	 * @param scale
	 * @return
	 */
		public static double round(double d,int scale){  
		   if(scale < 0){  
		       throw new IllegalArgumentException(  
			           "The scale must be a positive integer or zero"  
			       );  
		   }  
			   BigDecimal b = new BigDecimal(Double.toString(d));  
		   BigDecimal one = new BigDecimal("1");  
		 return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();  
		}  
		

}
