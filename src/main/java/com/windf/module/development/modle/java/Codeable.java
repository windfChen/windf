package com.windf.module.development.modle.java;

import java.util.List;

public interface Codeable<T> {

	/**
	 * 转换成代码的形式
	 * @return
	 */
	public List<String> toCodes() ;
	
	/**
	 * 转换代码成对象形式
	 * @return
	 */
	public T toObject();
	
	/**
	 * 是否能转换成代码
	 * @return
	 */
	public boolean verifyCodes();
}