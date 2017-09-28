package com.windf.module.priority.entity.vo;

import java.util.List;

import com.windf.core.general.bean.AbstractBean;

public class MenuVO extends AbstractBean {
	private static final long serialVersionUID = 8064319048898614343L;

	private String id; // id
	private String code; 
	private String text; // 名称
	private Integer sort; // 排序
	private String url; // 地址
	private List<MenuVO> children;
	private boolean leaf;

	/**
	 *TODO 写死的用于展示的图标的，该死
	 * @return
	 */
	public String getIconCls() {
		if (leaf) {
			return "iconCls_layout_content";
		} else {
			return null;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuVO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuVO> children) {
		this.children = children;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
