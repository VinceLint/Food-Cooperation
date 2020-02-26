package cn.scau.springcloud.context;

import org.apache.commons.lang.StringUtils;

public class UserSessionContextHolder {
    private static final ThreadLocal<UserInfo> USER_INFO_THREAD =
            new ThreadLocal<>();


    public static UserInfo getUserInfo(){
        return USER_INFO_THREAD.get();
    }

    public static Integer getUserId(){
        UserInfo userInfo = getUserInfo();
        if(userInfo == null){
            return -1;
        }
        if(StringUtils.isBlank(userInfo.getId().toString())){
            return -1;
        }
        return userInfo.getId();
    }

    public static String getUserName(){
        UserInfo userInfo = getUserInfo();
        return userInfo == null ? "" : userInfo.getUsername();
    }

    public static String getEmail(){
        UserInfo userInfo = getUserInfo();
        return userInfo == null ? "" : userInfo.getEmail();
    }

    public static void clearAllContext() {
        USER_INFO_THREAD.remove();
    }

    public static void setUserInfo(UserInfo userInfo){
        USER_INFO_THREAD.set(userInfo);
    }
}
