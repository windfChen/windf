package com.whaty.core.file;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.windf.core.file.FileUtil;
import com.windf.moudle.moudle.Constant;

public class FileUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void copyFolder() {
		String exampleConfigPath = FileUtilTest.class.getClassLoader().getResource("").getPath();
		String newModuleConfigPath = FileUtil.getWebappPath() + Constant.DEFAULT_MODULE_CLASS_PATH +  "new";
		FileUtil.copyFolder(exampleConfigPath, newModuleConfigPath);
	}

}
