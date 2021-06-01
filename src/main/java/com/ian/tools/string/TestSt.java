package com.ian.string;

public class TestSt {
	public static void main(String[] args) throws Exception {
		
	    String unicode = "中文";
	    System.out.println("UTF-16: " + unicode);
	    char y[] = unicode.toCharArray();
	    for (int i = 0; i < y.length; i++) {
	      System.out.printf("%x ", (int) y[i]);
	    }
	    System.out.println();
	    
	    // unicode 轉成 Big5 編碼
	    byte[] big5 = unicode.getBytes("Big5");
	    // Big5 編碼 轉回 unicode
	    unicode = new String(big5, "Big5");
	    System.out.println("Big5: " + unicode);
	    byte x[] = big5;
	    for (int i = 0; i < x.length; i++) {
	      System.out.printf("%x ", x[i]);
	    }
	    System.out.println();
	    byte[] utf8 = null;
	    // unicode 轉成 UTF-8 編碼
	    // utf8 = unicode.getBytes("UTF-8");
	    
	    // Big5 編碼 轉回 unicode 再轉成 UTF-8 編碼
	    utf8 = new String(big5, "Big5").getBytes("UTF-8");
	    
	    System.out.println("UTF-8: " +unicode);
	    byte z[] = utf8;
	    for (int i = 0; i < z.length; i++) {
	      System.out.printf("%x ", z[i]);
	    }
	  }
}
