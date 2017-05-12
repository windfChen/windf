package com.windf.core.util;

import java.text.ParseException;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {
	public static final String FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String SIMPLE_DATE = "yyyy-MM-dd";
    public static final String SMART_DATE = "MM-dd";
    public static final String YEAR_DATE = "yyyy";
    public static final String FULL_TIME = "HH:mm:ss";
    public static final String SIMPLE_TIME = "HH:mm";
    public static final String SMART_TIME = "mm:ss";
    public static final String[] FULL_DATE_TIME_Arr = new String[]{FULL_DATE_TIME, SIMPLE_DATE, SMART_DATE, FULL_TIME, YEAR_DATE, SIMPLE_TIME, SMART_TIME};
    public static final String[] SIMPLE_DATE_ARR = new String[]{SIMPLE_DATE};
    
    public static final Integer NINETYSECONDS = 90; //90 秒
    public static final Integer THREEMINS = 3 * 60; //3分钟（秒）
    public static final Integer FULLDAY = 60 * 60 * 24; //24小时 (秒)
    public static final Integer ONEHOUR = 60 * 60; //1小时 (秒)
    public static final Integer EXPIRTMILLIS = 30 * 60; //30分钟
    public static final Integer SMALLEXPIRTMILLIS = 10 * 60; //10分钟
    public static final Integer RESENDEMAILTIME = 5 * 60; //可重复发送邮件的周期 5分钟
    public static final Integer WEEKTIMES = 7 * 24 * 60 * 60; //一周 （秒）
    public static final Integer REMEMBERCOOKIES = Integer.MAX_VALUE;
    public static final long TENMINUTES = 600000; //10分钟（毫秒）
    
    /**
     * 校验是否为合法日期格式
     * @param str
     * @param type
     * @return
     */
    public static boolean isValidDate(String str, String[] type) {
        boolean convertSuccess=true;
        try {
            DateUtils.parseDate(str, type);
        } catch (ParseException e) {
            convertSuccess=false;
        }
        return convertSuccess;
    }
}
