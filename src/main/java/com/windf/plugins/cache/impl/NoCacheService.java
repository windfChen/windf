package com.windf.plugins.cache.impl;

import com.windf.plugins.cache.CacheService;

public class NoCacheService implements CacheService {

	@Override
	public boolean put(String cacheBlack, String key, Object value) {
		return true;
	}

	@Override
	public boolean put(String cacheBlack, String key, Object value, int time) {
		return true;
	}

	@Override
	public Object get(String key) {
		return null;
	}
	
	@Override
	public Object get(String cacheBlack, String key) {
		return null;
	}

	@Override
	public boolean clean(String cacheBlack, String key) {
		return true;
	}

	@Override
	public boolean put(String key, Object val) {
		return true;
	}

	@Override
	public boolean put(String key, Object val, int expire) {
		return true;
	}

	@Override
	public boolean remove(String key) {
		return true;
	}

	@Override
	public boolean remove(String cacheBlock, String key) {
		return true;
	}

	@Override
	public boolean exists(String cacheKey) {
		return true;
	}

	@Override
	public boolean exists(String cacheBlock, String cacheKey) {
		return true;
	}

}
