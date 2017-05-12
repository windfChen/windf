package com.windf.plugins.log;

public interface LogService {
	void error(String message);
	void warn(String message);
	void debug(String message);
	void info(String message);
	void error(String message, Throwable e);
	void warn(String message, Throwable e);
	void debug(String message, Throwable e);
	void info(String message, Throwable e);
}
