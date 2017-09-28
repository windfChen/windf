package com.windf.plugins.web.session;

import javax.servlet.http.HttpSession;

import com.windf.core.frame.session.Session;

public class WebSession implements Session{
	
	private HttpSession session;
	
	public WebSession(HttpSession session) {
		this.session = session;
	}

	@Override
	public Object get(String key) {
		return session.getAttribute(key);
	}

	@Override
	public boolean set(String key, Object value) {
		boolean result = true;
		
		Object o = session.getAttribute(key);
		if (o == null || o.equals(value)) {
			result = false;
		}

		session.setAttribute(key, value);
		
		return result;
	}

	@Override
	public void invalidate() {
		session.invalidate();
	}

}