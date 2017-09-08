package com.windf.core.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReadUtil {
	
	/**
	 * 读物文件每一行
	 * @param filePath
	 * @return
	 */
	public static List<String> readLine(String filePath) {
		List<String> result = new ArrayList<String>();

		File file = FileUtil.getFile(filePath);

		BufferedReader reader = null;
		String lineContent = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((lineContent = reader.readLine()) != null) {
				result.add(lineContent);
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

		return result;
	}
	
	/**
	 * 读取文件的内容作为字符串
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readFileAsString(String filePath) {
		StringBuffer result = new StringBuffer();

		List<String> lines = readLine(filePath);
		for (int i = 0; i < lines.size(); i++) {
			result.append(lines.get(i));
		}

		return result.toString();
	}


}
