package cn.scau.springcloud.controller;

import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.request.ApplyEndReq;
import cn.scau.springcloud.domain.vo.CooperationApplyVO;
import cn.scau.springcloud.domain.vo.ResultVO;
import cn.scau.springcloud.handler.ValidatorResultHandler;
import cn.scau.springcloud.helper.CooperationHelper;
import cn.scau.springcloud.manager.CooperationManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/purchaser/")
public class PurchaserController {

    @Resource
    private CooperationManager cooperationManager;

    @RequestMapping("seek")
    @ResponseBody
    public ResultVO seek(@RequestParam(required = false, defaultValue = "1") Integer page,
                         @RequestParam(required = false, defaultValue = "50") Integer pageSize,
                         @RequestParam(required = false) Integer status,
                         @RequestParam(required = false) Integer applyStatus){
        PageResult<CooperationDO> pageResult = cooperationManager.purchaserCooperation(page, pageSize, status, applyStatus);
        if (!pageResult.isSuccess()) {
            return ResultVO.error(pageResult.getCode(), pageResult.getMsg());
        }
        return ResultVO.listResult(CooperationHelper.transferCommonCooperationVOs(pageResult.getResults()),
                page, pageSize, pageResult.getTotal());
    }

    @RequestMapping("listFinish")
    @ResponseBody
    public ResultVO listFinish(@RequestParam(required = false, defaultValue = "1") Integer page,
                         @RequestParam(required = false, defaultValue = "50") Integer pageSize,
                         @RequestParam(required = false) Integer status,
                         @RequestParam(required = false) Integer applyStatus){
        PageResult<CooperationApplyVO> pageResult = cooperationManager.purchaserFinishCooperation(page, pageSize, status, applyStatus);
        if (!pageResult.isSuccess()) {
            return ResultVO.error(pageResult.getCode(), pageResult.getMsg());
        }
        return ResultVO.listResult(pageResult.getResults(), page, pageSize, pageResult.getTotal());
    }

    @RequestMapping("end")
    @ResponseBody
    public ResultVO end(@RequestBody @Valid ApplyEndReq applyEndReq, BindingResult bindingResult){
        ResultVO validRst = ValidatorResultHandler.handler(bindingResult);
        if (!validRst.isSuccess()) {
            return validRst;
        }
        Result<Boolean> result = cooperationManager.purchaserEnd(applyEndReq.getCooperationId(), applyEndReq.getComment(),
                applyEndReq.getScore());
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(Boolean.TRUE);
    }
}
