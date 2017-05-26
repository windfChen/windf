package com.windf.module.development.file;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.windf.core.exception.EntityException;
import com.windf.core.util.reflect.ReflectUtil;
import com.windf.core.util.reflect.SimpleField;

public class XmlFileUtil {
	/**
	 * 读取xml文档，加载所有xml内容到map里面
	 * @param xmlFile
	 * @throws EntityException 
	 */
	public static <T> T readXml2Object(File xmlFile, Class<T> clazz) throws EntityException {
		T result = null;
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(xmlFile);
			result = readXml2Object(document.getRootElement(), clazz, null);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 读取xml文档，加载所有xml内容到map里面
	 * @param element	解析的元素节点
	 * @param clazz	类型
	 * @param type 如果有泛型，泛型是什么类型（泛型可以嵌套），没有泛型则为null
	 * @throws EntityException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T readXml2Object(Element element, Class<T> clazz, Type type) throws EntityException {
		
		Object result = null;
		
		try {
			// 判断clazz类型
			if (ReflectUtil.isBaseType(clazz)) {	// 如果是基本类型，包括字符串和基本类型封装类
				result = ReflectUtil.getValue(clazz, element.getTextTrim());
			} else if (ReflectUtil.isCollection(clazz)) {	// 如果是集合
				if (type != null) {
					Collection collection = ReflectUtil.createCollection(clazz);
					
					// 遍历element的子元素，递归处理
					Iterator elementIterator = element.elementIterator();
					while (elementIterator.hasNext()) {
						Element subElement = (Element) elementIterator.next();
						
						// 判断是否为泛型
						Type genericType = null;
						Class genericClass = null;
						if (type instanceof  ParameterizedType) {	// 如果是，获取泛型中的类型
							 ParameterizedType parameterizedType = (ParameterizedType) type;  
				             genericType = parameterizedType.getActualTypeArguments()[0];
				             genericClass = (Class) parameterizedType.getRawType();
						} else {
							genericClass = (Class) type;
						}
						Object obj = readXml2Object(subElement, genericClass, genericType);
						collection.add(obj);
					}
					
					result = collection;
				}
			} else if (ReflectUtil.isMap(clazz)) {
				if (type != null) {
					Map map = ReflectUtil.createMap(clazz);
					// 遍历element的子元素，递归处理
					Iterator elementIterator = element.elementIterator();
					while (elementIterator.hasNext()) {
						Element subSubElement = (Element) elementIterator.next();
						
						// 判断是否为泛型
						Type valueType = null;
						Class genericClass = null;
						if (type instanceof  ParameterizedType) {	// 如果是，获取泛型中的类型
							 ParameterizedType parameterizedType = (ParameterizedType) type;  
							 Type keyType = parameterizedType.getActualTypeArguments()[0];
							 if (keyType != String.class) {
								 throw new EntityException("map的key必须是String类型");
							 }
				             valueType = parameterizedType.getActualTypeArguments()[1];
				             genericClass = (Class) parameterizedType.getRawType();
						} else {
							genericClass = (Class) type;
						}
						
						Object obj = readXml2Object(subSubElement, genericClass, valueType);
						map.put(subSubElement.getName(), obj);
					}
					result = map;
				}
			} else {	// 如果是其他自定义对象
				// 创建对象
				Object object = clazz.newInstance();
				
				// 遍历element的子元素，递归设置每个字段
				Iterator elementIterator = element.elementIterator();
				while (elementIterator.hasNext()) {
					Element subSubElement = (Element) elementIterator.next();
				
					SimpleField simpleField = SimpleField.getSimpleField(object, subSubElement.getName());
					if (simpleField != null) {
						simpleField.setValue(readXml2Object(subSubElement, simpleField.getFieldClass(), simpleField.getGenericType()));
					}
				}
				
				/*
				 * 遍历属性，设置值
				 */
				Iterator attributeIterator = element.attributeIterator();
				while (attributeIterator.hasNext()) {
					Attribute attribute = (Attribute) attributeIterator.next();
					
					SimpleField simpleField = SimpleField.getSimpleField(object, attribute.getName());
					simpleField.setValue(attribute.getStringValue());
				}
				
				// 返回创建的对象
				result = object;
			}
		
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return (T) result;
	}
	
}
