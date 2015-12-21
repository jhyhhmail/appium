package com.jhyhh.appium.utils;


public class BooleanUtils {

    /**
     * true|y|1|yes|on
     */
    public static boolean isTrue(String str) {

        return ("yes".equalsIgnoreCase(str) || "1".equalsIgnoreCase(str)
                || "true".equalsIgnoreCase(str) || "on".equalsIgnoreCase(str)
                || "y".equalsIgnoreCase(str));
    }

    public static boolean isTrue(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }
        if (obj instanceof Number) {
            return (((Number) obj).intValue() != 0);
        }
        String str = String.valueOf(obj);
        return isTrue(str);
    }

    /**
     * true|y|1|yes|on
     */
    public static boolean isTrue(String str, boolean defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        return isTrue(str);
    }
}
