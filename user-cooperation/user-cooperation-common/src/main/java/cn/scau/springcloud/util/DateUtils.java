package cn.scau.springcloud.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String date2Str(Date date) {
        return date2String(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String date2String(Date date, String formatter) {
        if (formatter != null && !"".equals(formatter)) {
            if (date == null) {
                return null;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat(formatter);
                return date2String(date, (DateFormat)sdf);
            }
        } else {
            return null;
        }
    }

    public static String date2String(Date date, DateFormat formatter) {
        return formatter.format(date);
    }
}
