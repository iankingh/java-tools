package com.ian.tools.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 
 * @ClassName: com.ian.tools.collection.MapRemoveNullUtil
 * @Description: 用來移除Map 中 nll 的值
 * @author Ian
 * @version 1.0, 2021年06月02日
 * @see https://blog.csdn.net/wuyu10FG/article/details/46445477
 */
public class MapRemoveNullUtil {

	/**
	 * 
	 * 移除Map中空鍵或者value空值
	 * 
	 * @param map
	 */
	public static void removeNullEntry(Map map) {
		removeNullKey(map);
		removeNullValue(map);
	}

	/**
	 * 移除map的空key
	 * 
	 * @param map
	 * @return
	 */
	public static void removeNullKey(Map<?, ?> map) {
		Set<?> set = map.keySet();
		for (Iterator<?> iterator = set.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			remove(obj, iterator);
		}
	}

	/**
	 * 移除map中的value空值
	 * 
	 * @param map
	 * @return
	 */
	public static void removeNullValue(Map<?, ?> map) {
		Set<?> set = map.keySet();
		for (Iterator<?> iterator = set.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			Object value = (Object) map.get(obj);
			remove(value, iterator);
		}
	}

	/**
	 * Iterator 是工作在一個獨立的線程中，並且擁有一個互斥鎖。
	 * 被創建之後會建立一個自己的單鏈索引表，當原來的對象發生變化時，這個索引表的內容不會同步改變，
	 * 所以當索引方法往後移動的時候就找不到要迭代的對象，所以按照fail-fast Iterator會馬上拋出
	 * java.util.ConcurrentModificationException 異常。所以迭代器
	 * 在工作的時候是不允許被迭代的對像被改變的。但是你可以使用迭代器來的方法 remove() 刪除對象，
	 * Iterator.remove()方法會在刪除當前對象的同時維護索引的隊列。
	 * 
	 * @param obj
	 * @param iterator
	 */
	private static void remove(Object obj, Iterator<?> iterator) {
		if (obj instanceof String) {
			String str = (String) obj;

			if (str == null || str.length() == 0) {
				iterator.remove();
			}
		} else if (obj instanceof Collection) {
			Collection<?> col = (Collection<?>) obj;
			if (col == null || col.isEmpty()) {
				iterator.remove();
			}

		} else if (obj instanceof Map) {
			Map temp = (Map) obj;
			if (temp == null || temp.isEmpty()) {
				iterator.remove();
			}

		} else if (obj instanceof Object[]) {
			Object[] array = (Object[]) obj;
			if (array == null || array.length <= 0) {
				iterator.remove();
			}
		} else {
			if (obj == null) {
				iterator.remove();
			}
		}
	}


	public static void main(String[] args) {
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
		MapRemoveNullUtil.removeNullKey(map);
		System.out.println(map);
		System.out.println("移除 null value-----------------------");
		MapRemoveNullUtil.removeNullValue(map);
		System.out.println(map);
		System.out.println("----------------------------------");
		MapRemoveNullUtil.removeNullEntry(map);
		System.out.println(map);



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
