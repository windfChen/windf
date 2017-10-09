package com.windf.module.development.modle.entity;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.core.util.StringUtil;
import com.windf.module.development.entity.Entity;
import com.windf.module.development.entity.Field;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.entity.Return;
import com.windf.module.development.modle.java.Attribute;
import com.windf.module.development.modle.java.CodeBlock;
import com.windf.module.development.modle.java.CodeConst;
import com.windf.module.development.modle.java.JavaCoder;
import com.windf.module.development.modle.java.Method;
import com.windf.module.development.modle.java.code.FieldCoder;
import com.windf.module.development.modle.sql.CreateTableCoder;

public class EntityCoder {
	private JavaCoder javaCoder;
	private CreateTableCoder createTableCoder;
	private Entity entity;
	
	public EntityCoder(Entity entity) {
		this.entity = entity;
		javaCoder = JavaCoder.getJavaCoderByName(entity.getName());
		createTableCoder = CreateTableCoder.getCreateTableCoder(entity.getModule().getCode());
	}
	
	/**
	 * 添加字段
	 * @param field
	 * @throws UserException 
	 */
	public void addField(Field field) throws UserException {
		/*
		 * 对象模型建立
		 */
		entity.getFields().add(field);
		
		/*
		 * field属性
		 */
		Attribute attribute = new Attribute(field.getType(), field.getName());
		attribute.setModifier(CodeConst.MODIFY_PRIVATE);
		attribute.setLineComment(field.getComment());
		javaCoder.createAttribute(attribute);
		
		/*
		 * getter方法
		 */
		Return getterRet = new Return(field.getType());
		Method getterMethod = new Method("get" + StringUtil.firstLetterUppercase(field.getName()), getterRet, null, null, false);
		javaCoder.createMethod(getterMethod);
		CodeBlock<Field> fieldGetterCoderBlock = new CodeBlock<Field>();
		fieldGetterCoderBlock.setCodeable(new FieldCoder(true));
		fieldGetterCoderBlock.setTabCount(2);
		fieldGetterCoderBlock.serialize(field);
		getterMethod.addCodeBlock(0, fieldGetterCoderBlock);
		
		/*
		 * setter方法
		 */
		Return setterRet = new Return(Return.VOID);
		List<Parameter> parameters = new ArrayList<Parameter>();
		Parameter parameter = new Parameter();
		parameter.setType(field.getType());
		parameter.setName(field.getName());
		parameters.add(parameter);
		Method setterMethod = new Method("set" + StringUtil.firstLetterUppercase(field.getName()), setterRet, parameters, null, false);
		CodeBlock<Field> fieldSetterCoderBlock = new CodeBlock<Field>();
		fieldSetterCoderBlock.setCodeable(new FieldCoder(false));
		fieldSetterCoderBlock.setTabCount(2);
		fieldSetterCoderBlock.serialize(field);
		setterMethod.addCodeBlock(0, fieldSetterCoderBlock);
		javaCoder.createMethod(setterMethod);
		
		/*
		 * sql语句更新
		 */
		createTableCoder.write();
	}
	
	public void write() {
		javaCoder.write();
	}
	
	

}
