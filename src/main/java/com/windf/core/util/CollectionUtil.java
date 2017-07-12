package com.windf.core.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;

public class CollectionUtil {
	public static boolean isNotEmpty(Collection<? extends Object> collection) {
		return !CollectionUtils.isEmpty(collection);
	}

	public static boolean isNotEmpty(Map<? extends Object, ? extends Object> result) {
		return result != null && result.size() > 0;
	}
}
