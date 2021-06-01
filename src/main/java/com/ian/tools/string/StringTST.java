package com.ian.string;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StringTST {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
//		String ts = "堃";
//		String tsbig5 =  new String(ts.getBytes(),"big5");
//		
//		System.out.println("bs._DPUSERNAME222-1>>"+ ts);
//		System.out.println("bs._DPUSERNAME222-2 UTF-8>>" + new String(ts.getBytes("UTF-8"),"UTF-8"));
//		System.out.println("bs._DPUSERNAME222-2 UTF-8>>" + new String(tsbig5.getBytes("UTF-8"),"UTF-8"));
//		System.out.println("bs._DPUSERNAME333-1>>"+(ts.getBytes("UTF-8")));
//		System.out.println("bs._DPUSERNAME333-2 UTF-8>>"+new String(ts.getBytes(),"UTF-8"));
//
//		
//		
		ArrayList<Map<String, String>> arrayList = new ArrayList<>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("A", "A");
		map.put("B", "B");
		map.put("C", "C");
		Map<String, String> map2 = new HashMap<String, String>();
		System.out.println(map);
		System.out.println(map2);
		arrayList.add(map);
		arrayList.add(map2);
		arrayList.removeIf( Map::isEmpty);
		System.out.println(arrayList);
	}

}
