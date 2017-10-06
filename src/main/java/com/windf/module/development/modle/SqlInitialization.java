package com.windf.module.development.modle;

import java.io.File;
import java.util.List;

import com.windf.core.util.SQLUtil;
import com.windf.core.util.StringUtil;
import com.windf.core.util.reflect.Scanner;
import com.windf.core.util.reflect.ScannerHandler;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Field;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.ModuleMaster;
import com.windf.module.development.modle.java.CodeConst;
import com.windf.module.development.util.file.JavaFileUtil;
import com.windf.module.development.util.file.LineReader;
import com.windf.module.development.util.file.SourceFileUtil;

public class SqlInitialization implements ScannerHandler{

	public SqlInitialization() {
		Scanner scanner = new Scanner(SourceFileUtil.DEVELOPMENT_BASE_PATH + "/document/sql/module", this);
		scanner.run();
	}
	
	@Override
	public void handle(final File file) {

		/*
		 * 加载document/sql/module
		 */
		JavaFileUtil.readLine(file, new LineReader() {
			String moduleCode = file.getName().substring(0, file.getName().lastIndexOf("."));
			Module currentModule = ModuleMaster.getInstance().findModuleByCode(moduleCode);
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
					currentEntity = currentModule.getEntityByName(entityName);
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
						field.setType(type);
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

}
