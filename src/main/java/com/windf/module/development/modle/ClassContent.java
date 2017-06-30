package com.windf.module.development.modle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public abstract class ClassContent {
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
	 * @return
	 */
	List<String> getAnnotations() {
		List<String> result = new ArrayList<String>(); 
		if (!CollectionUtils.isEmpty(annotations)) {
			 for (Annotation annotation : annotations) {
				 result.add(annotation.write(1));
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
