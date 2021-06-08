package com.ian.tools.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class MapUtilTest {

    
    @Test
	public void removeNullEntryTest() {
		Map map = new HashMap();
		map.put(1, "第一個int ");
		map.put("2", "第二個是String");
		map.put(new String[] { "1", "2" }, "第三個是Array");
		map.put(new ArrayList(), "第4个值是List");
		map.put(new HashMap(), "空Map");
		map.put("5", "第5個");
		map.put("6", null);
		map.put("7", "");
		map.put("8", "  ");
		System.out.println("開始--------------------------");
		System.out.println(map);
		System.out.println("移除 null Key-----------------------");
		MapUtil.removeNullKey(map);
		System.out.println(map);
		System.out.println("移除 null value-----------------------");
		MapUtil.removeNullValue(map);
		System.out.println(map);
		System.out.println("----------------------------------");
		MapUtil.removeNullEntry(map);
		System.out.println(map);

	}

    @Test
    public void test(){
        
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("1", "One");
		map2.put("2", "Two");
		map2.put("3", null);
		map2.put("4", "Four");
		map2.put("5", null);
		map2.put("6", "");
		map2.put("7", "seven");
		System.out.println("map2 :{} " + map2);
		// You can use the Java 8 method Collection.removeIf for this purpose:
		map2.values().removeIf(Objects::isNull);
		System.out.println("map22 :{} " + map2);
		// If you are using pre-Java 8, you can use:
		Collection<String> values = map2.values();
		while (values.remove(null) || values.remove("")) {
		}
		System.out.println("map23 :{} " + map2);
    }
    
}
