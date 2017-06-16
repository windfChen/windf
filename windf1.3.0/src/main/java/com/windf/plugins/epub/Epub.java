package com.windf.plugins.epub;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.windf.core.exception.EntityException;
import com.windf.core.file.FileUtil;
import com.windf.core.file.XmlFileUtil;
import com.windf.core.util.StringUtil;

public class Epub {
	public static final String TOC_FILE_NAME = "toc.ncx";
	public static final String OPS_PATH = "OPS/";
	private static final String FILTER_TITLES = "封面||书名页||版权页||出版说明||目录||封底";

	public static Epub loadEpub(String tocFilePath) {
		Epub reseult = null;
		try {
			reseult = XmlFileUtil.readXml2Object(new File(tocFilePath), Epub.class);
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return reseult;
	}
	
	public static Epub unzip(File epubFile, String targetPath) {
		Epub result = null;
		
		try {
			String unzipFolder = FileUtil.unzip(epubFile, targetPath);
			String tocFilePath = unzipFolder  + File.separator +  OPS_PATH + TOC_FILE_NAME; 
			File tocFile = new File(tocFilePath);
			if (tocFile.exists()) {
				result = Epub.loadEpub(tocFile.getPath());
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private String docTitle;
	private List<NavPoint> navMap;
	
	public void filter() {
		if (navMap != null) {
			Iterator<NavPoint> iterator = navMap.iterator();
			while (iterator.hasNext()) {
				NavPoint navPoint = iterator.next();
				if (StringUtil.isEmpty(navPoint.getTitle()) || FILTER_TITLES.contains(navPoint.getTitle())) {
					iterator.remove();
				}
			}
		}
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public List<NavPoint> getNavMap() {
		return navMap;
	}

	public void setNavMap(List<NavPoint> navMap) {
		this.navMap = navMap;
	}

}
