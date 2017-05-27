package com.windf.core.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectUtil {

	/**
	 * 判断类是否是基本类型
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isBaseType(Class clazz) {
		boolean result = true;
		if (!clazz.isPrimitive()) {
			if (!clazz.getName().startsWith("java.lang")) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 判断类是否是基本类型
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseType(Object obj) {
		return isBaseType(obj.getClass());
	}

	/**
	 * 判断类，是否是集合类型
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isCollection(Class clazz) {
		boolean result = false;
		if (clazz.isAssignableFrom(List.class) || clazz.isAssignableFrom(Set.class)) {
			result = true;
		}
		if (!result) {
			if (!result) {
				Class[] interfaces = clazz.getInterfaces();
				for (int i = 0; i < interfaces.length; i++) {
					Class c = interfaces[i];
					if (c.isAssignableFrom(List.class) || c.isAssignableFrom(Set.class)) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 根据集合类型，创建集合
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection createCollection(Class clazz) {
		Collection result = null;
		if (clazz.isAssignableFrom(List.class)) {
			result = new ArrayList();
		} else if (clazz.isAssignableFrom(Set.class)) {
			result = new HashSet();
		}
		return result;
	}

	/**
	 * 判断对象是map
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isMap(Class clazz) {
		boolean result = false;
		if (clazz.isAssignableFrom(Map.class)) {
			result = true;
		}

		if (!result) {
			Class[] interfaces = clazz.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				Class c = interfaces[i];
				if (c.isAssignableFrom(Map.class)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * 根据class，创建一个map
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Map createMap(Class clazz) {
		return new HashMap();
	}

	/**
	 * 根据class，转换字符串类型
	 * 
	 * @param clazz
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T getValue(Class clazz, String value) {
		T result = null;
		if (clazz.isAssignableFrom(Integer.class)) {
			result = (T) new Integer(value);
		} else if (clazz.isAssignableFrom(String.class)) {
			result = (T) value;
		}

		return result;
	}

	/**
	 * 判断，是否是用户自定义的getter方法
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isGetterMethod(Method method) {
		String methodName = method.getName();
		return methodName.startsWith("get") && method.getParameterTypes().length == 0 && !"getClass".equals(methodName);
	}

	/**
	 * 获得对象的所有非空属性
	 * 
	 * @param clazz
	 * @return 属性名-属性值
	 */
	public static Map<String, Object> getAllGetterMethods(Object object) {
		Class<? extends Object> clazz = object.getClass();

		Map<String, Object> getterMethodValueMap = new HashMap<String, Object>();
		Method[] methods = clazz.getMethods();
		if (methods != null) {
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String methodName = method.getName();

				if (ReflectUtil.isGetterMethod(method)) {
					Object result = null;
					try {
						result = method.invoke(object);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					if (result != null) {
						methodName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
						getterMethodValueMap.put(methodName, result);
					}
				}
			}
		}

		return getterMethodValueMap;
	}

	/**
	 * 判断type是否是泛型
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isGeneric(Type type) {
		return type instanceof ParameterizedType;
	}

	/**
	 * 获得map的key的类型
	 * 
	 * @param type
	 * @return
	 */
	public static Type getGenericOfMapKey(Type type) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type keyType = parameterizedType.getActualTypeArguments()[0];
		return keyType;
	}

	/**
	 * 获得map的value的类型
	 * 
	 * @param type
	 * @return
	 */
	public static Type getGenericOfMapValue(Type type) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type valueType = parameterizedType.getActualTypeArguments()[1];
		return valueType;
	}

	/**
	 * 获得类型
	 * @param type
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class getRawType(Type type) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Class genericClass = (Class) parameterizedType.getRawType();
		return genericClass;
	}

	/**
	 * 获得集合的泛型
	 * @param type
	 * @return
	 */
	public static Type getGenericOfCollection(Type type) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		return  parameterizedType.getActualTypeArguments()[0];
	}

}
