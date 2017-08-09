package com.windf.core.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
	public static boolean isBaseType(Class<? extends Object> clazz) {
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
	
	public static boolean isInteger(Class<? extends Object> clazz) {
		boolean result = false;
		
		if (clazz != null) {
			if ("java.lang.Integer".equals(clazz.getName())) {
				result = true;
			}
		}
		
		return result;
	}
	
	public static boolean isLong(Class<? extends Object> clazz) {
		boolean result = false;
		
		if (clazz != null) {
			if ("java.lang.Long".equals(clazz.getName())) {
				result = true;
			}
		}
		
		return result;
	}
	
	public static boolean isDate(Class<? extends Object> clazz) {
		boolean result = false;
		
		if (clazz != null) {
			if ("java.util.Date".equals(clazz.getName())) {
				result = true;
			}
		}
		
		return result;
	}

	/**
	 * 判断类，是否是集合类型
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isCollection(Class<? extends Object> clazz) {
		boolean result = false;
		if (clazz.isAssignableFrom(List.class) || clazz.isAssignableFrom(Set.class)) {
			result = true;
		}
		if (!result) {
			if (!result) {
				Class<? extends Object>[] interfaces = clazz.getInterfaces();
				for (int i = 0; i < interfaces.length; i++) {
					Class<? extends Object> c = interfaces[i];
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Collection<? extends Object> createCollection(Class<? extends Object> clazz) {
		Collection<? extends Object> result = null;
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
	public static boolean isMap(Class<? extends Object> clazz) {
		boolean result = false;
		if (clazz.isAssignableFrom(Map.class)) {
			result = true;
		}

		if (!result) {
			Class<? extends Object>[] interfaces = clazz.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				Class<? extends Object> c = interfaces[i];
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
	@SuppressWarnings("unchecked")
	public static <T> T getValue(Class<? extends Object> clazz, String value) {
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
	@SuppressWarnings("unchecked")
	public static Class<? extends Object> getRawType(Type type) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Class<? extends Object> genericClass = (Class<? extends Object>) parameterizedType.getRawType();
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

	public static Object cloneOnlyBaseType(Object object) {
			Class<? extends Object> clazz = object.getClass();
			Object newObject = null;
			try {
				newObject = clazz.newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			
			if (newObject != null) {
				Map<String, Object> getterMethodMap = getAllGetterMethods(object);
				Iterator<String> iterator = getterMethodMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					Object value = getterMethodMap.get(key);
					
					if (ReflectUtil.isBaseType(value.getClass())) {
						String setterMethodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
						try {
							Method setterMethod = clazz.getMethod(setterMethodName, value.getClass());
							setterMethod.invoke(newObject, value);
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			return newObject;
			
	}
	
	/**
	 * 获得所有常量
	 * @param clazz
	 * @return 变量名-value的键值对
	 */
	public static Map<String, Object> getAllConstantValue(Class<? extends Object> clazz) {
		Map<String, Object> result = new HashMap<String, Object>();
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
				if ((field.getModifiers() & Modifier.FINAL) == Modifier.FINAL) {
					try {
						result.put(field.getName(), field.get(clazz));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

}
