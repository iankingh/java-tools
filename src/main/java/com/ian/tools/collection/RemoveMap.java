package com.ian.tools.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RemoveMap {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("1", "One");
		map.put("2", "Two");
		map.put("3", null);
		map.put("4", "Four");
		map.put("5", null);
		map.put("6", "");
		map.put("7", "seven");
		System.out.println("map :{} " + map);
		// You can use the Java 8 method Collection.removeIf for this purpose:
//		map.values().removeIf(Objects::isNull);
//		System.out.println("map2 :{} " + map);
		// If you are using pre-Java 8, you can use:
		Collection<String> values = map.values();
		while (values.remove(null)||values.remove("")) {
		}
		System.out.println("map3 :{} " + map);

	}

}
