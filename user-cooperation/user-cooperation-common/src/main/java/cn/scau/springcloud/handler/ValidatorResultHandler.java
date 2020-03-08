package cn.scau.springcloud.handler;

import cn.scau.springcloud.domain.vo.ResultVO;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 数据校验
 * @author vincelin
 * @date 2020/3/8 10:02 PM
 */

public class ValidatorResultHandler {
    public static ResultVO handler(Errors errors) {
        if (null != errors && errors.hasErrors()) {
            List<ObjectError> list = errors.getAllErrors();
            ObjectError oe = list.get(0);
            return ResultVO.argsError(oe.getDefaultMessage());
        } else {
            return ResultVO.success();
        }
    }
}
