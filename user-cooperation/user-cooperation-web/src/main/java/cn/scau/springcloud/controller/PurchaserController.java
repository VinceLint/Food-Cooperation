package cn.scau.springcloud.controller;

import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.vo.ResultVO;
import cn.scau.springcloud.helper.CooperationHelper;
import cn.scau.springcloud.manager.CooperationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/purchaser/")
public class PurchaserController {

    @Resource
    private CooperationManager cooperationManager;

    @RequestMapping("seek")
    @ResponseBody
    public ResultVO seek(@RequestParam(required = false, defaultValue = "1") Integer page,
                         @RequestParam(required = false, defaultValue = "50") Integer pageSize,
                         @RequestParam Integer status){
        PageResult<CooperationDO> pageResult = cooperationManager.purchaserCooperation(page, pageSize, status);
        if (!pageResult.isSuccess()) {
            return ResultVO.error(pageResult.getCode(), pageResult.getMsg());
        }
        return ResultVO.listResult(CooperationHelper.transferCommonCooperationVOs(pageResult.getResults()),
                page, pageSize, pageResult.getTotal());
    }
}
