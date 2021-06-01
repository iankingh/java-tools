package com.ian.tools.enums;

public class EnumTest {
	public static void main(String[] args) {
		for (DayEnum day : DayEnum.values()) {
			System.out.println(day.name());
		}

		for (DayEnum day : DayEnum.values()) {
			System.out.println(day.toString());
		}

		for (AcnoTypeEnum acnoTypeEnum : AcnoTypeEnum.values())
			System.out.println(acnoTypeEnum.name());
	}

}
