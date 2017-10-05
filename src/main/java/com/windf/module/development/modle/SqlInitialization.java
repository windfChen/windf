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
				String[] ss = CodeConst.getInnerString(lineContent, "^CREATE\\s+TABLE\\s+`(\\w*)`\\s+\\(\\s*$");
				if (ss.length > 0) {
					String tableName = ss[0];
					String entityName = SQLUtil.tableName2EntityName(tableName);
				System.out.println(tableName + "-" + entityName);
					currentEntity = currentModule.getEntityByName(entityName);
					currentEntity.setTableName(tableName);
					return null;
				}
				
				ss = CodeConst.getInnerString(lineContent, "^\\s*`(\\w*)` (\\w*)(\\((\\d+)\\))?( NOT NULL)?( DEFAULT '([^']*)')?( AUTO_INCREMENT)?( COMMENT '([^']*)')?,?$");
				if (ss.length > 0) {
					String name = ss[0];
					String type = ss[1];
					String length = ss[3];
					String isNotNull = ss[4];
					String defaultValue = ss[6];
					String isAutoIncrement = ss[7];
					String comment = ss[9];
					
					Field field = new Field();
					field.setId(currentEntity.getId() + "." + name);
					field.setName(name);
					field.setType(type);
					field.setLength(length == null? null: new Integer(length));
					field.setIsNotNull(StringUtil.isNotEmpty(isNotNull));
					field.setDefaultValue(defaultValue);
					field.setIsAutoIncrement(StringUtil.isNotEmpty(isAutoIncrement));
					field.setComment(comment);
					
					currentEntity.getFields().add(field);
					return null;
				}
				return null;
			}
		}, true);
	}

}
