package com.windf.core.frame.session;

import com.windf.core.exception.CodeException;

public class SessionContext {

	private static ThreadLocal<Session> sessionThread = new ThreadLocal<>();
	
	public static void start(Session session) {
		sessionThread.set(session);
	}
	
	public static void end(Session session) {
		sessionThread.set(null);
	}
	
	public static Object get(String key) throws CodeException{
		Session session = sessionThread.get();
		if (session == null) {
			throw new CodeException("没有初始化session");
		}
		return session.get(key);
	}
	
	public static void set(String key, Object value) throws CodeException {
		Session session = sessionThread.get();
		if (session == null) {
			throw new CodeException("没有初始化session");
		}
		session.set(key, value);
	}
	
	public static void invalidate() throws CodeException {
		Session session = sessionThread.get();
		if (session == null) {
			throw new CodeException("没有初始化session");
		}
		session.invalidate();
	}
	
}
