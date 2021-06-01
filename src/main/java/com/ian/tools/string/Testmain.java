package com.ian.string;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Testmain {
	
	
	
	/**
	 * 
	 * 檢查是否為相同的英數字、連續英文字或連號數字
	 * 
	 * @param value
	 * @param length
	 * @return 是連續 true ,不是 false
	 */
	public boolean continuousLetterAndNumCheck(String value, int length)
	{
		// 是否不合法
		boolean isValidate = false;
		//

		// 計數器
		int counter = 1;
		//
		for (int i = 0; i < value.length() - 1; i++)
		{
			// 當前Ascii值
			int currentAscii = Integer.valueOf(value.charAt(i));
			// 下一個Ascii值
			int nextAscii = Integer.valueOf(value.charAt(i + 1));
			// 滿足區間進行判斷
			if ((this.rangeInDefined(currentAscii, 48, 57) || this.rangeInDefined(currentAscii, 65, 90)
					|| this.rangeInDefined(currentAscii, 97, 122))
					&& (this.rangeInDefined(nextAscii, 48, 57) || this.rangeInDefined(nextAscii, 65, 90)
							|| this.rangeInDefined(nextAscii, 97, 122)))
			{
				// 計算兩數之間差一位關於連續
				if (Math.abs((nextAscii - currentAscii)) == 1)
				{
					// 計數器++
					counter++;
				}
				else
				{
					// 否計算器重新計數
					counter = 1;
				}
			}
			// 滿足連續數字或者字母
			if (counter >= length)
				return !isValidate;
		}
		return isValidate;
	}

	/**
	 * 
	 * 檢查是否為相同的英數字英文字或數字
	 * 
	 * @param value
	 * @param length
	 * @return 是相同 true ,不是 false
	 */

	public boolean SameLetterAndNumCheck(String value, int length)
	{
		// 是否不合法
		boolean isValidate = false;
		//

		// 計數器
		int counter = 1;
		//
		for (int i = 0; i < value.length() - 1; i++)
		{
			// 當前Ascii值
			int currentAscii = Integer.valueOf(value.charAt(i));
			// 下一個ascii值
			int nextAscii = Integer.valueOf(value.charAt(i + 1));
			// 滿足區間進行判斷
			// 計算兩數之間差一位關於連續
			if (currentAscii == nextAscii)
			{
				// 計數器++
				counter++;
			}
			else
			{
				// 否計算器重新計數
				counter = 1;
			}
			// 滿足連續數字或者字母
			if (counter >= length)
				return !isValidate;
			//

		}

		//
		return isValidate;
	}

	/**
	 * 判斷一個數字是否在某個區間
	 *
	 * @param current 當前比對值
	 * @param min 最小範圍值
	 * @param max 最大範圍值
	 * @return
	 */
	public boolean rangeInDefined(int current, int min, int max)
	{

		return Math.max(min, current) == Math.min(current, max);
	}
	

	public Map<String, String> maptest (String f){
		
		Map<String,String> maptest = new HashMap<String, String>();
		return maptest;
		
	}
	
	public static void main(String[] args) {
		
	System.out.println(StringUtils.isNotEmpty(null))	;
		
		Map<String, Object> company_KeyAndUserNameMap = new HashMap<String, Object>();
		// 設定時間格式
		System.out.println("sdddddd"+ company_KeyAndUserNameMap.get("DDDD").toString());
		
		// TODO Auto-generated method stub
		Testmain test = new Testmain();
		System.out.println(test.SameLetterAndNumCheck("AAAAAA", 6));
		
		Map<String,String> maptest = test.maptest("df");
		if(maptest==null){
			
			if(maptest==null){
				
			}
			
		}

	}
}
