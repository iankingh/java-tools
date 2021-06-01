package com.ian.string;

public class StringReplace {

	public static void main(String[] args) {

		String bankCard = "10112054";
		int hideLength = 8;// 替換位數
		int startIndex = bankCard.length() + 1 - hideLength;// 替換位數
		String replaceSymbol = "*";// 替換字元
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < bankCard.length(); i++) {
			char number = bankCard.charAt(i);
			if (i >= startIndex - 1) {
				stringBuilder.append(replaceSymbol);
			} else {
				stringBuilder.append(number);
			}
		}
		System.out.println(stringBuilder);

		System.out.println(strReplaceByMask(bankCard));
	}

	static int HIDELENGHTSTAR = 5;
	static int HIDELENGHTEND = 3;

	/**
	 * 字串遮罩從後面開始
	 * 
	 * @param replaceStr
	 *            遮罩字串
	 * @return
	 */
	public static String strReplaceByMask(String replaceStr) {
		int startIndex = replaceStr.length() - HIDELENGHTSTAR;// 開始
		int endIndex = replaceStr.length() + 1 - HIDELENGHTEND;// 結束
		String replaceSymbol = "*";// 替換字元
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < replaceStr.length(); i++) {
			char number = replaceStr.charAt(i);
			if (i >= startIndex && i < endIndex) {
				stringBuilder.append(replaceSymbol);
			} else {
				stringBuilder.append(number);
			}
		}
		return String.valueOf(stringBuilder);
	}

	/**
	 * 字串遮罩
	 * 
	 * @param replaceStr
	 *            遮罩字串
	 * @return
	 */
	public static String strReplaceByMask2(String replaceStr) {
		int startIndex = replaceStr.length() - HIDELENGHTSTAR;// 起始
		String replaceSymbol = "*";// 替換字元
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < replaceStr.length(); i++) {
			char number = replaceStr.charAt(i);
			if (i >= startIndex) {
				stringBuilder.append(replaceSymbol);
			} else {
				stringBuilder.append(number);
			}
		}
		return String.valueOf(stringBuilder);
	}

}
