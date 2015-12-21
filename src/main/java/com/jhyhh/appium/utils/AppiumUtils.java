package com.jhyhh.appium.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppiumUtils {

	private static final SimpleDateFormat sdf = new SimpleDateFormat();

	public static String getFileName(java.util.Date date, String pattern) {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}

	public static String getFileName(String pattern) {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(new Date());
			return str;
		}
	}
	
	public static String getPNGFileName() {
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern("yyyyMMddHHmmssSSS");
			str = sdf.format(new Date())+".png";
			return str;
		}
	}
}
