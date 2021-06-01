package com.ian.tools.collection;

import java.util.HashSet;

public class HashSet1 {
	
	 public static void main(String[] args) {
	        HashSet<Integer> hashSet = new HashSet<Integer>();
	        hashSet.add(10);
	        hashSet.add(5);
	        hashSet.add(3);
	        hashSet.add(2);
	        hashSet.add(2);
	        hashSet.add(2);
	        hashSet.add(4);
	        for (Integer integer : hashSet) {
	            System.out.printf("%02d, ", integer);
	        }
	        System.out.printf("\n");
	    }

}
