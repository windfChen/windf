package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.List;

public class CodeBlock extends AbstractType {
	public static final int PARAMS_BLOCK_INDEX = Integer.MIN_VALUE;
	public static final int RETURN_BLOCK_INDEX = Integer.MAX_VALUE;
	public static final int NORMAL_BLOCK_INDEX = 0;

	private int serial = 0;
	private List<String> codes;
	
	public CodeBlock (List<String> codes) {
		this.codes = codes;
	}
	
	public void setSerial(int serial) {
		this.serial = serial;
	}
	
	public int getSerial() {
		return this.serial;
	}

	/**
	 * write code
	 * @return
	 */
	List<String> write() {
		List<String> result = new ArrayList<String>();
		
		if (comment != null) {
			result.addAll(comment.write());
		}
		result.addAll(codes);
		
		return result;
	}
}
