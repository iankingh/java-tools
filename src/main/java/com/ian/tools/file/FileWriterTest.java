package com.ian.tools.file;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterTest {

	public static void main(String[] args) throws IOException {
		FileWriter fw = new FileWriter("test.txt");
		fw.write("test");
		fw.flush();
		fw.close();
	}

}
