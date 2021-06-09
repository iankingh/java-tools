package com.ian.tools.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RemoveArray {

	public static void main(String[] args) {
		//
		List<String> list = new ArrayList<String>(Arrays.asList("", "HI1", null, "How1"));
		System.out.println(list);
		System.out.println(Arrays.asList("", null));
		list.removeAll(Arrays.asList("", null));
		System.out.println(list);

		//
		List<String> list2 = new ArrayList<String>(Arrays.asList("", "Hi2", null, "How2"));
		list2.removeAll(Collections.singleton(null));
		list2.removeAll(Collections.singleton(""));
		System.out.println(list2);
		
//		Another way to do this now that we have Java 8 lambda expressions.
		List<String> arrayList = new ArrayList<String>(Arrays.asList("", "Hi3", null, "How3"));
		arrayList.removeIf(item -> item == null || item.isEmpty());
		System.out.println(arrayList);
		// arrayList.removeIf(String::isNullOrEmpty);
		// arrayList.removeIf(StringUtils::isEmpty);
		// arrayList.removeIf(StringUtils::isBlank);
	}

}
