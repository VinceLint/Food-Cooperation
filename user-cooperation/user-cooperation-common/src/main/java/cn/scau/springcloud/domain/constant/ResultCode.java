package cn.scau.springcloud.domain.constant;

/**
 * return result code
 * @author vincelin
 */

public interface ResultCode {
    interface BaseCode {
        int SUCCESS = 0;        //执行成功
        int SYS_ERROR = -1;     //系统错误
        int ARGS_ERROR = -2;    //参数错误
        int EXCEPTION = -3;     //异常结果
    }

    interface ResponseCode {
        int SUCCESS = 1001;
        int SYS_ERROR = 500;
        int ARGS_ERROR = 400;
        int UNAUTHORIZED = 401;
        int FORBIDDEN = 403;
        int OK = 200;
        int EXCEPTION = 0;
    }

    interface LoginCode {
        int SUCCESS = 1001;//登录成功
        int USER_NOT_EXIST = 1002;//账号不存在
        int PWD_ERROR = 1003;//密码错误
        int CAPTCHA_ERROR = 1004;//验证码错误
        int NOT_AUDIT = 1005;//未审核过的商家
        int UPDATE_TOKEN_ERROR = 1006;//更新记住密码token错误
        int NOT_AGREE_CONTRACT = 1007;//未确认合约
    }

    interface OrderCode {
        int BUZ_FAILED = 2001;  //业务失败
    }
}
