package com.ian.tools.array;

/**
 * 
 * 
 * <p/>
 * Package: com.ian.tools.array <br>
 * File Name: ArrayUtils <br>
 * <p/>
 * Purpose: Array Use  <br>
 * 
 * @ClassName: com.ian.Array.ArrayUtils
 * @Description: Array Use 
 * @Company: Team.
 * @author Ian
 * @version 1.0, 2019年2月17日
 * @see 
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
