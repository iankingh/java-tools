package com.ian.tools.array;

/**
 * 
 * 本類別詳細說明。
 * <p/>
 * Package: com.ian.Array <br>
 * File Name: ArrayUtils <br>
 * <p/>
 * Purpose: <br>
 * 
 * @ClassName: com.ian.Array.ArrayUtils
 * @Description: TODO
 * @Company: Team.
 * @author Ian
 * @version 1.0, 2019年2月17日
 */
public class ArrayUtils {
	
	/**
	 * 
	 * @param data
	 * @param addLength
	 * @return
	 */
	public Object[] arraycloneAdd(Object[] data, int addLength) {
		Object[] returncCloneArry = new Object[data.length + addLength];
		for (int i = 0; i < data.length; i++) {
			returncCloneArry[i] = data[i];
		}
		return returncCloneArry;
	}


}
