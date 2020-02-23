package cn.scau.springcloud.domain.vo;

import cn.scau.springcloud.domain.constant.ResultCode;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ResultVO {

    private int status = ResultCode.ResponseCode.SUCCESS;

    private String message = "";

    private Object data;

    public ResultVO() {

    }

    public ResultVO(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * 不关心分页信息的列表返回
     *
     * @param list 列表数据
     * @return
     */
    public static ResultVO listResult(List list) {
        int page = 1, limit = 0, total = 0;
        if (list != null) {
            limit = list.size();
            total = list.size();
        }
        return listResult(list, page, limit, total, null);
    }

    /**
     * 分页信息返回
     *
     * @param list  列表数据
     * @param page  当前页码
     * @param limit 一页显示条数
     * @param total 记录总条数
     * @return ResultVO
     */
    public static ResultVO listResult(List list, int page, int limit, int total) {
        return listResult(list, page, limit, total, null);
    }

    /**
     * 附带额外数据的分页信息返回
     *
     * @param list  列表数据
     * @param page  当前页码
     * @param limit 一页显示条数
     * @param total 记录总条数
     * @param extra 附加数据
     * @return ResultVO
     */
    public static ResultVO listResult(List list, int page, int limit, int total, Object extra) {
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("limit", limit);
        data.put("list", list == null ? Lists.newArrayList() : list);
        data.put("page", page);
        data.put("extra", extra);
        ResultVO result = new ResultVO();
        result.setData(data);
        return result;
    }

    public static ResultVO success(Object data) {
        ResultVO result = new ResultVO();
        result.setData(data);
        return result;
    }

    public static ResultVO success(String msg) {
        ResultVO result = new ResultVO();
        result.setMessage(msg);
        return result;
    }

    public static ResultVO success(Object data, String msg) {
        ResultVO result = new ResultVO();
        result.setData(data);
        result.setMessage(msg);
        return result;
    }

    public static ResultVO error(int status, String msg) {
        ResultVO result = new ResultVO();
        result.setStatus(status);
        result.setMessage(msg);
        return result;
    }

    public static ResultVO error(int status, String msg, Object data) {
        ResultVO result = new ResultVO();
        result.setStatus(status);
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

    public static ResultVO sysError(String msg) {
        return new ResultVO(ResultCode.ResponseCode.SYS_ERROR, msg, null);
    }

    public static ResultVO sysError() {
        return new ResultVO(ResultCode.ResponseCode.SYS_ERROR, "system error", null);
    }

    public static ResultVO success() {
        return new ResultVO(ResultCode.ResponseCode.SUCCESS, null, null);
    }

    public static ResultVO argsError(String msg) {
        return new ResultVO(ResultCode.ResponseCode.ARGS_ERROR, msg, null);
    }

    public static ResultVO exceptionError(String msg) {
        return new ResultVO(ResultCode.ResponseCode.EXCEPTION, msg, null);
    }

    public boolean isSuccess() {
        return ResultCode.ResponseCode.SUCCESS == this.status;
    }
}
