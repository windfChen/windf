package com.windf.plugins.epub;

import java.util.ArrayList;
import java.util.List;

public class NavPoint {
	private String id;
	private String navLabel;
	private Integer playOrder;
	private Content content;
	private List<NavPoint> navMap;

	public NavPoint() {

	}
	
	public String getTitle() {
		return this.navLabel;
	}
	
	public List<NavPoint> getChildren() {
		return this.navMap;
	}

	public String getId() {
		return id;
	}

	public String getNavLabel() {
		return navLabel;
	}

	public Integer getPlayOrder() {
		return playOrder;
	}

	public Content getContent() {
		return content;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNavLabel(String navLabel) {
		this.navLabel = navLabel;
	}

	public void setPlayOrder(Integer playOrder) {
		this.playOrder = playOrder;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public NavPoint getNavPoint() {
		return null;
	}

	public void setNavPoint(NavPoint navPoint) {
		if (this.navMap == null) {
			this.navMap = new ArrayList<NavPoint>();
		}
		this.navMap.add(navPoint);
	}

}
