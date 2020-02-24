package cn.scau.springcloud.controller;

import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.request.CooperationReq;
import cn.scau.springcloud.domain.vo.ResultVO;
import cn.scau.springcloud.manager.CooperationManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/cooperation/")
public class CooperationController {

    @Autowired
    private CooperationManager cooperationManager;

    @RequestMapping("publish")
    @ResponseBody
    public ResultVO publish(@RequestBody @Valid CooperationReq cooperationReq){
        Result<Boolean> result= cooperationManager.publish(cooperationReq);
        if (!result.isSuccess()){
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }
}
