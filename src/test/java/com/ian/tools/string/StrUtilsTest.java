package com.ian.tools.string;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrUtilsTest {

	@Test
	public void replaceBlankTest() {
		String s = "just do it!";
		String sr = StrUtils.removeAllBlank(s);
	
		log.debug (sr);

	}

	@Test
	public void SameLetterAndNumCheckTest() {
		System.out.println(StrUtils.SameLetterAndNumCheck("AAAAAA", 6));
	}

	@Test
	public void strReplaceByMaskTest() {
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

		System.out.println(StrUtils.strReplaceByMask(bankCard));

	}

}
