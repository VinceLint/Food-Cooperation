package cn.scau.springcloud.controller;


import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationApplyDO;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.request.ApplyPassReq;
import cn.scau.springcloud.domain.vo.CooperationApplyVO;
import cn.scau.springcloud.domain.vo.ResultVO;
import cn.scau.springcloud.helper.CooperationHelper;
import cn.scau.springcloud.manager.CooperationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/boss/")
public class BossController {
    @Resource
    private CooperationManager cooperationManager;

    @RequestMapping("seek")
    @ResponseBody
    public ResultVO seek(@RequestParam(required = false, defaultValue = "1") Integer page,
                         @RequestParam(required = false, defaultValue = "50") Integer pageSize,
                         @RequestParam Integer status, @RequestParam Integer applyStatus){
        PageResult<CooperationApplyVO> pageResult = cooperationManager.bossCooperation(page, pageSize, status, applyStatus);
        if (!pageResult.isSuccess()) {
            return ResultVO.error(pageResult.getCode(), pageResult.getMsg());
        }
        return ResultVO.listResult(pageResult.getResults(),
                page, pageSize, pageResult.getTotal());
    }

    @RequestMapping("pass")
    @ResponseBody
    public ResultVO pass(@RequestBody @Valid ApplyPassReq applyPassReq){
        Result<Boolean> result = cooperationManager.pass(applyPassReq.getCooperationId(), applyPassReq.getCooperatorId());
        if (!result.isSuccess()){
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success();
    }
}
