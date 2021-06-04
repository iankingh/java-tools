package com.ian.tools.collection;

import java.util.HashSet;
import java.util.TreeSet;

public class HashSet4 {

	public static void main(String[] args) {
		HashSet<Integer> hashSet = new HashSet<Integer>();
		TreeSet<Integer> treeSet = new TreeSet<Integer>();

		int number = 0;
		for (int i = 1; i <= 10; i++) {
			number = (int) (Math.random() * 40);

			hashSet.add(number);
			treeSet.add(number);
			System.out.printf("%02d, ", number);
		}
		System.out.printf("\n");
		for (Integer integer : hashSet) {
			System.out.printf("%02d, ", integer);
		}
		System.out.printf("\n");
	}

}
