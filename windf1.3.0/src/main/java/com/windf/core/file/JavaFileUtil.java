package com.windf.core.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaFileUtil {
	/**
	 * 按行读取每个内容
	 * @param file
	 * @param lineReader
	 */
	public static void readLine(File file, LineReader lineReader) {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		String lineContent = null;
		try {
			/*
			 * 读取文件
			 */
			List<String> oldLines = new ArrayList<String>();
			reader = new BufferedReader(new FileReader(file));
			int line = 1;
			while ((lineContent = reader.readLine()) != null) {
				String newlineContent = lineReader.readLine(oldLines, lineContent, line);
				oldLines.add(newlineContent);
				line++;
			}
			
			/*
			 * 重新写入文件
			 */
			writer = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < oldLines.size(); i++) {
				lineContent = oldLines.get(i);
				writer.write(lineContent);
				writer.newLine();
			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
				}
			}
		}
		
	}

	public interface LineReader {
		/**
		 * 读取每一行
		 * @param oldLines
		 * @param lineContent
		 * @param lineNo
		 * @return 修改后的内容
		 */
		public String readLine(List<String> oldLines, String lineContent, int lineNo);
	}
}
