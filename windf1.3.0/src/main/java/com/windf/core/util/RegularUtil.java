package com.windf.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularUtil {
	public static boolean match(String s, String p) {
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}
}
