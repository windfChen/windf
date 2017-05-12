package com.windf.core.frame.init;

public class InitializationControler implements Initialization {

	@Override
	public boolean init() {
		this.modelInit();
		return false;
	}
	
	/**
	 * 模块初始化
     * 1、加载：读取、加载系统的模块（必须符合规范）
     * 2、检查：检查模块的合法性
     * 3、执行：读取，并执行每个模块或者插件下面的init.java 文件
     * 		init.java 必须实现OnProjectStart 接口
     * 		如果没有需要随项目启动的功能，则不需要有改java类
     */
	private void modelInit() {

		// 加载各个模块
		this.loadModel();
		
		// 读取，执行初始化文件（如果有）
		
		// 执行各个文件
	}


	private void loadModel() {
		// 读取 java文件
		
		// 读取 配置文件
		
		// 读取模板文件和资源文件
	}
}
