package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public abstract class AbstractType {
	Comment comment;
	List<Annotation> annotations;

	/**
	 * setAnnotations
	 * @param annotations
	 */
	void setAnnotations(List<String> annotationsCodes) {
		List<Annotation> annotations = null;
		
		if (!CollectionUtils.isEmpty(annotationsCodes)) {
			 annotations = new ArrayList<Annotation>();
			 
			 for (String codes : annotationsCodes) {
				 Annotation annotation = new Annotation(codes);
				 annotations.add(annotation);
			}
		}
		
		this.annotations = annotations;
	}
	
	/**
	 * get annotation codes
	 * @param tabCount 
	 * @return
	 */
	List<String> getAnnotations(int tabCount) {
		List<String> result = new ArrayList<String>(); 
		if (!CollectionUtils.isEmpty(annotations)) {
			 for (Annotation annotation : annotations) {
				 result.add(annotation.write(tabCount));
			}
		}
		return result;
	}
	
	/**
	 *  get comment codes
	 * @return
	 */
	List<String> getComment() {
		List<String> result = new ArrayList<String>(); 
		if (comment != null) {
			result.addAll(comment.write());
		}
		return result;
	}
}
