package com.windf.plugins.log;

public interface Logger {
	void error(String message);
	void warn(String message);
	void debug(String message);
	void info(String message);
	void error(String message, Throwable e);
	void warn(String message, Throwable e);
	void debug(String message, Throwable e);
	void info(String message, Throwable e);
	void error(Throwable e);
	void warn(Throwable e);
	void debug(Throwable e);
	void info(Throwable e);
}
