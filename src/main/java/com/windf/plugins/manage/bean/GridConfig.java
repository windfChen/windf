package com.windf.plugins.manage.bean;

import java.util.List;

public class GridConfig {
	
	private String title; // 表格标题
	private String dataSource;	// 数据源，对应dao，需要实现ListDao
	private List<ColumnConfig> columns;
	private List<MenuConfig> meuns;

	private boolean isPrepared;
	private boolean canAdd;
	private boolean canBatchAdd;
	private boolean canDelete;
	private boolean canUpdate;
	private boolean canSearch;
	
	private String roleCode;	// 列的全选过滤，只有相应角色code的人才能查看

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ColumnConfig> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnConfig> columns) {
		this.columns = columns;
	}

	public List<MenuConfig> getMeuns() {
		return meuns;
	}

	public void setMeuns(List<MenuConfig> meuns) {
		this.meuns = meuns;
	}

	public boolean isPrepared() {
		return isPrepared;
	}

	public void setPrepared(boolean isPrepared) {
		this.isPrepared = isPrepared;
	}

	public boolean isCanAdd() {
		return canAdd;
	}

	public void setCanAdd(boolean canAdd) {
		this.canAdd = canAdd;
	}

	public boolean isCanBatchAdd() {
		return canBatchAdd;
	}

	public void setCanBatchAdd(boolean canBatchAdd) {
		this.canBatchAdd = canBatchAdd;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public boolean isCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

	public boolean isCanSearch() {
		return canSearch;
	}

	public void setCanSearch(boolean canSearch) {
		this.canSearch = canSearch;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

}
