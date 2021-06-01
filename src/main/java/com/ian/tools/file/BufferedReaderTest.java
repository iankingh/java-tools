package com.ian.tools.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * 本類別詳細說明。
 * <p/>
 * Package: com.ian.file <br>
 * File Name: BufferedReaderTest <br>
 * <p/>
 * Purpose: 讀取檔案(使用BufferedReader方便讀取整行)<br>
 * 
 * @ClassName: com.ian.file.BufferedReaderTest
 * @Description: TODO
 * @Company: Team.
 * @author Ian
 * @version 1.0, 2019年2月20日
 */
public class BufferedReaderTest {

	public static void main(String[] args) throws  IOException {
		        FileReader fr = new FileReader("FileName");
		        BufferedReader br = new BufferedReader(fr);
		        while (br.ready()) {
		            System.out.println(br.readLine());
		        }
		        fr.close();
		}

}
