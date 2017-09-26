package com.windf.plugins.web.servlet;

import com.windf.core.exception.CodeException;

public interface OnProjectStart {
	void whenStart() throws CodeException;
}
