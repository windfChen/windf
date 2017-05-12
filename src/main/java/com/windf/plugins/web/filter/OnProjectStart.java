package com.windf.plugins.web.filter;

import com.windf.core.exception.SystemException;

public interface OnProjectStart {
	void whenStart() throws SystemException;
}
