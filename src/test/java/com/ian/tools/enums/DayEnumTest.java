package com.ian.tools.enums;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DayEnumTest {




	@Test
	public  void DatyEnumTest() {
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
