//package cn.scau.springcloud.context;
//
//import cn.hutool.system.UserInfo;
//
///**
// * current user context
// * @author vincelin
// * @date 2020/2/16 5:19 PM
// */
//public class UserSessionContextHolder {
//    private static final ThreadLocal<UserInfo> USER_INFO_THREAD =
//            new ThreadLocal<>();
//
//
//    public static UserInfo getUserInfo(){
//        return USER_INFO_THREAD.get();
//    }
//
//    public static Long getEmployeeId(){
//        UserInfo userInfo = getUserInfo();
//        return userInfo == null ? -1 : userInfo.getEmployeeId();
//    }
//
//    public static Long getUserId(){
//        UserInfo userInfo = getUserInfo();
//        if(userInfo == null){
//            return -1L;
//        }
//        if(StringUtils.isBlank(userInfo.getUserId())){
//            return -1L;
//        }
//        return Long.parseLong(userInfo.getUserId());
//    }
//
//    public static String getUserName(){
//        UserInfo userInfo = getUserInfo();
//        return userInfo == null ? "" : userInfo.getUserName();
//    }
//
//    public static String getNickName(){
//        UserInfo userInfo = getUserInfo();
//        return userInfo == null ? "" : userInfo.getNickName();
//    }
//
//    public static String getEmail(){
//        UserInfo userInfo = getUserInfo();
//        return userInfo == null ? "" : userInfo.getEmail();
//    }
//
//    public static void clearAllContext() {
//        USER_INFO_THREAD.remove();
//    }
//
//    public static void setUserInfo(UserInfo userInfo){
//        USER_INFO_THREAD.set(userInfo);
//    }
//}
