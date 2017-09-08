package com.windf.plugins.manage.bean;

import java.util.List;

public class PageConfig {

	private String name;

	private boolean canSelect;
	private boolean isSingleSelect;
	private String checkColumn = "id"; // 勾选操作functionB，
										// gridjs.jsp中的GridConfigMenuFunction
										// 所取的列
	private String actionAddress;
	private boolean isAjaxReturn;

	private String title;
	private List<FieldConfig> fields; // 如果有弹出框时候，每个列的配置

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCanSelect() {
		return canSelect;
	}

	public void setCanSelect(boolean canSelect) {
		this.canSelect = canSelect;
	}

	public boolean isSingleSelect() {
		return isSingleSelect;
	}

	public void setSingleSelect(boolean isSingleSelect) {
		this.isSingleSelect = isSingleSelect;
	}

	public String getCheckColumn() {
		return checkColumn;
	}

	public void setCheckColumn(String checkColumn) {
		this.checkColumn = checkColumn;
	}

	public String getActionAddress() {
		return actionAddress;
	}

	public void setActionAddress(String actionAddress) {
		this.actionAddress = actionAddress;
	}

	public boolean isAjaxReturn() {
		return isAjaxReturn;
	}

	public void setAjaxReturn(boolean isAjaxReturn) {
		this.isAjaxReturn = isAjaxReturn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<FieldConfig> getFields() {
		return fields;
	}

	public void setFields(List<FieldConfig> fields) {
		this.fields = fields;
	}

}
