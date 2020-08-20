package cn.scau.springcloud.domain;

import cn.scau.springcloud.domain.constant.ResultCode;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;



0:申请中  100:工单中含有无效座标  
200:安全扫描失败  
210:安全扫描通过  
300:审核通过 310:审核不通过  400:上传成功  410:上传失败
/**
 * return Result
 * @author vincelin
 */

@Data
public class Result<R> implements Serializable {


    private static final long serialVersionUID = 8907036893458450182L;

    /**
     * 接口执行成功失败标识
     */
    private boolean success;

    /**
     * 结果码，成功为0，系统错误异常为-1。其它业务code在业务模块里面定义。
     */
    private int code;

    /**
     * 结果数据
     */
    private R result;

    /**
     * 错误或者提示信息
     */
    private String msg;

    public Result(boolean success, int code, R result, String msg) {
        this.success = success;
        this.result = result;
        this.code = code;
        this.msg = msg;
    }

    /**
     * Result对象是否含有返回值校验
     *
     * @return boolean
     */
    public boolean hasSuccessValue() {
        return this.isSuccess() && this.getResult() != null;
    }

    /**
     * 成功返回对象
     *
     * @param result Object
     * @return Result
     */
    public static <R> Result<R> successResult(R result) {
        return new Result<R>(true, ResultCode.BaseCode.SUCCESS, result, "");
    }

    public static <R> Result<R> successResult(R result, String message) {
        return new Result<R>(true, ResultCode.BaseCode.SUCCESS, result, message);
    }

    public static <R> Result<R> errResult(Integer code, String msg) {
        return new Result<R>(false, code, null, msg);
    }

    public static <R> Result<R> errResult(Integer code, String msg, R result) {
        return new Result<R>(false, code, result, msg);
    }


    /**
     * 参数错误
     *
     * @return Result
     */
    public static <R> Result<R> argsErrResult() {
        return new Result<R>(false, ResultCode.BaseCode.ARGS_ERROR, null, "args error！");
    }


    /**
     * 参数错误
     *
     * @return Result
     */
    public static <R> Result<R> argsErrResult(String msg) {
        return new Result<R>(false, ResultCode.BaseCode.ARGS_ERROR, null, msg);
    }

    /**
     * 系统错误
     *
     * @return Result
     */
    public static <R> Result<R> sysErrResult() {
        return new Result<R>(false, ResultCode.BaseCode.SYS_ERROR, null, "system error！");
    }

    /**
     * @param msg
     * @param <R>
     * @return
     */
    public static <R> Result<R> sysErrResult(String msg) {
        return new Result<R>(false, ResultCode.BaseCode.SYS_ERROR, null, msg);
    }

    /**
     * 异常错误
     *
     * @param t Throwable
     * @return Result
     */
    public static <R> Result<R> exceptionResult(Throwable t) {
        String msg = null;
        if (t instanceof UndeclaredThrowableException) {
            msg = ((UndeclaredThrowableException) t).getUndeclaredThrowable().getCause().toString();
        }
        if (msg == null) {
            msg = t.getMessage();
        }
        if (msg == null) {
            msg = t.getCause().getMessage();
        }
        return new Result<R>(false, ResultCode.BaseCode.EXCEPTION, null, msg);
    }

}