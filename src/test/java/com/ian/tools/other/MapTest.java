package com.ian.tools.other;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class MapTest {

	public static void main(String[] args) {
		
		Map<String, String > nud =  new HashMap<String, String>();
		
		System.out.println(nud.get("AA"));
		
		

		String[] acnAllList = { "A", "B", "C" };

		//如果map 宣告在外面會頭拿到同一個
		LinkedHashMap<String, String> labelMap = new LinkedHashMap<String, String>();
		LinkedList<Object> labelList = new LinkedList<>();

		for (String strAcn : acnAllList) {
			labelMap.put("ACN", strAcn);
			labelList.add(labelMap);
		}
		System.out.println("labelList : "+ labelList);
		
		//
		LinkedList<Object> labelList2 = new LinkedList<>();

		for (String strAcn : acnAllList) {
			LinkedHashMap<String, String> labelMap22 = new LinkedHashMap<String, String>();
			labelMap22.put("ACN", strAcn);
			labelList2.add(labelMap22);
		}
		System.out.println("labelList22222222 : "+ labelList2);
	}

}
