package com.windf.plugins.web.filter;

import com.windf.core.exception.CodeException;

public interface OnProjectStart {
	void whenStart() throws CodeException;
}
