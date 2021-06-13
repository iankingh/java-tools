package com.ian.tools.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * 本類別詳細說明。
 * <p/>
 * File Name: MapUtil <br>
 * <p/>
 * 
 * @Description: 處理Map
 * @author Ian
 * @version 1.0, 2021年06月02日
 * @see https://blog.csdn.net/qq_27093465/article/details/79154566
 */
@Slf4j
public class MapUtil {

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
	 * 
     * 用來移除Map 中 nll 的值
     * 
     * Iterator 是工作在一個獨立的線程中，並且擁有一個互斥鎖。
	 * 被創建之後會建立一個自己的單鏈索引表，當原來的對象發生變化時，這個索引表的內容不會同步改變，
	 * 所以當索引方法往後移動的時候就找不到要迭代的對象，所以按照fail-fast Iterator會馬上拋出
	 * java.util.ConcurrentModificationException 異常。所以迭代器
	 * 在工作的時候是不允許被迭代的對像被改變的。但是你可以使用迭代器來的方法 remove() 刪除對象，
	 * Iterator.remove()方法會在刪除當前對象的同時維護索引的隊列。
	 * 
	 * @param obj
	 * @param iterator
     * @see https://blog.csdn.net/wuyu10FG/article/details/46445477
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


    /**
     * 
     * 用來移除Map 中 nll 的值
	 * 方式一：運算次數多
	 * 
	 * @param paramMap
	 * @return
     * @see https://blog.csdn.net/u010686046/article/details/78467741
	 */
	public static Map<String, String> removeMapEmptyValue1(Map<String, String> paramMap) {
		Set<String> set = paramMap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String str = it.next();
			if (paramMap.get(str) == null || paramMap.get(str) == "") {
				paramMap.remove(str);
				set = paramMap.keySet();
				it = set.iterator();
			}
		}
		return paramMap;
	}

	/**
     * 用來移除Map 中 nll 的值
	 * 方式二：
	 * 
	 * @param paramMap
	 * @return
	 */
	public static Map<String, String> removeMapEmptyValue(Map<String, String> paramMap) {
		Set<String> set = paramMap.keySet();
		Iterator<String> it = set.iterator();
		List<String> listKey = new ArrayList<String>();
		while (it.hasNext()) {
			String str = it.next();
			if (paramMap.get(str) == null || "".equals(paramMap.get(str))) {
				listKey.add(str);
			}
		}
		for (String key : listKey) {
			paramMap.remove(key);
		}
		return paramMap;
	}

	   /**
     * 將輸入的map字串轉為map物件
     *
     * @param str EX : String value = "{ACN=01077258228, TYPE=1, FDPNUM=0009205}";
     * @return EX : Map {FDPNUM=0009205, ACN=01077258228, TYPE=1}
     */
    public Map<String, String> StringConvertMap(String str) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            str = str.substring(1, str.length() - 1); // remove curly brackets
            String[] keyValuePairs = str.split(","); // split the string to creat key-value pairs
            for (String pair : keyValuePairs) // iterate over the pairs
            {
                String[] entry = pair.split("="); // split the pairs to get key and value
                if (entry.length > 1) {
                    map.put(entry[0].trim(), entry[1].trim()); // add them to the hashmap and trim whitespaces
                }
            }
        } catch (Exception e) {
            log.error("Exception: {} and map:{}", e, map);
        }
        return map;
    }



}