package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Imports {
	private List<String> lines = new ArrayList<String>();
	
	Imports() {
		
	}
	
	/**
	 * 添加一个新的导入代码
	 * 不验证是否重复
	 * @param lineContent
	 */
	void addLine(String lineContent) {
		lines.add(lineContent);
	}
	
	/**
	 * 导入一个新类
	 * 如果已经存在，不进行添加
	 * @param classFullName 类的全路径
	 * @return 是否有添加操作
	 */
	boolean importNewClass(String classFullName) {
		boolean result = true;
		
		String newLine = newImportLineFromClassFullName(classFullName);
		for (String line : lines) {
			if (line.equals(newLine)) {
				result = false;
				break;
			}
		}
		
		if (result) {
			lines.add(newLine);
		}
		
		return result;
	}
	
	/**
	 * 按照特定的规则，对improt的类的顺序进行排序
	 * 不进行去重复
	 * 不同包之间用，空行分开
	 */
	void sort() {
		Collections.sort(lines, new Comparator<String>() {
	          @Override
	          public int compare(String s1, String s2) {
				String s1Org = getImportOrg(s1);
				String s2Org = getImportOrg(s2);
				
				if (s1Org.equals(s2Org)) {
					return s1.compareTo(s2);
				} else {
					int s1Weight = getImportOrgWeight(s1Org);
					int s2Weight = getImportOrgWeight(s2Org);
					return s2Weight - s1Weight;
				}
	          }

	     });
		
		List<String> newLines = new ArrayList<String>();
		String frontLine = null;
		for (String line : lines) {
			if (frontLine != null && !getImportOrg(frontLine).equals(getImportOrg(line))) {
				newLines.add("");
			}
			newLines.add(line);
			frontLine = line;
		}
		
		lines = newLines;
	}
	
  	/**
  	 * 获得import中包名的机构名
  	 * @param str
  	 * @return
  	 */
  	private String getImportOrg(String str) {
  		String classFullName = str.split(CodeConst.WORD_SPLIT)[1];
  		return classFullName.substring(0, classFullName.indexOf("."));
  	}
  	
  	/**
  	 * 获得import的权重
  	 * @param org
  	 * @return
  	 */
  	private int getImportOrgWeight(String org) {
  		int result = 0;
  		
  		if ("java".equals(org)) {
  			result = 10;
  		} else if ("javax".equals(org)) {
  			result = 9;
  		} else if ("org".equals(org)) {
  			result = 5;
  		} else if ("com".equals(org)) {
  			result = 3;
  		}
  		
  		return result;
  	}
	
	/**
	 * 将import转换为代码输出
	 * 每行代码为list中的一项
	 * @return
	 */
	List<String> write() {
		sort();
		return lines;
	}
	
	private String newImportLineFromClassFullName(String classFullName) {
		return "improt" + CodeConst.WORD_SPLIT + classFullName + ";";
	}
}
