package cn.scau.springcloud.manager.impl;

import cn.scau.springcloud.context.UserSessionContextHolder;
import cn.scau.springcloud.dao.CooperationApplyDao;
import cn.scau.springcloud.dao.CooperationDao;
import cn.scau.springcloud.domain.PageResult;
import cn.scau.springcloud.domain.Result;
import cn.scau.springcloud.domain.dto.UserDTO;
import cn.scau.springcloud.domain.entity.CooperationApplyDO;
import cn.scau.springcloud.domain.entity.CooperationDO;
import cn.scau.springcloud.domain.query.CooperationApplyQuery;
import cn.scau.springcloud.domain.query.CooperationQuery;
import cn.scau.springcloud.domain.request.CooperationReq;
import cn.scau.springcloud.domain.vo.CooperationApplyVO;
import cn.scau.springcloud.domain.vo.CooperationVO;
import cn.scau.springcloud.domain.vo.UserVO;
import cn.scau.springcloud.enums.CooperationApplyEnums;
import cn.scau.springcloud.enums.CooperationEnums;
import cn.scau.springcloud.manager.CooperationManager;
import cn.scau.springcloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CooperationManagerImpl implements CooperationManager {

    @Autowired
    private CooperationDao cooperationDao;

    @Autowired
    private CooperationApplyDao cooperationApplyDao;

    @Autowired
    private UserService userService;

    @Override
    public Result<Boolean> publish(CooperationReq cooperationReq) {
        CooperationDO cooperationDO = new CooperationDO();
        cooperationDO.setTitle(cooperationReq.getTitle());
        cooperationDO.setCompany(cooperationReq.getCompany());
        cooperationDO.setPublishTime(new Date());
        cooperationDO.setProvince(cooperationReq.getProvince());
        cooperationDO.setCity(cooperationReq.getCity());
        cooperationDO.setAddress(cooperationReq.getAddress());
        cooperationDO.setDetail(cooperationReq.getDetail());
        cooperationDO.setStatus(CooperationEnums.SEEK.getType());
        cooperationDO.setUserId(UserSessionContextHolder.getUserId());
        Result<Boolean> result = cooperationDao.insert(cooperationDO);
        if (!result.isSuccess()) {
            return Result.sysErrResult(result.getMsg());
        }
        return Result.successResult(Boolean.TRUE);
    }

    @Override
    public PageResult<CooperationDO> listCommonMsg(Integer page, Integer pageSize) {
        CooperationQuery query = new CooperationQuery();
        query.setStatus(CooperationEnums.SEEK.getType());
        query.setCurPage(page);
        query.setPageSize(pageSize);
        query.setWithCount(Boolean.TRUE);
        PageResult<CooperationDO> pageResult = cooperationDao.query(query);
        if (!pageResult.isSuccess()) {
            PageResult.errorResult(pageResult.getCode(), pageResult.getMsg());
        }
        return pageResult;
    }

    @Override
    public Result<CooperationDO> commonMsg(Integer id) {
        if (id == null || id <= 0) {
            return Result.argsErrResult();
        }
        CooperationQuery query = new CooperationQuery();
        query.setId(id);
        Result<CooperationDO> result = cooperationDao.queryOne(query);
        if (!result.isSuccess()) {
            return Result.errResult(result.getCode(), result.getMsg());
        }
        return Result.successResult(result.getResult());
    }

    @Override
    public Result<Boolean> apply(Integer id) {
        if (id == null || id <= 0) {
            return Result.argsErrResult("参数错误！");
        }
        // 检查该合作状态
        CooperationQuery cooperationQuery = new CooperationQuery();
        cooperationQuery.setId(id);
        Result<CooperationDO> cooperationDOResult = cooperationDao.queryOne(cooperationQuery);
        CooperationDO cooperationDO = cooperationDOResult.getResult();
        if (CooperationEnums.ON.getType() == cooperationDO.getStatus()) {
            return Result.errResult(cooperationDOResult.getCode(), "该合作已经被人抢先一步啦！");
        }
        if (CooperationEnums.NORMALFIN.getType() == cooperationDO.getStatus() ||
                CooperationEnums.ABNORMALFIN.getType() == cooperationDO.getStatus()) {
            return Result.errResult(cooperationDOResult.getCode(), "该合作已经结束啦！");
        }
        // 自己不能申请
        Integer userId = UserSessionContextHolder.getUserId();
        if (cooperationDOResult.getResult().getUserId() == userId) {
            return Result.errResult(cooperationDOResult.getCode(), "自己不能申请自己发布的消息哦");
        }
        // 检查是否重复申请
        CooperationApplyQuery cooperationApplyQuery = new CooperationApplyQuery();
        cooperationApplyQuery.setCooperationId(id);
        cooperationApplyQuery.setCooperatorId(UserSessionContextHolder.getUserId());
        Result<CooperationApplyDO> cooperationApplyDOResult = cooperationApplyDao.queryOne(cooperationApplyQuery);
        if (cooperationApplyDOResult.hasSuccessValue()) {
            return Result.errResult(cooperationApplyDOResult.getCode(), "您已经提交过了申请，请静候佳音");
        }
        // 提交申请
        CooperationApplyDO cooperationApplyDO = new CooperationApplyDO();
        cooperationApplyDO.setCooperationId(id);
        cooperationApplyDO.setCooperatorId(UserSessionContextHolder.getUserId());
        cooperationApplyDO.setStatus(CooperationApplyEnums.APPLY.getType());
        Result<CooperationApplyDO> result = cooperationApplyDao.insert(cooperationApplyDO);
        if (!result.isSuccess()) {
            return Result.errResult(result.getCode(), result.getMsg());
        }
        return Result.successResult(Boolean.TRUE);
    }

    @Override
    public PageResult<CooperationDO> purchaserCooperation(Integer page, Integer pageSize, Integer status, Integer applyStatus) {
        // 先查询该用户的所有合作关系
        CooperationApplyQuery cooperationApplyQuery = new CooperationApplyQuery();
        cooperationApplyQuery.setCooperatorId(UserSessionContextHolder.getUserId());
        cooperationApplyQuery.setStatus(applyStatus);
        PageResult<CooperationApplyDO> applyDOPageResult = cooperationApplyDao.query(cooperationApplyQuery);
        if (!applyDOPageResult.isSuccess()) {
            return PageResult.errorResult(applyDOPageResult.getCode(), applyDOPageResult.getMsg());
        }
        List<CooperationApplyDO> cooperationApplyDOList = applyDOPageResult.getResults();
        if (cooperationApplyDOList.size() <= 0) {
            return PageResult.successResult(applyDOPageResult.getTotal(), new ArrayList<>());
        }

        // 根据用户合作关系得到ids去查询对应的合作细则
        List<Integer> ids = new ArrayList<>();
        cooperationApplyDOList.forEach(cooperationApplyDO -> {
            ids.add(cooperationApplyDO.getCooperationId());
        });
        CooperationQuery cooperationQuery = new CooperationQuery();
        cooperationQuery.setIds(ids);
        cooperationQuery.setStatus(status);
        cooperationQuery.setCurPage(page);
        cooperationQuery.setPageSize(pageSize);
        cooperationQuery.setWithCount(Boolean.TRUE);
        PageResult<CooperationDO> pageResult = cooperationDao.query(cooperationQuery);
        if (!pageResult.isSuccess()) {
            return PageResult.errorResult(pageResult.getCode(), pageResult.getMsg());
        }
        return pageResult;

    }

    @Override
    public PageResult<CooperationApplyVO> bossCooperation(Integer page, Integer pageSize, Integer status, Integer applyStatus) {
        // 当前用户的所发布的对应状态的合作的id
        PageResult<CooperationDO> cooperationDOPageResult = cooperationDao.queryIds(UserSessionContextHolder.getUserId(), status);
        if (!cooperationDOPageResult.isSuccess()) {
            return PageResult.sysErrResult();
        }
        List<CooperationDO> cooperationDOList = cooperationDOPageResult.getResults();
        List<Integer> cooperationIds = new ArrayList<>();
        cooperationDOList.forEach(cooperationDO -> {
            cooperationIds.add(cooperationDO.getId());
        });
        // 查询对应的合作申请(按申请时间排好序)
        CooperationApplyQuery query = new CooperationApplyQuery();
        query.setStatus(applyStatus);
        query.setCurPage(page);
        query.setPageSize(pageSize);
        query.setWithCount(Boolean.TRUE);
        query.setCooperationIds(cooperationIds);
        PageResult<CooperationApplyDO> pageResult = cooperationApplyDao.query(query);
        // 组装结果
        List<CooperationApplyDO> cooperationApplyDOList = pageResult.getResults();
        List<CooperationApplyVO> cooperationApplyVOList = new ArrayList<>();
        cooperationApplyDOList.forEach(cooperationApplyDO -> {
            CooperationApplyVO cooperationApplyVO = new CooperationApplyVO();
            BeanUtils.copyProperties(cooperationApplyDO, cooperationApplyVO);
            UserDTO userDTO = userService.getUserById(cooperationApplyVO.getCooperatorId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userDTO, userVO);
            cooperationApplyVO.setUser(userVO);
            cooperationApplyVOList.add(cooperationApplyVO);
        });

        // 每个申请对应的合作
        cooperationApplyVOList.forEach(cooperationApplyVO -> {
            CooperationQuery query1 = new CooperationQuery();
            query1.setId(cooperationApplyVO.getCooperationId());
            Result<CooperationDO> result = cooperationDao.queryOne(query1);
            CooperationVO cooperationVO = new CooperationVO();
            if (!result.hasSuccessValue()) {
                cooperationApplyVO.setCooperationVO(cooperationVO);
            } else {
                BeanUtils.copyProperties(result.getResult(), cooperationVO);
                UserDTO userDTO = userService.getUserById(cooperationVO.getUserId());
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(userDTO, userVO);
                cooperationVO.setUser(userVO);
                cooperationApplyVO.setCooperationVO(cooperationVO);
            }
        });

        return PageResult.successResult(cooperationApplyVOList.size(), cooperationApplyVOList);
    }

    @Override
    public Result<Boolean> pass(Integer cooperationId, Integer cooperatorId) {
        // 查询对应的合作
        CooperationQuery query = new CooperationQuery();
        query.setId(cooperationId);
        Result<CooperationDO> result = cooperationDao.queryOne(query);
        if (!result.isSuccess()) {
            return Result.argsErrResult("找不到该合作记录");
        }
        CooperationDO cooperationDO = result.getResult();
        if (cooperationDO.getStatus() != CooperationEnums.SEEK.getType()) {
            return Result.argsErrResult("该合作已经不处于未合作状态");
        }
        cooperationDO.setStatus(CooperationEnums.ON.getType());
        cooperationDO.setPurchaserId(cooperatorId);
        Result<Boolean> result1 = cooperationDao.update(cooperationDO);
        if (!result1.isSuccess()) {
            return Result.errResult(result1.getCode(), result1.getMsg());
        }
        // 同步对应申请的状态
        CooperationApplyQuery query1 = new CooperationApplyQuery();
        query1.setCooperationId(cooperationId);
        query1.setCooperatorId(cooperatorId);
        Result<CooperationApplyDO> result2 = cooperationApplyDao.queryOne(query1);
        if (!result2.isSuccess()) {
            return Result.errResult(result2.getCode(), result2.getMsg());
        }
        CooperationApplyDO cooperationApplyDO = result2.getResult();
        cooperationApplyDO.setStatus(CooperationApplyEnums.PASS.getType());
        Result<Boolean> result3 = cooperationApplyDao.update(cooperationApplyDO);
        if (!result3.isSuccess()) {
            return Result.errResult(result3.getCode(), result3.getMsg());
        }
        return Result.successResult(Boolean.TRUE);
    }

    @Override
    public Result<Boolean> reject(Integer cooperationId, Integer cooperatorId, String comment) {
        CooperationApplyQuery query = new CooperationApplyQuery();
        query.setCooperationId(cooperationId);
        query.setCooperatorId(cooperatorId);
        Result<CooperationApplyDO> result = cooperationApplyDao.queryOne(query);
        if (!result.isSuccess()) {
            return Result.errResult(result.getCode(), result.getMsg());
        }
        CooperationApplyDO cooperationApplyDO = result.getResult();
        cooperationApplyDO.setStatus(CooperationApplyEnums.REFUSE.getType());
        cooperationApplyDO.setComment(comment);
        Result<Boolean> result1 = cooperationApplyDao.update(cooperationApplyDO);
        if (!result1.isSuccess()) {
            return Result.errResult(result1.getCode(), result1.getMsg());
        }
        return Result.successResult(Boolean.TRUE);
    }

    @Override
    @Transactional
    public Result<Boolean> bossEnd(Integer cooperationId, Integer cooperatorId, Integer status, String comment, Integer score) {
        // 将合作状态设置为结束
        CooperationQuery cooperationQuery = new CooperationQuery();
        cooperationQuery.setId(cooperationId);
        Result<CooperationDO> cooperationDOResult = cooperationDao.queryOne(cooperationQuery);
        if (!cooperationDOResult.hasSuccessValue()) {
            return Result.errResult(cooperationDOResult.getCode(), cooperationDOResult.getMsg());
        }
        CooperationDO cooperationDO = cooperationDOResult.getResult();
        cooperationDO.setStatus(status);
        Result<Boolean> result1 = cooperationDao.update(cooperationDO);
        if (!result1.isSuccess()) {
            return Result.errResult(result1.getCode(), result1.getMsg());
        }

        // 将老板对配送员的评论写入
        CooperationApplyQuery cooperationApplyQuery = new CooperationApplyQuery();
        cooperationApplyQuery.setCooperationId(cooperationId);
        cooperationApplyQuery.setCooperatorId(cooperatorId);
        Result<CooperationApplyDO> cooperationApplyDOResult = cooperationApplyDao.queryOne(cooperationApplyQuery);
        if (!cooperationApplyDOResult.hasSuccessValue()) {
            return Result.errResult(cooperationApplyDOResult.getCode(), cooperationApplyDOResult.getMsg());
        }
        CooperationApplyDO cooperationApplyDO = cooperationApplyDOResult.getResult();
        cooperationApplyDO.setScore(score);
        cooperationApplyDO.setComment(comment);
        cooperationApplyDO.setStatus(CooperationApplyEnums.FINISH.getType());
        Result<Boolean> result2 = cooperationApplyDao.update(cooperationApplyDO);
        if (!result2.isSuccess()) {
            return Result.errResult(result2.getCode(), result2.getMsg());
        }
        syncData(cooperationApplyDO.getCooperatorId());
        return Result.successResult(Boolean.TRUE);
    }

    @Override
    public PageResult<CooperationApplyVO> purchaserFinishCooperation(Integer page, Integer pageSize, Integer status, Integer applyStatus) {
        // 当前用户的所有结束的合作
        CooperationApplyQuery cooperationApplyQuery = new CooperationApplyQuery();
        cooperationApplyQuery.setCooperatorId(UserSessionContextHolder.getUserId());
        cooperationApplyQuery.setStatus(applyStatus);
        cooperationApplyQuery.setCurPage(page);
        cooperationApplyQuery.setPageSize(pageSize);
        cooperationApplyQuery.setWithCount(Boolean.TRUE);
        cooperationApplyQuery.setOrderBy("updated_at");
        PageResult<CooperationApplyDO> cooperationApplyDOResult = cooperationApplyDao.query(cooperationApplyQuery);
        if (!cooperationApplyDOResult.isSuccess()) {
            return PageResult.errorResult(cooperationApplyDOResult.getCode(), cooperationApplyDOResult.getMsg());
        }
        List<CooperationApplyDO> cooperationApplyDOList = cooperationApplyDOResult.getResults();
        List<CooperationApplyVO> cooperationApplyVOList = new ArrayList<>();
        cooperationApplyDOList.forEach(cooperationApplyDO -> {
            CooperationApplyVO cooperationApplyVO = new CooperationApplyVO();
            BeanUtils.copyProperties(cooperationApplyDO, cooperationApplyVO);

            // 查询对应的合作
            CooperationQuery query = new CooperationQuery();
            query.setId(cooperationApplyDO.getCooperationId());
            Result<CooperationDO> result = cooperationDao.queryOne(query);
            CooperationDO cooperationDO = result.getResult();
            CooperationVO cooperationVO = new CooperationVO();
            if (cooperationDO != null) {
                BeanUtils.copyProperties(cooperationDO, cooperationVO);
            }
            // 查询对应的雇主
            UserDTO userDTO = userService.getUserById(cooperationVO.getUserId());
            UserVO userVO = new UserVO();
            if (userDTO != null) {
                BeanUtils.copyProperties(userDTO, userVO);
            }
            cooperationVO.setUser(userVO);
            cooperationApplyVO.setCooperationVO(cooperationVO);
            cooperationApplyVOList.add(cooperationApplyVO);
        });
        return PageResult.successResult(cooperationApplyVOList.size(), cooperationApplyVOList);
    }

    @Override
    public Result<Boolean> purchaserEnd(Integer cooperationId, String comment, Integer score) {
        CooperationQuery query = new CooperationQuery();
        query.setId(cooperationId);
        Result<CooperationDO> cooperationDOResult = cooperationDao.queryOne(query);
        if (!cooperationDOResult.isSuccess()) {
            return Result.errResult(cooperationDOResult.getCode(), cooperationDOResult.getMsg());
        }
        CooperationDO cooperationDO = cooperationDOResult.getResult();
        cooperationDO.setComment(comment);
        cooperationDO.setScore(score);
        Result<Boolean> result = cooperationDao.update(cooperationDO);
        if (!result.isSuccess()) {
            return Result.errResult(result.getCode(), result.getMsg());
        }
        syncData(cooperationDO.getUserId());
        return Result.successResult(Boolean.TRUE);
    }

    /**
     * 同步用户分数
     */
    private void syncData(Integer userId) {
        int total = 0;
        Result<List<Integer>> cooperationScoreListResult = cooperationDao.getScoreList(userId);
        List<Integer> cooperationScoreList = cooperationScoreListResult.getResult();
        for (Integer cScore : cooperationScoreList) {
            total += cScore;
        }
        System.out.println("total1: " + total);
        Result<List<Integer>> cooperationApplyScoreListResult = cooperationApplyDao.getScoreList(userId);
        List<Integer> cooperationApplyScoreList = cooperationApplyScoreListResult.getResult();
        for (Integer caScore : cooperationApplyScoreList) {
            total += caScore;
        }
        System.out.println("total2: " + total);
        if (cooperationScoreList.size() + cooperationApplyScoreList.size() > 0) {
            float score = ((float) total) / (cooperationScoreList.size() + cooperationApplyScoreList.size());
            // 保留2位小数
            String ss = String.format("%1.2f", score);
            System.out.println("ss: " + ss);
            userService.syncScore(userId, Float.parseFloat(ss));
        }
    }
}
