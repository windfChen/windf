package com.windf.core.util;

import com.whaty.framework.common.thread.AsynchronousExecutorHolder;

public class ThreadUtil {
	    private static AsynchronousExecutorHolder asynchronousExecutorHolder;
	    
	    public static void execute(Runnable runnable) {
	    	asynchronousExecutorHolder.execute(runnable);
	    }
}
