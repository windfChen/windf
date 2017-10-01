package com.windf.module.development.util.file;

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
		readLine(file, lineReader, false);
	}
	/**
	 * 按行读取每个内容
	 * @param file
	 * @param lineReader
	 */
	public static void readLine(File file, LineReader lineReader, boolean readonly) {
		BufferedReader reader = null;
		String lineContent = null;
		List<String> oldLines = null;
		try {
			/*
			 * 读取文件
			 */
			oldLines = new ArrayList<String>();
			reader = new BufferedReader(new FileReader(file));
			int line = 1;
			while ((lineContent = reader.readLine()) != null) {
				String newlineContent = lineReader.readLine(oldLines, lineContent, line);
				oldLines.add(newlineContent);
				line++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		/*
		 * 重新写入文件
		 */
		if (!readonly && oldLines != null) {
			writeFile(file, oldLines);
		}
		
	}
	
	/**
	 * 将内容写到指定的文件中
	 * @param file
	 * @param lines
	 */
	public static void writeFile(File file, List<String> lines) {
		BufferedWriter writer = null;
		
		try {
			String lineContent = null;
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			writer = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < lines.size(); i++) {
				lineContent = lines.get(i);
				writer.write(lineContent);
				writer.newLine();
			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
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
