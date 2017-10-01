package com.windf.module.sso.modle;

import com.windf.core.frame.Orderable;

public interface LoginObserver extends Orderable {
	void login();
	void logout();
}
