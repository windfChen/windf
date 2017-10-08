package com.windf.module.development.modle.sql;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.windf.core.util.SQLUtil;
import com.windf.core.util.StringUtil;
import com.windf.core.util.file.FileUtil;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Field;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;
import com.windf.module.development.modle.java.CodeConst;
import com.windf.module.development.util.file.LineReader;
import com.windf.module.development.util.file.TextFileUtil;

public class CreateTableSql {
	
	private Module module;
	private String sqlFilePath;

	public CreateTableSql(File file) {
		/*
		 * 初始化模块
		 */
		String moduleCode = file.getName();
		int lastPoint = moduleCode.lastIndexOf(".");
		if (lastPoint > -1) {
			moduleCode = moduleCode.substring(0, lastPoint);
		}
		this.module = ModuleMaster.getInstance().findModuleByCode(moduleCode);
		
		/*
		 * 初始化路径，方便写入
		 */
		sqlFilePath = file.getAbsolutePath();
		
		/*
		 * 读取文件
		 */
		readFile(file);
	}
	
	private void readFile(final File file) {
		/*
		 * 加载document/sql/module
		 */
		TextFileUtil.readLine(file, new LineReader() {
			Entity currentEntity = null;
			
			@Override
			public String readLine(List<String> oldLines, String lineContent, int lineNo) {
				/*
				 * 表头
				 */
				String[] ss = CodeConst.getInnerString(lineContent, "^CREATE\\s+TABLE\\s+`(\\w*)`\\s+\\(\\s*$");
				if (ss.length > 0) {
					String tableName = ss[0];
					String entityName = SQLUtil.tableName2EntityName(tableName);
					currentEntity = module.getEntityByName(entityName);
					currentEntity.setTableName(tableName);
					return null;
				}
				
				/*
				 * 字段
				 */
				ss = CodeConst.getInnerString(lineContent, "^\\s*`(\\w*)` (\\w*)(\\((\\d+)\\))?( NOT NULL)?( DEFAULT ('[^']*'|NULL))?( AUTO_INCREMENT)?( COMMENT '([^']*)')?,?$");
				if (ss.length > 0) {
					String databaseName = ss[0];
					String type = ss[1];
					String length = ss[3];
					String isNotNull = ss[4];
					String defaultValue = ss[6];
					String isAutoIncrement = ss[7];
					String comment = ss[9];

					Field field = null;
					String name = databaseName;
					String[] ss2 = CodeConst.getInnerString(name, "fk_(\\w*)_id");
					if (ss2.length > 0) {
						name = ss2[0];
					}
					name = StringUtil.toCamelCase(name, "_");
					for (int i = 0; i < currentEntity.getFields().size(); i++) {
						Field f = currentEntity.getFields().get(i);
						if (f.getName().equals(name)) {
							field = f;
							break;
						}
					}
					
					if (field != null) {
						field.setDatabaseName(databaseName);
						field.setDatabaseType(type);
						field.setLength(length == null? null: new Integer(length));
						field.setIsNotNull(StringUtil.isNotEmpty(isNotNull));
						field.setDefaultValue(defaultValue);
						field.setIsAutoIncrement(StringUtil.isNotEmpty(isAutoIncrement));
						field.setComment(comment);
					}
					
					return null;
				}
				
				/*
				 * 尾部信息
				 */
				ss = CodeConst.getInnerString(lineContent, "\\) ENGINE=InnoDB DEFAULT CHARSET=utf8( COMMENT='([^']*)')?;");
				if (ss.length > 0) {
					currentEntity.setComment(ss[1]);
					return null;
				}
				return null;
			}
		}, true);
	}
	
	public void writeFile() {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < module.getEntitys().size(); i++) {
			Entity e = module.getEntitys().get(i);
			result.addAll(this.writeEntity(e));
			result.add("");
		}
		
		TextFileUtil.writeFile(FileUtil.getFile(this.sqlFilePath, true), result);
	}
	
	private List<String> writeEntity(Entity t) {
		List<String> result = new ArrayList<String>();
		result.add("");
		result.add("CREATE TABLE `" + t.getTableName() + "` (");
		for (int i = 0; i < t.getFields().size(); i++) {
			String fieldSql = writeField(t.getFields().get(i));
			if (i != t.getFields().size() - 1) {
				fieldSql += ";";
			}
			result.add(fieldSql);
		}
		StringBuffer end = new StringBuffer();
		end.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8");
		if (StringUtil.isNotEmpty(t.getComment())) {
			end.append(" COMMENT='" + t.getComment() + "'");
		}
		end.append(";");
		result.add(end.toString());
		
		return result;
	}
	
	private String writeField(Field t) {
		StringBuffer result = new StringBuffer();
		result.append(CodeConst.getTabString(1) + "`" + t.getDatabaseName() + "`" + " " + t.getDatabaseType());
		if (t.getLength() != null) {
			result.append("(" + t.getLength() + ")");
		}
		if (t.getIsNotNull()) {
			result.append("( NOT NULL)");
		}
		if (StringUtil.isNotEmpty(t.getDefaultValue())) {
			if (t.getDefaultValue().equals("NULL")) {
				result.append(" DEFAULT NULL)");
			} else {
				result.append(" DEFAULT '" + t.getDefaultValue() + "')");
			}
		}
		if (t.getIsAutoIncrement()) {
			result.append(" AUTO_INCREMENT");
		}
		if (StringUtil.isNotEmpty(t.getComment())) {
			result.append(" COMMENT '" + t.getComment() + "'");
		}
		
		return result.toString();
	}
	
}
