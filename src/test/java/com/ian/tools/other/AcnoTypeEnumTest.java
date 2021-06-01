package com.ian.tools.other;

import com.ian.tools.enums.AcnoTypeEnum;

public class AcnoTypeEnumTest {

	public static void main(String[] args) {
		String[] acnotype = AcnoTypeEnum.valueOf("ACCSET").getAcnotype();
		System.out.println(acnotype);

	}

}
