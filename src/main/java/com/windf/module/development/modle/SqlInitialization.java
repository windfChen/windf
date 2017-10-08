package com.windf.module.development.modle;

import java.io.File;

import com.windf.core.util.reflect.Scanner;
import com.windf.core.util.reflect.ScannerHandler;
import com.windf.module.development.modle.sql.CreateTableSql;
import com.windf.module.development.util.file.SourceFileUtil;

public class SqlInitialization implements ScannerHandler{

	public SqlInitialization() {
		Scanner scanner = new Scanner(SourceFileUtil.DEVELOPMENT_BASE_PATH + "/document/sql/module", this);
		scanner.run();
	}
	
	@Override
	public void handle(File file) {
		new CreateTableSql(file);
	}

}
