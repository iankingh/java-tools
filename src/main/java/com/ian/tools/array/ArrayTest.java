package com.ian.tools.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ArrayTest {

	public static void main(final String[] args) {

		// create an array of strings
		final List<Map> listStringT = new ArrayList<>();
		final Map map = new HashMap<>();
		final Map map2 = new HashMap<>();
		map.put("ACN", "1");
		map2.put("ACN", "2");
		listStringT.add(map);
		listStringT.add(map2);
		System.out.println("listStringT : " + listStringT);
		final List listStringT2 = new ArrayList<>();
		for (final Map map3 : listStringT) {
			map3.get("ACN");
			System.out.println(map3.get("ACN"));
			listStringT2.add(map3.get("ACN"));
		}
		System.out.println(listStringT2);

		// create an array of strings
		final String strArry[] = new String[] { "TestA", "TestB", "TestC", "TestD" };
		final List<String> listString = Arrays.asList(strArry);
		System.out.println("listString : " + listString);
		// create an array of Int
		final ArrayList<Integer> accountNoList = new ArrayList<>();
		accountNoList.add(1);
		accountNoList.add(2);
		accountNoList.add(3);
		accountNoList.add(4);
		// new 一個 StringBuffer實體
		final StringBuffer queryString = new StringBuffer();
		queryString.append(" AND PAYER.ACCOUNT_KEY IN ('").append(StringUtils.join(accountNoList.toArray(), "','"))
				.append("')");
		System.out.println("queryString1111 : " + queryString);

		final NameValuePair[] data = { new NameValuePair("cid", "cid"), new NameValuePair("uid", "uid"),
				new NameValuePair("pwd", "pwd"), new NameValuePair("cip", "cip") };
		// 建立長度如果相同的變數有2個建+2
		// EX 原本 4 +2 = 6
		final NameValuePair[] dataclone = new NameValuePair[data.length + 2];
		for (int i = 0; i < data.length; i++) {
			dataclone[i] = data[i];
		}
		// 增加的變數,因為陣列中的 值是從0開始所以 dataclone長度最大為data.length +2 值最大為 length +1
		//// EX dataclone長度最大為data.length +2 ===>>> 6
		// dataclone[最大值為data.length+1 == 5]
		// dataclone[0],dataclone[1],dataclone[2],dataclone[3],dataclone[4],dataclone[5]
		// dataclone[data.length] = new NameValuePair("sys", "BFNB");
		// dataclone[data.length + 1] = new NameValuePair("log", "1");
		//
		for (int i = 0; i < dataclone.length; i++) {
			System.out.println(dataclone[i]);
		}
		final ArrayUtils arr = new ArrayUtils();
		final Object[] arr2 = arr.arraycloneAdd(data, 2);
		arr2[data.length] = new NameValuePair("sys", "BFNB");
		arr2[data.length + 1] = new NameValuePair("log", "1");
		for (int i = 0; i < arr2.length; i++) {
			System.out.println(arr2[i]);
		}

		// ANSON給的
		// Arrays.asList(new TransactionStatus[] {
		// TransactionStatus.SUCCESS, TransactionStatus.SCHEDULED })
		// ANSON給的-------------
	}

}
