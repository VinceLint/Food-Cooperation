package cn.scau.springcloud.helper;

import javax.servlet.http.Cookie;

public class CookieHelper {
    public static String getToken(Cookie[] cookies) {
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
