package com.windf.core.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.windf.core.exception.EntityException;

/**
 * 简单的字段啊控制方法
 * 用于javabean的字段，具有getter方法和setter方法
 * 用于判断字段的返回类型，为字段设置值
 * @author chenyafeng
 *
 */
public class SimpleField {
	
	private Object object;
	private Method getter;
	private Method setter;
	@SuppressWarnings("rawtypes")
	private Class fieldClazz;
	
	public static SimpleField getSimpleField(Object object, String fieldName) {
		SimpleField simpleField = null;
		try {
			simpleField = new SimpleField(object, fieldName);
		} catch (EntityException e) {
			simpleField = null;
		}
		return simpleField;
	}
	
	
	private SimpleField(Object object, String fieldName) throws EntityException{
		this.object = object;
		
		String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		try {
			getter = object.getClass().getMethod(getterName);
			fieldClazz = getter.getReturnType();
			setter = object.getClass().getMethod(setterName, fieldClazz);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new EntityException();
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new EntityException();
		}
	}
	
	/**
	 * 通过setter为field设置值
	 * @param value
	 * @return
	 */
	public boolean setValue(Object value) {
		boolean result = false;
		try {
			setter.invoke(object, value);
			result = true;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获得字段的类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Class getFieldClass() {
		return fieldClazz;
	}
	
	/**
	 * 是否是基本类型：包括java的基本类型和字符串
	 * @return
	 */
	public boolean isBaseType() {
		return ReflectUtil.isBaseType(fieldClazz);
	}
	
	/**
	 * 是否是集合
	 * @return
	 */
	public boolean isCollection() {
		return ReflectUtil.isCollection(fieldClazz);
	}
	
	@SuppressWarnings("rawtypes")
	public Type getGenericType() {
		Type genericClazz = null;
		Type type = getter.getGenericReturnType();
		if (type instanceof  ParameterizedType) {
			 ParameterizedType parameterizedType = (ParameterizedType) type;  
			 
             genericClazz = parameterizedType.getActualTypeArguments()[0];
		}
		return genericClazz;
	}

	/**
	 * 获得字段对象
	 * 如果如果字段的对象不为空，重新创建对象
	 * @return
	 */
	public Object getInstance() {
		Object result = null;
		try {
			result = getter.invoke(object);
			if (result == null) {
				result = fieldClazz.newInstance();
				this.setValue(result);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}