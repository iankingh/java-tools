package com.ian.tools.collection;

import java.util.HashSet;

public class HashSet2 {

    public static void main(String[] args) {

        HashSet<Integer> hashSet = new HashSet<Integer>();
        int[] i1 = { 15, 5, 3, 4 };
        for (int i = 0; i < i1.length; i++) {
            hashSet.add(i1[i]);
        }
        for (Integer integer : hashSet) {
            System.out.printf("%02d, ", integer);
        }
        System.out.printf("\n");
    }

}
