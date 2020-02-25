package cn.scau.springcloud.domain;

import cn.scau.springcloud.domain.constant.ResultCode;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

@Data
public class PageResult<E> implements Serializable {


    private static final long serialVersionUID = -5219209935774135120L;
    /**
     * 接口执行成功失败标识
     */
    private boolean success;

    /**
     * 结果码，成功为0，系统错误异常为-1。其它业务code在业务模块里面定义。
     */
    private int code;

    /**
     * 结果数据列表，有序列表
     */
    private List<E> results;

    /**
     * 记录总条数
     */
    private int total;

    /**
     * 错误或者提示信息
     */
    private String msg;


    public PageResult() {
        this.success = true;
    }

    /**
     * 成功返回对象
     *
     * @param totalCount 符合条件的结果记录总数
     * @param resultList 当前页的结果列表
     * @param <E>        泛型定义
     * @return PageResult
     */
    public static <E> PageResult<E> successResult(int totalCount, List<E> resultList) {
        PageResult<E> pageResult = new PageResult<E>();
        pageResult.setSuccess(true);
        pageResult.setCode(ResultCode.BaseCode.SUCCESS);
        pageResult.setMsg("Success");
        pageResult.setTotal(totalCount);
        pageResult.setResults(resultList == null ? new ArrayList<E>() : resultList);
        return pageResult;
    }

    /**
     * 失败返回对象
     *
     * @param code    错误码
     * @param message 错误提示
     * @param <E>     泛型
     * @return PageResult
     */
    public static <E> PageResult<E> errorResult(int code, String message) {
        PageResult<E> pageResult = new PageResult<E>();
        pageResult.setSuccess(false);
        pageResult.setCode(code);
        pageResult.setMsg(message);
        pageResult.setTotal(0);
        pageResult.setResults(new ArrayList<E>());
        return pageResult;
    }

    /**
     * 失败返回对象
     *
     * @param <E> 泛型
     * @return PageResult
     */
    public static <E> PageResult<E> argErrResult() {
        return errorResult(ResultCode.BaseCode.ARGS_ERROR, "args error!");
    }

    /**
     * 失败返回对象
     *
     * @param <E> 泛型
     * @return PageResult
     */
    public static <E> PageResult<E> sysErrResult() {
        return errorResult(ResultCode.BaseCode.SYS_ERROR, "system error!");
    }

    /**
     * 失败返回对象
     *
     * @param <E> 泛型
     * @return PageResult
     */
    public static <E> PageResult<E> sysErrResult(String message) {
        return errorResult(ResultCode.BaseCode.SYS_ERROR, message);
    }

    /**
     * 异常返回
     *
     * @param t   异常
     * @param <E> 泛型
     * @return PageResult
     */
    public static <E> PageResult<E> exceptionResult(Throwable t) {
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
        return errorResult(ResultCode.BaseCode.EXCEPTION, msg);
    }

    public boolean hasSuccessValue() {
        return this.isSuccess() && this.getResults() != null && this.getResults().size() > 0;
    }

    public Integer getTotalPage(Integer pageSize) {
        if (pageSize == null || pageSize == 0 || total == 0) {
            return 0;
        }
        return total / pageSize + (total % pageSize > 0 ? 1 : 0);
    }

}