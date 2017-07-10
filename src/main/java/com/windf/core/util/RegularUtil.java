package com.windf.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
	public static boolean match(String str, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(str);
		return matcher.matches();
	}
}
