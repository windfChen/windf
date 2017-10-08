package com.windf.module.development.entity;

import com.windf.core.util.StringUtil;
import com.windf.module.development.modle.java.CodeConst;

public class Field extends AbstractBaseCodeBean {
	private String databaseName;
	private String databaseType;
	private String type;
	private Integer length;
	private boolean isNotNull;
	private String defaultValue;
	private boolean isAutoIncrement;
	private String comment;
	
	private Entity entity;
	
	/**
	 * 转化为字符串
	 * @return
	 */
	public String writeSql() {
		StringBuffer result = new StringBuffer();
		result.append(CodeConst.getTabString(1) + "`" + this.getDatabaseName() + "`" + " " + this.getDatabaseType());
		if (this.getLength() != null) {
			result.append("(" + this.getLength() + ")");
		}
		if (this.isNotNull) {
			result.append("( NOT NULL)");
		}
		if (StringUtil.isNotEmpty(this.getDefaultValue())) {
			if (this.getDefaultValue().equals("NULL")) {
				result.append(" DEFAULT NULL)");
			} else {
				result.append(" DEFAULT '" + this.getDefaultValue() + "')");
			}
		}
		if (this.getIsAutoIncrement()) {
			result.append(" AUTO_INCREMENT");
		}
		if (StringUtil.isNotEmpty(this.getComment())) {
			result.append(" COMMENT '" + this.getComment() + "'");
		}
		
		return result.toString();
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public boolean getIsNotNull() {
		return isNotNull;
	}

	public void setIsNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean getIsAutoIncrement() {
		return isAutoIncrement;
	}

	public void setIsAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
