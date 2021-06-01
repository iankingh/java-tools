package com.ian.tools.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 本類別詳細說明。
 * <p/>
 * Package: com.ian.collection <br>
 * File Name: removeMapEmptyValue <br>
 * <p/>
 * Purpose: 
 * 
	 作者：Fly小美
	 来源：CSDN
	 原文：https://blog.csdn.net/u010686046/article/details/78467741
	 版权声明：本文为博主原创文章，转载请附上博文链接
 * 
 * <br>
 * 
 * @ClassName: com.ian.collection.removeMapEmptyValue
 * @Description: TODO
 * @Company: Team.
 * @author Ian
 * @version 1.0, 2019年3月1日
 */
public class RemoveMapEmptyValue {

	/**
	 * 方式一：运算次数较多
	 * 
	 * @param paramMap
	 * @return
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

}
