package vn.tcx.dw.database.utils;

public class CommonUtils {

    public static boolean isEmpty(CharSequence value) {
        return value == null || value.length() == 0;
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static String notEmpty(String value) {
        return value == null ? "" : value;
    }
    
    public static String truncateString(String str, int maxLength) {
        if (str != null && str.length() > maxLength) {
            return str.substring(0, maxLength);
        }
        return str;
    }

    public static boolean equalObjects(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
//        if (o1.getClass() != o2.getClass()) {
//            return false;
//        }
        return o1.equals(o2);
    }

    public static String toString(Object object) {
        if (object == null) {
            return "";
        } else if (object instanceof String) {
            return (String) object;
        } else {
            return object.toString();
        }
    }

    public static boolean toBoolean(Object object) {
        return object != null && getBoolean(object.toString());
    }

    public static boolean getBoolean(String value) {
        return Boolean.parseBoolean(value);
    }

    public static boolean isEmptyTrimmed(String str) {
        return str == null || str.length() == 0 || str.trim().length() == 0;
    }

}
