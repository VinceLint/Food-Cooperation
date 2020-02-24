package cn.scau.springcloud.util;

public class StringUtil {
    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean isTrimEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    public static boolean isNotTrimEmpty(String string) {
        return !isTrimEmpty(string);
    }

    public static String trim(String string) {
        return string == null ? "" : string.trim();
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        } else {
            return str1.equals(str2);
        }
    }
}
