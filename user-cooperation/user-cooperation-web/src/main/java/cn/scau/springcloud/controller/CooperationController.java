package cn.scau.springcloud.controller;

import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.request.CooperationReq;
import cn.scau.springcloud.domain.vo.CooperationVO;
import cn.scau.springcloud.domain.vo.ResultVO;
import cn.scau.springcloud.helper.CooperationHelper;
import cn.scau.springcloud.manager.CooperationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/cooperation/")
public class CooperationController {

    @Autowired
    private CooperationManager cooperationManager;


    @RequestMapping("publish")
    @ResponseBody
    public ResultVO publish(@RequestBody @Valid CooperationReq cooperationReq) {
        Result<Boolean> result = cooperationManager.publish(cooperationReq);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success(result.getResult());
    }

    @RequestMapping("listCommonMsg")
    @ResponseBody
    public ResultVO listCommonMsg(@RequestParam(required = false, defaultValue = "1") Integer page,
                                  @RequestParam(required = false, defaultValue = "50") Integer pageSize) {
        PageResult<CooperationDO> pageResult = cooperationManager.listCommonMsg(page, pageSize);
        if (!pageResult.isSuccess()) {
            return ResultVO.error(pageResult.getCode(), pageResult.getMsg());
        }
        return ResultVO.listResult(CooperationHelper.transferCommonCooperationVOs(pageResult.getResults()),
                page, pageSize, pageResult.getTotal());
    }

    @RequestMapping("commonMsg")
    @ResponseBody
    public ResultVO commonMsg(@RequestParam Integer id) {
        Result<CooperationDO> result = cooperationManager.commonMsg(id);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        CooperationVO cooperationVO = CooperationHelper.transferVO(result.getResult());
        return ResultVO.success(cooperationVO);
    }

    @RequestMapping("apply")
    @ResponseBody
    public ResultVO apply(@RequestParam Integer id) {
        Result<Boolean> result = cooperationManager.apply(id);
        if (!result.isSuccess()) {
            return ResultVO.error(result.getCode(), result.getMsg());
        }
        return ResultVO.success("申请成功！");
    }
}
