package com.windf.module.development.modle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.windf.module.development.pojo.Parameter;
import com.windf.module.development.pojo.Return;

class Method {
	Comment comment;
	List<String> annotations;
	String name;
	List<CodeBlock> codeBlocks;
	List<Parameter> parameters;
	Return ret;
	Exception exception;

	Method (List<String> lines, Comment comment) {
		this.codeBlocks = new ArrayList<CodeBlock>();
		this.comment = comment;
		init(lines);
	}

	private void init(List<String> lines) {

		if (!CollectionUtils.isEmpty(lines)) {
			boolean inComments = false;
			
			List<String> commentLines = new ArrayList<String>();
			List<String> coderLines = new ArrayList<String>();
			
			name = lines.get(0);
			
			for (int no = 1; no < lines.size(); no ++) {
				String line = lines.get(no);
				
				if (Comment.isCommentStart(line)) {
					inComments = true;
					
					if (!CollectionUtils.isEmpty(coderLines)) {
						Comment comment = new Comment(commentLines);
						CodeBlock codeBlock = new CodeBlock(coderLines, comment);
						codeBlocks.add(codeBlock);
					}
				}
				
				if (inComments) {
					commentLines.add(line);
				} else {
					coderLines.add(line);
				}
				
				if (Comment.isCommentEnd(line)) {
					inComments = false;
				}
				
			}
			
			if (!CollectionUtils.isEmpty(coderLines)) {
				Comment comment = new Comment(commentLines);
				CodeBlock codeBlock = new CodeBlock(coderLines, comment);
				codeBlocks.add(codeBlock);
			}
		}
	}

	/**
	 * setAnnotations
	 * @param annotations
	 */
	void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}

	List<String> write() {
		List<String> result = new ArrayList<String>();
		
		result.addAll(comment.write());
		if (!CollectionUtils.isEmpty(annotations)) {
			result.addAll(annotations);
		}
		
		result.add(name);
		for (int i = 0; i < codeBlocks.size(); i++) {
			CodeBlock codeBlock = codeBlocks.get(i);
			result.addAll(codeBlock.write());
		}
		
		return result;
	}
}
