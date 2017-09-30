package com.windf.core.frame;

public class InitializationControler implements Initializationable {

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
		
		// 判断是否是开发模式（依赖maven结构，寻找classes上面的目录是否存在src）
		// 读取各个模块，寻找模块的描述信息
			// 寻找初始化类
			// 寻找过滤器
			// session注册
		// 读取各个插件，寻找插件的描述信息
		
		
		
	}


	private void loadModel() {
		// 读取 java文件
		
		// 读取 配置文件
		
		// 读取模板文件和资源文件
	}
}
